package de.akquinet.jbosscc.flux.tdoservice.service;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.akquinet.jbosscc.flux.tdoservice.tdos.TaskDataObject;
import de.akquinet.jbosscc.flux.tdoservice.tdos.index.TdoIndexData;
import de.akquinet.jbosscc.flux.tdoservice.tdos.index.qualifier.TdoIndex;

@RunWith(Arquillian.class)
public class TaskDataObjectServiceTest {

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class)
				.addPackage("de.akquinet.jbosscc.flux.tdoservice.service")
				.addPackage("de.akquinet.jbosscc.flux.tdoservice.stereotypes")
				.addPackage("de.akquinet.jbosscc.flux.tdoservice.tdos")
				.addPackage("de.akquinet.jbosscc.flux.tdoservice.tdos.index")
				.addPackage("de.akquinet.jbosscc.flux.tdoservice.tdos.index.qualifier")
				.addClass(TaskDataObjectServiceTest.class)
				.addClass(TestTaskDataObject.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Inject
	@TdoIndex
	private TdoIndexData tdoIndexData;

	@Test
	public void testFindDefaultTdo() {
		TaskDataObject tdo = tdoIndexData.getTdoByTaskName("DefaultDataObject");
		assertNotNull(tdo);
		assertEquals("default", tdo.getFormName());
	}

	@Test
	public void testFindTestTdo() {
		TaskDataObject tdo = tdoIndexData.getTdoByTaskName("Test Task");
		assertNotNull(tdo);
		assertEquals("testForm", tdo.getFormName());
	}

	@Test
	public void testFindNotBlaaTdo() {
		TaskDataObject tdo = tdoIndexData.getTdoByTaskName("blaaa");
		assertNull(tdo);
	}
}
