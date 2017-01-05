/*
 * Copyright [2015] Paypal Software Foundation
 */
package com.yyh.thread.multthread;

import java.util.concurrent.CountDownLatch;

/**
 * @author yuhyang
 *
 */
public class TaskElem {

    private String message;
    public CountDownLatch resultReady;

    public String result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CountDownLatch getResultReady() {
        return resultReady;
    }

    public void setResultReady(CountDownLatch resultReady) {
        this.resultReady = resultReady;
    }

    public void setResult(String result) {
        this.result = result;
        resultReady.countDown();
    }

    public String getResult() throws InterruptedException {
        resultReady.await();
        return result;
    }

}
