package com.solsol.lock.service;

import com.solsol.lock.domain.Ticket;
import com.solsol.lock.domain.TicketStatus;
import com.solsol.lock.domain.dto.TicketDto;
import com.solsol.lock.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    public Long subtract(String ticketName){
        Ticket ticket = ticketRepository.findByTicketName(ticketName);
        ticket.subtractQuantity();
        return ticket.getQuantity();
    }

    public TicketDto readTicket(String ticketName){
        Ticket target = ticketRepository.findByTicketName(ticketName);
        return new TicketDto(target);
    }

    public Ticket findByTicketName(String name){
        return ticketRepository.findByTicketName(name);
    }

    public List<TicketDto> findByTicketStatus(TicketStatus status){
        List<Ticket> targets = ticketRepository.findByStatus(status);
        return targets.stream()
                .map(TicketDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateStatus(Long id, TicketStatus status){
        Ticket ticket = ticketRepository.findByTicketId(id);
        log.info("update ticket id : "+ticket.getTicketId());
        ticket.setStatus(status);
    }

    public Ticket saveTicket(Ticket ticket){
        return ticketRepository.save(ticket);
    }
}
