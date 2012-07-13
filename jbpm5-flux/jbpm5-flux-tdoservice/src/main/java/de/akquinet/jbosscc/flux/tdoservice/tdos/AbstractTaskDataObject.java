package de.akquinet.jbosscc.flux.tdoservice.tdos;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Ein TaskDataObject wird für die verwendung von TaskForms benötigt.
 * 
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
public abstract class AbstractTaskDataObject implements TaskDataObject,
		Serializable {

	private static final long serialVersionUID = 1L;

	private Map<String, Object> dataMap;
	private boolean isRevocable;
	private boolean isClosable;
	private String formName;
	private String taskName;

	public AbstractTaskDataObject() {
		this.dataMap = new HashMap<String, Object>();
	}

	public final void setDataMap(final Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public final String getTaskName() {
		return this.taskName;
	}

	public final String getFormName() {
		return this.formName;
	}

	protected final Object getValue(final String key) {
		return this.dataMap.get(key);
	}

	protected final void setValue(final String key, final Object value) {
		this.dataMap.put(key, value);
	}

	public final boolean isRevocable() {
		return this.isRevocable;
	}

	public final boolean isClosable() {
		return this.isClosable;
	}

	public final void setIsRevocable(final boolean isRevocable) {
		this.isRevocable = isRevocable;
	}

	public final void setIsClosable(final boolean isClosable) {
		this.isClosable = isClosable;
	}

	public final void setFormName(final String formName) {
		this.formName = formName;
	}

	public final void setTaskName(final String taskName) {
		this.taskName = taskName;
	}

	public final String getFormDisplayName() {
		return this.taskName;
	}
}
