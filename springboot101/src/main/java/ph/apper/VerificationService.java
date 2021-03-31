package ph.apper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class VerificationService {
    private final Map<String, String> verificationCodes = new HashMap<>();

    private final UserService userService;

    /*
        @Component/@Service will not be initialized until referenced by another bean or
        explicitly retrieved from the enclosing BeanFactory.
        - used to address circular dependency
     */
    public VerificationService(@Lazy UserService userService) {
        this.userService = userService;
    }

    // saves key: email and value: code inside the verificationCodes map
    public void addVerificationCode(String email, String code) {
        verificationCodes.put(email, code);
    }

    // Checks if email and code matches with any entry
    // updates isVerified variable to true if a match is found
    // and deletes the verification code entry subsequently
    public void verifyUser(String email, String Scode) {
        if(verificationCodes.entrySet().stream().anyMatch(entry -> email.matches(entry.getKey())))
            userService.getUser(email).setVerified(true);

        verificationCodes.entrySet().removeIf(entry -> code.matches(entry.getValue()));
    }

    // returns code that corresponds with given email
    // returns null if no match is found
    public String getVerificationCodes(String email) {
        for(Map.Entry<String, String> entry : verificationCodes.entrySet()) {
            if(entry.getKey().matches(email)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public int getNumberOfVerificationCodes() {
        return verificationCodes.size();
    }
}
