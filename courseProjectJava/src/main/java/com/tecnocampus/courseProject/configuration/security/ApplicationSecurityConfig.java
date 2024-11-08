package com.tecnocampus.courseProject.configuration.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.sql.DataSource;

import com.tecnocampus.courseProject.configuration.security.jwt.JwtConfig;
import com.tecnocampus.courseProject.configuration.security.jwt.JwtTokenVerifierFilter;
import com.tecnocampus.courseProject.configuration.security.jwt.JwtUsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private DataSource dataSource;

    private static final String USERS_QUERY = "select id, password, enabled from user where id = ?";
    private static final String AUTHORITIES_QUERY = "select username, role from authorities where username = ?";

    private final JwtConfig jwtConfig;

    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, DataSource dataSource, JwtConfig jwtConfig) {
        this.passwordEncoder = passwordEncoder;
        this.dataSource = dataSource;
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*", "/*.html").permitAll()
                .antMatchers(HttpMethod.PUT,"/subscription/{userId}/{subscriptionId}/{amount}").hasRole("USER")
                .antMatchers(HttpMethod.PUT,"/subscriptionAdmin/{userId}/{subscriptionId}/{amount}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/subscription/{userId}/{subscriptionId}").hasRole("USER")
                .antMatchers(HttpMethod.DELETE,"/subscriptionAdmin/{userId}/{subscriptionId}").hasRole("ADMIN")
                .anyRequest()
                .authenticated()

                .and()
                .addFilter(new JwtUsernamePasswordAuthenticationFilter(authenticationManager(), jwtConfig))
                .addFilterAfter(new JwtTokenVerifierFilter(jwtConfig), JwtUsernamePasswordAuthenticationFilter.class)

                .csrf().disable()
                .cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(USERS_QUERY)
                .authoritiesByUsernameQuery(AUTHORITIES_QUERY)
                .passwordEncoder(passwordEncoder);
    }
}
