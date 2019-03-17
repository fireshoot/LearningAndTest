package main.java.Thread.project;

/**
 * @author yangxin
 * @time 2019/3/17  13:12
 *
 * 类说明：封装数据类型，表示一个任务处理后返回的结果。
 *
 */
public class TaskResult<T> {
    private final TaskResultType taskResultType;
    private final T returnValue;
    private final String reason;

    public TaskResult(TaskResultType taskResultType, T returnValue, String reason) {
        super();
        this.taskResultType = taskResultType;
        this.returnValue = returnValue;
        this.reason = reason;
    }

    public TaskResult(TaskResultType taskResultType, T returnValue) {
        super();
        this.taskResultType = taskResultType;
        this.returnValue = returnValue;
        this.reason = "成功";
    }

    public TaskResultType getTaskResultType() {
        return taskResultType;
    }

    public T getReturnValue() {
        return returnValue;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "TaskResult{" +
                "taskResultType=" + taskResultType +
                ", returnValue=" + returnValue +
                ", reason='" + reason + '\'' +
                '}';
    }
}
