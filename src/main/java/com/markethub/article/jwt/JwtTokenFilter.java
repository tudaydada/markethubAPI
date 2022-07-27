package com.markethub.article.jwt;

import com.markethub.article.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    AccountService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getToken(request);
            if (StringUtils.hasText(token)&&jwtTokenProvider.validateToken(token)){
                long accountId = jwtTokenProvider.getUserId(token);
                UserDetails userDetails = userService.getUserDetailById(accountId);
                if (userDetails!=null){
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,null,userDetails.getAuthorities()
                    );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        } catch (Exception e){
            logger.error(e);
        }
        filterChain.doFilter(request, response);
    }

    public String getToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if (StringUtils.hasText(header)&&header.startsWith("Bearer ")){
            return header.substring(7);
        }
        return null;
    }
}
