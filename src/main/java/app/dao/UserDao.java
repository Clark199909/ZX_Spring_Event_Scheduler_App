package app.dao;

import java.util.List;

import app.entity.User;

public interface UserDao {

    User findByUserName(String userName);
    
    void save(User user);

	User findByUserEmail(String userEmail);
	
	List<User> searchUsers(String theSearchName);

	List<User> getCustomers(int theSortField);

	User getUser(long theId);

	void deleteUser(int theId);

	void updateInfo(User theCustomer);
    
}
