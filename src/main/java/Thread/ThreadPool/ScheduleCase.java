package main.java.Thread.ThreadPool;

import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yangxin
 * @time 2019/3/17  10:15
 *
 * 类说明：执行我们的定时任务
 */
public class ScheduleCase {
    public static void main(String[] args){
        ScheduledThreadPoolExecutor schedule=new ScheduledThreadPoolExecutor(1);
        schedule.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("Task run one");
            }
        },3000, TimeUnit.MICROSECONDS);

        schedule.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("FixedRate start:"+ScheduleWorker.format.format(new Date()));
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("FixedRate end "+ScheduleWorker.format.format(new Date()));
            }
        },1000,3000,TimeUnit.MICROSECONDS);

        schedule.scheduleAtFixedRate(new ScheduleWorker(ScheduleWorker.Normal)
                ,1000,3000,TimeUnit.MICROSECONDS);
    }

}
