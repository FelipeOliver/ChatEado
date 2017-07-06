package br.com.chateado.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.chateado.entities.Usuario;
import br.com.chateado.repositories.UsuarioDao;

@Controller
@CrossOrigin
public class LoginController {

	@RequestMapping(value="/login/{usuario}")
	public ResponseEntity<String> setSession(@PathVariable("usuario") String usuario, HttpSession session){
		Usuario e = new Usuario();
		e.setCodigo(usuario);
		e.setStatus("Online");
		UsuarioDao.usuarios.add(e );
		session.setAttribute("usuario", usuario);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
}
