package main.java.Thread.project;

import java.util.List;
import java.util.concurrent.*;

/**
 * @author yangxin
 * @time 2019/3/17  14:18
 */
public class PendingJobPool {
    //框架运行时的线程数，与机器的CPU数相同
    private static final int THREAD_COUNTS
            = Runtime.getRuntime().availableProcessors();
    //队列，线程池使用，用以存放待处理的任务
    private static BlockingQueue<Runnable> taskQueue
            = new ArrayBlockingQueue<Runnable>(5000);
    //线程池，固定大小，有界队列
    private static ExecutorService taskExecutor
            = new ThreadPoolExecutor(THREAD_COUNTS, THREAD_COUNTS,
            60, TimeUnit.SECONDS, taskQueue);
    //工作信息的存放容器
    private static ConcurrentHashMap<String,JobInfo<?>> jobInfoMap
            = new ConcurrentHashMap<String, JobInfo<?>>();
    //检查过期工作的处理器
    private static CheckJobProcess checkJob
            = new CheckJobProcess(new DelayQueue<ItemVo<String>>());

    //因为框架启动时，有一些需要初始的内容，将构造函数私有化，另行处理构造部分
    private PendingJobPool() {}

    //构建框架，初始化
    public static PendingJobPool buildPool() {
        PendingJobPool pool = new PendingJobPool();
        checkJob.initCheck(jobInfoMap);
        return pool;
    }

    //对工作中的任务进行包装，提交给线程池使用，并处理任务的结果，写入缓存以供查询
    private static class PendingTask<T,R> implements Runnable{

        private JobInfo<R> jobInfo;
        private T processData;

        public PendingTask(JobInfo<R> jobInfo, T processData) {
            super();
            this.jobInfo = jobInfo;
            this.processData = processData;
        }

        @SuppressWarnings("unchecked")
        @Override
        public void run() {
            R r = null;
            //取得任务的处理器
            TaskProcesser<T, R> taskProcesser
                    = (TaskProcesser<T, R>) jobInfo.getTaskProcesser();
            TaskResult<R> result = null;
            try {
                //执行任务，获得处理结果
                result = taskProcesser.taskExecute(processData);
                //检查处理器的返回结果，避免调用者处理不当
                if (result==null) {
                    result = new TaskResult<R>(TaskResultType.Exeception,r,"result is NULL");
                }
                if(result.getTaskResultType()==null) {
                    if(result.getReason()==null) {
                        result = new TaskResult<R>(TaskResultType.Exeception,r,"result is NULL");
                    }else {
                        result = new TaskResult<R>(TaskResultType.Exeception,r,
                                "result is NULL，reason："+result.getReason());
                    }
                }
            }catch(Exception e) {
                e.printStackTrace();
                result = new TaskResult<R>(TaskResultType.Exeception,r,e.getMessage());
            }
            finally {
                //将任务的处理结果写入缓存
                jobInfo.addTaskResult(result,checkJob);
            }
        }
    }

    //调用者提交工作中的任务
    @SuppressWarnings("unchecked")
    public <T,R> void putTask(String jobName,T t) {
        JobInfo<R> jobInfo = getJob(jobName);
        PendingTask<T,R> task = new PendingTask<>(jobInfo,t);
        taskExecutor.execute(task);
    }

    //调用者注册工作，如工作名，任务的处理器等等
    public <R> void registerJob(String jobName,int jobLength,
                                TaskProcesser taskProcesser,long expireTime) {
        JobInfo<R> jobInfo = new JobInfo<R>(jobName,jobLength,taskProcesser,expireTime);
        if(jobInfoMap.putIfAbsent(jobName, jobInfo)!=null) {
            throw new RuntimeException(jobName+"已经注册！");
        }

    }

    //根据工作名称检索工作
    private <R> JobInfo<R> getJob(String jobName){
        JobInfo<R> jobInfo = (JobInfo<R>) jobInfoMap.get(jobName);
        if (null==jobInfo)
            throw new RuntimeException(jobName+"是非法任务！");
        return jobInfo;
    }

    //获得每个任务的处理详情
    public <R> List<TaskResult<R>> getTaskDetail(String jobName){
        JobInfo<R> jobInfo = getJob(jobName);
        return jobInfo.getTaskDetail();
    }

    //获得工作的整体处理进度
    public <R> String getTaskProgess(String jobName) {
        JobInfo<R> jobInfo = getJob(jobName);
        return jobInfo.getTotalProcess();
    }

}