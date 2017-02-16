/*
 Информационно-вычислительный центр космодрома Байконур
 */
package com.ivc.libraryweb.config;

import javax.servlet.ServletContext;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.stereotype.Component;

/**
 *Класс предназначен для регистрации фильтров  SpringSecurity.
 * @author Администратор
 */
@Component
public class WebApplicationInitializerImpl extends AbstractSecurityWebApplicationInitializer {
//
//    @Override
//    protected void afterSpringSecurityFilterChain(ServletContext servletContext) {
//       	appendFilters(servletContext, new CookisFilter());
//        
//    }
   
}
