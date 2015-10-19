/**
 * 
 */
package com.java.blog.web.config;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.java.blog.config.AppConfig;
import com.java.blog.web.controllers.AdminFilter;

/**
 * @author Semir
 *
 */
public class SpringWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer 
{

	@Override
	protected Class<?>[] getRootConfigClasses()
	{
		return new Class<?>[] { AppConfig.class};
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

	@Override
    protected Filter[] getServletFilters() {
       return new Filter[]{
//    		   new AdminFilter(),
    		   new DelegatingFilterProxy("springSecurityFilterChain"),
    		   new OpenEntityManagerInViewFilter()
    		   };
    } 

//	  @Override
//	  protected Filter[] getServletFilters() {
//	    return new Filter[] {
//	      new AdminFilter();
//	    };
//	  }
	
	
	@Override
	  public void onStartup(ServletContext container) throws ServletException {
	      container.addFilter("AdminFilter", AdminFilter.class)
          .addMappingForUrlPatterns(null, false, "/*");
	  }

}