package br.com.diegomarques.meusgastos.services;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import br.com.diegomarques.meusgastos.domains.Usuario;
import br.com.diegomarques.meusgastos.domains.dtos.UsuarioDTO;
import br.com.diegomarques.meusgastos.domains.dtos.UsuarioNewDTO;
import br.com.diegomarques.meusgastos.repositories.UsuarioRepository;
import br.com.diegomarques.meusgastos.services.exception.BadRequestException;
import br.com.diegomarques.meusgastos.services.exception.ObjectNotFoundException;

@Service
public class UsuarioService implements ICRUDService<UsuarioNewDTO, UsuarioDTO>{
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public List<UsuarioDTO> findAll() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		
		return usuarios.stream()
				.map(usuario -> mapper.map(usuario, UsuarioDTO.class)).collect(Collectors.toList());
	}

	@Override
	public UsuarioDTO findById(Long id) {
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		
		if(usuario.isEmpty()) {
			throw new ObjectNotFoundException("Não foi possivel encontrar o usuário com o id: " + id);
		}
		
		return mapper.map(usuario.get(), UsuarioDTO.class);
	}
	

	public UsuarioDTO findByEmail(String email) {
		Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
		
		if(usuario.isEmpty()) {
			throw new ObjectNotFoundException("Não foi possivel encontrar o usuário com o email: " + email);
		}
		
		return mapper.map(usuario.get(), UsuarioDTO.class);
	}

	@Override
	public UsuarioDTO create(UsuarioNewDTO dto) {
		validaUsuario(dto);
		Optional<Usuario> optusuario = usuarioRepository.findByEmail(dto.getEmail());
		
		if(optusuario.isPresent()) {
			throw new BadRequestException("já existe um usuário com esse email cadastrado");
		}
		
		Usuario usuario = mapper.map(dto, Usuario.class);
		usuario.setId(null);
		usuario.setDateCadastro(LocalDate.now());
		//encode
		usuario = usuarioRepository.save(usuario);
		return mapper.map(usuario, UsuarioDTO.class);
		
	}

	@Override
	public UsuarioDTO update(Long id, UsuarioNewDTO dto) {
		UsuarioDTO usuarioBanco = findById(id);
		validaUsuario(dto);
		Usuario usuario = mapper.map(dto, Usuario.class);
		usuario.setId(id);
		usuario.setDateInativacao(usuarioBanco.getDateInativacao());
		usuario.setDateCadastro(usuarioBanco.getDateCadastro());
		//encode
		usuario = usuarioRepository.save(usuario);
		return mapper.map(usuario, UsuarioDTO.class);
	}

	@Override
	public void delete(Long id) {
		
		Optional<Usuario> usuarioOPT = usuarioRepository.findById(id);
		if(usuarioOPT.isEmpty()) {
			throw new ObjectNotFoundException("Não foi possivel encontrar o usuário com o id: " + id);
		}
		
		Usuario usuario = usuarioOPT.get();
			usuario.setDateInativacao(LocalDate.now());
			usuarioRepository.save(usuario);
		
	}
	
	private void validaUsuario(UsuarioNewDTO dto) {
		
		if(dto.getEmail() == null || dto.getPassword() == null) {
			throw new BadRequestException("Email e Senha são obrigatorios");
		}
	}

}
