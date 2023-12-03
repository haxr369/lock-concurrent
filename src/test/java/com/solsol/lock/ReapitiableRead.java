package com.solsol.lock;

import com.solsol.lock.domain.Ticket;
import com.solsol.lock.domain.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class ReapitiableRead {

    @Test
    @Transactional(isolation = Isolation.DEFAULT) // Read Committed 격리 수준
    void isRepeatiable(){
        // given

    }
}
