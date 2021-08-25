package com.myproject.restaurantvoting.config;

import com.myproject.restaurantvoting.error.exceptions.NotFoundException;
import com.myproject.restaurantvoting.model.Role;
import com.myproject.restaurantvoting.model.User;
import com.myproject.restaurantvoting.repository.UserRepository;
import com.myproject.restaurantvoting.security.SecurityUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    private final UserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            log.info("Authenticating {} ", email);
            User user = userRepository.findByEmailIgnoreCase(email.toLowerCase())
                    .orElseThrow(() -> new NotFoundException("User '" + email + "' was not found"));
            return new SecurityUser(user);
        };
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/restaurants/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                .antMatchers(HttpMethod.POST, "/api/restaurants/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/api/restaurants/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/api/restaurants/**").hasRole(Role.ADMIN.name())
                .antMatchers("/api/account").hasRole(Role.USER.name())
                .antMatchers(HttpMethod.POST, "/api/account/register").anonymous()
                .antMatchers("/api/admin/**").hasRole(Role.ADMIN.name())
                .antMatchers("/api/meals/**").hasRole(Role.ADMIN.name())
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable();
    }

    //https://stackoverflow.com/questions/55968110/angular-spring-with-cors-mapping-added-blocked-by-cors-policy-no-access-cont
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("*");
    }

    //https://stackoverflow.com/questions/37671125/how-to-configure-spring-security-to-allow-swagger-url-to-be-accessed-without-aut
    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**");//disable swagger security
    }
}
