/*
 * Copyright [2015] Paypal Software Foundation
 */
package com.yyh.akka.sample.sample4;

import java.util.Optional;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

/**
 * @author yuhyang
 *
 */
public class DbActor extends AbstractActor {

    private final SynchronousDatabaseConnection connection;

    {
        this.connection = new SynchronousDatabaseConnection();
        receive(ReceiveBuilder.match(GetProduct.class, query -> getProduct(query.id)).build());
    }

    private void getProduct(long id) {
        final Optional<Product> product = connection.findProduct(id);
        sender().tell(new ProductResult(product), self());
    }

    public static Props props() {
        return Props.create(DbActor.class);
    }

    // protocol
    public static class GetProduct {
        public final long id;

        public GetProduct(long id) {
            this.id = id;
        }
    }

    public static class ProductResult {
        public final Optional<Product> product;

        public ProductResult(Optional<Product> product) {
            this.product = product;
        }

    }
}
