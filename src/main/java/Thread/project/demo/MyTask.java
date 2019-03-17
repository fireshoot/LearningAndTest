package main.java.Thread.project.demo;

import java.util.Random;
import main.java.Thread.project.TaskProcesser;
import main.java.Thread.project.TaskResult;
import main.java.Thread.project.TaskResultType;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *更多课程咨询 安生老师 QQ：669100976  VIP课程咨询 依娜老师  QQ：2470523467
 *
 *类说明：一个实际任务类，将数值加上一个随机数，并休眠随机时间
 */
public class MyTask implements TaskProcesser<Integer,Integer> {

	@Override
	public TaskResult<Integer> taskExecute(Integer data) throws InterruptedException {
		Random r = new Random();
		int flag = r.nextInt(500);
		Thread.sleep(flag);
		if(flag<=300) {//正常处理的情况
			Integer returnValue = data.intValue()+flag;
			return new TaskResult<Integer>(TaskResultType.Success,returnValue);
		}else if(flag>301&&flag<=400) {//处理失败的情况
			return new TaskResult<Integer>(TaskResultType.Failure,-1,"Failure");
		}else {//发生异常的情况
			try {
				throw new RuntimeException("异常发生了！！");
			} catch (Exception e) {
				return new TaskResult<Integer>(TaskResultType.Exeception,
						-1,e.getMessage());
			}
		}
	}

}
