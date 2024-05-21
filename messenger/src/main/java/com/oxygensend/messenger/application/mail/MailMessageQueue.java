package com.oxygensend.messenger.application.mail;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.stereotype.Component;

@Component
class MailMessageQueue {

    private final Queue<Runnable> tasks = new ConcurrentLinkedQueue<>();
    private final ExecutorService executor = Executors.newThreadPerTaskExecutor(Thread.ofVirtual().name("mail-message-queue-", 0).factory());


    public void bookmark(Runnable runnable) {
        tasks.add(runnable);

        if (tasks.size() == 1) {
            executor.execute(this::releaseQueue);
        }

    }

    private void releaseQueue() {
        while (!tasks.isEmpty()) {
            tasks.poll().run();
        }
    }

}
