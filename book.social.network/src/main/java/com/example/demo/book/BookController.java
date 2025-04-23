package com.example.demo.book;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bboks")
@Tag(name = "Book")
@RequiredArgsConstructor
public class BookController {


    private final BookService bookService;


    @PostMapping
    public ResponseEntity<Integer> saveBook(@Valid @RequestBody BookRequest request, Authentication connectedUser){
        return ResponseEntity.ok(bookService.save(request,connectedUser));

    }
}
