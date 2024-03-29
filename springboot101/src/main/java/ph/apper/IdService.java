package ph.apper;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IdService {
    public String getNextUserId() {
        return UUID.randomUUID().toString();
    }

    public String generateCode(int size) {
        return RandomStringUtils.randomAlphanumeric(size);
    }
}
