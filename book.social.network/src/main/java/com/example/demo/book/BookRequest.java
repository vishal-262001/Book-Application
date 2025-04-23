package com.example.demo.book;

import jakarta.validation.constraints.NotEmpty;
import org.aspectj.bridge.IMessage;
import org.jetbrains.annotations.NotNull;

public record BookRequest(Integer id,
                          @NotNull
                          @NotEmpty(message = "100")
                          String title,
                          @NotNull
                          @NotEmpty(message = "102")
                          String autherName,
                          @NotNull
                          @NotEmpty(message = "103")
                          String isbn,
                          @NotNull
                          @NotEmpty(message = "104")
                          String  synopsis,

                          boolean shareable) {

}
