package app.dao;

import app.entity.Meeting;

public interface MeetingDao {
	
	Meeting findByMeetingTitle(String title);
	
	void save(Meeting theMeeting);
}
