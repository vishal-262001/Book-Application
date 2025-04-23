package com.example.demo.auth;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder

public class ResgistrationRequest {

    @NotEmpty (message = "First Name iS Mandatory")
    @NotBlank(message = "First Name iS Mandatory")
    private String firstName;
    @NotEmpty (message = "Last Name iS Mandatory")
    @NotBlank(message = "Last Name iS Mandatory")
    private String lastName;
    @Email(message = "Email is Not Formatter")
    @NotEmpty (message = "First Name iS Mandatory")
    @NotBlank(message = "First Name iS Mandatory")
    private String email;
    @NotEmpty (message = "Password iS Mandatory")
    @NotBlank(message = "Password iS Mandatory")
    @Size( min = 8,max = 15, message ="Password should be greater than 8 character" )
//    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$",
//            message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character.")
    private String password;
}
