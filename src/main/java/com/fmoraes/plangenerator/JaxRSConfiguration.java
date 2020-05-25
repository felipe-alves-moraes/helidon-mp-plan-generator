package com.fmoraes.plangenerator;


import com.fmoraes.plangenerator.resources.ConstraintViolationExceptionMapper;
import com.fmoraes.plangenerator.resources.PlanGeneratorResource;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Simple JAXRS Application that registers one resource class.
 */
@ApplicationScoped
@ApplicationPath("/resources")
public class JaxRSConfiguration extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(PlanGeneratorResource.class);
    }

    
    @Override
    public Set<Object> getSingletons() {
        return Set.of(new ConstraintViolationExceptionMapper());
    }

    
}
