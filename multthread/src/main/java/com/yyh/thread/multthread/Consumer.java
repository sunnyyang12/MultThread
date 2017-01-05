/*
 * Copyright [2015] Paypal Software Foundation
 */
package com.yyh.thread.multthread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yuhyang
 *
 */
// simulator thread pool with a lot of future task
public class Consumer implements Runnable {

    BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(300);
    public BlockingQueue<TaskElem> workerqueue = new LinkedBlockingQueue<TaskElem>();
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 15, 15, TimeUnit.SECONDS, workQueue);

    public BlockingQueue<TaskElem> getWorkerqueue() {
        return workerqueue;
    }

    public void setWorkerqueue(BlockingQueue<TaskElem> workerqueue) {
        this.workerqueue = workerqueue;
    }

    public void ConsumerTask() throws InterruptedException, ExecutionException {
        while(true) {
            System.out.println("begin consume task" + "Constants.workerqueue.size()" + Constants.workerqueue.size());
            System.out.println();
            TaskElem taskElem = Constants.getElement();
            processRequestTask(taskElem);
            System.out.println("consumer message " + taskElem.getMessage());

        }
    }

    /**
     * @param taskElem
     * @throws ExecutionException
     * @throws InterruptedException
     * 
     */
    private void processRequestTask(TaskElem taskElem) throws InterruptedException, ExecutionException {
        DummyTask paymentSearchQueryTask = new DummyTask();
        Future<String> fiFuture = executor.submit(paymentSearchQueryTask);
        String result = fiFuture.get();
        taskElem.setResult("after consumer , restsult is set " + result);
        System.out.println("execute result is " + result);
        // user thread pool to sumit future task process task

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        try {
            ConsumerTask();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
