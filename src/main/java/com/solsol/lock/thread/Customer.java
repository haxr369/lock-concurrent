package com.solsol.lock.thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Customer implements Runnable{
    private Table table;
    private String food;
    public Customer(Table table, String food){
        this.table = table;
        this.food = food;
    }
    @Override
    public void run() {
        while(true){
            try{Thread.sleep(100);} catch (InterruptedException e) {}
            String name = Thread.currentThread().getName();
            table.remove(food);
            log.info(name+" eat a "+food);
        }
    }
}
