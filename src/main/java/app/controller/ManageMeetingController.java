package app.controller;

import java.util.List;
import java.util.logging.Logger;

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
		logger.info(thePersonalMeeting.getInitializerName());
		if (theBindingResult.hasErrors()){
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
		
		if (theBindingResult.hasErrors()){
			return "partner-meeting-form";
		}
		
		User nameExisting = userService.findByUserName(participantName);
		if(nameExisting == null) {
			theModel.addAttribute("partnerMeeting", new PartnerMeeting());
			theModel.addAttribute("FindPartnerError", "No account with this name exists.");
			return "partner-meeting-form";
		}
		meetingService.save(thePartnerMeeting);
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
	public String processPartnerMeetingForm(
			@Valid @ModelAttribute("teamMeeting") TeamMeeting theTeamMeeting,
			BindingResult theBindingResult,
			Model theModel) {
		
		String participantNames = theTeamMeeting.getParticipantNames();
		
		if (theBindingResult.hasErrors()){
			return "team-meeting-form";
		}
		
		String[] participants = participantNames.split(",");
		for(String p:participants) {
			if(userService.findByUserName(p) == null) {
				theModel.addAttribute("partnerMeeting", new PartnerMeeting());
				theModel.addAttribute("FindPeopleError", "No account with this name exists.");
				return "team-meeting-form";
			}
		}
		theTeamMeeting.setParticipants(participants);
		
		meetingService.save(theTeamMeeting);
		return "redirect:/";
	}

}
