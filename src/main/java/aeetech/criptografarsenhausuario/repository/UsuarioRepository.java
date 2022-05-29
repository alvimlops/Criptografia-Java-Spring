package aeetech.criptografarsenhausuario.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import aeetech.criptografarsenhausuario.model.UsuarioModel;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Integer>{
	
	public Optional<UsuarioModel> findByLogin(String login);


}
