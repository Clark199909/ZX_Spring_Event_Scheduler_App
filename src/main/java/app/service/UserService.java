package app.service;

import app.entity.User;
import app.user.CrmUser;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByUserName(String userName);
    
    User findByUserEmail(String userEmail);

    void save(CrmUser crmUser);
    
    void save(User theUser);
    
    List<User> searchUsers(String theSearchName);

	List<User> getUsers(int theSortField);

	User getUser(long theId);

	void deleteUser(int theId);

	void updateInfo(User theCustomer);
}
