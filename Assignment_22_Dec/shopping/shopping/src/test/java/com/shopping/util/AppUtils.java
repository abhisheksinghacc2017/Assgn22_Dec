package com.shopping.util;

import java.util.Arrays;
import java.util.Date;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class AppUtils {
	
	public static String generateJwtToken_Admin() {
			
			String str = JWT.create()
						   .withSubject("admin")
						   .withIssuedAt(new Date(System.currentTimeMillis()))
						   .withExpiresAt(new Date(System.currentTimeMillis()+(1000*60*60*5)))
						   .withClaim("roles", Arrays.asList( "ROLE_ADMIN"))
						   .sign(Algorithm.HMAC256("helloworld"));
					
			return "Bearer "+ str ;
			
	}
	
	
	public static String generateJwtToken_Customer() {
		
		String str = JWT.create()
					   .withSubject("customer")
					   .withIssuedAt(new Date(System.currentTimeMillis()))
					   .withExpiresAt(new Date(System.currentTimeMillis()+(1000*60*60*5)))
					   .withClaim("roles", Arrays.asList( "ROLE_CUSTOMER"))
					   .sign(Algorithm.HMAC256("helloworld"));
				
		return "Bearer "+ str ;
		
}

}
