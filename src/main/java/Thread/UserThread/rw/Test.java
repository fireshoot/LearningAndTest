package main.java.Thread.UserThread.rw;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @author yangxin
 * @time 2019/3/16  16:23
 */
public class Test {
    static final int readWriteRatio=10;//读写线程比列
    static final int minthreadCount=3;//最少线程数
    static CountDownLatch latch=new CountDownLatch(1);//控制读线程和写线程同时运行

    private static class GetThread implements Runnable{//读线程
        private GoodsService goodsService;
        public GetThread(GoodsService goodsService) {
            this.goodsService = goodsService;
        }
        @Override
        public void run() {
            try{
                latch.await();
            }catch(InterruptedException e){
            }
            long start=System.currentTimeMillis();
            for(int i=0;i<100;i++){
                goodsService.getNumber();
            }
            System.out.println(Thread.currentThread().getName()+" 读取商品耗时的时间： "
                    +(System.currentTimeMillis()-start)+" ms");
        }
    }

    private static class SetThrea implements  Runnable{//写线程
        private GoodsService goodsService;

        public SetThrea(GoodsService goodsService) {
            this.goodsService = goodsService;
        }

        @Override
        public void run() {
            try{
                latch.await();
            }catch (InterruptedException e){
            }

            long start=System.currentTimeMillis();
            Random r=new Random();
            for(int i=0;i<10;i++){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                goodsService.setNum(r.nextInt(10));
            }
            System.out.println(Thread.currentThread().getName()+" 写入商品耗时的时间： "
                    +(System.currentTimeMillis()-start)+" ms---");
        }
    }

    public static void main(String[] args){
        Goodsinfo goodsinfo=new Goodsinfo("cup",100000,10000);
        GoodsService goodsService=new GoodsImpl(goodsinfo);/* new GoodsImpl2(goodsinfo);*/
        for(int i=0;i<minthreadCount;i++){
            Thread thread=new Thread(new SetThrea(goodsService));//加载写线程
            for(int j=0;j<readWriteRatio;j++){
                Thread thread1=new Thread(new GetThread(goodsService));//加载读线程
                thread1.start();
            }
            thread.start();
        }
        latch.countDown();//让读写线程同时进行
    }


}
