package main.java.Thread.project;

/**
 * @author yangxin
 * @time 2019/3/17  13:00
 *
 * 类说明：我们要时时刻刻的知道任务的执行状态，肯定就要先封装状态的枚举类型
 */
public enum TaskResultType {
    Success(1," 操作成功 "),
    Failure(-1," 操作失败 "),
    Exeception(2,"发生异常");

    private int state;
    private String satteInfo;

    TaskResultType(int state, String satteInfo) {
        this.state = state;
        this.satteInfo = satteInfo;
    }

    public static TaskResultType stateof(int index){
        for(TaskResultType state:values()){
            if(state.getState()==index)
                return state;
        }
        return null;
    }

    public int getState() {
        return state;
    }

    public String getSatteInfo() {
        return satteInfo;
    }}
