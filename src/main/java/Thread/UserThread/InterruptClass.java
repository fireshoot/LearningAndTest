package main.java.Thread.UserThread;

/**
 * @author yangxin
 * @time 2019/3/16  13:02
 */
public class InterruptClass {
    private static class UseInterrupt implements Runnable{
        @Override
        public void run() {
            //但是实际上并没有中断，需要检测中断标识符
            while (!Thread.currentThread().isInterrupted()){
                System.out.println(Thread.currentThread().getName()+" am implements Runnable");
            }
            System.out.println(Thread.currentThread().getName()+" interrupt is "+Thread.currentThread().isInterrupted());
        }
    }
    public static void main(String[] args) throws InterruptedException {
        UseInterrupt useInterrupt=new UseInterrupt();
        Thread thread=new Thread(useInterrupt,"endThread");
        thread.start();
        Thread.sleep(1);
        thread.interrupt();//通过调用函数实现中断
    }
}
