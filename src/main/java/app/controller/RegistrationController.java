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

import app.email.Email;
import app.entity.User;
import app.service.UserService;
import app.user.CrmUser;

@Controller
@RequestMapping("/register")
public class RegistrationController {
	
    @Autowired
    private UserService userService;
	
    private Logger logger = Logger.getLogger(getClass().getName());
    
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}	
	
	@GetMapping("/showRegistrationForm")
	public String showMyLoginPage(Model theModel) {
		
		theModel.addAttribute("crmUser", new CrmUser());
		
		return "registration-form";
	}

	@PostMapping("/processRegistrationForm")
	public String processRegistrationForm(
				@Valid @ModelAttribute("crmUser") CrmUser theCrmUser, 
				BindingResult theBindingResult, 
				Model theModel) {
		
		String userName = theCrmUser.getUserName();
		logger.info("Processing registration form for: " + userName);
		
		// form validation
		if (theBindingResult.hasErrors()){
			 return "registration-form";
		}

		// check the database if user already exists
        User existing = userService.findByUserName(userName);
        if (existing != null){
        	theModel.addAttribute("crmUser", new CrmUser());
			theModel.addAttribute("registrationError", "User name already exists.");

			logger.warning("User name already exists.");
        	return "registration-form";
        }
        
        String code = Email.generateCode();
        Email.sendEmail(theCrmUser.getEmail(), code, "Email Confirmation"); 
        theCrmUser.setCode(code);
        theModel.addAttribute("crmUser", theCrmUser);
        
        return "reg-confirm";
	}
	
	@PostMapping("/confirmEmail")
	public String confirmEmail(@ModelAttribute("crmUser") CrmUser theCrmUser,
							   Model theModel) {
		
		if(theCrmUser.getCodeInput() == null) {
			theModel.addAttribute("crmUser", theCrmUser);
			theModel.addAttribute("registrationError", "Code Input needed.");
			return "reg-confirm";
		}else if(!theCrmUser.getCodeInput().equals(theCrmUser.getCode())) {
			theModel.addAttribute("crmUser", theCrmUser);
			theModel.addAttribute("registrationError", "Wrong Code Input.");
			return "reg-confirm";
		}
			
		userService.save(theCrmUser);
		logger.info("Successfully created user: ");
		return "registration-confirmation";	
	}
	
}
