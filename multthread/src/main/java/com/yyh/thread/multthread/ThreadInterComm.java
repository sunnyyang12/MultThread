/*
 * Copyright [2015] Paypal Software Foundation
 */
package com.yyh.thread.multthread;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuhyang
 *
 */
public class ThreadInterComm {

    public static void main(String args[]) {

        List<Integer> sharedObject = new ArrayList<Integer>(1);
        sharedObject.add(new Integer(0));

        Runnable task = new MyTask(sharedObject);

        Thread t1 = new Thread(task, "T1");
        Thread t2 = new Thread(task, "T2");
        Thread t3 = new Thread(task, "T3");

        t1.start();
        t2.start();
        t3.start();

    }
}
