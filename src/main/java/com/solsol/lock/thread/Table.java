package com.solsol.lock.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

//@Slf4j
//public class Table {
//    String[] dishNames = {"donut", "donut", "burger"}; //donut을 더 자주 추가한다.
//    final int MAX_FOOD = 6; // 테이블에 놓을 수 있는 음식 수.
//    private ArrayList<String> dishes = new ArrayList<>();
//    int eatCount = 0;
//
//    // ReentrantLock을 Chef와 Customer에 대해 생성
//    private ReentrantLock lock = new ReentrantLock();
//    private Condition forChef = lock.newCondition();
//    private Condition forCust = lock.newCondition();
//
//    public void add(String dish){
//        lock.lock(); // 임계 영역 생성
//        try {
//            int waitingCustomers = lock.getQueueLength();
//            log.info("add Number of waiting members: " + waitingCustomers);
//            // 테이블에 음식이 가득찼으며 테이블에 음식을 추가하지 않는다.
//            while (dishes.size() >= MAX_FOOD) {
//                String name = Thread.currentThread().getName();
//                log.info(name + "is waiting.");
//                try {
//                    forChef.await(); //Chef에게 음식이 충분하니 기다리게 (lock 반납하게) 한다.
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                }
//            }
//            dishes.add(dish);
//            forCust.signal(); // 음식을 기다리던 고객에게 음식을 채웠음을 알린다.
//            log.info("Dishes: "+ dishes);
//        } finally {
//            lock.unlock();
//        }
//
//    }
//    public void remove(String dishName){
//        lock.lock();
//        try {
//            int waitingCustomers = lock.getQueueLength();
//            log.info("remove Number of waiting members: " + waitingCustomers);
//            String name = Thread.currentThread().getName();
//            while(dishes.size()==0){
//                log.info(name+" is wating.");
//                try{
//                    forCust.await(); // 고객에게 음식이 없으니 기다리게 (lock 반납하게) 한다.
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {}
//            }
//            while(true){
//                for(int i=0; i<dishes.size(); i++){ // 지정된 요리와 일치하는 요리를 테이블에서 제거한다.
//                    if(dishName.equals(dishes.get(i))){
//                        dishes.remove(i);
//                        eatCount++;
//                        log.info("new eat count is "+eatCount);
//                        forChef.signal(); // 음식 수가 줄었으니 요리사에게 알린다.
//                        return;
//                    }
//                }
//                try{
//                    log.info(name + " is waiting.");
//                    forCust.await(); // 원하는 음식이 없는 고객을 기다리게한다.
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {}
//            }
//        } finally {
//            lock.unlock();
//        }
//    }
//    public int dishNumb(){return dishNames.length;}
//}


@Slf4j
public class Table {
    String[] dishNames = {"donut", "donut", "burger"}; //donut을 더 자주 추가한다.
    final int MAX_FOOD = 6; // 테이블에 놓을 수 있는 음식 수.
    private ArrayList<String> dishes = new ArrayList<>();
    int eatCount = 0;

    // ReentrantLock을 Chef와 Customer에 대해 생성
    private ReentrantLock lock = new ReentrantLock();
    private Condition forChef = lock.newCondition();
    private List<Condition> forCusts = Arrays.asList(lock.newCondition(),lock.newCondition());

    public void add(String dish){
        lock.lock(); // 임계 영역 생성
        try {
            int waitingCustomers = lock.getQueueLength();
            log.info("add Number of waiting members: " + waitingCustomers);
            // 테이블에 음식이 가득찼으며 테이블에 음식을 추가하지 않는다.
            while (dishes.size() >= MAX_FOOD) {
                String name = Thread.currentThread().getName();
                log.info(name + " is waiting.");
                try {
                    forChef.await(); //Chef에게 음식이 충분하니 기다리게 (lock 반납하게) 한다.
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
            }
            dishes.add(dish);
            if(dish.contains(dishNames[1])){
                // 도넛을 기다리던 고객1에게 도넛이 있음을 알린다.
                forCusts.get(0).signal();
            }else{
                // 버거를 기다리던 고객2에게 버거가 있음을 알린다.
                forCusts.get(1).signal();
            }
            log.info("Dishes: "+ dishes);
        } finally {
            lock.unlock();
        }

    }
    public void remove(String dishName){
        lock.lock();
        try {
            String name = Thread.currentThread().getName();
            int waitingCustomers = lock.getQueueLength();
            log.info("remove Number of waiting members: " + waitingCustomers);
            while(!dishes.contains(dishName)){ // 테이블에 고객이 원하는 음식이 없는 경우
                log.info(name+" is waiting.");
                try{
                    if(dishName.equals(dishNames[1])){
                        // 고객1에게 도넛이 없으니 기다리게 한다.
                        forCusts.get(0).await();
                    } else{
                        // 고객2에게 버거가 없으니 기다리게 한다.`
                        forCusts.get(1).await();
                    }
                    Thread.sleep(500);
                } catch (InterruptedException e) {}
            }
            // 지정된 요리를 테이블에서 제거한다.
            dishes.remove(dishName);
            eatCount++;
            log.info("new eat count is "+eatCount);
            // 음식 수가 줄었으니 요리사에게 알린다.
            forChef.signal();

        } finally {
            lock.unlock();
        }
    }
    public int dishNumb(){return dishNames.length;}
}

