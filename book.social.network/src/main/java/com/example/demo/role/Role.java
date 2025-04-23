package com.example.demo.role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.demo.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
@EntityListeners(AuditingEntityListener.class)
public class Role {
	
	@Id
	@GeneratedValue
	private Integer id;
	@Column(unique = true)
	private String name;
	
	@ManyToMany(mappedBy = "roles")
	@JsonIgnore
	private List<User> users;
	
	
	@CreatedDate
	@Column(nullable = false,updatable = false)
	private LocalDateTime createDateTime;
	@LastModifiedBy
	@Column(insertable = false)
	private LocalDateTime lastModifyDate;

}
