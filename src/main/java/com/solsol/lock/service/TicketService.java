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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    /**
     * Spring Data JPA의 기본 쿼리 메서드 사용
     * 격리 수준을 Repeatable Read로 설정해도 서로 다른 transaction이 한 데이터를 동시에 update하게된다.
     * 이 문제는 한 transaction 쓰기 작업을 할 때 다른 transaction이 기다리지 않는 것을 의미한다. -> read-write lock 수행 X
     */
    @Transactional  //(isolation = Isolation.SERIALIZABLE) // deadlock 발생으로 update하지 못하는 상황 발생!
    public Long subtractDefault(String ticketName){
        try{ // try 제거해도 ok
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

    // Lock 어노테이션을 이용한 비관적락 : 격리수준을 구현하는 방법 중 하나
    @Transactional
    public Long subtractPessimistic(String ticketName){
        try{ // try 제거해도 ok
            Optional<Ticket> ticket = ticketRepository.findByTicketNamepessimistic(ticketName);
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

    @Transactional
    public Ticket findByTicketName(String name){
        Optional<Ticket> ticket = ticketRepository.findByTicketNamepessimistic(name);
        log.info("ticket info "+ ticket);
        if(ticket.isEmpty()){
            throw new RuntimeException("It's a non-existent ticket.");
        }
        return ticket.get();
    }

    @Transactional
    public TicketDto readTicket(String ticketName) {
        Ticket target = ticketRepository.findByTicketNamepessimistic(ticketName).get();
        return new TicketDto(target);
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
