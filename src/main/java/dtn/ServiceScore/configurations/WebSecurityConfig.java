package dtn.ServiceScore.configurations;

import dtn.ServiceScore.filters.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;
    @Value("${api.prefix}")
    private String apiPrefix;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configure(http))
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> {
                    requests // Các API có thể truy cập mà không cần đăng nhập
                            .requestMatchers(POST, "/api/v1/users/register", "/api/v1/users/login").permitAll()
                            .requestMatchers(GET, "/api/v1/five_good/all", "/api/v1/events/all"
                                                    , "/api/v1/events/all/**"
                                                    ,"/api/v1/events/criteria/**","/swagger-ui/**"
                                    ,"/v3/api-docs","/v3/api-docs/**"
                            ,"/swagger-ui").permitAll()
                            .requestMatchers(POST,"/api/v1/points/batchAll/**"
                                                            ,"/api/v1/points/batch/**",
                                                             "/api/v1/points/**").hasAnyRole("HSV","LCD","BTV","CTSV")
                            .requestMatchers(PUT, "/api/v1/points/**"
                                                            ).hasAnyRole("HSV")
                            .requestMatchers(GET, "/api/v1/points/by-user").hasAnyRole("SV")
                            .requestMatchers(POST, "/api/v1/events/createEventImage"
                                                        ,"/api/v1/events/uploadImage/**").hasAnyRole("HSV","LCD","BTV","CTSV")
                            .requestMatchers(PUT, "/api/v1/events/**").hasAnyRole("HSV","LCD","BTV","CTSV")
                            .requestMatchers(GET, "/api/v1/events/my-events").hasAnyRole("HSV","LCD","BTV","CTSV")
                            .requestMatchers(POST, "/api/external-events").hasAnyRole("SV")
                            .requestMatchers(GET, "/api/external-events/pending").hasAnyRole("HSV")
                            .requestMatchers(GET, "/api/external-events/my-events").hasAnyRole("SV")
                            .requestMatchers(PUT, "/api/v1/five_good/delete/**").hasAnyRole("HSV")
                            .requestMatchers(POST, "/api/v1/five_good").hasAnyRole("HSV")
                            .requestMatchers(POST, "/api/v1/five_good_lcd").hasAnyRole("BTV")
                            .requestMatchers(PUT, "/api/v1/five_good_lcd/delete/**").hasAnyRole("BTV")
                            .requestMatchers(POST, "/api/v1/registrations/**").hasAnyRole("SV")
                            .requestMatchers(DELETE, "/api/v1/registrations/**").hasAnyRole("SV")
                            .requestMatchers(GET, "/api/v1/registrations/event/**"
                            ,"/api/v1/registrations/export/**").hasAnyRole("HSV","LCD","BTV","CTSV")

                            // Các request còn lại yêu cầu xác thực
                            .anyRequest().authenticated();

                });
        return http.build();
    }
//@Bean
//public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    http
//            .cors(cors -> cors.configure(http))
//            .csrf(AbstractHttpConfigurer::disable)
//            .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
//            .authorizeHttpRequests(requests -> {
//                requests.anyRequest().permitAll(); // Cho phép tất cả các request, không cần xác thực
//            });
//    return http.build();
//}
}
