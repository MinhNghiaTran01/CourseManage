package com.javaweb.course.config;

import com.javaweb.course.repository.UserRepository;
import com.javaweb.course.security.handeler.LocalLoginSuccessHandler;
import com.javaweb.course.security.jwt.JwtTokenFilter;
import com.javaweb.course.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

//    @Autowired
//    private CustomerOAuth2UserService oauth2UserService;

//    @Autowired
//    private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    @Autowired
    private LocalLoginSuccessHandler databaseLoginSuccessHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http = http.authorizeRequests()
                .antMatchers("/", "/user/auth/login", "/oauth/**"
                        , "/login", "/auth/register", "/user/**", "/ws/**", "/v1/**", "/admin/**").permitAll()
                .antMatchers("/products").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers("/admin/manageProduct").hasAnyAuthority("ROLE_ADMIN")
                .anyRequest().authenticated().and();
//                .antMatchers("*").permitAll().and();
        // login with form
        http
                .formLogin().permitAll()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .and();
//            .oauth2Login()
//            .loginPage("/login")
//            .userInfoEndpoint()
//            .userService(oauth2UserService)
//            .and()
//            .successHandler(oAuth2AuthenticationSuccessHandler);

        // Set unauthorized requests exception handler
//        http = http
//                .exceptionHandling()
//                .authenticationEntryPoint(
//                        (request, response, ex) -> {
//                            response.sendError(
//                                    HttpServletResponse.SC_UNAUTHORIZED,
//                                    ex.getMessage()
//                            );
//                        }
//                )
//                .and();

        http.addFilterBefore(jwtTokenFilter,
                UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}