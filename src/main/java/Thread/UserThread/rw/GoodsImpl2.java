package main.java.Thread.UserThread.rw;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author yangxin
 * @time 2019/3/16  16:35
 */
public class GoodsImpl2 implements GoodsService {
    ReentrantReadWriteLock lock=new ReentrantReadWriteLock();
    Lock read=lock.readLock();
    Lock write=lock.writeLock();
    private Goodsinfo goodsinfo;

    public GoodsImpl2(Goodsinfo goodsinfo) {
        this.goodsinfo = goodsinfo;
    }

    @Override
    public Goodsinfo getNumber() {
        read.lock();
        try{
            return goodsinfo;
        }finally {
            read.unlock();
        }
    }

    @Override
    public void setNum(int number) {
        write.lock();
        try{
            goodsinfo.changeNumber(number);
        }finally {
            write.unlock();
        }
    }
}
