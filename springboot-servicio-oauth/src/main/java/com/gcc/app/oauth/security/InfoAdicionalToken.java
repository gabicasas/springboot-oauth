package com.gcc.app.oauth.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.stereotype.Component;

import com.gcc.app.common.usuarios.entity.Usuario;
import com.gcc.app.oauth.services.IUsuarioService;


/**
 * Este componente añade informacion a la que tiene el token por defecto de oauth2
 * @author gabriel.casascorral
 *
 */

@Component
public class InfoAdicionalToken extends TokenEnhancerChain {
	@Autowired
	IUsuarioService usuarioService;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		Usuario user=usuarioService.findByUsername(authentication.getName());
		System.out.println("Añadiendo datos al token");
		Map<String, Object> info= new HashMap<String,Object>();
		info.put("nombre", user.getNombre());
		info.put("apellido", user.getApellido());
		info.put("mail",user.getEmail());
		((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(info);
		return accessToken;
	}

	
	
}
