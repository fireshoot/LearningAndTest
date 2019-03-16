package main.java.Thread.UserThread.rw;

/**
 * @author yangxin
 * @time 2019/3/16  16:21
 */
public class GoodsImpl implements GoodsService {
    private Goodsinfo goodsinfo;

    public GoodsImpl(Goodsinfo goodsinfo) {
        this.goodsinfo = goodsinfo;
    }

    @Override
    public synchronized Goodsinfo getNumber() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this.goodsinfo;
    }

    @Override
    public synchronized void setNum(int number) {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        goodsinfo.changeNumber(number);
    }
}
