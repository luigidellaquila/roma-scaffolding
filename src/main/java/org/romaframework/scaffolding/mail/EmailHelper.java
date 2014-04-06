package org.romaframework.scaffolding.mail;

import org.romaframework.core.Roma;
import org.romaframework.core.config.ApplicationConfiguration;
import org.romaframework.module.mail.javamail.Authentication;
import org.romaframework.module.mail.javamail.HtmlMail;
import org.romaframework.module.mail.javamail.ServerConfiguration;

public class EmailHelper {

	public static void sendMail(String from, String to, String subject, String body, String authentication) {
		if (to == null || to.trim().length() == 0 || to.indexOf("@") < 0) {
			return;
		}
		if (authentication == null) {
			authentication = "default";
		}

		Authentication auth = Roma.component(ServerConfiguration.class).getAuthentications().get(authentication);
		if (auth == null) {
			return;
		}
		if (!(auth instanceof EmailAuthentication)) {
			return;
		}
		if (from == null || from.trim().length() == 0) {
			from = ((EmailAuthentication) auth).getSenderAddress();
			if (from == null || from.trim().length() == 0) {
				return;
			}
		}
		from = from.trim();
		HtmlMail mail = Roma.component(HtmlMail.class);
		if (!Roma.component(ApplicationConfiguration.class).isApplicationDevelopment()) {
			mail.sendMail(subject, body, from, to, authentication);
		}
	}
}
