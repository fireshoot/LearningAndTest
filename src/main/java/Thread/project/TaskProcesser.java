package main.java.Thread.project;

/**
 * @author yangxin
 * @time 2019/3/17  13:18
 *
 * 类说明：这是一个给使用者提供的接口，使用者使用这个框架要实现这个接口。
 * 因为任务的性质只能等到具体的任务才知道是什么，所有返回值什么的都使用泛型。
 *
 */
public interface TaskProcesser<T,R> {

    TaskResult<R> taskExecute(T data) throws InterruptedException;

}
