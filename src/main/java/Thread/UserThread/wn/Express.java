package main.java.Thread.UserThread.wn;

/**
 * @author yangxin
 * @time 2019/2/28  11:55
 */
public class Express {
    public final static String city="Shanghai";
    private int km;
    private String site;

    public Express() {
    }

    public Express(int km, String site) {
        this.km = km;
        this.site = site;
    }

    public synchronized void changeKm(){
        this.km=101;
        notifyAll();
    }

    public synchronized void changeSite(){
        this.site="BeiJing";
        notifyAll();
    }

    public synchronized void waitKm(){
        while (this.km<100){
            try {
                wait();
                System.out.println("check km Thream["+Thread.currentThread().getName()
                        +"] is be notify");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("this km is "+this.km+",I will change db");
    }

    public synchronized  void waitSite(){
        while (this.site.equals(city)){
            try {
                wait();
                System.out.println("check km Thream["+Thread.currentThread().getName()
                        +"] is be notify");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("this is site"+this.site+",I will change db");
    }
}
