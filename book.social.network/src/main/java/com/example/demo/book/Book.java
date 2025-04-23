package com.example.demo.book;

import com.example.demo.common.BaseEntity;
import com.example.demo.feedback.Feedback;
import com.example.demo.history.BookTransaction;
import com.example.demo.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Book extends BaseEntity {


    private String title;

    private String autherName;

    private String isbn;

    private String synopsis;

    private String bookCover;

    private boolean archived;

    private boolean shareable;

    @ManyToOne
    @JoinColumn(name = "Owner_id")
    private User owner;

    @OneToMany(mappedBy = "books")
    private List<Feedback>  feedbacks;



    @OneToMany(mappedBy = "book")
    private List<BookTransaction>  transactions;


}
