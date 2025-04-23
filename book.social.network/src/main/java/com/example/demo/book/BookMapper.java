package com.example.demo.book;

import org.springframework.stereotype.Service;

@Service
public class BookMapper {
    public Book toBook(BookRequest request) {

        return Book.builder()
                .id(request.id())
                .title(request.title())
                .autherName(request.autherName())
                .isbn(request.isbn())
                .synopsis(request.synopsis())
                .archived(false)
                .shareable(request.shareable())
                .build();
    }
}
