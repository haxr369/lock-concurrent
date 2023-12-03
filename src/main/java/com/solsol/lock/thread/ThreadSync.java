package com.solsol.lock.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class ThreadSync implements Runnable{
    Account acc = new Account();
    @Override
    public void run() {
        while(acc.getBalance() > 0){
            // 100, 200, 300 중의 한 값을 임의로 선택해서 출금
            int money = (int) (Math.random()*3+1)*100;
            acc.withdraw(money);
            log.info("balance = "+acc.getBalance());
        }
    }
}

class Account{
    private int balance = 1000;
    public int getBalance(){
        return balance;
    }
    public synchronized void withdraw(int money){
        if(balance >= money){
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            balance -= money;
        }
    }
}
