package app.dao;

import app.entity.User;

public interface UserDao {

    User findByUserName(String userName);
    
    void save(User user);

	User findByUserEmail(String userEmail);
    
}
