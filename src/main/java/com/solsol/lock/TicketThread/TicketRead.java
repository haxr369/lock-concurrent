package com.solsol.lock.TicketThread;

import com.solsol.lock.domain.dto.TicketDto;
import com.solsol.lock.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TicketRead implements Runnable{
    private final TicketService ticketService;

    @Override
    public void run() {
        TicketDto ticketDtoFirst = ticketService.readTicket("A좌석");
        log.info("first read : "+ticketDtoFirst.toString());
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        TicketDto ticketDtoSecond = ticketService.readTicket("A좌석");
        log.info("second read : "+ticketDtoSecond.toString());
    }
}
