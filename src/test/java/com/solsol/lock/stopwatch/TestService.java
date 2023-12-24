package com.solsol.lock.stopwatch;

import com.solsol.lock.domain.Ticket;
import com.solsol.lock.repository.TicketRepository;
import com.solsol.lock.service.TicketService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestService {

    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    TicketService ticketService;
    static int threadCount = 20; // 멀티 스레드 생성
    static int requestCount = 100;

    @BeforeEach
    public void before(){ // ProductServiceV2 객체 생성을 위한 생성자를 통한 DI
        ticketRepository.save(new Ticket("A좌석", (long) requestCount));
    }

    @AfterEach
    public void after() {
        ticketRepository.deleteAll();
    }

    @Test
    @Transactional
    @DisplayName("싱글스레드로 transaction이 걸린 티켓에 수량 감소 후 체크")
    public void givenSingleThreadAndTransaction_whenUpdated_thenSuccess() throws InterruptedException {
        StopWatch stopWatch = new StopWatch(); // 스톱워치 객체 생성
        stopWatch.start(); // 스톱워치 시작

        // given
        Long result1 = ticketService.findByTicketName("A좌석").getQuantity();
        System.out.println("초기 티켓 수량 : "+result1);

        // when
        ticketService.subtract("A좌석"); // 티켓 수량 감소

        // then
        Long result = ticketService.findByTicketName("A좌석").getQuantity();
        System.out.println("티켓 수량 : "+result);

        stopWatch.stop(); // 스톱워치 스탑
        System.out.println(stopWatch.prettyPrint());
        assertEquals(99L, result);
    }

    @Test
    @DisplayName("멀티스레드로 transaction이 걸린 티켓에 수량 감소 후 체크")
    public void givenMultiThreadAndTransaction_whenUpdated_thenSuccess() throws InterruptedException {
        StopWatch stopWatch = new StopWatch(); // 스톱워치 객체 생성
        stopWatch.start(); // 스톱워치 시작

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        // 스레드는 countDown을 호출해서 requestCount를 하나씩 감소시킴
        CountDownLatch countDownLatch = new CountDownLatch(requestCount);
        Long result1 = ticketService.findByTicketName("A좌석").getQuantity();
        System.out.println("초기 티켓 수량 : "+result1);

        for (int i = 0; i < requestCount; i++) { // wating time일 수 있다.
            executorService.submit(() -> { // submit 안에 함수는 스레드가 실행시킴
                try {
                    ticketService.subtract("A좌석"); // 티켓 수량 감소
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
                finally {
                    countDownLatch.countDown();
                } });
        }
        // 메인 스레드는 requestCount가 0이 될때까지 blocked된다.
        countDownLatch.await();

        Long result = ticketService.findByTicketName("A좌석").getQuantity();
        System.out.println("티켓 수량 : "+result);

        stopWatch.stop(); // 스톱워치 스탑
        System.out.println(stopWatch.prettyPrint());
        assertEquals(0L, result);
    }
}

