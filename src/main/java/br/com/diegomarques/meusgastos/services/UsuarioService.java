package br.com.diegomarques.meusgastos.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.diegomarques.meusgastos.domains.dtos.UsuarioDTO;
import br.com.diegomarques.meusgastos.domains.dtos.UsuarioNewDTO;

@Service
public class UsuarioService implements ICRUDService<UsuarioNewDTO, UsuarioDTO>{

	@Override
	public List<UsuarioDTO> findAll() {
		return null;
	}

	@Override
	public UsuarioDTO findById(Long id) {
		return null;
	}

	@Override
	public UsuarioDTO create(UsuarioNewDTO dto) {
		return null;
	}

	@Override
	public UsuarioDTO update(Long id, UsuarioNewDTO dto) {
		return null;
	}

	@Override
	public void delete(Long id) {
		
	}

}
