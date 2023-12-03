package com.solsol.lock.repository;

import com.solsol.lock.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Ticket findByTicketName(String name);
}
