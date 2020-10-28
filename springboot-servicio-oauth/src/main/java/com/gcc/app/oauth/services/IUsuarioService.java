package com.gcc.app.oauth.services;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.gcc.app.common.usuarios.entity.Usuario;

public interface IUsuarioService {

	
	public Usuario  findByUsername( String username);
	
	public Usuario update(Usuario usuario,  Long id);
}
