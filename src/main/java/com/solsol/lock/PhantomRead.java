package com.solsol.lock;

import com.solsol.lock.TicketThread.ReadCloseTickets;
import com.solsol.lock.TicketThread.UpdateTicketStatus;
import com.solsol.lock.domain.Ticket;
import com.solsol.lock.domain.TicketStatus;
import com.solsol.lock.service.TicketService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@RequiredArgsConstructor
//@SpringBootApplication
public class PhantomRead {

	private final TicketService ticketService;
	private final ReadCloseTickets readCloseTickets;
	private final UpdateTicketStatus updateTicketStatus;

	public static void main(String[] args) {
//		SpringApplication.run(PhantomRead.class, args);
	}

	@PostConstruct
	public void phantomRead(){
		Ticket ticketA = new Ticket("A좌석", 5L);
		Ticket savedTicketA = ticketService.saveTicket(ticketA);
		ticketService.updateStatus(savedTicketA.getTicketId(), TicketStatus.CLOSE);
		Ticket ticketB = new Ticket("B좌석", 5L);
		Ticket savedTicketB = ticketService.saveTicket(ticketB);

		Thread read = new Thread(readCloseTickets);
		Thread update = new Thread(updateTicketStatus);

		read.start();
		update.start();

	}
}
