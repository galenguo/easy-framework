package com.efun.test;

import com.efun.core.utils.HttpUtils;
import redis.clients.jedis.GeoUnit;

import java.util.HashMap;

/**
 * App
 *
 * @author Galen
 * @since 2017/2/26
 */
public class App {
    static int threadCount = 8;
    static int iterateCount = 100000;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < threadCount; i++) {
            new Thread(new Runnable() {
                public void run() {
                    for (int m = 0; m < iterateCount; m++) {
                        HttpUtils.doGet("http://localhost:8000/app/validUser1", new HashMap<String, String>(), null);
                    }
                    System.out.println(Thread.currentThread().getName() + " finish");
                }
            }, "Thread" + i).start();
        }
        System.out.println("QPS:" + (threadCount * iterateCount * 1000L / (System.currentTimeMillis()) - startTime));
    }
}
