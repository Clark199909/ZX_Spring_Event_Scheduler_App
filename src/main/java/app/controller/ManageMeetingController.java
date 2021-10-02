package app.controller;

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

import app.meeting.PersonalMeeting;
import app.service.MeetingService;

@Controller
@RequestMapping("/manageMeeting")
public class ManageMeetingController {
	
	@Autowired
    private MeetingService meetingService;
	
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
		
		return "home";
	}

}
