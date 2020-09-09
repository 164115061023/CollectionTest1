package Map;

import java.util.Hashtable;
import java.util.UUID;

/**
 * @Description
 * @Author zl
 * @Date 2020/9/9 17:15
 * @Version 1.0
 */
public class HashTableTest {
    static int dataSize = 1000000;
    static int threadSize = 100;

    static Hashtable<UUID, UUID> hashtable = new Hashtable<>();
    static UUID[] keys = new UUID[dataSize];
    static UUID[] values = new UUID[dataSize];
    static {
        for (int i = 0; i < dataSize; i++) {
            keys[i] = UUID.randomUUID();
            values[i] = UUID.randomUUID();
        }
    }

    static long startTime;

    static class MyThread extends Thread{
        int start;
        int size = dataSize/threadSize;
        public MyThread(int start){
            this.start = start;
        }

        @Override
        public void run() {
            for (int i = start; i < start+size; i++) {
                hashtable.put(keys[i],values[i]);
            }
        }
    }


    public static void main(String[] args) {
        startTime = System.currentTimeMillis();
        Thread[] threads = new Thread[threadSize];
        for (int i = 0; i < threadSize; i++) {
            threads[i] = new MyThread(i*(dataSize/threadSize));
        }

        for (Thread t : threads) {
            t.start();
        }



        for (Thread t : threads) {
            try {
                t.join();
                System.out.println("////"+hashtable.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(System.currentTimeMillis()-startTime);
        System.out.println(hashtable.size());
    }


}
