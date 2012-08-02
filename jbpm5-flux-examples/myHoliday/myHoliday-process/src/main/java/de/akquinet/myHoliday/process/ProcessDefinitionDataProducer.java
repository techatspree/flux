package de.akquinet.myHoliday.process;

import java.io.Serializable;

import javax.enterprise.inject.Produces;

import org.drools.io.Resource;
import org.drools.io.ResourceFactory;

import de.akquinet.jbosscc.flux.bpmservice.deployment.qualifier.BpmnResource;

/**
 * Producer um die Prozessdefinitionen in die KBase zu laden/injecten.
 * 
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
public class ProcessDefinitionDataProducer implements Serializable {

	private static final long serialVersionUID = 1L;

	// Name der Prozessdefinition
	private final String processDefinitionFileName = "requestForHoliday.bpmn";

	@Produces
	@BpmnResource
	public Resource createBpmResource() {
		return ResourceFactory.newClassPathResource(processDefinitionFileName);
	}

}
