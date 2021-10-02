package app.entity;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "meeting")
public class Meeting {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="initializer")
	private User initializer;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="type_id")
	private Type meetingType;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="status_id")
	private Status meetingStatus;
	
	@Column(name="title")
	private String title;
	
	@Column(name="description")
	private String description;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "users_meetings", 
	joinColumns = @JoinColumn(name = "meeting_id"), 
	inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Collection<User> users;
	
	public Meeting() {}

	public Meeting(User initializer, Type meetingType, Status meetingStatus, String title, String description) {
		this.initializer = initializer;
		this.meetingType = meetingType;
		this.meetingStatus = meetingStatus;
		this.title = title;
		this.description = description;
	}

	public Meeting(User initializer, Type meetingType, Status meetingStatus, String title, String description,
			Collection<User> users) {
		this.initializer = initializer;
		this.meetingType = meetingType;
		this.meetingStatus = meetingStatus;
		this.title = title;
		this.description = description;
		this.users = users;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getInitializer() {
		return initializer;
	}

	public void setInitializer(User initializer) {
		this.initializer = initializer;
	}

	public Type getMeetingType() {
		return meetingType;
	}

	public void setMeetingType(Type meetingType) {
		this.meetingType = meetingType;
	}

	public Status getMeetingStatus() {
		return meetingStatus;
	}

	public void setMeetingStatus(Status meetingStatus) {
		this.meetingStatus = meetingStatus;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "Meeting [id=" + id + ", initializer=" + initializer + ", meetingType=" + meetingType
				+ ", meetingStatus=" + meetingStatus + ", title=" + title + ", description=" + description + ", users="
				+ users + "]";
	}
	
	
}
