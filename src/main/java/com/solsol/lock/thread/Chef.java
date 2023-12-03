package com.solsol.lock.thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Chef implements Runnable{
    private Table table;
    public Chef(Table table){this.table = table;}
    @Override
    public void run() {
        while(true){
            // 임의의 요리를 하나 선택해서 table에 추가한다.
            int idx = (int) (Math.random()*table.dishNumb());
            table.add(table.dishNames[idx]);
            try{Thread.sleep(10);} catch (InterruptedException e) {}
        }
    }
}
