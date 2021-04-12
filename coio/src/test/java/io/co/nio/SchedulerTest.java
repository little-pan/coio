/*
 * Copyright (c) 2021, little-pan, All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */
package io.co.nio;

import com.offbynull.coroutines.user.Coroutine;
import io.co.CoStarter;
import io.co.Scheduler;
import static io.co.util.LogUtils.*;

public class SchedulerTest {

    public static void main(String[] args) {
        int times = Integer.getInteger("times", 1000000);
        SchedulerTest test = new SchedulerTest();
        test.testAwait(0, times);
        test.testAwait(1, 1000);
        test.testAwait(10, 100);
        test.testAwait(100, 10);
        test.testAwait(1000, 1);
    }

    void testAwait(int millis, int times) {
        long ta = System.currentTimeMillis();
        Scheduler scheduler = new NioScheduler();
        Coroutine co = c -> {
            long last = System.currentTimeMillis();
            for (int i = 0; i < times; ++i) {
                scheduler.await(c, millis);
                long curr = System.currentTimeMillis();
                if (curr - last >= 5000) {
                    last = curr;
                    debug("at %s", i);
                }
            }
            scheduler.shutdown();
        };
        CoStarter.start(co, scheduler);
        scheduler.run();
        long tb = System.currentTimeMillis();
        info("await(%sms) times %s, time %sms", millis, times, tb - ta);
    }

}
