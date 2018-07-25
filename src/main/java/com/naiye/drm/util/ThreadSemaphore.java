package com.naiye.drm.util;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class ThreadSemaphore {
    private static Semaphore s = new Semaphore(5);

    public static int tryAcquire(Long timeout) throws InterruptedException {
        if(timeout == null) timeout = 1000L;
        if(!s.tryAcquire(timeout, TimeUnit.MILLISECONDS)){
            return -1;
        }
        return s.availablePermits();
    }

    public static void release(){
        s.release();
    }

    public static int availablePermits(){
        return availablePermits();
    }

    public static void main(String[] args) throws Exception {
        s.tryAcquire();
        s.tryAcquire();
        s.tryAcquire();
        s.tryAcquire();
        s.tryAcquire();
        s.tryAcquire(100, TimeUnit.MILLISECONDS);
        s.tryAcquire(100, TimeUnit.MILLISECONDS);
        s.tryAcquire(100, TimeUnit.MILLISECONDS);
        s.tryAcquire(100, TimeUnit.MILLISECONDS);
        if(s.availablePermits() > 0){
            System.out.println("avaliable num=" + s.availablePermits());
            System.out.println(tryAcquire(3000L));
        }else {
            System.out.println("avaliable num=" + s.availablePermits());
            System.out.println(tryAcquire(100L));
            System.out.println("not avaliable");
        }
    }

}
