package com.anonymous63.crs.dtos;

import com.anonymous63.crs.models.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.Set;

@Data
public class UserDto {

    private Long id;

    @NotNull(message = "Name cannot be null.")
    @Size(min = 3, max = 30, message = "Name must be between {min} and {max} characters long.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Name must contain only alphabetic characters.")
    private String name;

    @NotNull(message = "Phone No cannot be null.")
    @Size(min = 10, max = 10, message = "Phone No. must be exactly {min} digits long.")
    @Pattern(regexp = "^[0-9]+$", message = "Phone No. must contain only numeric characters.")
    private String phoneNo;

    @NotNull(message = "Email cannot be null.")
    @Email(message = "Email should be valid.")
    private String emailId;

    @Size(min = 10, max = 100, message = "Address must be between {min} and {max} characters long.")
    @Pattern(regexp = "^[a-zA-Z0-9,.\\s]+$", message = "Address can only contain letters, numbers, commas, periods, and spaces.")
    private String address;

    @NotNull(message = "Username cannot be null.")
    @Size(min = 3, max = 20, message = "Username must be between {min} and {max} characters long.")
    @Pattern(regexp = "^[a-zA-z0-9._]+$", message = "Username can only contain letters, numbers, dot, and underscore.")
    private String Username;

    @NotNull(message = "Password cannot be null.")
    @Size(min = 6, max = 16, message = "Password must be between {min} and {max} characters long.")
    private String password;

    private Set<Role> roles;

    @JsonProperty(defaultValue = "true")
    private Boolean enabled = true;
}
