package com.gcc.app.oauth.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.gcc.app.common.usuarios.entity.Usuario;
import com.gcc.app.oauth.client.UsuarioFeignClient;
import com.gcc.app.oauth.services.IUsuarioService;

import feign.FeignException;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

	
	Logger log=LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);
	
	
	@Autowired
	IUsuarioService usuarioService;
	
	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		UserDetails user=(UserDetails) authentication.getPrincipal();
		
		log.info("USUARIO LOGUEADO "+user.getUsername());
	/*	try {
		Usuario usuario = usuarioService.findByUsername(user.getUsername());
		usuario.setIntentos(0);
		usuario.setEnabled(true);
		usuarioService.update(usuario, usuario.getId());
		}catch(FeignException e) {
			log.error("El usuario "+user.getUsername() + "no existe en es sistema o es la app cliente");
		}*/	
		
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		UserDetails user=(UserDetails) authentication.getPrincipal();
		log.error("USUARIO NO LOGUEADO "+user.getUsername());
	/*	try {
		Usuario usuario = usuarioService.findByUsername(user.getUsername());
		
		if(usuario.getIntentos()==null) {
			usuario.setIntentos(1);
		}else {
			usuario.setIntentos(usuario.getIntentos()+1);
		}
		
		log.error("Se han intentado loguear incorrectamente "+ usuario.getIntentos() + " veces");
		if(usuario.getIntentos()==3)
			usuario.setEnabled(false);
		usuarioService.update(usuario, usuario.getId());
		}catch(FeignException e) {
			log.error("El usuario "+user.getUsername() + "no exist en es sistema o es la app cliente");
		}	*/
		
	}

}
