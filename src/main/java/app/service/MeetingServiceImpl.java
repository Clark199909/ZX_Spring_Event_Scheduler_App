package app.service;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dao.MeetingDao;
import app.dao.TypeDao;
import app.dao.UserDao;
import app.entity.Meeting;
import app.entity.User;
import app.meeting.PartnerMeeting;
import app.meeting.PersonalMeeting;
import app.meeting.TeamMeeting;

@Service
public class MeetingServiceImpl implements MeetingService {

	@Autowired
	private TypeDao typeDao;
	
	
	@Autowired
	private MeetingDao meetingDao;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public Meeting findByMeetingTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public void save(PersonalMeeting thePersonalMeeting) {
		Meeting theMeeting = new Meeting();
		User theInitializer = userDao
				.findByUserName(thePersonalMeeting.getInitializerName());
		theMeeting.setDescription(thePersonalMeeting.getDescription());
		theMeeting.setTitle(thePersonalMeeting.getTitle());
		theMeeting.setMeetingType(typeDao.findTypeByName("personal"));
		theMeeting.setInitializer(theInitializer);
		Collection<User> theUsers = new ArrayList<>(); 
		theUsers.add(theInitializer);
		theMeeting.setUsers(theUsers);
		
		meetingDao.save(theMeeting);
	}

	@Override
	public void save(Meeting theMeeting) {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional
	public void save(PartnerMeeting thePartnerMeeting) {
		Meeting theMeeting = new Meeting();
		User theInitializer = userDao
				.findByUserName(thePartnerMeeting.getInitializerName());
		User theParticipant = userDao
				.findByUserName(thePartnerMeeting.getParticipantName());
		theMeeting.setDescription(thePartnerMeeting.getDescription());
		theMeeting.setTitle(thePartnerMeeting.getTitle());
		theMeeting.setMeetingType(typeDao.findTypeByName("partner"));
		theMeeting.setInitializer(theInitializer);
		Collection<User> theUsers = new ArrayList<>(); 
		theUsers.add(theInitializer);
		theUsers.add(theParticipant);
		theMeeting.setUsers(theUsers);
		
		meetingDao.save(theMeeting);
	}

	@Override
	@Transactional
	public void save(TeamMeeting theTeamMeeting) {
		Meeting theMeeting = new Meeting();
		User theInitializer = userDao
				.findByUserName(theTeamMeeting.getInitializerName());
		String[] participants = theTeamMeeting.getParticipants();
		theMeeting.setDescription(theTeamMeeting.getDescription());
		theMeeting.setTitle(theTeamMeeting.getTitle());
		theMeeting.setMeetingType(typeDao.findTypeByName("team"));
		theMeeting.setInitializer(theInitializer);
		Collection<User> theUsers = new ArrayList<>(); 
		theUsers.add(theInitializer);
		for(String p: participants) {
			theUsers.add(userDao.findByUserName(p));
		}
		theMeeting.setUsers(theUsers);
		
		meetingDao.save(theMeeting);
	}

}
