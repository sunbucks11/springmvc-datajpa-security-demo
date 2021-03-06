/**
 * 
 */
package com.java.blog.web.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.java.blog.config.AppConfig;
import com.java.blog.config.security.SecurityConfig;



/**
 * @author Semir
 *
 */
public class SpringWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer 
{

	@Override
	protected Class<?>[] getRootConfigClasses()
	{
		return new Class<?>[] { AppConfig.class, SecurityConfig.class};
		
		
		
	}
	
	@Override
	protected Class<?>[] getServletConfigClasses()
	{
		return new Class<?>[] { WebMvcConfig.class };
	}

	@Override
	protected String[] getServletMappings()
	{

		return new String[] { "/" };
	}

/*
	@Override
    protected Filter[] getServletFilters() {
       return new Filter[]{
    		   //new CharacterEncodingFilter(),
    		  // new AdminFilter(),
    		   new DelegatingFilterProxy("springSecurityFilterChain"),
    		   new OpenEntityManagerInViewFilter()
    		   };
    } 
    */
}