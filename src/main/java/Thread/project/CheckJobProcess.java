package main.java.Thread.project;

import java.util.Map;
import java.util.concurrent.DelayQueue;

/**
 * @author yangxin
 * @time 2019/3/17  13:37
 */
public class CheckJobProcess {

    private DelayQueue<ItemVo<String>> queue;//存放队列

    public CheckJobProcess(DelayQueue<ItemVo<String>> queue) {
        this.queue = queue;
    }

    private  static class FetchJob implements Runnable{

        private DelayQueue<ItemVo<String>> queue;

        private Map<String,JobInfo<?>> jobInfoMap;

        public FetchJob(DelayQueue<ItemVo<String>> queue, Map<String, JobInfo<?>> jobInfoMap) {
            this.queue = queue;
            this.jobInfoMap = jobInfoMap;
        }

        @Override
        public void run() {
            while(true){
                try{
                    ItemVo<String> itemVo=queue.take();
                    String jobName=(String)itemVo.getData();
                    jobInfoMap.remove(jobName);
                    System.out.println("Job:["+jobName+"] is out of date,remove form jobList！");
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

        }
    }
    //任务完成后，放入队列，经过expireTime时间后，从整个框架中移除
    public void putJob(String jobName,long expireTime){
        ItemVo<String> itemVo=new ItemVo<String>(expireTime,jobName);
        queue.offer(itemVo);
        System.out.println("Job [ "+jobName+" ]已经放入过期检查缓存，时长："+expireTime+" ms");
    }

    /*启动时检查线程队列，生命周期非常长，应该定义为守护线程*/
    public void initCheck(Map<String,JobInfo<?>> jobInfoMap){
        Thread thread=new Thread(new FetchJob(queue,jobInfoMap));
        thread.setDaemon(true);//为守护线程\
        thread.start();
        System.out.println("开启了任务过期的检查守护线程.........");
    }
}
