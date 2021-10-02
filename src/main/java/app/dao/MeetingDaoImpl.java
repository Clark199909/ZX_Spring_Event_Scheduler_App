package app.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import app.entity.Meeting;

@Repository
public class MeetingDaoImpl implements MeetingDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Meeting findByMeetingTitle(String title) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// now retrieve/read from database using username
		Query<Meeting> theQuery = currentSession.createQuery("from Meeting where title=:meetingTitle", Meeting.class);
		theQuery.setParameter("meetingTitle", title);
		Meeting theMeeting = null;
		try {
			theMeeting = theQuery.getSingleResult();
		} catch (Exception e) {
			theMeeting = null;
		}

		return theMeeting;
	}

	@Override
	public void save(Meeting theMeeting) {
		
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// create the user ... finally LOL
		currentSession.saveOrUpdate(theMeeting);

	}

}
