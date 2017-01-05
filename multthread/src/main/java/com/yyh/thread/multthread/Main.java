/*
 * Copyright [2015] Paypal Software Foundation
 */
package com.yyh.thread.multthread;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author yuhyang
 *
 */
public class Main {

    public static Map<UUID, Producer> uuidProducerMap = new HashMap<UUID, Producer>();

    /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

        for(int i = 0; i < 5; i++) {
            Producer producer = new Producer();
            new Thread(producer).start();
        }

        Consumer consumer = new Consumer();
        consumer.setWorkerqueue(Constants.workerqueue);
        new Thread(consumer).start();

    }

}
