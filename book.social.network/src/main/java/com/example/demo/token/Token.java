package com.example.demo.token;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.demo.role.Role;
import com.example.demo.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity 
public class Token {

	@Id
	@GeneratedValue
	private Integer id;
	
	private String token;
	
	private LocalDateTime createdDateTime;
	private LocalDateTime expireDateTime;
	private LocalDateTime validateDateTime;
	
	
	@ManyToOne
	@JoinColumn(name ="userId",nullable = false)
	private User user;
}
