package main.java.Thread.UserThread;

import java.util.concurrent.CountDownLatch;

/**
 * @author yangxin
 * @time 2019/2/28  15:30
 *
 * 类说明：5个线程执行6个初始化任务,使用CountDownLatch。
 */
public class UseCountDownLatch {
    static CountDownLatch countDownLatch=new CountDownLatch(6);

    private static class initThread implements Runnable{

        @Override
        public void run() {
            System.out.println("Thread:"+Thread.currentThread().getName()+"初始化中....");
            countDownLatch.countDown();
            System.out.println("Thread:"+Thread.currentThread().getName()+"初始化完成，执行其他操作中...");
        }
    }

    private static class BussThread implements Runnable{

        @Override
        public void run() {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread:"+Thread.currentThread().getName()+"业务逻辑工作++++++");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread:"+Thread.currentThread().getName()+"初始化111");
                countDownLatch.countDown();
                System.out.println("Thread:"+Thread.currentThread().getName()+"初始化222");
                countDownLatch.countDown();
            }
        }).start();
        new Thread(new BussThread()).start();
        for(int i=0;i<=3;i++){
            Thread thread=new Thread(new initThread());
            thread.start();
        }

        countDownLatch.await();
        System.out.println("Main work finish");
    }

}
