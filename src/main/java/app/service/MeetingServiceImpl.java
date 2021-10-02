package app.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dao.MeetingDao;
import app.dao.StatusDao;
import app.dao.TypeDao;
import app.dao.UserDao;
import app.entity.Meeting;
import app.entity.User;
import app.meeting.PersonalMeeting;

@Service
public class MeetingServiceImpl implements MeetingService {

	@Autowired
	private TypeDao typeDao;
	
	@Autowired
	private StatusDao statusDao;
	
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
		theMeeting.setMeetingStatus(statusDao.findStatusByName("upcoming"));
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

}
