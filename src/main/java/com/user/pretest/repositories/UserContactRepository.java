package com.user.pretest.repositories;

import com.user.pretest.entities.UserContact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserContactRepository extends JpaRepository<UserContact, String> {
}
