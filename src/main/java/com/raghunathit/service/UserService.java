package com.raghunathit.service;


import com.raghunathit.binding.LoginForm;
import com.raghunathit.binding.SignUpForm;
import com.raghunathit.binding.UnlockForm;


public interface UserService {
	
	
	public boolean signup(SignUpForm form);
	
	public boolean unlockAccount(UnlockForm form);
	
	public String login(LoginForm form);
	
	public boolean forgotPwd(String email);
	

}
