package fr.cogigroup.ingbank.configs;


import org.springframework.context.annotation.Configuration;
/*
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
*/

//@Configuration
//@EnableWebSecurity
public class WebSecurityConfig
//		extends WebSecurityConfigurerAdapter
{


	private static final String[] SWAGGER_URLS = {
			"/swagger-resources/**",
			"/swagger-ui.html",
			"/v2/api-docs",
			"/webjars/**"
	};

	/*@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
		//	.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests().antMatchers("/api/auth/login").permitAll().and()
			.authorizeRequests().antMatchers("/api/auth/register").permitAll()
			.and().authorizeRequests().antMatchers(SWAGGER_URLS).permitAll()
			.antMatchers("/api/accounts/**").permitAll()
			.anyRequest().authenticated()
		;

		//http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}*/
}
