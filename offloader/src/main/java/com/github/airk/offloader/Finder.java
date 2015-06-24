package com.github.airk.offloader;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.WebView;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kevin on 15/4/16.
 * <p/>
 * Find, check, replace and load the result into WebView.
 */
final class Finder implements Runnable {
    private static final String TAG = "Finder";
    private static final boolean DEBUG = true;
    private static String[] PREFIXES = new String[]{
            "http://",
            "https://"
    };
    private final WebView webView;
    private final File indexUrl;
    private final String localOfflineResourceDir;
    private final MatchFactory matcher;
    private Handler handler;
    private int hitCount = 0;

    public Finder(WebView webView, File indexUrl, String dir, MatchFactory matcher) {
        handler = new Handler(Looper.getMainLooper());
        this.webView = webView;
        this.indexUrl = indexUrl;
        this.localOfflineResourceDir = dir;
        this.matcher = matcher;
    }

    @Override
    public void run() {
        boolean isInterrupt;
        if (!indexUrl.exists()) {
            Log.e(TAG, "File " + indexUrl.getPath() + " don't exist.");
            return;
        }
        try {
            String source = Utils.getStringFromFile(indexUrl.getAbsolutePath());
            String[] targets = findAll(source);
            if (targets == null) {
                if (DEBUG) {
                    Log.e(TAG, "No one match at all.");
                }
                return;
            }
            for (String target : targets) {
                isInterrupt = Thread.currentThread().isInterrupted();
                if (isInterrupt) {
                    if (DEBUG) {
                        Log.d(TAG, "Finder interrupted by user.");
                    }
                    return;
                }
                if (DEBUG) {
                    Log.d(TAG, "found: " + target);
                }

                String fixed = target.toLowerCase();
                for (String prefix : PREFIXES) {
                    if (fixed.startsWith(prefix)) {
                        fixed = fixed.replace(prefix, "");
                        break;
                    }
                }
                String[] dirsPath = fixed.split(File.separator);
                if (dirsPath.length <= 1) {
                    if (DEBUG) {
                        Log.e(TAG, "Skip it: " + target);
                    }
                    continue;
                }
                StringBuilder replace = new StringBuilder();
                String dir = localOfflineResourceDir;
                for (int depth = 0; depth < dirsPath.length; depth++) {
                    String path = dirsPath[depth];
                    if (depth == dirsPath.length - 1) {
                        path = matcher.match(path);
                    }
                    File file = new File(dir, path);
                    if (!file.exists()) {
                        if (DEBUG) {
                            Log.e(TAG, "local resource not found: " + file.getAbsolutePath());
                        }
                        break;
                    }
                    if (depth == dirsPath.length - 1) { //hit aimFile, such as main.css?v=19293581
                        replace.append(path);
                        source = replaceExact(target, replace.toString(), source);
                        hitCount++;
                    } else {
                        replace.append(path).append(File.separator);
                        dir += (File.separator + path);
                    }
                }
            }
            final String resultHtml = source;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    webView.loadDataWithBaseURL("file://" + localOfflineResourceDir, resultHtml, "text/html", "utf-8", null);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (DEBUG) {
            Log.d(TAG, "Finder finished.");
        }
    }

    private String[] findAll(CharSequence cs) {
        Pattern p = Pattern.compile("(?<=(\\bhref=\\\"|\\bsrc=\\\")).*?(?=\\\")");
        Matcher m = p.matcher(cs);
        ArrayList<String> ret = new ArrayList<>();
        while (m.find()) {
            ret.add(m.group());
        }
        if (ret.size() == 0) {
            return null;
        } else {
            String[] a = new String[ret.size()];
            return ret.toArray(a);
        }
    }

    /**
     * 返回替换后的字符串
     *
     * @param target  需要替换的目标字符串
     * @param replace 需要替换成的字符串
     * @param content 内容
     * @return 替换后的内容
     */
    private String replaceExact(String target, String replace, CharSequence content) {
        Pattern p = Pattern.compile(Pattern.quote(target));
        Matcher m = p.matcher(content);
        String ret = content.toString();
        if (m.find()) {
            ret = m.replaceFirst(replace);
        }
        return ret;
    }

}
