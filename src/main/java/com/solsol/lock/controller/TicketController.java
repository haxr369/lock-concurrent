package com.solsol.lock.controller;

import com.solsol.lock.domain.Ticket;
import com.solsol.lock.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("/test/ticket/{ticketName}")
    public Ticket getTicket(@PathVariable(value = "ticketName") String ticketName){
        log.info("ticket name : "+ticketName);
        return ticketService.findByTicketName(ticketName);
    }

    @PatchMapping("/test/quntity/{ticketName}")
    public Long subtractQuntity(@PathVariable(value = "ticketName") String ticketName){
        log.info("ticket name : "+ticketName);
        return ticketService.subtract(ticketName);
    }
}
