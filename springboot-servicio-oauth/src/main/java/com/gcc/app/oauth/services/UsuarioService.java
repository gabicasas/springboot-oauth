package com.gcc.app.oauth.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gcc.app.common.usuarios.entity.Usuario;
import com.gcc.app.oauth.client.UsuarioFeignClient;

import feign.FeignException;


@Service
public class UsuarioService implements IUsuarioService,UserDetailsService //<-- Es propia de Spring para auntenticar el user
{

	private Logger log= LoggerFactory.getLogger(UsuarioService.class);
	
	@Autowired
	private UsuarioFeignClient usuarioFeignClient;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		try {
			
		
		Usuario user=usuarioFeignClient.findByUsername(username);
		if(user==null) {
			throw new UsernameNotFoundException (username + " no existe");
		}
		List<GrantedAuthority> authorities=user.getRoles()
				.stream()
				.map(rol -> new SimpleGrantedAuthority(    rol.getNombre()   ))
				.peek(autorithy -> log.info("Role: "+autorithy.getAuthority()))
				.collect(Collectors.toList());
			log.info("Usuario Logueado: "+user.getNombre());	
		return new User(user.getUsername(),user.getPassword(),user.getEnabled(),true,true, true, authorities);
		
		} catch (FeignException e) {
			throw new UsernameNotFoundException("No existe el usuario "+ username);
		}
		
	}

	@Override
	public Usuario update(Usuario usuario, Long id) {
		// TODO Auto-generated method stub
		return usuarioFeignClient.update(usuario, id);
	}

	@Override
	public Usuario findByUsername(String username) {
		// TODO Auto-generated method stub
		return usuarioFeignClient.findByUsername(username);
	}

}
