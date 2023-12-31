package com.solsol.lock.stopwatch;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class StopWatchTest {

    private static int temp = 100;

    @Test
    @DisplayName("Stopwatch 실행시켜보는 코드")
    public void testStopWatch (){
        StopWatch stopWatch = new StopWatch(); // 스톱워치 객체 생성
        stopWatch.start(); // 스톱워치 시작
        // 비즈니스 코드 시작
        // 1초 대기
        for(int i=0; i<100; i++){
            try {
                Thread.sleep(10); //0.01초 대기
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 비즈니스 코드 끝
        stopWatch.stop(); // 스톱워치 스탑
        System.out.println(stopWatch.prettyPrint());
    }

    @Test
    @DisplayName("메인 스레드가 멀티 스레드 작업이 다 끝날 때까지 기다리는지 테스트")
    public void multiThreadTest() throws InterruptedException {
        StopWatch stopWatch = new StopWatch(); // 스톱워치 객체 생성
        stopWatch.start(); // 스톱워치 시작
        int threadCount = 20; // 멀티 스레드 생성
        int requestCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        // 스레드는 countDown을 호출해서 requestCount를 하나씩 감소시킴
        CountDownLatch countDownLatch = new CountDownLatch(requestCount);

        for (int i = 0; i < requestCount; i++) {
            executorService.submit(() -> { // submit 안에 함수는 스레드가 실행시킴
                try {
                    temp -= 1; // static 변수 temp을 1씩 감소
                } finally {
                    countDownLatch.countDown();
                } });
        }

        // 메인 스레드는 requestCount가 0이 될때까지 blocked된다.
        countDownLatch.await();
        stopWatch.stop(); // 스톱워치 스탑
        System.out.println(stopWatch.prettyPrint());
        assertEquals(0, temp);
    }
}
