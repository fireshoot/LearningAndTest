package main.java.Thread.project.demo;

import main.java.Thread.project.PendingJobPool;
import main.java.Thread.project.TaskResult;

import java.util.List;
import java.util.Random;




public class AppTest {
	
	private final static String JOB_NAME = "计算数值";
	private final static int JOB_LENGTH = 1000;
	
	//查询任务进度的线程
	private static class QueryResult implements Runnable{
		
		private PendingJobPool pool;

		public QueryResult(PendingJobPool pool) {
			super();
			this.pool = pool;
		}

		@Override
		public void run() {
			int i=0;
			while(i<350) {
				List<TaskResult<String>> taskDetail = pool.getTaskDetail(JOB_NAME);
				if(!taskDetail.isEmpty()) {
					System.out.println(pool.getTaskProgess(JOB_NAME));
					System.out.println(taskDetail);					
				}
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				i++;
			}
		}
	}

	public static void main(String[] args) {
		MyTask myTask = new MyTask();
		PendingJobPool pool = PendingJobPool.buildPool();
		pool.registerJob(JOB_NAME, JOB_LENGTH, myTask,1000*5);
		Random r = new Random();
		for(int i=0;i<JOB_LENGTH;i++) {
			pool.putTask(JOB_NAME, r.nextInt(1000));
		}
		Thread t = new Thread(new QueryResult(pool));
		t.start();
	}
}
