package com.solsol.lock.service;

import com.solsol.lock.domain.Ticket;
import com.solsol.lock.domain.dto.TicketDto;
import com.solsol.lock.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    @Transactional(isolation = Isolation.DEFAULT)
    public Long subtract(String ticketName){
        Ticket ticket = ticketRepository.findByTicketName(ticketName);
        ticket.subtractQuantity();
        return ticket.getQuantity();
    }

    public TicketDto readTicket(String ticketName){
        Ticket target = ticketRepository.findByTicketName(ticketName);
        return new TicketDto(target);
    }

    public void saveTicket(Ticket ticket){
        ticketRepository.save(ticket);
    }
}
