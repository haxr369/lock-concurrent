package com.solsol.lock;

import com.solsol.lock.TicketThread.TicketRead;
import com.solsol.lock.TicketThread.TicketSubtract;
import com.solsol.lock.domain.Ticket;
import com.solsol.lock.domain.User;
import com.solsol.lock.repository.TicketRepository;
import com.solsol.lock.service.TicketService;
import com.solsol.lock.service.UserService;
import com.solsol.lock.thread.*;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class LockApplication{

	private final TicketService ticketService;
	private final UserService userService;
	private final EntityManager em;

	public static void main(String[] args) {
		SpringApplication.run(LockApplication.class, args);
	}

	@PostConstruct // 빈이 생성되고 자동 실행
	public void initialize() {
		// 원하는 동작을 여기에 구현
		User user = new User("고객1");
		userService.saveUser(user);
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
