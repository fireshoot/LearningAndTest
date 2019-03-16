package main.java.Thread.UserThread.ForkJoin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * @author yangxin
 * @time 2019/2/28  15:19
 *
 * 统计一个目录下的txt文件个数。
 *
 */
public class FornJoinFramework extends RecursiveAction {
    private File path;
    public FornJoinFramework(File path) {
        this.path = path;
    }
    @Override
    protected void compute() {
        List<FornJoinFramework> listTask=new ArrayList<>();
        File[] files=path.listFiles();
        if(files!=null){
            for(File file:files){
                if(file.isDirectory()){
                    listTask.add(new FornJoinFramework(file));
                }else{
                    if(file.getAbsolutePath().endsWith("txt")){
                        System.out.println("文件："+file.getAbsolutePath());
                    }
                }
            }
        }
        if(!listTask.isEmpty()){
            for(FornJoinFramework fornJoinFramework: invokeAll(listTask)){
                fornJoinFramework.join();
            }
        }
    }
    public static void main(String[] args){
        ForkJoinPool forkJoinPool=new ForkJoinPool();
        FornJoinFramework fornJoinFramework=new FornJoinFramework(new File("D:/"));
        forkJoinPool.execute(fornJoinFramework);
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("I am ok ");
        fornJoinFramework.join();
        System.out.println("work finish ");
    }
}
