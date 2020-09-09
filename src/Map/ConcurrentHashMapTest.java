package Map;

import java.util.Hashtable;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @Author zl
 * @Date 2020/9/9 17:15
 * @Version 1.0
 */
public class ConcurrentHashMapTest {
    static int dataSize = 1000000;
    static int threadSize = 100;

    static ConcurrentHashMap<UUID, UUID> concurrentHashMap = new ConcurrentHashMap<>();
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
                concurrentHashMap.put(keys[i],values[i]);
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
                System.out.println("///"+concurrentHashMap.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(System.currentTimeMillis()-startTime);
        System.out.println(concurrentHashMap.size());
    }


}
