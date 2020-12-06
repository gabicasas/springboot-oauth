package com.gcc.app.oauth.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;


@Configuration
@EnableAuthorizationServer // <!-- El javadoc dice las rutas para obtener token y refrescarlo
public class AuthorizationServerConfig  extends AuthorizationServerConfigurerAdapter
{
	
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	private Environment environment;
	
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	InfoAdicionalToken infoAdicionalToken;

	
	/**
	 * Aqui se configura la seguridad de la ruta a la que se envian los tokens jwt
	 *  (POST /oauth/authtorize)
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()") // tokenKeyAcces es /ouath/authorize y se configura como publici
		.checkTokenAccess("isAuthenticated()")
		
		; // checkToken access son las rutas que validan y se piden q esten autenticadas
		//super.configure(security);
		
	}

	/**
	 * Aqui se configuran las aplicaciones clientes
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients
		.inMemory() // autenticacion en memoria configurada en memoria 
		.withClient(environment.getProperty("config.security.oauth.client.id")) // id de app cliente
		.secret(passwordEncoder.encode(environment.getProperty("config.security.oauth.client.secret"))) //pass encodeado
		.scopes("read,","write") // permismo
		.authorizedGrantTypes("password","refresh_token") //tipos de autorizacion
		.accessTokenValiditySeconds(3600) //timeouts
		.refreshTokenValiditySeconds(3600);
//		.and() //para otro segundo cliente
//		.withClient("clientIdApp").secret(passwordEncoder.encode("12345"))
//		.scopes("read,","write")
//		.authorizedGrantTypes("password","refresh_token")
//		.accessTokenValiditySeconds(3600)
//		.refreshTokenValiditySeconds(3600)
		
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAdicionalToken,accessTokenConverter()));
		//Registra el autentication manager inyectadas q se crea en SpringSecurityConf
		endpoints.authenticationManager(authenticationManager)
		//Almacen de tokens
		.tokenStore(tokenStore())
		// Conversor de token que genera el token jwt
		.accessTokenConverter(accessTokenConverter())
		.userDetailsService(userDetailsService)
		.tokenEnhancer(tokenEnhancerChain);
		
		
		
	}
	//Almacen de tokens
	@Bean
	public JwtTokenStore tokenStore() {
		// TODO Auto-generated method stub
		return new JwtTokenStore(accessTokenConverter());
	}

	// Conversor de token que genera el token jwt
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter= new JwtAccessTokenConverter();
		jwtAccessTokenConverter.setSigningKey(environment.getProperty("config.security.ouath.jwt.key"));
		return jwtAccessTokenConverter;
	}
	
	

}
