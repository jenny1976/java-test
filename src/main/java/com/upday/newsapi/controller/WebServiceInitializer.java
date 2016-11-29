package com.upday.newsapi.controller;

import com.upday.newsapi.configuration.WebConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
 
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
 
/**
 * //TODO
 * @author jschulz
 */
public class WebServiceInitializer implements WebApplicationInitializer {
 
    @Override
    public void onStartup(ServletContext container) {
 
        // Create the dispatcher servlet's Spring application context
        AnnotationConfigWebApplicationContext dispatcherServlet = new AnnotationConfigWebApplicationContext();
        dispatcherServlet.register(WebConfig.class);
             
        // Register and map the dispatcher servlet
        ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", new DispatcherServlet(dispatcherServlet));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
         
    }
 
 }
