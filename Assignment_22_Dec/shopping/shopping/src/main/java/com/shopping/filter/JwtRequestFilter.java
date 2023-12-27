package com.shopping.filter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.impl.JWTParser;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		System.out.println("Request : "+ request.getServletPath());
		
		if (request.getServletPath().indexOf("/health") != -1
				|| request.getServletPath().indexOf("/swagger-ui") != -1
				|| request.getServletPath().indexOf("/v3/api-docs") != -1) {
			System.out.println("Bypassed request without authentication token check : "+ request.getServletPath());
			filterChain.doFilter(request, response);

		} else {	
			
			System.out.println("Checking authorization for request : "+ request.getServletPath() );

			String authorizeHeader = request.getHeader("Authorization");
			
			System.out.println("Authorization header : "+ authorizeHeader);

			if (authorizeHeader != null && authorizeHeader.startsWith("Bearer ")) {

				String token = authorizeHeader.substring("Bearer ".length());

				try {
					Algorithm a = Algorithm.HMAC256("helloworld");

					JWTVerifier v = JWT.require(a).build();

					DecodedJWT decodeJwt = v.verify(token);

					String name = decodeJwt.getSubject();

					System.out.println(name);

					Claim claim = decodeJwt.getClaim("roles");
					List<SimpleGrantedAuthority>  authorities = claim.asList(SimpleGrantedAuthority.class);
					

					UsernamePasswordAuthenticationToken upa = new UsernamePasswordAuthenticationToken(
							name, null, authorities);

					SecurityContextHolder.getContext().setAuthentication(upa);

					filterChain.doFilter(request, response);

				} catch (Exception e) {
					e.printStackTrace();
					filterChain.doFilter(request, response);
				}

			}

		}

	}

}
