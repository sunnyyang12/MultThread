/*
 * Copyright [2015] Paypal Software Foundation
 */
package com.yyh.akka.sample.sample4;

import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.resume;

import java.util.ArrayList;
import java.util.List;

import scala.concurrent.duration.Duration;
import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.japi.pf.DeciderBuilder;
import akka.japi.pf.ReceiveBuilder;
import akka.routing.ActorRefRoutee;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Routee;
import akka.routing.Router;

/**
 * @author yuhyang
 *
 */
public class DbSupervisor extends AbstractLoggingActor {

    {
        final Props connectionProps = DbActor.props().withDispatcher("akkasample.blocking-dispatcher");

        // create a router and 5 actors, each handling a single
        // database connection
        Router router;
        {
            List<Routee> routees = new ArrayList<Routee>();
            for(int i = 0; i < 5; i++) {
                ActorRef r = getContext().actorOf(connectionProps);
                getContext().watch(r);
                routees.add(new ActorRefRoutee(r));
            }
            router = new Router(new RoundRobinRoutingLogic(), routees);
        }

        receive(ReceiveBuilder.match(DbActor.GetProduct.class, request -> router.route(request, sender())).build());

    }

    private final SupervisorStrategy strategy = new OneForOneStrategy(10, Duration.create("1 minute"), DeciderBuilder
            .match(SynchronousDatabaseConnection.ConnectionLost.class, e -> restart())
            .match(SynchronousDatabaseConnection.RequestTimeOut.class, e -> resume()).build());

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    public static Props props() {
        return Props.create(DbSupervisor.class);
    }

}
