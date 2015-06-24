package com.github.airk.democollection.webview;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kevin on 15/4/13.
 */
public class OfflineLoadKit {
    public static String[] findAll(CharSequence cs) {
//        Pattern p = Pattern.compile("(?<=\\bhref=\\\"(https://|http://)).*?(?=(\\/\\\"|\\\"))");
//        Pattern p = Pattern.compile("(?<=\\bhref=\\\").*?(?=(\\/\\\"|\\\"))");
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

    public static String[] findContainer(CharSequence cs) {
        Pattern p = Pattern.compile("(\\bhref=\\\"(https://|http://)).+?\\\"");
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
    public static String replaceExact(String target, String replace, CharSequence content) {
        Pattern p = Pattern.compile("\\b" + target + "\\b");
        Matcher m = p.matcher(content);
        if (m.find()) {
            return m.replaceFirst(replace);
        }
        return null;
    }
}
