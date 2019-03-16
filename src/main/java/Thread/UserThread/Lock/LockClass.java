package main.java.Thread.UserThread.Lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yangxin
 * @time 2019/3/16  16:03
 */
public class LockClass {

    public static void main(String[] args){
        Lock l=new ReentrantLock();
        l.lock();
        try{
            //业务逻辑
        }finally {
            l.unlock();
        }
    }

}
