package dtn.ServiceScore.filters;

import dtn.ServiceScore.components.JwtTokenUtil;
import dtn.ServiceScore.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    @Value("${api.prefix}")
    private String apiPrefix;

    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request
            , @NotNull HttpServletResponse response
            , @NotNull FilterChain filterChain) throws IOException {
        try {
            //filterChain.doFilter(request,response); // ai cung cho di qua
            if (isBypassToken(request)) {
                filterChain.doFilter(request, response);
                return;
            }
            final String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                final String token = authHeader.substring(7);
                final String phoneNumber = jwtTokenUtil.extractUserName(token);
                if (phoneNumber != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    User userDetails = (User) userDetailsService.loadUserByUsername(phoneNumber);
                    if (jwtTokenUtil.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(userDetails
                                        , null
                                        , userDetails.getAuthorities()
                                );
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return;
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }

    }

    private boolean isBypassToken(@NotNull HttpServletRequest request) {
        final List<Pair<String, String>> bypassTokens = Arrays.asList(
                // liet ke danh sach api khong can jwt
                // vd:
                Pair.of("api/v1/users/login", "POST"),
                Pair.of("api/v1/users/register", "POST"),
                Pair.of("/swagger-ui/", "GET"),
                Pair.of("/v3/api-docs", "GET"),
                Pair.of("/images/", "GET"),
                Pair.of("/api/v1/five_good/all", "GET"),
                Pair.of("/api/v1/events/all", "GET"),
                Pair.of("/api/v1/events/all/", "GET"),
                Pair.of("/api/v1/events/criteria/", "GET")


        );
        for (Pair<String, String> bypassToken : bypassTokens) {
            if (request.getServletPath().contains(bypassToken.getFirst()) &&
                    request.getMethod().equals(bypassToken.getSecond())) {
                return true;
            }
        }
        return false;
    }
}
