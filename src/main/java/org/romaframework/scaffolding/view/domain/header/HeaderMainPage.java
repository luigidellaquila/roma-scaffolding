package org.romaframework.scaffolding.view.domain.header;

import org.romaframework.aspect.flow.annotation.FlowAction;
import org.romaframework.aspect.view.ViewConstants;
import org.romaframework.aspect.view.annotation.ViewAction;
import org.romaframework.aspect.view.annotation.ViewField;
import org.romaframework.core.Roma;
import org.romaframework.frontend.domain.message.Message;
import org.romaframework.frontend.domain.message.MessageResponseListener;
import org.romaframework.frontend.view.domain.RomaControlPanel;
import org.romaframework.module.users.domain.BaseAccount;
import org.romaframework.module.users.view.domain.ChangePassword;
import org.romaframework.scaffolding.view.domain.MainPage;

public class HeaderMainPage implements MessageResponseListener {

	@ViewField(render = ViewConstants.RENDER_OBJECTEMBEDDED)
	private MenuElementContainer<HeaderMenuElement>	menu;

	public HeaderMainPage() {
		menu = new MenuElementContainer<HeaderMenuElement>(Roma.schema().getSchemaClass(HeaderMenuElement.class), Roma.component(HeaderMenuElementComparator.class));
	}

	@ViewAction(label = "", render = ViewConstants.RENDER_BUTTON)
	public void home() {
		BaseAccount currAccount = Roma.session().getAccount();
		Roma.flow().forward(currAccount.getProfile().getHomePage(), "screen://body");
	}

	@ViewAction(label = "Cambio password", render = ViewConstants.RENDER_LINK)
	public void changePassword() {
		BaseAccount currAccount = Roma.session().getAccount();
		Roma.flow().forward(new ChangePassword(currAccount, this), "screen:popup");
	}

	@ViewAction(label = "Impostazioni", render = ViewConstants.RENDER_LINK)
	@FlowAction(next = RomaControlPanel.class, position = "screen://body")
	public void controlPanel() {
	}

	public void responseMessage(Message iMessage, Object iResponse) {
		home();
	}

	public MenuElementContainer<HeaderMenuElement> getMenu() {
		return menu;
	}

	public void setMenu(MenuElementContainer<HeaderMenuElement> menu) {
		this.menu = menu;
	}

	@ViewAction(render = ViewConstants.RENDER_LINK)
	public void disconnect() {
		Roma.flow().forward(new Object(), "header");
		Roma.flow().clearHistory();
		Roma.flow().forward(new MainPage(), "screen://body");
	}
}
