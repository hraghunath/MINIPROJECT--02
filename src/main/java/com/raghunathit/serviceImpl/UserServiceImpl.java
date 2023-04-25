package com.raghunathit.serviceImpl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raghunathit.binding.LoginForm;
import com.raghunathit.binding.SignUpForm;
import com.raghunathit.binding.UnlockForm;
import com.raghunathit.entity.UserDtlsEntity;
import com.raghunathit.repository.UserDtlsRepo;
import com.raghunathit.service.UserService;
import com.raghunathit.utils.EmailUtils;
import com.raghunathit.utils.PwdUtils;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDtlsRepo userDtlsRepo;
	
	@Autowired
	private EmailUtils emailUtils;
	
	@Autowired
	private HttpSession session;
	
	
	@Override
	public boolean signup(SignUpForm form) {
		
		UserDtlsEntity user = userDtlsRepo.findByEmail(form.getEmail());
		
		if(user!=null) {
			return false;
		}
		//TODO:Copy data from binding obj to entity obje
		UserDtlsEntity entity =new UserDtlsEntity();
		BeanUtils.copyProperties(form, entity);
		
		//TODO:generate random pwd and set to object
		String tempPwd = PwdUtils.generatedRandomPwd();
		entity.setPwd(tempPwd);
		
		//TODO:Set account Status as Locked
		entity.setAccStatus("LOCKED");
		
		
		
		//TODO:Insert record
		userDtlsRepo.save(entity);
		
		//TODO:Send email to unlock the account
		String to=form.getEmail();
		String subject="Unlock Your Account | RaghunathIT";
		
		StringBuffer body=new StringBuffer("");
		
		body.append("<h1>Use below temporary password to unlock your account </h1>");
		body.append("Temporary pwd:"+ tempPwd);
		body.append("<br/>");
		body.append("<a href=\"http://localhost:8090/unlock?email="+to+"\">Click here to unlock your account</a>");
		
		emailUtils.sendEmail(to, subject, body.toString());
		return true;
		//here we send the email like this
		//Use below temporary possword to unlock your account
		//TEMP pwd:jfstiew
	//<a href="unlock?email=raghunath@gmail.com">Click here to unlock Your Account</a>	
	}


	@Override
	public boolean unlockAccount(UnlockForm form) {
		UserDtlsEntity entity=userDtlsRepo.findByEmail(form.getEmail());
		if(entity.getPwd().equals(form.getTempPwd())) {
			entity.setPwd(form.getNewPwd());
			entity.setAccStatus("UNLOCKED");
			userDtlsRepo.save(entity);
			return true;
		}else {
			return false;

		}
	}


	@Override
	public String login(LoginForm form) {
		UserDtlsEntity entity = userDtlsRepo.findByEmailAndPwd(form.getEmail(), form.getPwd());
		if(entity==null) {
			return "Invalid Credentials";
		}
		if(entity.getAccStatus().equals("LOCKED")) {
			return "Your Account is Locked";
		}
		
		//create session and store user data in session
		
		session.setAttribute("userId", entity.getUserId());
		
		
		return "success";
	}


	@Override
	public boolean forgotPwd(String email) {
		//checked record present in db with gien email
		UserDtlsEntity entity = userDtlsRepo.findByEmail(email);
		//if record not available send error message to the ui
		if(entity==null) {
			return false;
		}
		//if record available send pwd to email and success message
		String Subject="Recover Password";
		String body="Your Pwd ::"+ entity.getPwd();
		
		
		
		emailUtils.sendEmail(email, Subject, body);
		
		return true;
	}
	}
