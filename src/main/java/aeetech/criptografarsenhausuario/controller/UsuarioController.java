package aeetech.criptografarsenhausuario.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aeetech.criptografarsenhausuario.model.UsuarioModel;
import aeetech.criptografarsenhausuario.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
	
	private final UsuarioRepository repository;
	private final PasswordEncoder encoder;
	
	public UsuarioController(UsuarioRepository repository, PasswordEncoder encoder) {
		this.repository = repository;
		this.encoder = encoder;
	}
	
	@GetMapping("/listarTodos")
	public ResponseEntity<List<UsuarioModel>> listarTodos(){
		
		return ResponseEntity.ok(repository.findAll());
		
	}
	
	@PostMapping("/salvar")
	public ResponseEntity<UsuarioModel> salvar(@RequestBody UsuarioModel usuario){
		usuario.setPassword(encoder.encode(usuario.getPassword()));
		return ResponseEntity.ok(repository.save(usuario));
	}

}
