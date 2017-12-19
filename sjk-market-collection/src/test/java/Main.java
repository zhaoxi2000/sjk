import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private int id = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static Logger getLogger() {
        return logger;
    }

    @Override
    public int hashCode() {
        return 111;
    }

    public synchronized void handle(int type) throws InterruptedException {
        if (type == 1) {
            this.wait();
            System.out.println("wait end!");
        } else {
            System.out.print("....");
        }
    }

    public void wakeup() {
        this.notifyAll();
    }

    /**
     * @param <E>
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        System.out.println(0x0 ^ 0x0);
        System.out.println(0x1 ^ 0x1);
        System.out.println(-1 ^ -1);

        Main m1 = new Main();
        Main m2 = new Main();
        m1.setId(100);
        m2.setId(200);
        System.out.println("......main   " + (m1 == m2));
        System.out.println(m1 + "   " + m2);
        System.out.println(m1.equals(m2));
        HashMap<String, Main> map = new HashMap<String, Main>();
        map.put("m1", m1);
        map.put("m2", m2);
        Main getM1 = map.get("m1");
        Main getM2 = map.get("m2");
        System.out.println(getM1);
        System.out.println(getM2);

        // EnumMarket m = EnumMarket.valueOf(EnumMarket.class, "NINE_ONE");
        // System.out.println(m);

        BlockingQueue<Object> bQ = new ArrayBlockingQueue<Object>(10);
        bQ.offer(new Object());

        AtomicInteger ai = new AtomicInteger(100);
        System.out.println(ai.getAndSet(100));
        System.out.println(ai.get());

        System.out.println(ai.getAndSet(99));
        System.out.println(ai.get());
    }
}
