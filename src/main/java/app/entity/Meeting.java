package app.entity;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	
	@Column(name="title")
	private String title;
	
	@Column(name="description")
	private String description;
	
	@Column(name = "date_and_time", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "users_meetings", 
	joinColumns = @JoinColumn(name = "meeting_id"), 
	inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> users;
	
	public Meeting() {}

	public Meeting(User initializer, Type meetingType, String title, String description) {
		this.initializer = initializer;
		this.meetingType = meetingType;
		this.title = title;
		this.description = description;
	}

	public Meeting(User initializer, Type meetingType, String title, String description,
			Set<User> users) {
		this.initializer = initializer;
		this.meetingType = meetingType;
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

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "Meeting [id=" + id + ", initializer=" + initializer + ", meetingType=" + meetingType
				+ ", title=" + title + ", description=" + description + ", users="
				+ users + "]";
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	
	
}
