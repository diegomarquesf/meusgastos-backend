package br.com.diegomarques.meusgastos.services;

import java.util.List;

public interface ICRUDService<req, res> {
	
	List<res> findAll();
	res findById(Long id);
	res create(req dto);
	res update(Long id, req dto);
	void delete(Long id);
	

}
