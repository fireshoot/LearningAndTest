package main.java.Thread.UserThread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yangxin
 * @time 2019/3/16  17:28
 */
public class Condition33Class {
    Lock kmLock=new ReentrantLock();
    Condition condition1=kmLock.newCondition();

    Lock siteLock=new ReentrantLock();
    Condition condition2=siteLock.newCondition();

    private String name;
    private int km;
    private String site;

    public void setKm(int km) {
        kmLock.lock();
        try{
            this.km = km;
            condition1.signal();
        }finally {
            kmLock.unlock();
        }
    }

    public void setSite(String site) {
        siteLock.lock();
        try{
            this.site = site;
            condition2.signal();
        }finally {
            siteLock.unlock();
        }
    }

    public void changeKm() throws InterruptedException {
        kmLock.lock();
        try{
            while (this.km<100){
                condition1.await();
                //.....
            }
            //......
        }finally {
            kmLock.unlock();
        }
    }

    public void changeSite() throws InterruptedException {
        siteLock.lock();
        try{
            while (site.equals("BeiJing")){
                condition2.await();
            }
        }finally {
            siteLock.unlock();
        }
    }

    public Condition33Class(String name, int km, String site) {
        this.name = name;
        this.km = km;
        this.site = site;
    }

    public int getKm() {
        return km;
    }

    public String getSite() {
        return site;
    }
}
