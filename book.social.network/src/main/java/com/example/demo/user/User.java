package com.example.demo.user;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.book.Book;
import com.example.demo.history.BookTransaction;
import jakarta.persistence.*;
import org.hibernate.validator.constraints.Mod10Check;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.role.Role;

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
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails, Principal {
	@Id
	@GeneratedValue
	private Integer id;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;
	@Column(unique = true)
	private String email;
	private String password;
	private boolean accountLocked;
	private boolean enables;

	@ManyToMany(fetch = FetchType.EAGER)
	private List<Role> roles;

	@OneToMany(mappedBy = "owner")
	private  List<Book> books;

	@OneToMany(mappedBy = "user")
	private List<BookTransaction>  transactions;

	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDateTime createDateTime;
	@LastModifiedBy
	@Column(insertable = false)
	private LocalDateTime lastModifyDate;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return email;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.roles.stream()
				.map(r -> new SimpleGrantedAuthority(r.getName()))
				.collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}

	public String fullName() {
		return firstName + " " + lastName;
	}
}
