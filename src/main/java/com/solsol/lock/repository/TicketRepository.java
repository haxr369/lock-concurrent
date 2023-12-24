package com.solsol.lock.repository;

import com.solsol.lock.domain.Ticket;
import com.solsol.lock.domain.TicketStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select t from Ticket t where t.ticketName = :name" ) // 왜 findByTicketName는 직접 못할까?
    Optional<Ticket> findByTicketNamepessimistic(@Param("name") String name);
    // 비관적 락이나 낙관적 락을 걸어야한다.
    // 회원 정보 변경 같은 건 락을 필요 없다.
    // 여러 명이 동시에 접근하고 업데이트할 때는 락을 걸어야한다.
    // 인스타 좋아요 같은 경우는 lock을 사용할까? -> 시간이 매우 오래 걸릴 것이다.
    // 피드 게시글 pull vs push

    Optional<Ticket> findByTicketName(String name);

    List<Ticket> findByStatus(TicketStatus status);

    Ticket findByTicketId(Long id);
}
