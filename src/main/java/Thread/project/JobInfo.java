package main.java.Thread.project;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yangxin
 * @time 2019/3/17  13:21
 */
public class JobInfo<R> {
    private final String jobName;

    private final int jobLenth;//任务的总长度

    private final TaskProcesser<?,?> taskProcesser; //处理工作任务的处理器

    private AtomicInteger successCount;//任务成功的次数

    private AtomicInteger taskProcesserCount;//已经处理的任务

    private LinkedBlockingDeque<TaskResult<R>> taskResults;//阻塞队列,双端队列，因为客户端在查询业务的时候，我们业务在运行
    //同时也在往里面插入；好处是，头部取数据，尾部插入数据，因为这个LinkedBlockingDeque的头尾的锁不一样，能够提高性能。

    private final long expireTime;

    public JobInfo(String jobName, int jobLenth, TaskProcesser<?, ?> taskProcesser, long expireTime) {
        successCount=new AtomicInteger(0);
        taskProcesserCount=new AtomicInteger(0);
        this.jobName = jobName;
        this.jobLenth = jobLenth;
        this.taskProcesser = taskProcesser;
        this.expireTime = expireTime;
        taskResults=new LinkedBlockingDeque<TaskResult<R>>(jobLenth);
    }

    public int getSuccessCount() {
        return successCount.get();
    }

    public int getTaskProcesserCount() {
        return taskProcesserCount.get();
    }

    //获取失败的次数
    public int getFailCount(){
        return taskProcesserCount.get()-successCount.get();
    }

    public TaskProcesser<?, ?> getTaskProcesser() {
        return taskProcesser;
    }

    //提供工作的整体进度信息
    public String getTotalProcess() {
        return "Success["+successCount.get()+"]/Current["+taskProcesserCount.get()
                +"] Total["+jobLenth+"]";
    }

    public int getJobLenth() {
        return jobLenth;
    }

    //提供每个任务的处理结果
    public List<TaskResult<R>> getTaskDetail(){
        List<TaskResult<R>> taskResultList=new LinkedList<>();
        TaskResult<R> taskResult;
        while((taskResult=taskResults.pollFirst())!=null)
            taskResultList.add(taskResult);
        return taskResultList;
    }

    //每个任务完成以后，要记录任务的处理结果。
    public void addTaskResult(TaskResult<R> rTaskResult,CheckJobProcess checkJobProcess){
        if(rTaskResult.getTaskResultType().getState()==TaskResultType.Success.getState()){
            successCount.incrementAndGet();
        }
        taskProcesserCount.incrementAndGet();
        taskResults.addLast(rTaskResult);
        //定期清除工作结果信息。
        if(taskProcesserCount.get()==jobLenth){
            checkJobProcess.putJob(jobName,expireTime);
        }
    }

}
