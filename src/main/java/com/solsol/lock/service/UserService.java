package com.solsol.lock.service;

import com.solsol.lock.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void saveUser(User user){
        User admin = new User("관리자");
        em.persist(admin);
    }
}
