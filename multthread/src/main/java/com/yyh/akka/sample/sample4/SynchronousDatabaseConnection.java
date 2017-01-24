/*
 * Copyright [2015] Paypal Software Foundation
 */
package com.yyh.akka.sample.sample4;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

/**
 * @author yuhyang
 *
 */
public class SynchronousDatabaseConnection {

    public static class ConnectionLost extends RuntimeException {
        public ConnectionLost() {
            super("Database connectin lost");
        }
    }

    public static class RequestTimeOut extends RuntimeException {
        public RequestTimeOut() {
            super("database request time out");
        }
    }

    private Map<Long, Product> inMemoryDb;
    private final Random failureRandom = new Random();

    public SynchronousDatabaseConnection() {
        inMemoryDb = new HashMap<Long, Product>();
        inMemoryDb.put(1L, new Product(1, "Clean Socks", "Socks for both feet, that are clean"));
        inMemoryDb.put(2L, new Product(2, "Hat", "A thing you wear on your head"));
        inMemoryDb.put(3L, new Product(3, "Gloves", "Keeps your hands warm on cold days"));
    }

    public Optional<Product> findProduct(long id) {
        try {
            Thread.sleep(failureRandom.nextInt(500) + 200);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        final int random = failureRandom.nextInt(10);
        if(random == 0) {
            inMemoryDb = null;
            throw new ConnectionLost();
        } else if(random > 6) {
            throw new RequestTimeOut();
        }
        return Optional.ofNullable(inMemoryDb.get(id));
    }
}
