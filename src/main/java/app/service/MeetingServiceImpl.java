package app.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	
	public Date convertDate(String startDate, String startTime) {
		String input = startDate + " " + startTime;
		System.out.println(input);
		DateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy KK:mm a");
		try {
			Date output =  inputFormat.parse(input);
			System.out.println(output.toString());
			return output;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public void save(PersonalMeeting thePersonalMeeting) {
		Meeting theMeeting = new Meeting();
		User theInitializer = userDao
				.findByUserName(thePersonalMeeting.getInitializerName());
		if(thePersonalMeeting.getId() != -1) theMeeting.setId(thePersonalMeeting.getId());
		theMeeting.setDescription(thePersonalMeeting.getDescription());
		theMeeting.setTitle(thePersonalMeeting.getTitle());
		theMeeting.setMeetingType(typeDao.findTypeByName("personal"));
		theMeeting.setInitializer(theInitializer);
		Set<User> theUsers = new HashSet<>(); 
		theUsers.add(theInitializer);
		theMeeting.setUsers(theUsers);
		theMeeting.setStartTime(convertDate(thePersonalMeeting.getStartDate(), 
				thePersonalMeeting.getStartTime()));
		
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
		if(thePartnerMeeting.getId() != -1) theMeeting.setId(thePartnerMeeting.getId());
		theMeeting.setDescription(thePartnerMeeting.getDescription());
		theMeeting.setTitle(thePartnerMeeting.getTitle());
		theMeeting.setMeetingType(typeDao.findTypeByName("partner"));
		theMeeting.setInitializer(theInitializer);
		Set<User> theUsers = new HashSet<>(); 
		theUsers.add(theInitializer);
		theUsers.add(theParticipant);
		theMeeting.setUsers(theUsers);
		theMeeting.setStartTime(convertDate(thePartnerMeeting.getStartDate(), 
				thePartnerMeeting.getStartTime()));
		
		meetingDao.save(theMeeting);
	}

	@Override
	@Transactional
	public void save(TeamMeeting theTeamMeeting) {
		Meeting theMeeting = new Meeting();
		User theInitializer = userDao
				.findByUserName(theTeamMeeting.getInitializerName());
		String[] participants = theTeamMeeting.getParticipants();
		if(theTeamMeeting.getId() != -1) theMeeting.setId(theTeamMeeting.getId());
		theMeeting.setDescription(theTeamMeeting.getDescription());
		theMeeting.setTitle(theTeamMeeting.getTitle());
		theMeeting.setMeetingType(typeDao.findTypeByName("team"));
		theMeeting.setInitializer(theInitializer);
		Set<User> theUsers = new HashSet<>(); 
		theUsers.add(theInitializer);
		for(String p: participants) {
			theUsers.add(userDao.findByUserName(p));
		}
		theMeeting.setUsers(theUsers);
		theMeeting.setStartTime(convertDate(theTeamMeeting.getStartDate(), 
				theTeamMeeting.getStartTime()));
		
		meetingDao.save(theMeeting);
	}

	@Override
	@Transactional
	public List<Meeting> findMeetings(String when, User user) {
		
		return meetingDao.findMeetings(when, user);
	}

	@Override
	@Transactional
	public Meeting getMeeting(long theId) {
		
		return meetingDao.getMeeting(theId);
	}

	@Override
	@Transactional
	public void deleteMeeting(long theId) {
		
		meetingDao.deleteMeeting(theId);
	}

}
