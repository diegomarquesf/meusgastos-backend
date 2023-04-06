package br.com.diegomarques.meusgastos.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.diegomarques.meusgastos.domains.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>  {

	Optional<Usuario> findByEmail(String email);
}
