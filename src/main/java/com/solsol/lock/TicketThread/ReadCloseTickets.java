package com.solsol.lock.TicketThread;

import com.solsol.lock.domain.TicketStatus;
import com.solsol.lock.domain.dto.TicketDto;
import com.solsol.lock.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class ReadCloseTickets implements Runnable{
    private final TicketService ticketService;
    @Override
//    @Transactional(isolation = Isolation.SERIALIZABLE) // 이 주석을 풀면 Phantom read가 사라진다.
    public void run() {
        List<TicketDto> ticketDtoListFirst = ticketService.findByTicketStatus(TicketStatus.CLOSE);
        log.info("first query dto count : "+ticketDtoListFirst.size()+" "+ticketDtoListFirst);
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);}
        List<TicketDto> ticketDtoListSecond = ticketService.findByTicketStatus(TicketStatus.CLOSE);
        log.info("second query dto count : "+ticketDtoListSecond.size()+" "+ticketDtoListSecond);
    }
}
