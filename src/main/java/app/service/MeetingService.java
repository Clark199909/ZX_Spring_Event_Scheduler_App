package app.service;

import java.util.List;

import javax.validation.Valid;

import app.entity.Meeting;
import app.entity.User;
import app.meeting.PartnerMeeting;
import app.meeting.PersonalMeeting;
import app.meeting.TeamMeeting;

public interface MeetingService {
	
	List<Meeting> findMeetings(String when, User user);
	
	void save(PersonalMeeting thePersonMeeting);
	
	void save(Meeting theMeeting);

	void save(PartnerMeeting thePartnerMeeting);

	void save(TeamMeeting theTeamMeeting);
	
	Meeting getMeeting(long theId);
}
