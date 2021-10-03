package app.meeting;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PartnerMeeting {
	
	private long id;
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String description;
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String title;
	
	private String initializerName;
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String participantName;
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String startDate;
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String startTime;
	
	public PartnerMeeting() {
		this.id = -1;
	}

	public PartnerMeeting(String description, String title, String initializerName, 
			String participantNames) {
		this.description = description;
		this.title = title;
		this.initializerName = initializerName;
		this.participantName = participantNames;
	}

	public PartnerMeeting(String description, String title, String initializerName,
					      String participantName, String startDate, String startTime) {
		this.description = description;
		this.title = title;
		this.initializerName = initializerName;
		this.participantName = participantName;
		this.startDate = startDate;
		this.startTime = startTime;
	}
	
	public PartnerMeeting(long id, String description, String title, String initializerName,
						  String participantName, String startDate, String startTime) {
		this.id = id;
		this.description = description;
		this.title = title;
		this.initializerName = initializerName;
		this.participantName = participantName;
		this.startDate = startDate;
		this.startTime = startTime;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getInitializerName() {
		return initializerName;
	}

	public void setInitializerName(String initializerName) {
		this.initializerName = initializerName;
	}

	public String getParticipantName() {
		return participantName;
	}

	public void setParticipantName(String participantName) {
		this.participantName = participantName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	
}
