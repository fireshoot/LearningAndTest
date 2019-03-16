package main.java.Thread.UserThread.wn;

/**
 * @author yangxin
 * @time 2019/2/28  11:55
 */
public class TestWn {
    private static Express express=new Express(0,Express.city);

    private static class chenkKm extends Thread{
        @Override
        public void run() {
            express.waitKm();
        }
    }

    private static class checkSite extends  Thread{
        @Override
        public void run() {
            express.waitSite();
        }
    }

    public static void main(String[] args) throws InterruptedException{
        for(int i=0;i<1;i++){
            new checkSite().start();
        }
        for(int i=0;i<1;i++){
             new chenkKm().start();
        }
        Thread.sleep(1000);
        express.changeKm();
    }
}
