package com.br.smallmanager.apismallManager.config;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration; 
import com.br.smallmanager.apismallManager.exeptions.JwtAuthenticationEntryPoint;
import com.br.smallmanager.apismallManager.exeptions.JwtAutheticationTokenFilter;
 
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthEntryPoint;
	@Autowired
	private UserDetailsService userDetailsService;
	 
	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public JwtAutheticationTokenFilter authenticationTokenFilterBean() throws Exception {
		return new JwtAutheticationTokenFilter();
	}
	
		@Override
	    @Bean
	    public AuthenticationManager authenticationManagerBean() throws Exception {
	        return super.authenticationManagerBean();
	    }
	 
		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");
		}
		
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception{
		httpSecurity.cors().configurationSource(request-> new CorsConfiguration().applyPermitDefaultValues())
		.and().csrf().disable()
		.exceptionHandling().authenticationEntryPoint(jwtAuthEntryPoint).and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.authorizeRequests()
		.antMatchers(HttpMethod.GET,
				"/",
				"/*.html",
				"favicon.ico",
				"/**/*.html",
				"/**/*.css",
				"/**/*.js"
				).permitAll()
				.antMatchers("/api/auth/**").permitAll() 
				.antMatchers(HttpMethod.POST,"/api/usuario/cadastrar/").permitAll()
				.antMatchers("/api/usuario/cadastrar/").permitAll()
				.antMatchers(HttpMethod.POST,"/api/usuario/uploadFotoStorage/").permitAll();
				 
		httpSecurity.addFilterBefore(authenticationTokenFilterBean(),UsernamePasswordAuthenticationFilter.class);
		 
		httpSecurity.headers().cacheControl();
	}
 
}
