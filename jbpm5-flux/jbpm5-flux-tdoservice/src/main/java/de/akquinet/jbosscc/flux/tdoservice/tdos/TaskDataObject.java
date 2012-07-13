package de.akquinet.jbosscc.flux.tdoservice.tdos;

import java.util.Map;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
public interface TaskDataObject {

	void setDataMap(Map<String, Object> dataMap);

	String getTaskName();

	String getFormName();

	boolean isRevocable();

	boolean isClosable();

	String getFormDisplayName();
}
