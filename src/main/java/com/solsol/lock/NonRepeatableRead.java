package com.solsol.lock;

import com.solsol.lock.TicketThread.ReadCloseTickets;
import com.solsol.lock.TicketThread.TicketRead;
import com.solsol.lock.TicketThread.TicketSubtract;
import com.solsol.lock.TicketThread.UpdateTicketStatus;
import com.solsol.lock.domain.Ticket;
import com.solsol.lock.domain.TicketStatus;
import com.solsol.lock.domain.User;
import com.solsol.lock.service.TicketService;
import com.solsol.lock.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class NonRepeatableRead {

	private final TicketService ticketService;
	private final ReadCloseTickets readCloseTickets;
	private final UpdateTicketStatus updateTicketStatus;

	public static void main(String[] args) {

	}
	@PostConstruct // 빈이 생성되고 자동 실행
	public void nonRepeatableRead() {
		// 원하는 동작을 여기에 구현
		Ticket ticketA = new Ticket("A좌석", 5L);
		ticketService.saveTicket(ticketA);

		Runnable ticketRead = new TicketRead(ticketService);
		Thread read = new Thread(ticketRead);
		Runnable ticketSubtract = new TicketSubtract(ticketService);
		Thread subtract = new Thread(ticketSubtract);
		read.start(); // 두번 읽기
		subtract.start(); // 티켓 수량 바꾸기
	}
}
