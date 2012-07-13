package de.akquinet.jbosscc.flux.bpmservice.utils;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.task.Content;
import org.jbpm.task.service.ContentData;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import de.akquinet.jbosscc.flux.bpmservice.exceptions.CanNotTransformContentToDataMapException;
import de.akquinet.jbosscc.flux.bpmservice.exceptions.CanNotTransformDataMapToContentDataException;
import de.akquinet.jbosscc.needle.annotation.ObjectUnderTest;
import de.akquinet.jbosscc.needle.junit.NeedleRule;

@Ignore
public class HumanTaskUtilsTest {

	@Rule
	public NeedleRule needleRule = new NeedleRule();

	@ObjectUnderTest
	private HumanTaskUtils humanTaskUtils;

	@Test
	public void testDataMapToContentDataAndContentToDataMap()
			throws CanNotTransformDataMapToContentDataException,
			CanNotTransformContentToDataMapException {

		Map<String, Object> firstMap = new HashMap<String, Object>();
		firstMap.put("testKey1", "testValue1");
		firstMap.put("testKey2", "testValue2");

		ContentData contentData = this.humanTaskUtils
				.dataMapToContentData(firstMap);
		assertNotNull(contentData);

		Content content = new Content(contentData.getContent());
		assertNotNull(content);

		Map<String, Object> secondMap = this.humanTaskUtils.contentToDataMap(
				content, null);
		assertEquals(secondMap, firstMap);
	}

	@Test
	public void testDataMapToResultContentAndContentToDataMap()
			throws CanNotTransformDataMapToContentDataException,
			CanNotTransformContentToDataMapException {

		String resultName = "Result";
		String key1 = "testKey1";
		String testValue1 = "testValue1";

		Map<String, Object> firstMap = new HashMap<String, Object>();
		firstMap.put(key1, testValue1);

		ContentData contentData = this.humanTaskUtils.dataMapToResultContentData(
				firstMap, resultName);
		assertNotNull(contentData);

		Content content = new Content(contentData.getContent());
		assertNotNull(content);

		Map<String, Object> secondMap = this.humanTaskUtils.contentToDataMap(
				content, null);
		assertNotNull(secondMap);

		@SuppressWarnings("unchecked")
		Map<String, Object> resultMap = (Map<String, Object>) secondMap
				.get(resultName);
		assertEquals(resultMap, firstMap);

		String value1 = (String) secondMap.get(key1);
		assertEquals(value1, testValue1);
	}
}
