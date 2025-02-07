package task2;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class client1 {
    private final static int THREAD_NUM = 400;
    private final static int TOTAL_REQUESTS = 500000;
    private final static String url = "http://52.13.27.242:8080/server_war";

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(TOTAL_REQUESTS);
        Counter counter = new Counter();
        ExecutorService pool = Executors.newFixedThreadPool(THREAD_NUM);
        long start = System.currentTimeMillis();
        for (int i = 0; i < THREAD_NUM; i++) {
            ClientThread clientThread = new ClientThread(url, (int)(TOTAL_REQUESTS / THREAD_NUM * 1.1), latch, counter);
            pool.execute(clientThread);
        }
        latch.await();
        counter.setStop(true);
        pool.shutdown();
        long end = System.currentTimeMillis();
        System.out.println("Thread Number: " + THREAD_NUM);
        System.out.println("Run Time: " + (end - start) * 1.0 / 1000 + "s");
        System.out.println("Successful Requests: " + counter.getSuccessCount());
        System.out.println("Failed Requests: " + counter.getFailCount());
        System.out.println("Throughput: " + (counter.getSuccessCount()+counter.getFailCount()) / ((end - start) * 1.0 /1000));
//        Thread.sleep(2000);
//        System.out.println(pool.isShutdown());
//        System.out.println(counter.getSuccessCount()+counter.getFailCount());
    }
}
