package com.solsol.lock.repository;

import com.solsol.lock.domain.Ticket;
import com.solsol.lock.domain.TicketStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
//    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Ticket> findByTicketName(String name);

    List<Ticket> findByStatus(TicketStatus status);

    Ticket findByTicketId(Long id);
}
