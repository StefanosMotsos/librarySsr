package cf.library.libraryapp.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/index.html").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/users/register", "/users/success").permitAll()
                        .requestMatchers("/books/insert").hasAuthority("INSERT_BOOK")
                        .requestMatchers(HttpMethod.GET, "/books/edit/{uuid}").hasAuthority("EDIT_BOOKS")
                        .requestMatchers(HttpMethod.POST, "/books/edit").hasAuthority("EDIT_BOOKS")
                        .requestMatchers(HttpMethod.GET, "/books/update-success").hasAuthority("EDIT_BOOKS")
                        .requestMatchers(HttpMethod.POST, "/books/delete/{uuid}").hasAuthority("DELETE_BOOKS")
                        .requestMatchers(HttpMethod.GET, "/books/delete-success").hasAuthority("DELETE_BOOKS")
                        .requestMatchers("/books/**").hasAnyRole("ADMIN", "EMPLOYEE")
                        .requestMatchers("/users/**").hasRole("ADMIN")
                        .requestMatchers("/css/**", "/js/**", "/img/**", "/error").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        //.successHandler()
                        //.failureHandler()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
