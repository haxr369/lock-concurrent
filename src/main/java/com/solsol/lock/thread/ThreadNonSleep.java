package com.solsol.lock.thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadNonSleep implements Runnable {
    @Override
    public void run() {
        log.info("thread2 시작!");

        log.info("thread2 종료");
    }
}
