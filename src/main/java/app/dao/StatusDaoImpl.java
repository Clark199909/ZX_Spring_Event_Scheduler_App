package app.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import app.entity.Status;

@Repository
public class StatusDaoImpl implements StatusDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Status findStatusByName(String theStatusName) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// now retrieve/read from database using name
		Query<Status> theQuery = currentSession.createQuery("from Status where status=:statusName", Status.class);
		theQuery.setParameter("statusName", theStatusName);
						
		Status theStatus = null;
						
		try {
			theStatus = theQuery.getSingleResult();
		} catch (Exception e) {
			theStatus = null;
		}
						
		return theStatus;
	}

}
