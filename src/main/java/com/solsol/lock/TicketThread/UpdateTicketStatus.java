package com.solsol.lock.TicketThread;

import com.solsol.lock.domain.Ticket;
import com.solsol.lock.domain.TicketStatus;
import com.solsol.lock.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class UpdateTicketStatus implements Runnable{
    private final TicketService ticketService;

    @Override
    public void run() {
        Ticket ticketB = ticketService.findByTicketName("B좌석");
        ticketService.updateStatus(ticketB.getTicketId(), TicketStatus.CLOSE);
    }
}
