/*
 * Copyright [2015] Paypal Software Foundation
 */
package com.yyh.akka.sample.sample1;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

/**
 * sample1 sample2 sample3 sample 4 copied from https://github.com/johanandren/akka-actor-java8-webinar
 * 
 * @author yuhyang
 *
 */
public class App {
    static class Counter extends AbstractLoggingActor {
        // protocal
        static class Message {
        }

        private int counter = 0;

        {
            receive(ReceiveBuilder.match(Message.class, this::onMessage).build());
        }

        private void onMessage(Message message) {
            counter++;
            log().info("increased counter" + counter);
        }

        public static Props props() {
            return Props.create(Counter.class);
        }
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("sample1");
        final ActorRef counter = system.actorOf(Counter.props(), "counter");
        for(int i = 0; i < 5; i++) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    for(int j = 0; j < 5; j++) {
                        counter.tell(new Counter.Message(), ActorRef.noSender());
                    }
                }
            }).start();
        }
    }
}
