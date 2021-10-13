package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import app.entity.User;
import app.service.UserService;
import app.utils.CustomerSortUtils;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/list")
	public String listCustomers(Model theModel, 
			@RequestParam(required=false) String sorting) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String myName = authentication.getName();
		
		// get the customer from the service
		List<User> theCustomers = null;
		
		// check for sort field
		if(sorting != null) {
			int theSortField = Integer.parseInt(sorting);
			theCustomers = userService.getUsers(theSortField);
		}else {
			// no sort field provided ... default to sorting by last name
			theCustomers = userService.getUsers(CustomerSortUtils.LAST_NAME);
		}
				
		// add the customers to the model
		theModel.addAttribute("customers", theCustomers);
		theModel.addAttribute("myName", myName);
				
		return "list-customers";
	}
	
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") long theId,
									Model theModel) {
		// get the customer from the service
		User theCustomer = userService.getUser(theId);
		
		// set customer as a model attribute to pre-populate the form
		theModel.addAttribute("customer", theCustomer);
		
		// send over to our form
		return "customer-form";
	}
	
	@GetMapping("/homeShowFormForUpdate")
	public String showFormForUpdate(Model theModel) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String myName = authentication.getName();
		
		// get the customer from the service
		User theCustomer = userService.findByUserName(myName);

		// set customer as a model attribute to pre-populate the form
		theModel.addAttribute("customer", theCustomer);

		// send over to our form
		return "profile-form";
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") User theCustomer) {
		
		// save the customer using our service
		userService.updateInfo(theCustomer);
		
		return "redirect:/customer/list";
	}
	
	@PostMapping("/saveProfile")
	public String saveProfile(@ModelAttribute("customer") User theCustomer) {
		
		// save the customer using our service
		userService.updateInfo(theCustomer);
		
		return "redirect:/";
	}
	
	@GetMapping("/search")
	public String DeleteData(@RequestParam("theSearchName") String theSearchName,
									Model theModel) {
		// get the customer from the service
		List<User> theCustomers = userService.searchUsers(theSearchName);
		
		// add the customers to the model
		theModel.addAttribute("customers", theCustomers);
		return "list-customers";
	}
}
