package com.bridgelabz.onlinebookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.onlinebookstore.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>  {

}
