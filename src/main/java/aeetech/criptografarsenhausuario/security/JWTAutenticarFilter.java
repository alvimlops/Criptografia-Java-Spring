package aeetech.criptografarsenhausuario.security;

import java.io.IOException;

import javax.naming.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.multipart.support.AbstractMultipartHttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import aeetech.criptografarsenhausuario.data.DetalheUsuarioData;
import aeetech.criptografarsenhausuario.model.UsuarioModel;

public class JWTAutenticarFilter extends UsernamePasswordAuthenticationFilter {
	
	public static final int TOKEN_EXPIRACAO = 600_000;
    public static final String TOKEN_SENHA = "463408a1-54c9-4307-bb1c-6cced559f5a7";
	
	private final AuthenticationManager authenticationManager;
	
	public JWTAutenticarFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	 @Override
	    public Authentication  attemptAuthentication(HttpServletRequest request,
	                                                HttpServletResponse response) throws AuthenticationException {
	        try {
	            UsuarioModel usuario = new ObjectMapper()
	                    .readValue(request.getInputStream(), UsuarioModel.class);

	            return (Authentication) authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
	                    usuario.getLogin(),
	                    usuario.getPassword(),
	                    new ArrayList<>()
	            ));

	        } catch (IOException e) {
	            throw new RuntimeException("Falha ao autenticar usuario", e);
	        }

	    }
	
	 protected void successfulAuthentication(HttpServletRequest request,
	                                            HttpServletResponse response,
	                                            FilterChain chain,
	                                            Authentication authResult) throws IOException, ServletException {

	        DetalheUsuarioData usuarioData = (DetalheUsuarioData) ((org.springframework.security.core.Authentication) authResult).getPrincipal();

	        String token = JWT.create().
	                withSubject(usuarioData.getUsername())
	                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRACAO))
	                .sign(Algorithm.HMAC512(TOKEN_SENHA));

	        response.getWriter().write(token);
	        response.getWriter().flush();
	    }
	}
	
	


