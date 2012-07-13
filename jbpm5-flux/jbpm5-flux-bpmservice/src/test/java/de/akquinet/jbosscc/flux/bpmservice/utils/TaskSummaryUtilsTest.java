package de.akquinet.jbosscc.flux.bpmservice.utils;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.jbpm.task.Status;
import org.jbpm.task.query.TaskSummary;
import org.junit.Rule;
import org.junit.Test;

import de.akquinet.jbosscc.flux.bpmservice.container.TaskXContainer;
import de.akquinet.jbosscc.needle.annotation.ObjectUnderTest;
import de.akquinet.jbosscc.needle.junit.NeedleRule;

public class TaskSummaryUtilsTest {

	@Rule
	public NeedleRule needleRule = new NeedleRule();

	@ObjectUnderTest
	private TaskSummaryUtils taskSummaryUtils;

	@Test
	public void testTransformToTaskSummaryContainerList() {
		Status taskStateFilter = Status.Ready;
		List<TaskSummary> taskSummaryList = new ArrayList<TaskSummary>();
		taskSummaryList.add(new TaskSummary(0l, 0l, "task1", null, null, Status.Ready, 0, false, null, null, null, null, null, null, 0));
		taskSummaryList.add(new TaskSummary(0l, 0l, null, null, null, Status.Completed, 0, false, null, null, null, null, null, null, 0));
		taskSummaryList.add(new TaskSummary(0l, 0l, "task2", null, null, Status.Ready, 0, false, null, null, null, null, null, null, 0));
		assertEquals(3, taskSummaryList.size());

		List<TaskXContainer> taskXContainerList = taskSummaryUtils.transformToTaskSummaryContainerList(taskSummaryList, taskStateFilter);
		assertEquals(2, taskXContainerList.size());
		assertEquals("task1", taskXContainerList.get(0).getName());
		assertEquals("task2", taskXContainerList.get(1).getName());
	}
}
