package com.bridgelabz.onlinebookstore.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;

import com.bridgelabz.onlinebookstore.service.IUserService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private IUserService userService;
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider ();
		auth.setUserDetailsService(userService);
		auth.setPasswordEncoder(bCryptPasswordEncoder());
		return auth;
	}
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable(); 
		http
			.authorizeRequests()
				.antMatchers("/all/**").permitAll()
				.antMatchers("/getmessage").hasRole("ADMIN")
				.antMatchers("/delete").hasRole("ADMIN")
				.anyRequest().authenticated()
				.and()
			.formLogin().permitAll()
				.and()
			.logout().permitAll();
	}
	

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/v2/api-docs",
//                                   "/configuration/ui",
//                                   "/swagger-resources/**",
//                                   "/configuration/security",
//                                   "/swagger-ui.html",
//                                   "/webjars/**");
//    }
   
	
//    @Override
//    public void configure(WebSecurity web) throws Exception {    
//        web.ignoring().antMatchers("/v2/api-docs/**");
//        web.ignoring().antMatchers("/swagger.json");
//        web.ignoring().antMatchers("/swagger-ui.html");
//        web.ignoring().antMatchers("/swagger-resources/**");
//        web.ignoring().antMatchers("/webjars/**");
//    }   
}