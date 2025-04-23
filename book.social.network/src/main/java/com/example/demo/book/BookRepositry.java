package com.example.demo.book;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepositry extends JpaRepository<Book ,Integer> {
}
