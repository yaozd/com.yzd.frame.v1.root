package com.yzd.logging.component;

import com.yzd.logging.component.RunnableDecorator;

public class RunnableWrapper {
    public static Runnable wrap(Runnable runnable) {
        return new RunnableDecorator(runnable);
    }
}
