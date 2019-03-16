package main.java.Thread.UserThread.ForkJoin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author yangxin
 * @time 2019/2/28  14:58
 *
 * 类说明：fork/join框架的使用范式，分为同步和异步。
 * 使用fork/join来同步统计目录下的文件个数和目录个数。
 * RecusiveTask<T>是有返回值，用于同步。
 * RecursiveAction 没有返回值，用于异步。
 */
public class ForkAndJoinFramework extends RecursiveTask<Integer> {
    private File path;//文件路径
    public ForkAndJoinFramework(File path) {
        this.path = path;
    }
    @Override
    protected Integer compute() {
        int count=0;//文件个数的计数器
        int dircount=0;//目录计数器
        List<ForkAndJoinFramework> listTask=new ArrayList<>();//子目录任务容器
        File[] files=path.listFiles();
        if(files!=null){
            for(File file:files){
                if(file.isDirectory()){
                    listTask.add(new ForkAndJoinFramework(file));
                    dircount++;
                }else{
                    count++;
                }
            }
        }
        System.out.println("目录："+path.getAbsolutePath()+" 包含的子目录个数"+dircount
                +" 包含的文件个数"+count);
        if(!listTask.isEmpty()){
            for(ForkAndJoinFramework forkAndJoinFramework:invokeAll(listTask)){
                count+=forkAndJoinFramework.join();
            }
        }
        return count;
    }

    public static void main(String[] args){
        ForkJoinPool forkJoinPool=new ForkJoinPool();
        ForkAndJoinFramework forkAndJoinFramework=new ForkAndJoinFramework(new File("D:/"));
        forkJoinPool.invoke(forkAndJoinFramework);
        System.out.println("File Count ="+forkAndJoinFramework.join());
    }
}
