/*
 * Copyright [2015] Paypal Software Foundation
 */
package com.yyh.thread.multthread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author yuhyang
 *
 */
// simulator all a lot kinds of request
public class Producer implements Runnable {

    public Object lock = new Object();
    private List<TaskElem> taskElemList = new ArrayList<TaskElem>();

    public void produceTask() throws InterruptedException {
        CountDownLatch allResultReady = new CountDownLatch(2);
        for(int i = 0; i < 2; i++) {
            TaskElem taskEle = new TaskElem();
            taskElemList.add(taskEle);
            taskEle.setResultReady(allResultReady);
            System.out.println("begin produce message");
            taskEle.setMessage("thread from " + Thread.currentThread().getName() + " thread ID"
                    + Thread.currentThread().getId() + " element i is:" + i);
            synchronized(lock) {
                Constants.putElement(taskEle);
                System.out.println("put  message in queue" + "Constants.workerqueue.size()"
                        + Constants.workerqueue.size());

            }

        }

        allResultReady.await();
        updateStatus();
        // Main.uuidProducerMap.put(uuid, this);
    }

    public void updateStatus() throws InterruptedException {
        System.out.println("current Thread" + Thread.currentThread().getName() + " update ..");
        for(TaskElem taskElem: taskElemList) {
            System.out.println("update" + taskElem.getResult());
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        try {
            produceTask();

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
