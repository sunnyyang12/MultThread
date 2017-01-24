/*
 * Copyright [2015] Paypal Software Foundation
 */
package com.yyh.akka.sample.sample3;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

/**
 * @author yuhyang
 *
 */
public class NonTrustWorthyChild extends AbstractLoggingActor {
    public static class Command {
    }

    private long messages = 0l;
    {
        receive(ReceiveBuilder.match(Command.class, this::onCommand).build());
    }

    private void onCommand(Command c) {
        messages++;
        if(messages % 4 == 0) {
            throw new RuntimeException("Oh,no, i got four commands,I can't handler any more");
        } else {
            log().info("Got a command" + messages);
        }
    }

    public static Props props() {
        return Props.create(NonTrustWorthyChild.class);
    }

}
