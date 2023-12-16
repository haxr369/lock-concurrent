package com.solsol.lock.service;

import com.solsol.lock.domain.Ticket;
import com.solsol.lock.domain.TicketStatus;
import com.solsol.lock.domain.dto.TicketDto;
import com.solsol.lock.repository.TicketRepository;
import jakarta.persistence.LockModeType;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;


    @Transactional(isolation = Isolation.SERIALIZABLE) // deadlock 발생으로 update하지 못하는 상황 발생!
    public Long subtract(String ticketName){
        try{
            Optional<Ticket> ticket = ticketRepository.findByTicketName(ticketName);
            if(ticket.isEmpty()){
                throw new RuntimeException("It's a non-existent ticket.");
            }
            ticket.get().subtractQuantity();
            return ticket.get().getQuantity();
        } catch (RuntimeException ex) {
            // Handle concurrent modification
            // For example, you can retry the operation or take other appropriate actions
            throw new RuntimeException("Concurrent modification detected "+ ex.getMessage());
        }
    }

    public TicketDto readTicket(String ticketName){
        Ticket target = ticketRepository.findByTicketName(ticketName).get();
        return new TicketDto(target);
    }

    public Ticket findByTicketName(String name){
        Optional<Ticket> ticket = ticketRepository.findByTicketName(name);
        if(ticket.isEmpty()){
            throw new RuntimeException("It's a non-existent ticket.");
        }
        return ticket.get();
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
