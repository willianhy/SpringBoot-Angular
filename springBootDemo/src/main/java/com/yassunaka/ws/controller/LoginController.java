package com.yassunaka.ws.controller;

import java.util.Date;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yassunaka.ws.model.Usuario;
import com.yassunaka.ws.service.UsuarioService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class LoginController {

	@Autowired
	private UsuarioService usuarioService;

	@RequestMapping(value = "/autenticar", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public LoginResponse autenticar(@RequestBody Usuario usuario) throws ServletException {
		if (usuario.getNome() == null || usuario.getSenha() == null)
			throw new ServletException("Nome e senha são obrigatórios");

		Usuario usuAutenticado = usuarioService.buscarPorNome(usuario.getNome());
		
		if (usuAutenticado == null)
			throw new ServletException("Usuário não encontrado");
		
		if (!usuAutenticado.getSenha().equals(usuario.getSenha()))
			throw new ServletException("Usuário ou senha inválida");
		
		String token = Jwts.builder()
				.setSubject(usuAutenticado.getNome())
				.signWith(SignatureAlgorithm.HS512, "banana")
				.setExpiration(new Date(System.currentTimeMillis() + 1*60 * 1000))
				.compact();

		return new LoginResponse(token);
	}

	private class LoginResponse {
		public String token;

		public LoginResponse(String token) {
			this.token = token;
		}
	}
}
