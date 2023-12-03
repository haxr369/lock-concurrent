package com.solsol.lock.thread;

public class ThreadException implements Runnable{
    @Override
    public void run() {
        try{
            throw new Exception();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
