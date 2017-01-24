/*
 * Copyright [2015] Paypal Software Foundation
 */
package com.paypal.akka.sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author yuhyang
 *
 */
public class StdIn {
    public static String readLine() {

        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            return in.readLine();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
