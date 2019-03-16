package main.java.Thread.UserThread.rw;

/**
 * @author yangxin
 * @time 2019/3/16  16:17
 */
public class Goodsinfo {
    private final String name;
    private double totalMoney;
    private int storeNumber;

    public Goodsinfo(String name, double totalMoney, int storeNumber) {
        this.name = name;
        this.totalMoney = totalMoney;
        this.storeNumber = storeNumber;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public int getStoreNumber() {
        return storeNumber;
    }

    public void changeNumber(int seltNumber){
        this.totalMoney+=seltNumber*25;
        this.storeNumber-=seltNumber;
    }
}
