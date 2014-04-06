package org.romaframework.scaffolding.view.domain;


import org.romaframework.aspect.persistence.PersistenceAspect;
import org.romaframework.aspect.view.ViewConstants;
import org.romaframework.aspect.view.annotation.ViewField;
import org.romaframework.core.Roma;
import org.romaframework.module.users.domain.BaseAccount;
import org.romaframework.module.users.repository.BaseAccountRepository;
import org.romaframework.scaffolding.mail.EmailHelper;

public class PasswordRecoveryPanel {

	private static final int	PASSWORD_SIZE		= 8;
	private String						PASSWORD_CHARS	= "abcdefghjkmnpqrstuvzABCDEFGHIKLMNPQRSTUVXYZ23456789@#$%@#______";
	private String						accountName;

	@ViewField(render = ViewConstants.RENDER_LABEL)
	public String getMessage() {
		return "";
	}

	public void resetPassword() {
		BaseAccount account = Roma.component(BaseAccountRepository.class).findByName(accountName);
		if (account == null) {
			Roma.flow().alert("", "Account non trovato");
			return;
		}
		String newPw = generateRandomPassowrd();
		account.setPassword(newPw);
		// account.setChangePasswordNextLogin(true);
		account = Roma.context().persistence().updateObject(account, PersistenceAspect.STRATEGY_DETACHING);
		sendEmail(account, newPw);
		Roma.flow().back();
		Roma.flow().alert("", "Controlla la tua casella email, riceverai la nuova password");
	}

	private void sendEmail(BaseAccount account, String newPw) {
		StringBuilder body = new StringBuilder();
		// TODO i18n
		body.append("Dear user<br/><br/>");
		body.append("As you requested we reset your password.<br/>");
		body.append("your new passowrd is:<br/><br/>");
		body.append(newPw);
		body.append("<br/><br/>");

		EmailHelper.sendMail(null, account.getEmail(), "reset password", body.toString(), "default");
		System.out.println(newPw);
	}

	private String generateRandomPassowrd() {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < PASSWORD_SIZE; i++) {
			Double charIndex = Math.random() * PASSWORD_CHARS.length();
			buffer.append(PASSWORD_CHARS.charAt(charIndex.intValue()));
		}
		return buffer.toString();
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

}
