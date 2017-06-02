package com.efun.test;

import com.efun.core.utils.HttpUtils;
import redis.clients.jedis.GeoUnit;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

/**
 * App
 *
 * @author Galen
 * @since 2017/2/26
 */
public class App {
    static int threadCount = 8;
    static int iterateCount = 10000;

    /*public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        final CountDownLatch countDownLatch=new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            new Thread(new Runnable() {
                public void run() {
                    for (int m = 0; m < iterateCount; m++) {
                        HttpUtils.doGet("http://localhost:8000/app/validUser2");
                    }
                    countDownLatch.countDown();
                    System.out.println(Thread.currentThread().getName() + " finish");
                }
            }, "Thread" + i).start();
        }
        countDownLatch.await();
        System.out.println("QPS:" + (threadCount * iterateCount * 1000L / (System.currentTimeMillis() - startTime)));
    }*/

    public static void main(String[] args) {
        System.out.println(HttpUtils.doPost("http://login.eflygame.com/game/updateGame", "{}"));
    }
}
