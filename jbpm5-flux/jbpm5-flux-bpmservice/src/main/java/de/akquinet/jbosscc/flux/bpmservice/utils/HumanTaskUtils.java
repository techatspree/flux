package de.akquinet.jbosscc.flux.bpmservice.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.drools.KnowledgeBase;
import org.jboss.logging.Logger;
import org.jbpm.task.Content;
import org.jbpm.task.Task;
import org.jbpm.task.service.ContentData;
import org.jbpm.task.utils.ContentMarshallerHelper;

import de.akquinet.jbosscc.flux.bpmservice.deployment.data.WSHumanTaskHandlerDelegate;
import de.akquinet.jbosscc.flux.bpmservice.deployment.producer.EnvironmentProvider;
import de.akquinet.jbosscc.flux.bpmservice.deployment.qualifier.KBase;
import de.akquinet.jbosscc.flux.bpmservice.deployment.qualifier.WSHumanTaskHandler;
import de.akquinet.jbosscc.flux.bpmservice.exceptions.CanNotTransformContentToDataMapException;
import de.akquinet.jbosscc.flux.bpmservice.exceptions.CanNotTransformDataMapToContentDataException;

/**
 * Die Klasse stellt Hilfsmethoden für den Human-Task-Client und Human-Task-Server bereit
 * 
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Named
@SessionScoped
public class HumanTaskUtils implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@Inject
	@WSHumanTaskHandler
	private WSHumanTaskHandlerDelegate humanTaskHandlerDelegate;

	@Inject
	private EnvironmentProvider environmentProvider;

	@Inject
	@KBase
	private KnowledgeBase knowledgeBase;

	public HumanTaskUtils() {
		// NOP
	}

	/**
	 * Die Methode mappt die dataMap in eine übergeordnete ResultMap, um den kompletten Datensatz unter dem Namen resultParamName verfügbar zu machen.
	 * 
	 * @param dataMap DataMap, die gemappt werden soll
	 * @param resultParamName Name des ErgebnisParameters
	 * @return ResultMap
	 */
	public ContentData dataMapToResultContentData(
			final Map<String, Object> dataMap, final String resultParamName) {
		final Map<String, Object> dataResultMap = new HashMap<String, Object>();

		/*
		 * Ergebnisse einzeln verfügbar machen Erleichtert das handling im Prozess
		 */
		final Set<Entry<String, Object>> entrySet = dataMap.entrySet();
		for (final Entry<String, Object> entry : entrySet) {
			dataResultMap.put(entry.getKey(), entry.getValue());
		}

		dataResultMap.put(resultParamName, dataMap);

		try {
			return dataMapToContentData(dataResultMap);
		} catch (final CanNotTransformDataMapToContentDataException e) {
			log.error(e);
		}

		return null;
	}

	public ContentData dataMapToContentData(final Map<String, Object> dataMap)
			throws CanNotTransformDataMapToContentDataException {

		ContentData contentData = ContentMarshallerHelper.marshal(dataMap,
				environmentProvider.getEnvironment());

		return contentData;
	}

	@SuppressWarnings("rawtypes")
	public Map<String, Object> contentToDataMap(final Content content, Task task)
			throws CanNotTransformContentToDataMapException {

		final Map<String, Object> dataMap = new HashMap<String, Object>();

		Object contentObject = ContentMarshallerHelper.unmarshall(
				content.getContent(), environmentProvider.getEnvironment(),
				knowledgeBase.getClass().getClassLoader());
		if (contentObject instanceof Map) {
			final Map<?, ?> map = (Map) contentObject;
			for (final Map.Entry<?, ?> entry : map.entrySet()) {
				if (entry.getKey() instanceof String) {
					dataMap.put((String) entry.getKey(), entry.getValue());
				}
			}
		} else {
			log.info("contentObject is no Map");
		}
		return dataMap;
	}
}
