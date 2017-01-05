/*
 * Copyright [2015] Paypal Software Foundation
 */
package com.yyh.thread.multthread;

import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * @author yuhyang
 *
 */
public class DummyTask implements Callable<String> {

    /*
     * (non-Javadoc)
     * 
     * @see java.util.concurrent.Callable#call()
     */
    @Override
    public String call() throws Exception {
        System.out.println("run DummyTask");
        return UUID.randomUUID().toString();
    }

}
