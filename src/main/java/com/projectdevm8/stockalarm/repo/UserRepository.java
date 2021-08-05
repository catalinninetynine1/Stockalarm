package com.projectdevm8.stockalarm.repo;

import com.projectdevm8.stockalarm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  User findFirstByEmail(String email);
}
