package ph.apper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final List<User> users = new ArrayList<>();
    //    private final Map<String, String> verificationCodes = new HashMap<>();
    private final IdService idService;
    private final VerificationService verificationService;

    public UserService(IdService idService, VerificationService verificationService) {
        this.idService = idService;
        this.verificationService = verificationService;
    }

    public User register(UserRegistration userRegistration) throws InvalidUserRegistrationException {
        // validate inputs. all fields are required. Valid email. age should be at least 18 years.
        if (userRegistration.getEmail().isBlank()
                || userRegistration.getFirstName().isBlank()
                || userRegistration.getLastName().isBlank()
                || userRegistration.getPassword().isEmpty()
                || Objects.isNull(userRegistration.getBirthDate())
        ) {
            throw new InvalidUserRegistrationException("All fields are required");
        }

//        1 - a-z/A-Z characters allowed
//        2 - 0-9 allowed
//        4 - email may contain only dot(.), dash(-), and underscore(_)
        if (!userRegistration.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new InvalidUserRegistrationException("Invalid email");
        }

        if (userRegistration.getPassword().length() < 5) {
            throw new InvalidUserRegistrationException("password must be at least 5 characters");
        }

        Period periodDiff = Period.between(userRegistration.getBirthDate(), LocalDate.now());
        if (periodDiff.getYears() < 18) {
            throw new InvalidUserRegistrationException("age must be at least 18");
        }

        // get user id
        String userId = idService.getNextUserId();
        LOGGER.info("Generated User ID: {}", userId);

        // save registration details as User with ID
        User newUser = new User(userId);
        newUser.setFirstName(userRegistration.getFirstName());
        newUser.setLastName(userRegistration.getLastName());
        newUser.setBirthDate(userRegistration.getBirthDate());
        newUser.setEmail(userRegistration.getEmail());

        // hash password prior to storage
        Integer pw = userRegistration.getPassword().hashCode();
        newUser.setPassword(pw.toString());

        // create verification code
        String verificationCode = idService.generateCode(5);
        verificationService.addVerificationCode(newUser.getEmail(), verificationCode);

        users.add(newUser);

        return newUser;
    }

    public int getNumberOfRegisteredUser() {
        return users.size();
    }

    public int getNumberOfVerificationCodes() {
        return verificationService.getNumberOfVerificationCodes();
    }

    // returns the User object with the specified email
    public User getUser(String email) {
        for(User user : users) {
            if(user.getEmail().matches(email))
                return user;
        }
        return null;
    }
}
