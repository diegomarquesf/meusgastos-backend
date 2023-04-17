package br.com.diegomarques.meusgastos.domains.dtos;

public class LoginDTO {
	
	private String token;
	private UsuarioDTO usuarioResponse;
	
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public UsuarioDTO getUsuarioResponse() {
		return usuarioResponse;
	}
	public void setUsuarioResponse(UsuarioDTO usuarioResponse) {
		this.usuarioResponse = usuarioResponse;
	}
	
	

}
