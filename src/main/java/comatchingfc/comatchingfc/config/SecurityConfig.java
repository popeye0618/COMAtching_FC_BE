package comatchingfc.comatchingfc.config;

import comatchingfc.comatchingfc.auth.filter.AdminAuthenticationFilter;
import comatchingfc.comatchingfc.auth.jwt.JwtExceptionFilter;
import comatchingfc.comatchingfc.auth.jwt.JwtFilter;
import comatchingfc.comatchingfc.auth.jwt.JwtUtil;
import comatchingfc.comatchingfc.auth.service.AdminUserDetailsService;
import comatchingfc.comatchingfc.utils.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final SecurityUtil securityUtil;
    private final AdminUserDetailsService adminUserDetailsService;

    private static final List<String> CORS_WHITELIST = List.of(
            "https://fc.comatching.site",
            "https://bucheon.fc-comatching.site"
    );

    private static final List<String> ADMIN_WHITELIST = List.of(
            "/admin/register",
            "/admin/login",
            "/admin/check/**"
    );

    private static final List<String> USER_WHITELIST = List.of(
            "/user/login"
    );

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() { // security를 적용하지 않을 리소스
        return web -> web.ignoring()
                .requestMatchers("/error", "/favicon.ico");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationConfiguration authenticationConfiguration) throws Exception {
        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();

        // 관리자 인증 필터 설정
        AdminAuthenticationFilter adminAuthFilter = new AdminAuthenticationFilter(authenticationManager, adminUserDetailsService, jwtUtil, securityUtil);
        adminAuthFilter.setFilterProcessesUrl("/admin/login"); // 관리자 로그인 URL 설정

        http
                .cors(corsCustomizer -> corsCustomizer.configurationSource(corsConfigurationSource()));

        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);

        http
                .addFilterBefore(adminAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtExceptionFilter(), JwtFilter.class);


        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/test/match/**", "/auth/refresh",  "/api/participations").permitAll()
                        .requestMatchers(ADMIN_WHITELIST.toArray(new String[0])).permitAll()
                        .requestMatchers("/auth/admin/**").hasRole("ADMIN")
                        .requestMatchers(USER_WHITELIST.toArray(new String[0])).permitAll()
                        .requestMatchers("/auth/pending/**").hasRole("PENDING")
                        .requestMatchers("/auth/user/**").hasRole("USER")
                        .requestMatchers("/check-role").hasAnyRole("ADMIN", "PENDING", "USER")
                        .anyRequest().authenticated()
                );

        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(CORS_WHITELIST);
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
