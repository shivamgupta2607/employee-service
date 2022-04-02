package com.shivam.employee.service.impl;

import com.shivam.employee.service.ThreadService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
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
        List<Callable> tasks = Arrays.asList(
                new Task1CallableTask("AI", task1Service),
                new Task2CallableTask("AI", task2Service)
        );
        ExecutorService taskExecutor = Executors.newFixedThreadPool(tasks.size());
        CompletionService<String> taskCompletionService = new ExecutorCompletionService<>(taskExecutor);

        tasks.forEach(task -> {
            taskCompletionService.submit(task);
        });

        for (int tasksHandled = 0; tasksHandled < tasks.size(); tasksHandled++) {
            try {
                log.info("trying to take from Completion service");
                Future<String> result = taskCompletionService.take();
                log.info("result for a task available in queue.Trying to get()");
                String l = result.get();
                log.info("Task {} Completed - results obtained : {}", tasksHandled, l);

            } catch (InterruptedException e) {
                log.info("Error Interrupted exception");
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
                log.info("Error get() threw exception");
            }

        }
        log.info("It should execute last");
    }
}

