package app.service;

import app.entity.User;
import app.user.CrmUser;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByUserName(String userName);
    
    User findByUserEmail(String userEmail);

    void save(CrmUser crmUser);
    
    void save(User theUser);
}
