package br.com.diegomarques.meusgastos.security;


import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import br.com.diegomarques.meusgastos.domains.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	@Value("${auth.jwt.secret}")
	private String jwtSecret;
	
	@Value("${auth.jwt-expiration-milliseg}")
	private Long jwtExpiration;
	
	
	public String gerarToken(Authentication authentication) {
		// Data atual e soma com milliseconds da variavel
		Date dataExpiracao = new Date(new Date().getTime() + jwtExpiration);
		
		// Pegamos usuario atual da autenticação
		Usuario usuario = (Usuario) authentication.getPrincipal();
		
		try {
			//Gerar uma chave com base na nossa secret
			Key secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes("UTF-8"));
			
			//gera o token
			return Jwts.builder()
					.setSubject(usuario.getUsername())
					.setIssuedAt(new Date())
					.setExpiration(dataExpiracao)
					.signWith(secretKey)
					.compact();
					
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "";
		}
	}

	//Metodo que descobre qual as permissoes do usuario dentro do token com base na chave privada
	private Claims getClaims(String token) {
		
		try {
			Key secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes("UTF-8"));
			Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
			
			return claims;
			
		} catch (Exception e) {
			e.printStackTrace();
			
			return null ;
		}
	}
	
	
	//Pegar o email do usuario dentro do token
	public String getUserName(String token) {
		Claims claims = getClaims(token);
		
		if (claims == null) {
			return null;
		}
		
		return claims.getSubject();
	}
	
	//Validar o token
	public boolean isValidToken(String token) {
		Claims claims = getClaims(token);
		
		if (claims == null) {
			return false;
		}
		
		String email = claims.getSubject();
		Date dataExpiracao = claims.getExpiration();
		Date agora = new Date(System.currentTimeMillis());
		
		if (email != null && agora.before(dataExpiracao)) {
			return true;
		}
		
		return false;
	}
}
