package es.codeurj.mortez365.security;


import es.codeurj.mortez365.security.jwt.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    @Autowired
    public RepositoryUserDetailsService userDetailService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    private final AuthenticationConfiguration authConfiguration;

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
    
          DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    
          authProvider.setUserDetailsService(userDetailService);
    
          authProvider.setPasswordEncoder(passwordEncoder());
    
          return authProvider;
    
    }


    @SuppressWarnings({ "deprecation" })
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http.authenticationProvider(authenticationProvider());
        
        http.csrf().ignoringRequestMatchers("/api/events/*");
        http.csrf().ignoringRequestMatchers("/api/auth/login");
        http.csrf().ignoringRequestMatchers("/updateValue");
        http.csrf().ignoringRequestMatchers("/getValue");
        http.csrf().ignoringRequestMatchers("/api/users/*");

        http.authorizeRequests(authorize -> authorize
                        .requestMatchers("/css/**", "/js/**", "/img/**", "/assets/**", "/scss/**", "/vendor/**", "/video/**", "/fragments/**").permitAll()

                        // PERMIT ALL
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/index").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/bets").permitAll()
                        .requestMatchers("/bets/json").permitAll()      // Permitting load more even if the user is not authenticated
                        .requestMatchers("/contact").permitAll()
                        .requestMatchers("/about").permitAll()
                        .requestMatchers("/responsablegame").permitAll()
                        .requestMatchers("/games").permitAll()
                        .requestMatchers("/slots").permitAll()
                        .requestMatchers("/api/events/*").hasRole("ADMIN")
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/events/championship/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/events/sport/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/events/").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/events/").hasRole("ADMIN")
                        .requestMatchers (HttpMethod.PUT, "/api/events/").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/events/").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/events/image/*").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/updateValue").permitAll()
                        .requestMatchers(HttpMethod.GET, "/getValue").permitAll()


                        //Security of UserRestController
                        .requestMatchers("/api/users/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/users/").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/users/").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/users/").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/users/").hasRole("ADMIN")

                        // PRIVATE PAGES
                        .requestMatchers("/betsadmin").hasRole("ADMIN")
                 
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .failureUrl("/loginerror")
                        .defaultSuccessUrl("/index")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/index")
                        .permitAll()
                );

        // Add JWT Token filter
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}