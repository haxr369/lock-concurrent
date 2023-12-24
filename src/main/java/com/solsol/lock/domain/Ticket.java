package com.solsol.lock.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Synchronized;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Entity
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
    public void setStatus(TicketStatus status){
        this.status = status;
    }


    public void subtractQuantity() { //syncro..
        if(this.quantity >= 1){
            // 스레드가 동시에 올 수 있으니
            //
            this.quantity = this.quantity-1;
        } else{
            throw new RuntimeException("티켓 수량이 부족합니다.");
        }
    }
}
