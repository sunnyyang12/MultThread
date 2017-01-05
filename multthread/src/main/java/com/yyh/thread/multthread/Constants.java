/*
 * Copyright [2015] Paypal Software Foundation
 */
package com.yyh.thread.multthread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author yuhyang
 *
 */
public class Constants {

    public static BlockingQueue<TaskElem> workerqueue = new LinkedBlockingQueue<TaskElem>();

    public static void putElement(TaskElem e) throws InterruptedException {
        workerqueue.put(e);
    }

    public static TaskElem getElement() throws InterruptedException {
        return workerqueue.take();
    }

}
