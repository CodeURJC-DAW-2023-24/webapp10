package es.codeurj.mortez365.security;


import es.codeurj.mortez365.security.jwt.JwtRequestFilter;
import es.codeurj.mortez365.security.jwt.UnauthorizedHandlerJwt;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    @Autowired
    public RepositoryUserDetailsService userDetailService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private UnauthorizedHandlerJwt unauthorizedHandlerJwt;

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

    @Bean
public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("http://localhost:4200")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
                    .allowedHeaders("*")
                    .allowCredentials(true);
        }
    };
}
        
    @Bean 
    @Order(1)
    public SecurityFilterChain apFilterChain (HttpSecurity http ) throws Exception {
        http.authenticationProvider(authenticationProvider());

        http
			.securityMatcher("/api/**")
			.exceptionHandling(handling -> handling.authenticationEntryPoint(unauthorizedHandlerJwt));

        http
            .authorizeHttpRequests(authorize -> authorize
         
            .requestMatchers("/api/auth/login").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/events/championship/*").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/events/sport/*").permitAll()
          
            .requestMatchers(HttpMethod.POST, "/api/events/").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/api/events/").hasRole("ADMIN")
            .requestMatchers (HttpMethod.PUT, "/api/events/").hasRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/api/events/*").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/events/image/*").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/events/image/*").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/api/events/image/*").hasRole("ADMIN")
      
                  //Security of UserRestController
              
                  .requestMatchers(HttpMethod.GET, "/api/users/*").permitAll()
                  .requestMatchers(HttpMethod.DELETE,"/api/users/").hasRole("ADMIN")
                  .requestMatchers(HttpMethod.POST, "/api/users/").permitAll()
                  .requestMatchers(HttpMethod.PUT, "/api/users/").hasRole("ADMIN")
                  .requestMatchers(HttpMethod.GET, "/api/users/image/*").permitAll()
                  .requestMatchers(HttpMethod.POST, "/api/users/image/*").permitAll()
                  .requestMatchers(HttpMethod.DELETE, "/api/users/image/*").permitAll()
                  .requestMatchers(HttpMethod.PUT, "/api/users/image/*").permitAll()
                  //Cambiar luego
                  .requestMatchers(HttpMethod.PUT, "/api/users/image/*").permitAll()

                  //Security of WalletRestController
                    .requestMatchers(HttpMethod.GET, "/api/wallets/").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/api/wallets/").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/wallets/").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/wallets/").hasRole("ADMIN")

                    //Security of BetRestController
                    .requestMatchers(HttpMethod.GET, "/api/bets/").authenticated()
                    .requestMatchers(HttpMethod.GET, "/api/bets/*").authenticated()
                    .requestMatchers(HttpMethod.DELETE, "/api/bets/").authenticated()
                    .requestMatchers(HttpMethod.POST, "/api/bets/").authenticated()
                    .requestMatchers(HttpMethod.PUT, "/api/bets/").authenticated()
                    


                  .anyRequest().authenticated()

            );


            http.formLogin(formLogin -> formLogin.disable());

            http.csrf(csrf -> csrf.disable());

            http.httpBasic(httpBasic -> httpBasic.disable());

            http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

            http.addFilterBefore( jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

         return http.build();
    }


    @SuppressWarnings({ "deprecation" })
    @Bean
    @Order(2)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf((csrf) -> csrf
                .ignoringRequestMatchers("/updateValue", "/getValue", "/uploadProfilePicture")
        );

        http.authenticationProvider(authenticationProvider());
        
    

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
                       
                        .requestMatchers(HttpMethod.POST, "/updateValue").permitAll()
                        .requestMatchers(HttpMethod.GET, "/getValue").permitAll()
                        .requestMatchers(HttpMethod.POST, "/uploadProfilePicture").permitAll()
                        .requestMatchers(HttpMethod.GET, "/uploadProfilePicture").permitAll()

                  

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

      

        return http.build();
    }

}