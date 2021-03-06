package app.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import app.utils.CustomerSortUtils;
import app.utils.MeetingSortUtils;

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
					"from Meeting where (initializer =:myself or :myself in elements(users)) "
					+ "and startTime < :curTime order by startTime", Meeting.class);
			theQuery.setParameter("curTime", date);
			theQuery.setParameter("myself", user);
		}else {
			theQuery = currentSession.createQuery(
					"from Meeting where (initializer =:myself or :myself in elements(users)) "
					+ "and startTime >= :curTime order by startTime", Meeting.class);
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

	@Override
	public void deleteMeeting(long theId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
				
		Query theQuery = currentSession.createQuery("delete from Meeting where id=:meetingId");
		theQuery.setParameter("meetingId", theId);
				
		theQuery.executeUpdate();
	}

	@Override
	public boolean findMeetingByDateTime(Date startDateTime, User initializer) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Meeting> theQuery = currentSession.createQuery("from Meeting where "
				+ "initializer =:theUser and startTime =:theStartDateTime", Meeting.class);
		theQuery.setParameter("theUser", initializer);
		theQuery.setParameter("theStartDateTime", startDateTime);
		
		Meeting theMeeting = null;
		try {
			theMeeting = theQuery.getSingleResult();
		}catch(Exception e) {
			return false;
		}
		
		
		return true;
	}

}
