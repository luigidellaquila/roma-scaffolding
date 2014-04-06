package org.romaframework.scaffolding.view.domain.user;

import java.security.NoSuchAlgorithmException;


import org.romaframework.aspect.authentication.AuthenticationAspect;
import org.romaframework.aspect.persistence.PersistenceAspect;
import org.romaframework.aspect.view.ViewConstants;
import org.romaframework.aspect.view.annotation.ViewField;
import org.romaframework.core.Roma;
import org.romaframework.module.users.domain.BaseAccount;
import org.romaframework.module.users.repository.BaseAccountRepository;
import org.romaframework.scaffolding.helper.PasswordPolicyVerifier;

public class ChangeUserPasswordPanel {
	private BaseAccount	entity;

	@ViewField(render = ViewConstants.RENDER_PASSWORD)
	private String			oldPassword;

	@ViewField(render = ViewConstants.RENDER_PASSWORD)
	private String			password;
	@ViewField(render = ViewConstants.RENDER_PASSWORD)
	private String			confirmPassword;

	public ChangeUserPasswordPanel(BaseAccount account) {
		this.entity = Roma.component(BaseAccountRepository.class).findByName(account.getName());
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public void save() {
		if (oldPassword == null) {
			if (this.password == null || !this.password.equals(confirmPassword)) {
				Roma.flow().alert("", "Inserire la vecchia password");
				return;
			}

		}
		try {
			String encodedPw = Roma.aspect(AuthenticationAspect.class).encryptPassword(oldPassword);
			if (!encodedPw.equals(entity.getPassword())) {
				Roma.flow().alert("", "Password errata");
				return;
			}
		} catch (NoSuchAlgorithmException e) {
		}
		if (this.password == null || !this.password.equals(confirmPassword)) {
			Roma.flow().alert("", "Password non valida o le password non corrispondono");
			return;
		}

		if (!Roma.component(PasswordPolicyVerifier.class).verify(password)) {
			Roma.flow().alert("", "La password non rispetta i criteri minimi di sicurezza");
			return;
		}
		entity.setPassword(password);
		entity = Roma.component(BaseAccountRepository.class).update(entity, PersistenceAspect.STRATEGY_DETACHING);
		Roma.flow().back();
	}

	public void cancel() {
		Roma.flow().back();
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

}
