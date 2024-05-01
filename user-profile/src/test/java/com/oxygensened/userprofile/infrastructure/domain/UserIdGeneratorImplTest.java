package com.oxygensened.userprofile.infrastructure.domain;


import java.util.concurrent.ConcurrentLinkedQueue;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserIdGeneratorImplTest {

    private final UserIdGeneratorImpl userIdGenerator = new UserIdGeneratorImpl();


    @Test
    void checkUniquenessOfId() throws InterruptedException {
        var ids = new ConcurrentLinkedQueue<Long>();

        Thread.startVirtualThread(() -> {
            for (int i = 0; i < 1_000_000; i++) {
                var id = userIdGenerator.generate();
                ids.add(id);
            }
        }).join();
        Thread.startVirtualThread(() -> {
            for (int i = 0; i < 1_000_000; i++) {
                var id = userIdGenerator.generate();
                ids.add(id);
            }
        }).join();
        Thread.startVirtualThread(() -> {
            for (int i = 0; i < 1_000_000; i++) {
                var id = userIdGenerator.generate();
                ids.add(id);
            }
        }).join();

        assertThat(ids).doesNotHaveDuplicates();
    }
}
