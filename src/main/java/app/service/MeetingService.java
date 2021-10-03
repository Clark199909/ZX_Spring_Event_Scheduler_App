package app.service;

import javax.validation.Valid;

import app.entity.Meeting;
import app.meeting.PartnerMeeting;
import app.meeting.PersonalMeeting;
import app.meeting.TeamMeeting;

public interface MeetingService {
	
	Meeting findByMeetingTitle(String title);
	
	void save(PersonalMeeting thePersonMeeting);
	
	void save(Meeting theMeeting);

	void save(PartnerMeeting thePartnerMeeting);

	void save(TeamMeeting theTeamMeeting);
}
