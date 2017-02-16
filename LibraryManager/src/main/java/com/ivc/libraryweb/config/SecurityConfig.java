/*
 Информационно-вычислительный центр космодрома Байконур
 */
package com.ivc.libraryweb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.web.configuration.*;

@Configuration
@EnableWebSecurity
@ImportResource("classpath:security.xml")
/**
 * Задает конфигурацию для SpringSecurity. Импортирует  настройки из security.xml. security.xml 
 * содержит информацию о правах доступа, описывает роли пользователей user и 
 * administrator. 
 */
public class SecurityConfig extends WebSecurityConfigurerAdapter {

}
