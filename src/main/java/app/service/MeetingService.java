package app.service;

import app.entity.Meeting;
import app.meeting.PersonalMeeting;

public interface MeetingService {
	
	Meeting findByMeetingTitle(String title);
	
	void save(PersonalMeeting thePersonMeeting);
	
	void save(Meeting theMeeting);
}
