package main.java.Thread.ThreadPool;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author yangxin
 * @time 2019/3/14  8:16
 * <p>
 * 对线程的初步使用。
 * <p>
 * Executor接口
 * /         \
 * ExecutorService   ScheduledExecutorService
 * \
 * AbstractExecutorService
 * \
 * ThreadPoolExecutor----------------------------------------
 * \
 * 这些都是使用Executors工厂类来创建的。
 * ①：FixedThreadPool：创建时，核心线程数和最大线程数是一样的，是一个固定线程的线程池。常用于，需要限定线程使用的场景
 * ，负载比较大的服务器。他的阻塞队列是：LinkedBlockingQueue队列，这个队列是一个无界队列，容量没有
 * 限制，那么使用无界队列，当任务量多的时候，会导致阻塞队列量过多导致内存溢出，那么饱和策略也没有
 * 什么用
 * ②：SingleThreadExecutor： 创建时，核心线程数=最大线程数=1。说明这个线程池无论怎么样，他的线程都只有一个。阻塞队列也
 * 是无界队列。
 * ③：CachedThreadPool：缓冲线程池：创建时：他的核心线程数为0，最大线程数是 Integer.MAX_VALUE。说明这个线程池，使用到
 * 这个线程时，只要用任务，就会无上限的创建线程。而且他的阻塞队列是使用的是：SynchronousQueue，这个队列本身不会存
 * 储任何东西。缺点，大量提交任务时，创建太多线程消耗计算机资源。。
 * 优点：每个任务的执行时间非常短，使用这个线程池就非常高效。
 * ④：ScheduleThreadExecutor ：定期执行任务的线程池。有两种 ScheduleTreadPoolExecutor.只包含多个线程的
 * ScheduleThreadPoolExecutor。 SingleThreadScheduleExecutor  只包含一个线程。
 * <p>
 * ThreadPoolExecutor的构造函数
 * public ThreadPoolExecutor(int corePoolSize, 核心线程数，
 * int maximumPoolSize, 最大线程数
 * long keepAliveTime,  空闲线程存活的时间。这里指的是最大线程数里的线程，核心线程会一直存活的
 * TimeUnit unit,       时间单位
 * BlockingQueue<Runnable> workQueue)  阻塞队列，表示核心线程数量已经到达上限后，会将后面来的任务扔到阻塞队列中。  {
 * }
 * 当核心线程数已经占满的时候，并且阻塞队列满了，最大线程数也到达上限后，
 * 会采用饱和策略  RejectedExecutionHandler，一般饱和策略有四种：
 * ①：AbortPolicy ：直接抛出异常，表示线程已经满了
 * ②：CallerRunsPolicy  ： 将调用者所在的线程执行任务。
 * ③：DiscardOldestPolicy ：阻塞队列中最靠前(最老)的任务丢弃。
 * ④：DiscardPolicy：直接丢弃当前任务。
 * ⑤：可以自定义策略。
 */
public class UserThreadPool {
    public static class WorkThread implements Runnable {
        private String taskName;
        private Random r = new Random();
        public WorkThread(String taskName) {
            this.taskName = taskName;
        }
        public String getTaskName() {
            return taskName;
        }
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()
                    + "  process the task : " + taskName);
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        //创建自定义线程池，
        ExecutorService  threadPool=new ThreadPoolExecutor(2,4,3,
                TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(10),new ThreadPoolExecutor.DiscardPolicy());
        /* ExecutorService threadPool = Executors.newCachedThreadPool();//使用Executors线程池创建。*/
        for (int i = 0; i <= 6; i++) {
            WorkThread workThread = new WorkThread("work " + i);
            System.out.println("A new Task has been added: " + workThread.getTaskName());
            threadPool.execute(workThread);
        }
        threadPool.shutdown();
    }
}
