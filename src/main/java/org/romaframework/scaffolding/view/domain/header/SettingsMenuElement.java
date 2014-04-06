package org.romaframework.scaffolding.view.domain.header;

import org.romaframework.core.Roma;
import org.romaframework.frontend.view.domain.RomaControlPanel;

public class SettingsMenuElement extends HeaderMenuElement {

	@Override
	public void execute() {
		Roma.flow().forward(new RomaControlPanel(), "screen://body");
	}
}