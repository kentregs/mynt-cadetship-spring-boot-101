package ph.apper;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    private String id;

    public User(String id) {
        this.id = id;
    }

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private boolean isVerified = false;
}
