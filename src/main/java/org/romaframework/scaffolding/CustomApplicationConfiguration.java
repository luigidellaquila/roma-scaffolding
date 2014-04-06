package org.romaframework.scaffolding;


import org.romaframework.aspect.flow.FlowAspect;
import org.romaframework.aspect.view.ViewAspect;
import org.romaframework.aspect.view.command.impl.RedirectViewCommand;
import org.romaframework.aspect.view.screen.config.ScreenManager;
import org.romaframework.core.Roma;
import org.romaframework.core.config.AbstractApplicationConfiguration;
import org.romaframework.module.users.listener.DefaultLoginListener;
import org.romaframework.scaffolding.view.domain.CustomLogin;

/**
 * Application's configuration class valued by the Component Engine. <br/>
 * Use the 'configuration' field map to store application specific parameters.
 * 
 * @author #{author}
 * 
 */
public class CustomApplicationConfiguration extends AbstractApplicationConfiguration {

	public static final String	IS_IE_SESSION_PARAMETER	= "browserIE";

	public void startup() {
		// INSERT APPLICATION STARTUP HERE
	}

	public void shutdown() {
		// INSERT APPLICATION SHUTDOWN HERE
	}

	/**
	 * Callback called on every user connected to the application
	 */
	public void startUserSession() {
		if (Roma.session().getActiveSessionInfo().getAccount() != null) {
			new DefaultLoginListener().onSuccess();
		} else {
			CustomLogin login = new CustomLogin();
			Roma.view().setScreen(Roma.component(ScreenManager.class).getScreen("main-screen"));
			Roma.aspect(FlowAspect.class).forward(login);
		}
	}

	/**
	 * Callback called on every user disconnected from application
	 */
	public void endUserSession() {
		Roma.aspect(ViewAspect.class).pushCommand(new RedirectViewCommand("dynamic/common/logout.jsp"));
	}

	public String getStatus() {
		return STATUS_UNKNOWN;
	}
}
