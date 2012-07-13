package de.akquinet.jbosscc.flux.taskitems.service;

import javax.enterprise.inject.Produces;

import org.jboss.logging.Logger;

public class TestLoggerProducer {

	@Produces
	public Logger produceLogger() {
		return null;
	}
}
