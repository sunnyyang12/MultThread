/*
 * Copyright [2015] Paypal Software Foundation
 */
package com.yyh.thread.multthread;

import java.util.List;

/**
 * @author yuhyang
 *
 */
public class MyTask implements Runnable {
    private final List<Integer> sharedObject;
    String name = "T1";// Initializing with T1

    public MyTask(List<Integer> sharedObject) {
        this.sharedObject = sharedObject;
    }

    public void run() {

        synchronized(sharedObject) {
            while(true) {// Or use a counter how many times to do the job
                if(!name.equals(Thread.currentThread().getName())) {
                    try {
                        sharedObject.wait();// Let other Threads wait
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(name.equals(Thread.currentThread().getName())) {
                    int value = sharedObject.remove(0).intValue();
                    sharedObject.add(new Integer(++value));
                    System.out.println(Thread.currentThread().getName() + " : " + sharedObject.get(0));
                    if(Thread.currentThread().getName().equals("T1")) {

                        name = "T2";// give lock to t2
                    } else if(Thread.currentThread().getName().equals("T2")) {

                        name = "T3";// give lock to t3
                    } else if(Thread.currentThread().getName().equals("T3")) {

                        name = "T1";// give lock to t1
                    }
                    // i--;
                    sharedObject.notifyAll();
                }

            }
        }

    }
}
