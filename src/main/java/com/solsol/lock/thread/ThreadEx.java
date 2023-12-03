package com.solsol.lock.thread;

public class ThreadEx implements Runnable{
    @Override
    public void run() { // Thread가 수행할 작업은 run 메서드 안에 작성
        for(int i=0; i<100; i++){
            System.out.println(Thread.currentThread().getName());
        }
    }
}
