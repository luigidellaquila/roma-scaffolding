package org.romaframework.scaffolding.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component("PasswordPolicyVerifier")
@Lazy
public class PasswordPolicyVerifier {
	private Pattern	pattern;

	// private String pwdPolicy = "((?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@#$%_-]).{8,})";
	private String	pwdPolicy	= "((?=.*[a-zA-Z])(?=.*[0-9@#$%_-]).{8,})";

	public PasswordPolicyVerifier() {
		pattern = Pattern.compile(getPwdPolicy());
	}

	public boolean verify(String password) {
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
		// return true;

	}

	public String getPwdPolicy() {
		return pwdPolicy;
	}

	public void setPwdPolicy(String pwd_policy) {
		this.pwdPolicy = pwd_policy;
	}
}
