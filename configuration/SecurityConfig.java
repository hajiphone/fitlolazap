package customlogin.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import customlogin.service.CustomUserDetailsServices;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	CustomUserDetailsServices customUserDetailsServices;

    @Bean
    static PasswordEncoder passwordEncoder() {
		
		return new  BCryptPasswordEncoder();
	}

    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(requests -> requests
                .requestMatchers("/register").permitAll()
                .requestMatchers("/home").permitAll()
                .requestMatchers("/save").permitAll()
                .requestMatchers("/cherche").permitAll()
                .requestMatchers("/all").permitAll())
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/home", true).permitAll())
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout").permitAll());
		
		return http.build() ;}

@Autowired
public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	
	auth.userDetailsService(customUserDetailsServices).passwordEncoder(passwordEncoder());
}
}




