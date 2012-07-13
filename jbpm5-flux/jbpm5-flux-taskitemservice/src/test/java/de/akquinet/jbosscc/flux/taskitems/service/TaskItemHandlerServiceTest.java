package de.akquinet.jbosscc.flux.taskitems.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Map;

import javax.inject.Inject;

import org.drools.runtime.process.WorkItemHandler;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */

@RunWith(Arquillian.class)
public class TaskItemHandlerServiceTest {

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class)
				.addPackage("de.akquinet.jbosscc.flux.taskitems.index")
				.addPackage("de.akquinet.jbosscc.flux.taskitems.index.qualifier")
				.addPackage("de.akquinet.jbosscc.flux.taskitems.service")
				.addPackage("de.akquinet.jbosscc.flux.taskitems.stereotypes")
				.addClass(WorkItemHandler.class)
				.addClass(TaskItemHandlerServiceTest.class)
				.addClass(TestTaskItem.class).addClass(TestLoggerProducer.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Inject
	private TaskItemHandlerService taskItemIndex;

	@Test
	public void testLoadTestTaskItem() {
		Map<String, WorkItemHandler> taskItems = taskItemIndex.getTaskItems();
		assertNotNull(taskItems.get("testTaskItem"));
	}

	@Test
	public void testLoadNoTaskItem() {
		Map<String, WorkItemHandler> taskItems = taskItemIndex.getTaskItems();
		assertNull(taskItems.get("blaa"));
	}
}
