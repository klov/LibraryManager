/*
 Информационно-вычислительный центр космодрома Байконур
 */
package experement;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author Администратор
 */
public class PrimeProducer extends Thread{

    private final BlockingQueue<BigInteger> queue;

    PrimeProducer() {
        this.queue = new LinkedBlockingQueue<>();
    }

    public void run() {
        try {
            System.out.println("start..");
            BigInteger p = BigInteger.ONE;
            while (true) {
                queue.put(p = p.nextProbablePrime());
            }
        } catch (InterruptedException consumed) {
            System.out.println("queue size "+queue.size()+"\nstope.");
        }
    }

    public void cancel() {
        interrupt();
    }
}
