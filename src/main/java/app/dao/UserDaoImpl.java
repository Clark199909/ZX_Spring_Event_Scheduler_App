package app.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import app.entity.User;
import app.utils.CustomerSortUtils;

@Repository
public class UserDaoImpl implements UserDao {

	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public User findByUserName(String theUserName) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// now retrieve/read from database using username
		Query<User> theQuery = currentSession.createQuery("from User where userName=:uName", User.class);
		theQuery.setParameter("uName", theUserName);
		User theUser = null;
		try {
			theUser = theQuery.getSingleResult();
		} catch (Exception e) {
			theUser = null;
		}

		return theUser;
	}

	@Override
	public void save(User theUser) {
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// create the user ... finally LOL
		currentSession.saveOrUpdate(theUser);
	}

	@Override
	public User findByUserEmail(String userEmail) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// now retrieve/read from database using username
		Query<User> theQuery = currentSession.createQuery("from User where email=:uEmail", User.class);
		theQuery.setParameter("uEmail", userEmail);
		User theUser = null;
		try {
			theUser = theQuery.getSingleResult();
		} catch (Exception e) {
			theUser = null;
		}

		return theUser;
	}
	
	@Override
	public List<User> searchUsers(String theSearchName) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<User> theQuery = null;
		
		//
		// Only search by name if theSearchName is not empty
		//
		if(theSearchName != null && theSearchName.trim().length()>0) {
			// search for firstName or lastName ... case insensitive
			theQuery = currentSession.createQuery(
					"from User where lower(firstName) like :theName or "
					+ "lower(lastName) like :theName", User.class);
			theQuery.setParameter("theName","%" + theSearchName.toLowerCase() + "%");
		}else {
			// theSearchName is empty ... so just get all customers
			theQuery = currentSession.createQuery("from User", User.class);
		}
		
		// execute query and get result list
		List<User> users = theQuery.getResultList();
		
		// return the results
		return users;
	}

	@Override
	public List<User> getCustomers(int theSortField) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
				
				// determine sort field
		String theFieldName = null;
				
		switch(theSortField){
			case CustomerSortUtils.USER_NAME:
				theFieldName = "userName";
				break;
			case CustomerSortUtils.FIRST_NAME:
				theFieldName = "firstName";
				break;
			case CustomerSortUtils.LAST_NAME:
				theFieldName = "lastName";
				break;
			case CustomerSortUtils.EMAIL:
				theFieldName = "email";
				break;
			default:
				theFieldName = "userName";
		}
				
		// create a query
		String queryString = "from User order by " + theFieldName;
		Query<User> theQuery = 
				currentSession.createQuery(queryString, User.class);
				
		// execute query and get result list
		List<User> users = theQuery.getResultList();
				
		// return the result
		return users;
	}

	@Override
	public User getUser(long theId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
				
		// now retrieve/read from database using the primary key
		User theCustomer = currentSession.get(User.class, theId);
				
		return theCustomer;
	}

	@Override
	public void deleteUser(int theId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
						
		Query theQuery = currentSession.createQuery("delete from User where id=:userId");
		theQuery.setParameter("userId", theId);
						
		theQuery.executeUpdate();
	}

	@Override
	public void updateInfo(User theCustomer) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		Query theQuery = currentSession.createQuery("update User set firstName=:userFirstName "
				+ ", lastName=:userLastName, email=:userEmail where id=:userId");
		theQuery.setParameter("userFirstName", theCustomer.getFirstName());
		theQuery.setParameter("userLastName", theCustomer.getLastName());
		theQuery.setParameter("userEmail", theCustomer.getEmail());
		theQuery.setParameter("userId", theCustomer.getId());
		theQuery.executeUpdate();
	}

}
