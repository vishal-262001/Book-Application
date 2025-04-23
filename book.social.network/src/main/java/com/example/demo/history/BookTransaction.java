package com.example.demo.history;

import com.example.demo.book.Book;
import com.example.demo.common.BaseEntity;
import com.example.demo.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class BookTransaction extends BaseEntity {

    //user relation
    @ManyToOne
    @JoinColumn(name = "User_id")
    private User user;

    //book relation
    @ManyToOne
    @JoinColumn(name = "Book_id")
    private Book book;


    private boolean returned;
    private boolean returenApproved;
}
