package ph.apper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.Month;

@SpringBootApplication
public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner start(UserService userService, VerificationService verificationService) {
        return args -> {
            UserRegistration userRegistration = new UserRegistration();
            userRegistration.setEmail("jdoe@apper.ph");
            userRegistration.setFirstName("John");
            userRegistration.setLastName("Doe");
            userRegistration.setPassword("strongpassword");
            userRegistration.setBirthDate(LocalDate.of(2000, Month.DECEMBER, 3));

            userService.register(userRegistration);

            UserRegistration userRegistration1 = new UserRegistration();
            userRegistration1.setEmail("mreyes@apper.ph");
            userRegistration1.setFirstName("Mary");
            userRegistration1.setLastName("Reyes");
            userRegistration1.setPassword("$trongerPassword");
            userRegistration1.setBirthDate(LocalDate.of(1993, Month.JANUARY, 7));

            User user = userService.register(userRegistration1);

            LOGGER.info("Current # of registered user: {}", userService.getNumberOfRegisteredUser()); //Should display 2
            LOGGER.info("Current # of verification codes: {}", userService.getNumberOfVerificationCodes()); //Should display 2

            /*
             * the following code runs :)
             */
            String verificationCode = verificationService.getVerificationCodes(user.getEmail());
            verificationService.verifyUser("mreyes@apper.ph", verificationCode);

            LOGGER.info("Current # of verification codes: {}", userService.getNumberOfVerificationCodes()); //Should display 1
            
            LOGGER.info("Is {} verified: {}", userRegistration1.getEmail(), userService.getUser("mreyes@apper.ph").isVerified());

        };
    }

}
