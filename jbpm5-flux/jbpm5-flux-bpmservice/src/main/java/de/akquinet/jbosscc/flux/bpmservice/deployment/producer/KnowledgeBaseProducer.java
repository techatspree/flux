package de.akquinet.jbosscc.flux.bpmservice.deployment.producer;

import java.io.Serializable;
import java.util.Iterator;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.compiler.BPMN2ProcessFactory;
import org.drools.compiler.ProcessBuilderFactory;
import org.drools.io.Resource;
import org.drools.marshalling.impl.ProcessMarshallerFactory;
import org.drools.runtime.process.ProcessRuntimeFactory;
import org.jbpm.bpmn2.BPMN2ProcessProviderImpl;
import org.jbpm.marshalling.impl.ProcessMarshallerFactoryServiceImpl;
import org.jbpm.process.builder.ProcessBuilderFactoryServiceImpl;
import org.jbpm.process.instance.ProcessRuntimeFactoryServiceImpl;

import de.akquinet.jbosscc.flux.bpmservice.deployment.qualifier.BpmnResource;
import de.akquinet.jbosscc.flux.bpmservice.deployment.qualifier.KBase;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Named
@SessionScoped
public class KnowledgeBaseProducer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	@BpmnResource
	private Instance<Resource> processResources;

	@Produces
	@Named
	@KBase
	@ApplicationScoped
	public KnowledgeBase buildKnowledgeBase() {
		ProcessBuilderFactory.setProcessBuilderFactoryService(new ProcessBuilderFactoryServiceImpl());
		ProcessMarshallerFactory.setProcessMarshallerFactoryService(new ProcessMarshallerFactoryServiceImpl());
		ProcessRuntimeFactory.setProcessRuntimeFactoryService(new ProcessRuntimeFactoryServiceImpl());

		BPMN2ProcessFactory.setBPMN2ProcessProvider(new BPMN2ProcessProviderImpl());
		final KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

		final Iterator<Resource> iterator = processResources.iterator();

		while (iterator.hasNext()) {
			final org.drools.io.Resource resource = iterator.next();
			kbuilder.add(resource, ResourceType.BPMN2);
		}

		return kbuilder.newKnowledgeBase();

	}
}
