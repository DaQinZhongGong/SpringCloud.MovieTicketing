package com.bingbinlee.springcloud.micro.repository;

import com.bingbinlee.springcloud.micro.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
