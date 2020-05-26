package com.sungur.repository;

import com.sungur.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmail(String email);
    User findByUserName(String userName);
}
