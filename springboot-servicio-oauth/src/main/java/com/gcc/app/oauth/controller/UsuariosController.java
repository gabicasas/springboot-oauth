package com.gcc.app.oauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gcc.app.common.usuarios.entity.Usuario;
import com.gcc.app.oauth.client.UsuarioFeignClient;

@RestController
public class UsuariosController {

	@Autowired
	private UsuarioFeignClient usuarioFeignClient;

	/*public UsuariosController(UsuarioFeignClient usuarioFeignClient) {
		this.usuarioFeignClient = usuarioFeignClient;
	}*/
	
	
	
	
	//Esto fuhnciona o al menos deberia
	///@PreAuthorize("!hasAuthority('USER') || (authentication.principal == #email)")
	
	
	
	
	@GetMapping("/ver/{username}")
	public Usuario ver(@Param("username") String username) {
		System.out.println("AAAAAQQQQQUUUUIIIIII");
		return usuarioFeignClient.findByUsername(username);
		//return null;
	}
	
	@GetMapping("/test")
	public String test() {
		
		return "asdfsaf";
	}
	
	
}
