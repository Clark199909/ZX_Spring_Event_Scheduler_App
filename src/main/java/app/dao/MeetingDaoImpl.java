package app.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import app.entity.Meeting;
import app.entity.User;

@Repository
public class MeetingDaoImpl implements MeetingDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void save(Meeting theMeeting) {
		
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// create the user ... finally LOL
		currentSession.saveOrUpdate(theMeeting);

	}

	@Override
	public List<Meeting> findMeetings(String when, User user) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
				
		Query<Meeting> theQuery = null;
				
		//
		// Only search by name if theSearchName is not empty
		//
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));  
		Date date = new Date();  
		try {
			date = formatter.parse(formatter.format(date));
		}catch(Exception e) {
		}
		
		if(when.equals("previous")) {
			theQuery = currentSession.createQuery(
					"from Meeting where initializer =:myself and startTime < :curTime", Meeting.class);
			theQuery.setParameter("curTime", date);
			theQuery.setParameter("myself", user);
		}else {
			theQuery = currentSession.createQuery(
					"from Meeting where initializer =:myself and startTime >= :curTime", Meeting.class);
			theQuery.setParameter("curTime", date);
			theQuery.setParameter("myself", user);
		}
				
		// execute query and get result list
		List<Meeting> meetings = theQuery.getResultList();
				
		// return the results
		return meetings;
	}

	@Override
	public Meeting getMeeting(long theId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Meeting theMeeting = currentSession.get(Meeting.class, theId);
		return theMeeting;
	}

}
