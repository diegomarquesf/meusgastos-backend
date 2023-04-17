package br.com.diegomarques.meusgastos.security;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import br.com.diegomarques.meusgastos.domains.Usuario;
import br.com.diegomarques.meusgastos.domains.dtos.LoginDTO;
import br.com.diegomarques.meusgastos.domains.dtos.LoginNewDTO;
import br.com.diegomarques.meusgastos.domains.dtos.UsuarioDTO;
import br.com.diegomarques.meusgastos.resources.exception.StandardError;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private AuthenticationManager authenticationManager;
	private JwtUtil jwtUtil;
	
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager,JwtUtil jwtUtil){
		super();
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		
		setFilterProcessesUrl("/api/auth");
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
		
		try {
			LoginNewDTO login = new ObjectMapper().readValue(request.getInputStream(), LoginNewDTO.class);
			
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getSenha());
			Authentication auth = authenticationManager.authenticate(authToken);
			
			return auth;
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Usuário ou Senha Inválidos");
		}catch (Exception e) {
			throw new InternalAuthenticationServiceException(e.getMessage());
		}
		
	}
	
	@Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, 
    										FilterChain chain, Authentication authResult) throws IOException{

        Usuario usuario  = (Usuario) authResult.getPrincipal();
        String token = jwtUtil.gerarToken(authResult);

        UsuarioDTO usuarioResponse =  new UsuarioDTO();
        usuarioResponse.setId(usuario.getId());
        usuarioResponse.setEmail(usuario.getEmail());
        usuarioResponse.setName(usuario.getName());
        usuarioResponse.setPhoto(usuario.getPhoto());
        usuarioResponse.setDateInativacao(usuario.getDateInativacao());
        usuarioResponse.setDateCadastro(usuario.getDateCadastro());

        LoginDTO loginResponseDto = new LoginDTO();
        loginResponseDto.setToken("Bearer " + token);
        loginResponseDto.setUsuarioResponse(usuarioResponse);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(loginResponseDto));
    }
	
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, 
    											AuthenticationException failed)  throws IOException, ServletException {

        StandardError error = new StandardError(System.currentTimeMillis(), 401, "Unauthorized", failed.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        response.setCharacterEncoding("UTF-8"); 
        response.setContentType("application/json");
        response.getWriter().print(new Gson().toJson(error));
    }

}
