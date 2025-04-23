package com.example.demo.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AuthenticationRequest {

    @Email(message = "Email is Not Formatter")
    @NotEmpty(message = "First Name iS Mandatory")
    @NotBlank(message = "First Name iS Mandatory")
    private String email;
    @NotEmpty (message = "Password iS Mandatory")
    @NotBlank(message = "Password iS Mandatory")
    @Size( min = 8,max = 15, message ="Password should be greater than 8 character" )
    private String password;
}
