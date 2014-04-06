package org.romaframework.scaffolding.listener;

public interface SessionFieldRefreshListener<T> {

	public void onFieldRefresh(T refreshed, String fieldRefreshed);
}
