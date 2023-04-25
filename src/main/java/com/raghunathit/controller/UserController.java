package com.raghunathit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.raghunathit.binding.LoginForm;
import com.raghunathit.binding.SignUpForm;
import com.raghunathit.binding.UnlockForm;
import com.raghunathit.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/signup")
	public String signUpPage(Model model){
		
		//this is the method to load the page 
		SignUpForm user=new SignUpForm();
		model.addAttribute("user", user);
		return "signup";
	}
	//to submit the post we need one method wiht postRequest
	//this method is for signup functionality
	//@ModelAttribute (when our form is submited again i want to send the binding object back to the user)
	@PostMapping("/signup")
	public String handleSignUp(@ModelAttribute("user") SignUpForm form,Model model) {
		
		boolean status=userService.signup(form);
		if(status) {
			model.addAttribute("succMsg", "Account Created , Check Your Email");
			
		}else {
			model.addAttribute("errMsg", "Choose Unique Email");
		}
		return "signup";
	}
	
	@GetMapping("/login")
	public String loginPage(Model model){
		
		model.addAttribute("loginFormObj", new LoginForm());
		return "login";
	}
	@PostMapping("/login")
	public String loginFunctionality(@ModelAttribute("loginForm") LoginForm loginForm,Model model){
		String status = userService.login(loginForm);
		
		if(status.contains("success")) {
			//redirect reqest to dashboard method 
			//retur "dashboard"
			return "redirect:/dashboard";
			
		}
			
		model.addAttribute("errMsg", status);
		
		model.addAttribute("loginFormObj", new LoginForm());
		return "login";
	}
	

	@GetMapping("/forgot")
	public String forgotPassowrd()
	{
		return "forgotPwd";
	}

	@PostMapping("/forgotPwd")
	public String forgot(@RequestParam("email") String email,Model model){
		System.out.println(email);
		
		boolean status = userService.forgotPwd(email);
		
		if(status) {
			//send the success message
			model.addAttribute("succMsg", "pwd sent to your email");
		}else {
			//else error message
			model.addAttribute("errMsg", "invalid email id");

		}
		
		return "forgotPwd";
	}
	//RequestParam is used to capture the data form the query parameter
	@GetMapping("/unlock")
	public String unlockPage(@RequestParam String email,Model model)
	{
		UnlockForm unlockFormObj=new UnlockForm();
		unlockFormObj.setEmail(email);
		
		model.addAttribute("unlock", unlockFormObj);
		
		return "unlock";
	}
	//ModelAttribute is used to bind the data
	@PostMapping("/unlock")
	public String unlockUserAccount(@ModelAttribute("unlock") UnlockForm unlock ,Model model) {
		System.out.println(unlock);
		
	if(	unlock.getNewPwd().equals(unlock.getConfirmPwd())) {

		boolean status = userService.unlockAccount(unlock);
		if(status) {
			model.addAttribute("succMsg", "Your account unlocked successfully");
		}else {
			model.addAttribute("errMsg", "Given Temporayr password is incorect,checked your Email");
		}
	}else {
		model.addAttribute("errMsg", "New Password and Confirm Password should be same");
	}
		return "unlock";
	}
	
}
