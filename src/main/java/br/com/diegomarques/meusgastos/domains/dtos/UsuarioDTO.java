package br.com.diegomarques.meusgastos.domains.dtos;

import java.time.LocalDate;

//Response
public class UsuarioDTO {

	private Long id;
	
	private String name;
	
	private String email;
	
	private String photo;
	
	private LocalDate dateInativacao;
	
	private LocalDate dateCadastro;

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public LocalDate getDateInativacao() {
		return dateInativacao;
	}

	public void setDateInativacao(LocalDate dateInativacao) {
		this.dateInativacao = dateInativacao;
	}

	public LocalDate getDateCadastro() {
		return dateCadastro;
	}

	public void setDateCadastro(LocalDate dateCadastro) {
		this.dateCadastro = dateCadastro;
	}
	
	
	
	
}
