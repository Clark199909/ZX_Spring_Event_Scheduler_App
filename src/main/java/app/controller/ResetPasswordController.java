package app.controller;

import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
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
import app.service.UserService;
import app.user.CrmUser;
import app.user.PasswordUser;
import app.user.ResetUser;

@Controller
@RequestMapping("/resetPassword")
public class ResetPasswordController {
	
	@Autowired
	private UserService userService;
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	private User myUser;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}	
	
	@GetMapping("/showResetPasswordForm")
	public String showResetPasswordPage(Model theModel) {
		
		theModel.addAttribute("resetUser", new ResetUser());
		
		return "reset-password-first-form";
	}
	
	@PostMapping("/processResetPasswordForm")
	public String processResetPasswordForm(
			@Valid @ModelAttribute("resetUser") ResetUser theResetUser, 
			BindingResult theBindingResult,
			Model theModel) {
		String userNameOrEmail = theResetUser.getUserNameOrEmail();
		
		logger.info("Processing registration form for: " + userNameOrEmail);
		
		// form validation
		if (theBindingResult.hasErrors()){
			return "reset-password-first-form";
		}
		
		User nameExisting = userService.findByUserName(userNameOrEmail);
		User emailExisting = userService.findByUserEmail(userNameOrEmail);
		logger.info(String.valueOf(nameExisting == null && emailExisting == null));
        if (nameExisting == null && emailExisting == null){
        	theModel.addAttribute("resetUser", new ResetUser());
			theModel.addAttribute("registrationError", "No account with this name or email exists.");

			logger.warning("No such a user.");
        	return "reset-password-first-form";
        }
        if(nameExisting != null) {
        	myUser = nameExisting;
        }else if(emailExisting != null) {
        	myUser = emailExisting;
        }
        PasswordUser passwordUser = new PasswordUser();
        theModel.addAttribute("passwordUser", passwordUser);
        
        return "reset-password-second-form";
	}
	
	@PostMapping("/processResetPasswordSecondForm")
	public String processResetPasswordSecondForm(
			@Valid @ModelAttribute("passwordUser") PasswordUser thePasswordUser, 
			BindingResult theBindingResult,
			Model theModel) {
		// form validation
		if (theBindingResult.hasErrors()){
			logger.info(String.valueOf(theBindingResult.getAllErrors()));
			return "reset-password-second-form";
		}
		myUser.setPassword(thePasswordUser.getPassword());
		userService.save(myUser);
		return "reset-confirmation";
	}
}
