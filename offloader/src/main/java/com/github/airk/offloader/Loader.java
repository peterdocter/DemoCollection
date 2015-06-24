package com.github.airk.offloader;

import java.util.concurrent.Future;

/**
 * Created by kevin on 15/4/16.
 * <p/>
 * Future Wrapper
 */
public class Loader {
    private final Future<?> future;

    public Loader(Future<?> future) {
        this.future = future;
    }

    /**
     * Cancel WebView offline load request
     */
    public void cancel() {
        future.cancel(true);
    }
}
