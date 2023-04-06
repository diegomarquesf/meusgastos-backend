package br.com.diegomarques.meusgastos.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.diegomarques.meusgastos.domains.dtos.UsuarioDTO;
import br.com.diegomarques.meusgastos.domains.dtos.UsuarioNewDTO;
import br.com.diegomarques.meusgastos.services.UsuarioService;

@RestController
@CrossOrigin("*")
@RequestMapping("api/usuarios")
public class UsuarioResource {
	
	@Autowired
	private UsuarioService usuarioService;
	
	
	@GetMapping
	public ResponseEntity<List<UsuarioDTO>> findAll(){
		
		return ResponseEntity.ok().body(usuarioService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioDTO> findById(@PathVariable Long id){
		
		return ResponseEntity.ok().body(usuarioService.findById(id));
	}
	
	@PostMapping
	public ResponseEntity<UsuarioDTO> create(@RequestBody UsuarioNewDTO usuarioNewDTO){
		
		UsuarioDTO usuario = usuarioService.create(usuarioNewDTO);
		
		return new ResponseEntity<>(usuario, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UsuarioDTO> update(@PathVariable Long id, @RequestBody UsuarioNewDTO usuarioNewDTO){
		
		UsuarioDTO usuario = usuarioService.update(id, usuarioNewDTO);
		
		return new ResponseEntity<>(usuario, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id){
		
		usuarioService.delete(id);		
	}
	

}
