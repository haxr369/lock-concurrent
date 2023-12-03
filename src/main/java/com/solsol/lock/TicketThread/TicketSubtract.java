package com.solsol.lock.TicketThread;

import com.solsol.lock.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TicketSubtract implements Runnable {

    private final TicketService ticketService;

    @Override
    public void run() {
        ticketService.subtract("A좌석");
    }
}
