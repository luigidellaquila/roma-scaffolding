package org.romaframework.scaffolding.view.domain.header;

import org.romaframework.core.Roma;
import org.romaframework.frontend.domain.message.Message;
import org.romaframework.frontend.domain.message.MessageResponseListener;
import org.romaframework.frontend.domain.message.MessageYesNo;

public abstract class HeaderMenuElement extends AbstractMenuElement {

	private static final String	DEFAULT_MESSAGE					= "You are leaving the page.<br/> <b>Are you sure?</b>";
	private static final String	BUTTON_CONFIRM_REQUIRED	= "buttonConfirmRequired";

	@Override
	public void action() {
		String messageText = Roma.session().getProperty(BUTTON_CONFIRM_REQUIRED);

		if (messageText != null) {
			MessageResponseListener listener = new MessageResponseListener() {

				public void responseMessage(Message iMessage, Object iResponse) {
					if (Boolean.TRUE.equals(iResponse)) {
						HeaderMenuElement.super.action();
					}
				}
			};

			MessageYesNo message = new MessageYesNo("header", "Confirm required", listener, messageText);
			Roma.flow().popup(message);
		} else {
			super.action();
		}
	}

	public static void makeConfirmRequired() {
		makeConfirmRequired(DEFAULT_MESSAGE);
	}

	public static void makeConfirmRequired(String iMessage) {
		Roma.session().setProperty(BUTTON_CONFIRM_REQUIRED, iMessage);
	}

	public static void removeConfirmRequired() {
		Roma.session().setProperty(BUTTON_CONFIRM_REQUIRED, null);
	}
}
