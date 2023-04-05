package br.com.diegomarques.meusgastos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.diegomarques.meusgastos.domains.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>  {

	List<Usuario> findByEmail(String email);
}
