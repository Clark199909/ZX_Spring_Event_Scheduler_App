package app.dao;

import java.util.List;

import app.entity.Meeting;
import app.entity.User;

public interface MeetingDao {
	
	List<Meeting> findMeetings(String when, User user);
	
	void save(Meeting theMeeting);

	Meeting getMeeting(long id);
}
