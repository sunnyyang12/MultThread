/*
 * Copyright [2015] Paypal Software Foundation
 */
package com.yyh.akka.sample.sample3;

import static akka.actor.SupervisorStrategy.restart;
import scala.concurrent.duration.Duration;
import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.japi.pf.DeciderBuilder;
import akka.japi.pf.ReceiveBuilder;

/**
 * @author yuhyang
 *
 */
public class Supervisor extends AbstractLoggingActor {

    public static final OneForOneStrategy STRATEGY = new OneForOneStrategy(10, Duration.create("10 seconds"),
            DeciderBuilder.match(RuntimeException.class, ex -> restart()).build());

    {
        final ActorRef child = getContext().actorOf(NonTrustWorthyChild.props(), "child");
        receive(ReceiveBuilder.matchAny(any -> child.forward(any, getContext())).build());
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return STRATEGY;
    }

    public static Props props() {
        return Props.create(Supervisor.class);
    }
}
