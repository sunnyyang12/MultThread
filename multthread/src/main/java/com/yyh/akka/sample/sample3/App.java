/*
 * Copyright [2015] Paypal Software Foundation
 */
package com.yyh.akka.sample.sample3;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import com.paypal.akka.sample.StdIn;

/**
 * @author yuhyang
 *
 */
public class App {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create();
        final ActorRef supervisor = system.actorOf(Supervisor.props(), "supervisor");
        for(int i = 0; i < 10; i++) {
            supervisor.tell(new NonTrustWorthyChild.Command(), ActorRef.noSender());
        }
        System.out.println("ENTER to terminate");
        StdIn.readLine();
        system.terminate();
    }
}
