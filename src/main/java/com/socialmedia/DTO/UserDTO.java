package com.socialmedia.DTO;

import com.socialmedia.entity.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String email;


    public UserDTO() {}

    // Constructor to convert User entity to UserDTO
    public UserDTO(User user) {
        this.id = user.getId();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }


    @Override
    public String toString() {
        return "UserDTO{id=" + id + ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' + ", username='" + username + '\'' +
                ", email='" + email + '\'' + '}';
    }

}

