package com.fmoraes.plangenerator;


import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Simple JAXRS Application that registers one resource class.
 */
@ApplicationScoped
@ApplicationPath("/resources")
public class JaxRSConfiguration extends Application {
}
