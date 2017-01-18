package info.biosfood.hibernate.audit.services;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    public static final String HARDCODED_USER_NAME = "BIOSFOOD";

    public String getCurrentUserName() {
        return HARDCODED_USER_NAME;
    }

}
