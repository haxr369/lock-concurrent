package com.solsol.lock.stopwatch;

import com.solsol.lock.domain.Ticket;
import com.solsol.lock.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.util.StopWatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestRepository {

    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    TestEntityManager testEntityManager;


    @Test
    @DisplayName("티켓 수량 감소 후 수량 체크")
    public void givenNewTicket_whenUpdated_thenSuccess() throws InterruptedException {
        StopWatch stopWatch = new StopWatch(); // 스톱워치 객체 생성
        stopWatch.start(); // 스톱워치 시작

        Ticket newTiket = new Ticket("효랑이", 100L);
        testEntityManager.persist(newTiket);
        newTiket.subtractQuantity(); // 티켓 수량 감소
        ticketRepository.save(newTiket);
        Long result = testEntityManager.find(Ticket.class, newTiket.getTicketId()).getQuantity();

        assertEquals(99L, result);
        System.out.println("티켓 수량 : "+result);
        stopWatch.stop(); // 스톱워치 스탑
        System.out.println(stopWatch.prettyPrint());
    }

//    @Test
//    @DisplayName("멀티스레드로 티켓 수량 감소 후 수량 체크")
//    public void givenMultiThreadAndNewTicket_whenUpdated_thenFail() throws InterruptedException {
//        StopWatch stopWatch = new StopWatch(); // 스톱워치 객체 생성
//        stopWatch.start(); // 스톱워치 시작
//        int threadCount = 20; // 멀티 스레드 생성
//        int requestCount = 100;
//        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
//        // 스레드는 countDown을 호출해서 requestCount를 하나씩 감소시킴
//        CountDownLatch countDownLatch = new CountDownLatch(requestCount);
//
//        for (int i = 0; i < requestCount; i++) {
//            executorService.submit(() -> { // submit 안에 함수는 스레드가 실행시킴
//                try {
//
//                } finally {
//                    countDownLatch.countDown();
//                } });
//        }
//
//        // 메인 스레드는 requestCount가 0이 될때까지 blocked된다.
//        countDownLatch.await();
//        stopWatch.stop(); // 스톱워치 스탑
//        System.out.println(stopWatch.prettyPrint());
////        assertEquals();
//    }
}
