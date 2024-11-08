package net.dsa.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.dsa.todo.model.User;



public interface UserRepository extends JpaRepository<User, String> {
	

}
