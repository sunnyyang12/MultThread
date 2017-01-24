/*
 * Copyright [2015] Paypal Software Foundation
 */
package com.yyh.akka.sample.sample4;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;

/**
 * @author yuhyang
 *
 */
public class CompletionStageUtils {

    public static <T> CompletionStage<T> withRetries(Supplier<CompletionStage<T>> tFactory, int maxRetries) {
        return tFactory.get().handle((t, failure) -> {
            if(failure == null) {
                return CompletableFuture.completedFuture(t);
            } else if(maxRetries > 0) {
                return withRetries(tFactory, maxRetries - 1);
            } else {
                throw new RuntimeException("Reached max retires", failure);
            }
        }).thenCompose(stageT -> stageT);
    }
}
