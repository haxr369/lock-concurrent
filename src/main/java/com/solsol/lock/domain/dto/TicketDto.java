package com.solsol.lock.domain.dto;

import com.solsol.lock.domain.Ticket;
import com.solsol.lock.domain.TicketStatus;
import com.solsol.lock.service.TicketService;
import lombok.Data;

@Data
public class TicketDto {
    private Long ticketId;
    private String ticketName;
    private TicketStatus status;

    private Long quantity;

    public TicketDto(Ticket ticket){
        this.ticketId = ticket.getTicketId();
        this.ticketName = ticket.getTicketName();
        this.status = ticket.getStatus();
        this.quantity = ticket.getQuantity();
    }
}
