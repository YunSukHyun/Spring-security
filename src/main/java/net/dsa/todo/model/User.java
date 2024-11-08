package net.dsa.todo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="tb_user")
public class User {
	
	@Id
	private String id;
	private String password;
	private String name;
	private String email;
	
	@Enumerated(EnumType.STRING)
	private RoleType role;
}
