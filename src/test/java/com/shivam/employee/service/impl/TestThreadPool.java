package com.shivam.employee.service.impl;

import org.junit.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class TestThreadPool {

    public static void main(String[] args) {
        try {
            taskExecutor().submit(new FailureRunnableTask());
        } catch (Exception e) {
            System.out.printf("Exception in runnable task Exception is %s", e.getMessage());
            throw new RuntimeException(e);
        }
    }


    @Test
    public void testSuccess() {
        try {
            taskExecutor().submit(new SuccessRunnableTask());
        } catch (Exception e) {
            System.out.printf("Exception in runnable task Exception is %s", e.getMessage());
            throw new RuntimeException(e);
        }
    }


    @Test
    public void testFailure() {
        try {
            taskExecutor().submit(new FailureRunnableTask());
        } catch (Exception e) {
            System.out.printf("Exception in runnable task Exception is %s", e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public static ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // Set your core pool size
        executor.setMaxPoolSize(10); // Set your max pool size
        executor.setQueueCapacity(25); // Set your queue capacity
        executor.setThreadNamePrefix("MyTaskExecutor-"); // Set your thread name prefix
        executor.initialize();
        return executor;
    }
}


class SuccessRunnableTask implements Runnable {

    @Override
    public void run() {
        System.out.println("Started the task");
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Completed the task");
    }
}


class FailureRunnableTask implements Runnable {

    @Override
    public void run() {
        System.out.println("Started the task");
        throw new RuntimeException("new error");
    }
}

