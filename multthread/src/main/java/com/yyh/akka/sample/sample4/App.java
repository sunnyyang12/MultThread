/*
 * Copyright [2015] Paypal Software Foundation
 */
package com.yyh.akka.sample.sample4;

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

        ActorRef db = system.actorOf(DbSupervisor.props(), "db");

        system.actorOf(WebServer.props(db, "localhost", 8080), "webserver");

        System.out.println("ENTER to terminate");
        StdIn.readLine();
        system.terminate();
    }
}
