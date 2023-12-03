package com.solsol.lock.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ticket")
@NoArgsConstructor
@Getter
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;

    @Column(nullable = false)
    private String ticketName;

    @Column
    private TicketStatus status = TicketStatus.OPEN;

    @Column
    private Long quantity = 0L;


    public Ticket(String name, Long quantity){
        this.ticketName = name;
        this.quantity=quantity;
    }

    public void subtractQuantity() {
        if(this.quantity >= 1){
            this.quantity = this.quantity-1;
        } else{
            throw new RuntimeException("티켓 수량이 부족합니다.");
        }
    }
}
