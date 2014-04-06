package org.romaframework.scaffolding.view.domain.simplesearch;

import org.romaframework.core.Roma;

public class SimpleSearchListable {
	private Object	content;

	public SimpleSearchListable(Object content) {
		this.content = content;
	}

	public String getValue() {
		return content == null ? "" : content.toString();
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	@Override
	public String toString() {
		Roma.context().create();
		try {
			return content == null ? "" : content.toString();
		} finally {
			Roma.context().destroy();
		}
	}
}
