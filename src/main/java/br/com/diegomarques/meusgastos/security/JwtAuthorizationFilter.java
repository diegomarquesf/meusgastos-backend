package br.com.diegomarques.meusgastos.security;

import java.io.IOException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import br.com.diegomarques.meusgastos.domains.Usuario;
import br.com.diegomarques.meusgastos.domains.dtos.UsuarioDTO;
import br.com.diegomarques.meusgastos.services.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter{

	private JwtUtil jwtUtil;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ModelMapper mapper;
	
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
	}
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		String header = request.getHeader("Authorization");
		
		if(header != null && header.startsWith("Bearer ")) {
			UsernamePasswordAuthenticationToken auth = getAuthentication(header.substring(7));
			
			if(auth != null && auth.isAuthenticated()) {
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		
		chain.doFilter(request, response);
		
	}

	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		if(jwtUtil.isValidToken(token)) {
			String email =  jwtUtil.getUserName(token);
			UsuarioDTO usuarioDTO = usuarioService.findByEmail(email);
			Usuario usuario = mapper.map(usuarioDTO, Usuario.class);
			
			return new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
			
			
		}
		
		return null;
	}
	

}
