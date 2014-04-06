package org.romaframework.scaffolding.listener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.romaframework.aspect.session.IdentityWeakHashMap;
import org.romaframework.aspect.session.SessionInfo;
import org.romaframework.aspect.session.SessionListener;
import org.romaframework.core.Roma;
import org.romaframework.core.flow.Controller;
import org.romaframework.core.flow.FieldRefreshListener;
import org.romaframework.core.flow.SchemaFieldListener;
import org.romaframework.core.schema.SchemaFeaturesChangeListener;
import org.romaframework.core.schema.SchemaField;

public class RefreshManager implements FieldRefreshListener, SchemaFieldListener, SessionListener, SchemaFeaturesChangeListener {

	public RefreshManager() {
		Controller.getInstance().registerListener(FieldRefreshListener.class, this);
		Controller.getInstance().registerListener(SchemaFieldListener.class, this);
		Controller.getInstance().registerListener(SessionListener.class, this);
		Controller.getInstance().registerListener(SchemaFeaturesChangeListener.class, this);
	}

	private Map<SessionInfo, Map<Object, Set<SessionFieldRefreshListener<?>>>>						listeners					= new HashMap<SessionInfo, Map<Object, Set<SessionFieldRefreshListener<?>>>>();
	private Map<SessionInfo, Map<Object, Map<String, Set<SchemaFeaturesChangeListener>>>>	featureListeners	= new HashMap<SessionInfo, Map<Object, Map<String, Set<SchemaFeaturesChangeListener>>>>();

	public <T> void register(T toListen, SessionFieldRefreshListener<T> listener) {
		SessionInfo session = Roma.session().getActiveSessionInfo();
		if (session == null)
			return;
		Map<Object, Set<SessionFieldRefreshListener<?>>> objects = listeners.get(session);
		if (objects == null) {
			objects = new IdentityWeakHashMap<Object, Set<SessionFieldRefreshListener<?>>>();
			listeners.put(session, objects);
		}
		Set<SessionFieldRefreshListener<?>> curListeners = objects.get(toListen);
		if (curListeners == null) {
			curListeners = new HashSet<SessionFieldRefreshListener<?>>();
			objects.put(toListen, curListeners);
		}
		curListeners.add(listener);
	}

	public <T> void unRegister(T toListen, String iFieldToListen, SchemaFeaturesChangeListener listener) {
		Set<SchemaFeaturesChangeListener> curListeners = findFeatureListeners(iFieldToListen, toListen);
		if (curListeners == null)
			return;
		curListeners.remove(listener);
	}

	public <T> void register(T toListen, String iFieldToListen, SchemaFeaturesChangeListener listener) {
		SessionInfo session = Roma.session().getActiveSessionInfo();
		if (session == null)
			return;
		Map<Object, Map<String, Set<SchemaFeaturesChangeListener>>> objects = featureListeners.get(session);
		if (objects == null) {
			objects = new IdentityWeakHashMap<Object, Map<String, Set<SchemaFeaturesChangeListener>>>();
			featureListeners.put(session, objects);
		}
		Map<String, Set<SchemaFeaturesChangeListener>> curListeners = objects.get(toListen);
		if (curListeners == null) {
			curListeners = new HashMap<String, Set<SchemaFeaturesChangeListener>>();
			objects.put(toListen, curListeners);
		}
		if (curListeners.get(iFieldToListen) == null) {
			curListeners.put(iFieldToListen, new HashSet<SchemaFeaturesChangeListener>());
		}
		curListeners.get(iFieldToListen).add(listener);
	}

	public <T> void unRegister(T toListen, SessionFieldRefreshListener<T> listener) {
		Set<SessionFieldRefreshListener<?>> curListeners = findListeners(toListen);
		if (curListeners == null)
			return;
		curListeners.remove(listener);
	}

	private <T> Set<SchemaFeaturesChangeListener> findFeatureListeners(String field, T toListen) {
		SessionInfo session = Roma.session().getActiveSessionInfo();
		if (session == null)
			return null;
		Map<Object, Map<String, Set<SchemaFeaturesChangeListener>>> objects = featureListeners.get(session);
		if (objects == null)
			return null;
		if (objects.get(toListen) == null)
			return null;
		return objects.get(toListen).get(field);
	}

	private <T> Set<SessionFieldRefreshListener<?>> findListeners(T toListen) {
		SessionInfo session = Roma.session().getActiveSessionInfo();
		if (session == null)
			return null;
		Map<Object, Set<SessionFieldRefreshListener<?>>> objects = listeners.get(session);
		if (objects == null)
			return null;
		return objects.get(toListen);
	}

	@SuppressWarnings("unchecked")
	private void fireFieldRefresh(Object iContent, String fieldName) {
		Set<SessionFieldRefreshListener<?>> curListeners = findListeners(iContent);
		if (curListeners == null)
			return;
		for (SessionFieldRefreshListener<?> sessionFieldRefreshListener : curListeners) {
			SessionFieldRefreshListener<Object> list = (SessionFieldRefreshListener<Object>) sessionFieldRefreshListener;
			list.onFieldRefresh(iContent, fieldName);
		}
	}

	public void onFieldRefresh(SessionInfo iSession, Object iContent, SchemaField iField) {
		fireFieldRefresh(iContent, iField.getName());
	}

	public Object onBeforeFieldRead(Object iContent, SchemaField iField, Object iCurrentValue) {
		return SchemaFieldListener.IGNORED;
	}

	public Object onAfterFieldRead(Object iContent, SchemaField iField, Object iCurrentValue) {
		return iCurrentValue;
	}

	public Object onBeforeFieldWrite(Object iContent, SchemaField iField, Object iCurrentValue) {
		return SchemaFieldListener.IGNORED;
	}

	public Object onAfterFieldWrite(Object iContent, SchemaField iField, Object iCurrentValue) {
		fireFieldRefresh(iContent, iField.getName());
		return iCurrentValue;
	}

	public <T extends Object> void signalChangeAction(Object iUserObject, String iActionName, org.romaframework.core.schema.Feature<T> iFeature, T iOldValue, T iFeatureValue) {
		Set<SchemaFeaturesChangeListener> listeners = findFeatureListeners(iActionName, iUserObject);
		if (listeners == null)
			return;
		for (SchemaFeaturesChangeListener listener : listeners) {
			listener.signalChangeAction(iUserObject, iActionName, iFeature, iOldValue, iFeatureValue);
		}
	}

	public <T extends Object> void signalChangeClass(Object iUserObject, org.romaframework.core.schema.Feature<T> iFeature, T iOldValue, T iFeatureValue) {
		Set<SchemaFeaturesChangeListener> listeners = findFeatureListeners(iUserObject.getClass().getName(), iUserObject);
		if (listeners == null)
			return;
		for (SchemaFeaturesChangeListener listener : listeners) {
			listener.signalChangeClass(iUserObject, iFeature, iOldValue, iFeatureValue);
		}
	}

	public <T extends Object> void signalChangeField(Object iUserObject, String iFieldName, org.romaframework.core.schema.Feature<T> iFeature, T iOldValue, T iFeatureValue) {
		Set<SchemaFeaturesChangeListener> listeners = findFeatureListeners(iFieldName, iUserObject);
		if (listeners == null)
			return;
		for (SchemaFeaturesChangeListener listener : listeners) {
			listener.signalChangeField(iUserObject, iFieldName, iFeature, iOldValue, iFeatureValue);
		}
	}

	public void onSessionCreating(SessionInfo iSession) {
	}

	public void onSessionDestroying(SessionInfo iSession) {
		if (iSession == null)
			return;
		listeners.remove(iSession);
	}

}
