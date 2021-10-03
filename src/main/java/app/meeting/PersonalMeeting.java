package app.meeting;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PersonalMeeting {
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String description;
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String title;
	
	private String initializerName;

	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String startDate;
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String startTime;
	
	public PersonalMeeting() {}

	public PersonalMeeting(String description, String title, String initializerName) {
		this.description = description;
		this.title = title;
		this.initializerName = initializerName;
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
