package com.shivam.employee.service.impl;

import com.shivam.employee.service.ThreadService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@Log4j2
public class ThreadServiceImpl implements ThreadService {

    @Autowired
    private Task1Service task1Service;

    @Autowired
    private Task2Service task2Service;

    @Override
    public void testCompletionService() {

        ExecutorService taskExecutor = Executors.newFixedThreadPool(10);
        CompletionService<String> taskCompletionService = new ExecutorCompletionService<String>(taskExecutor);


        int submittedTasks = 2;
        taskCompletionService.submit(new Task1CallableTask("AI", task1Service));
        taskCompletionService.submit(new Task2CallableTask("AI", task2Service));
        for (int tasksHandled = 0; tasksHandled < submittedTasks; tasksHandled++) {
            try {
                System.out.println("trying to take from Completion service");
                Future<String> result = taskCompletionService.take();
                System.out.println("result for a task availble in queue.Trying to get()");
                String l = result.get();
                System.out.println("Task " + String.valueOf(tasksHandled) + "Completed - results obtained : " + l);

            } catch (InterruptedException e) {
                System.out.println("Error Interrupted exception");
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
                System.out.println("Error get() threw exception");
            }

        }
    }
}

