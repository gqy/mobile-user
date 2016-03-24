package com.fx.moile.user.rest.config;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;



public class UserApplication extends ResourceConfig{
	
    /**
	* Register JAX-RS application components.
	*/	
	public UserApplication(){
		register(RequestContextFilter.class);
		register(JacksonFeature.class);		//
	}

}

