package com.solsol.lock.thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadSleep implements Runnable{
    @Override
    public void run() {
      log.info("thread1 시작!");
      try{
          Thread.sleep(1000);
      } catch (InterruptedException e) {
          throw new RuntimeException(e);
      }
        log.info("thread1 종료");
    }
}

