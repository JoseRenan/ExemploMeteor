package br.edu.test.service;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.jboss.resteasy.plugins.interceptors.CorsFilter;

public class TestApplication extends Application {
	
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();

	public TestApplication() {
		
		// Multiple client-request: Cross-Filter
		CorsFilter filter = new CorsFilter();
		filter.getAllowedOrigins().add("*");
		filter.setAllowedMethods("POST, GET, DELETE, PUT, OPTIONS");
		filter.setAllowedHeaders("Content-Type");
		
		this.singletons.add(filter);
		
		// ADD YOUR RESTFUL RESOURCES HERE
		this.singletons.add(new TestService());		
	}

	public Set<Class<?>> getClasses() {
		return this.empty;
	}

	public Set<Object> getSingletons() {
		return this.singletons;
	}
	
}
