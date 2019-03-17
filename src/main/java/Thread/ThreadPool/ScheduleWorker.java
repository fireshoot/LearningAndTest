package main.java.Thread.ThreadPool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

/**
 * @author yangxin
 * @time 2019/3/17  10:06
 *
 * 类说明：定时任务的工作
 */
public class ScheduleWorker implements Runnable {
    public static final int Normal=0;
    public static final int HasException=-1;
    public static final int ProcessException=1;

    public static SimpleDateFormat format=new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");

    private int taskType;

    public ScheduleWorker(int taskType) {
        this.taskType = taskType;
    }

    @Override
    public void run() {
        if(taskType==HasException){
            System.out.println(format.format(new Date())+"Exception be made");
            throw new RuntimeException("ExceptionHappen");
        }else if(taskType==ProcessException){
            try{
                System.out.println(format.format((new Date()))+"Exception be made"+"will be catch");
                throw new RuntimeException("ExceptionHappen");
            }catch(Exception e){
                System.out.println("we catch Exception!");
            }
        }else{
            System.out.println(format.format(new Date())+"Normal");
        }
    }
}
