package app.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import app.email.Email;
import app.entity.Meeting;
import app.entity.User;
import app.meeting.PartnerMeeting;
import app.meeting.PersonalMeeting;
import app.meeting.TeamMeeting;
import app.service.MeetingService;
import app.service.UserService;

@Controller
@RequestMapping("/manageMeeting")
public class ManageMeetingController {
	
	@Autowired
    private MeetingService meetingService;
	
	@Autowired
	private UserService userService;
	
    private Logger logger = Logger.getLogger(getClass().getName());
    
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}	
	
	@GetMapping("/showPersonalMeetingForm")
	public String showPersonalMeetingForm(Model theModel) {
		PersonalMeeting thePersonalMeeting = new PersonalMeeting();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUserName = authentication.getName();
		    logger.info(currentUserName);
		    thePersonalMeeting.setInitializerName(currentUserName);
		}
		theModel.addAttribute("personalMeeting", thePersonalMeeting);
		return "personal-meeting-form";
	}
	
	@PostMapping("/processPersonalMeetingForm")
	public String processPersonalMeetingForm(
			@Valid @ModelAttribute("personalMeeting") PersonalMeeting thePersonalMeeting, 
			BindingResult theBindingResult, 
			Model theModel) {

		if (theBindingResult.hasErrors()){
			 return "personal-meeting-form";
		}
		if(meetingService.findMeetingByDateTime(thePersonalMeeting.getStartDate(), 
				thePersonalMeeting.getStartTime(), thePersonalMeeting.getInitializerName())) {
			theModel.addAttribute("personalMeeting", thePersonalMeeting);
			theModel.addAttribute("participantError", "You already have a scheduled event at the same time!");
			return "personal-meeting-form";
		}
		meetingService.save(thePersonalMeeting);
		
		return "redirect:/";
	}
	
	@GetMapping("/showPartnerMeetingForm")
	public String showPartnerMeetingForm(Model theModel) {
		PartnerMeeting thePartnerMeeting = new PartnerMeeting();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUserName = authentication.getName();
		    logger.info(currentUserName);
		    thePartnerMeeting.setInitializerName(currentUserName);
		}
		theModel.addAttribute("partnerMeeting", thePartnerMeeting);
		return "partner-meeting-form";
	}
	
	@PostMapping("/processPartnerMeetingForm")
	public String processPartnerMeetingForm(
			@Valid @ModelAttribute("partnerMeeting") PartnerMeeting thePartnerMeeting,
			BindingResult theBindingResult,
			Model theModel) {
		
		String participantName = thePartnerMeeting.getParticipantName();
		String startDate = thePartnerMeeting.getStartDate();
		String startTime = thePartnerMeeting.getStartTime();
		String initializerName = thePartnerMeeting.getInitializerName();
		
		if (theBindingResult.hasErrors()){
			return "partner-meeting-form";
		}
		
		User nameExisting = userService.findByUserName(participantName);
		if(nameExisting == null) {
			theModel.addAttribute("partnerMeeting", new PartnerMeeting());
			theModel.addAttribute("FindPartnerError", "No account with this name exists.");
			return "partner-meeting-form";
		}else if(meetingService.findMeetingByDateTime(startDate, startTime, initializerName) ||
				meetingService.findMeetingByDateTime(startDate, startTime, participantName)) {
			theModel.addAttribute("partnerMeeting", thePartnerMeeting);
			theModel.addAttribute("FindPartnerError", "Either you or your partner already have "
					+ "a scheduled meeting at the same time!");
			return "partner-meeting-form";
		}
		meetingService.save(thePartnerMeeting);
		String emailContent = "Dear " + nameExisting.getFirstName() + ",\nUser " 
				+ thePartnerMeeting.getInitializerName() + " has invited you "
				+ "to a schduled meeting on " + thePartnerMeeting.getStartDate().toString() 
				+ ", starting at " + thePartnerMeeting.getStartTime().toString()
				+ ".\n\nBest Wishes,\nZX Scheduler Admin" ;
		Email.sendEmail(nameExisting.getEmail(), emailContent, "Meeting Notification");
		return "redirect:/";
	}
	
	@GetMapping("/search")
	public String searchForUsers(@RequestParam("theSearchName") String theSearchName,
			Model theModel) {
		// get the customer from the service
		List<User> theUsers = userService.searchUsers(theSearchName);
				
		// add the customers to the model
		theModel.addAttribute("customers", theUsers);
		return "partner-meeting-form";
	}
	
	@GetMapping("/showTeamMeetingForm")
	public String showTeamMeetingForm(Model theModel) {
		TeamMeeting theTeamMeeting = new TeamMeeting();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUserName = authentication.getName();
		    logger.info(currentUserName);
		    theTeamMeeting.setInitializerName(currentUserName);
		}
		theModel.addAttribute("teamMeeting", theTeamMeeting);
		return "team-meeting-form";
	}
	
	@PostMapping("/processTeamMeetingForm")
	public String processTeamMeetingForm(
			@Valid @ModelAttribute("teamMeeting") TeamMeeting theTeamMeeting,
			BindingResult theBindingResult,
			Model theModel) {
		
		String participantNames = theTeamMeeting.getParticipantNames();
		String startDate = theTeamMeeting.getStartDate();
		String startTime = theTeamMeeting.getStartTime();
		String initializerName = theTeamMeeting.getInitializerName();
		
		if (theBindingResult.hasErrors()){
			return "team-meeting-form";
		}
		
		String[] participants = participantNames.split(",");
		Set<User> participantsSet = new HashSet<>();
		if(meetingService.findMeetingByDateTime(startDate, startTime, initializerName)) {
			theModel.addAttribute("teamMeeting", theTeamMeeting);
			theModel.addAttribute("FindPeopleError", "You already has a schduled meeting starting at the same time!");
			return "team-meeting-form";
		}
		for(String p:participants) {
			User pUser = userService.findByUserName(p);
			if(pUser == null) {
				theModel.addAttribute("teamMeeting", new TeamMeeting());
				theModel.addAttribute("FindPeopleError", "No account with this name exists.");
				return "team-meeting-form";
			}else if(meetingService.findMeetingByDateTime(startDate, startTime, p)) {
				theModel.addAttribute("teamMeeting", theTeamMeeting);
				theModel.addAttribute("FindPeopleError", p+" already has a schduled meeting starting at the same time!");
				return "team-meeting-form";
			}
			participantsSet.add(pUser);
		}
		theTeamMeeting.setParticipants(participants);
		
		meetingService.save(theTeamMeeting);
		for(User pUser:participantsSet) {
			String emailContent = "Dear " + pUser.getFirstName() + ",\nUser " 
					+ theTeamMeeting.getInitializerName() + " has invited you "
					+ "to a schduled meeting on " + theTeamMeeting.getStartDate().toString() 
					+ ", starting at " + theTeamMeeting.getStartTime().toString()
					+ ".\n\nBest Wishes,\nZX Scheduler Admin" ;
			Email.sendEmail(pUser.getEmail(), emailContent, "Meeting Notification");
		}
		return "redirect:/";
	}
	
	@GetMapping("/showPastMeetings")
	public String showPastMeetings(Model theModel) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String myName = authentication.getName();
		User myself = userService.findByUserName(myName);
		List<Meeting> theUpcomingMeetings = meetingService.findMeetings("previous", myself);
		theModel.addAttribute("upcomingMeetings", theUpcomingMeetings);
		return "previous-meeting-form";
	}

	@GetMapping("/showFutureMeetings")
	public String showFutureMeetings(Model theModel) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String myName = authentication.getName();
		User myself = userService.findByUserName(myName);
		List<Meeting> theUpcomingMeetings = meetingService.findMeetings("future", myself);
		theModel.addAttribute("upcomingMeetings", theUpcomingMeetings);
		theModel.addAttribute("myName", myName);
		return "upcoming-meeting-form";
	}
	
	@GetMapping("/showUpdateForm")
	public String showUpdateForm(@RequestParam("meetingId") long theId,
								Model theModel) {
		Meeting theMeeting = meetingService.getMeeting(theId);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String title = theMeeting.getTitle();
		String description = theMeeting.getDescription();
		String initializerName = theMeeting.getInitializer().getUserName();
		Date startDateTime = theMeeting.getStartTime();
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		DateFormat timeFormat = new SimpleDateFormat("KK:mm a");
		String startDate = dateFormat.format(startDateTime);
		String startTime = timeFormat.format(startDateTime);
		Collection<User> participants = theMeeting.getUsers();
		StringBuilder sb = new StringBuilder();
		for(User p:participants) {
			if(p.getUserName().equals(authentication.getName())) continue;
			sb.append(p.getUserName());
			sb.append(',');
		}
		if(!sb.isEmpty()) sb.deleteCharAt(sb.length()-1);
		String participantNames = sb.toString();
		
		if(theMeeting.getMeetingType().getTypeName().equals("personal")) {
			PersonalMeeting thePersonalMeeting = 
					new PersonalMeeting(theId, description, title, 
					initializerName, startDate, startTime);
			theModel.addAttribute("personalMeeting", thePersonalMeeting);
			return "personal-meeting-form";
		}else if(theMeeting.getMeetingType().getTypeName().equals("partner")) {
			PartnerMeeting thePartnerMeeting = 
					new PartnerMeeting(theId, description, title, initializerName,
						      participantNames, startDate, startTime);
			theModel.addAttribute("partnerMeeting", thePartnerMeeting);
			return "partner-meeting-form";
		}else {
			TeamMeeting theTeamMeeting = 
					new TeamMeeting(theId, description, title, initializerName,
						      participantNames, null, startDate, startTime);
			theModel.addAttribute("teamMeeting", theTeamMeeting);
			return "team-meeting-form";
		}
	}
	
	@GetMapping("/delete")
	public String deleteMeeting(@RequestParam("meetingId") long theId,
								Model theModel) {
		meetingService.deleteMeeting(theId);
		
		return "redirect:/manageMeeting/showFutureMeetings";
	}
	
}
