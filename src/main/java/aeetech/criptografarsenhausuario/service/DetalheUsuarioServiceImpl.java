package aeetech.criptografarsenhausuario.service;


import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import aeetech.criptografarsenhausuario.data.DetalheUsuarioData;
import aeetech.criptografarsenhausuario.model.UsuarioModel;
import aeetech.criptografarsenhausuario.repository.UsuarioRepository;

@Component
public class DetalheUsuarioServiceImpl implements UserDetailsService {
	
	private final UsuarioRepository repository;
	
	public DetalheUsuarioServiceImpl(UsuarioRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UsuarioModel> usuario = repository.findByLogin(username);
		if(usuario.isEmpty()) {
			throw new UsernameNotFoundException("usuario [" + username + "] não encontrado");
		}
		
		return new DetalheUsuarioData(usuario);
	}

}
