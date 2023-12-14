package com.solsol.lock.repository;

import com.solsol.lock.domain.Ticket;
import com.solsol.lock.domain.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Ticket findByTicketName(String name);

    List<Ticket> findByStatus(TicketStatus status);

    Ticket findByTicketId(Long id);
}
