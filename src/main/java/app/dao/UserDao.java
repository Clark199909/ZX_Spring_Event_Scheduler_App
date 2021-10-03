package app.dao;

import java.util.List;

import app.entity.User;

public interface UserDao {

    User findByUserName(String userName);
    
    void save(User user);

	User findByUserEmail(String userEmail);
	
	List<User> searchUsers(String theSearchName);
    
}
