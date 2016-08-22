package ba.pehli.facebook.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/login","/register","/res/**").permitAll()
			.anyRequest().access("hasRole('ROLE_USER')")
			.and().formLogin().loginPage("/login").defaultSuccessUrl("/home").failureUrl("/login?error=true")
			.and().logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout").invalidateHttpSession(true).deleteCookies("SESSIONID")
			.and().sessionManagement().invalidSessionUrl("/login").maximumSessions(1);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)	throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
			.authoritiesByUsernameQuery("select username,role from users where username=?")
			.usersByUsernameQuery("select username,password,enabled from users where username=?");
	}
	
	
	
}
