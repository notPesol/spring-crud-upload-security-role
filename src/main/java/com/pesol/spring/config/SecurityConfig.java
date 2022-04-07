package com.pesol.spring.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Bean
	@Override
	protected UserDetailsService userDetailsService() {
		return new MyUserDetailService();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests(req -> {
			req.antMatchers("/css/**", "/js/**").permitAll();
			req.antMatchers("/principal").authenticated();
			req.antMatchers("/admin/**", 
					"/products/add", "/products/delete/**", "/products/update/**").hasRole("ADMIN");
			req.antMatchers("/**").permitAll();

			
			}).exceptionHandling(exceptionHandling -> {
				exceptionHandling.accessDeniedPage("/access-denied");
			})
				.formLogin().loginPage("/login").permitAll().and()
				.logout()
					.deleteCookies("JSESSIONID").invalidateHttpSession(true)
							.logoutRequestMatcher(new AntPathRequestMatcher("/logout", HttpMethod.GET.name()));
	}
	
	
}
