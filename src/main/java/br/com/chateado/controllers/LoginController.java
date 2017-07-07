package br.com.chateado.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.chateado.entities.Usuario;
import br.com.chateado.repositories.UsuarioDao;
import br.com.chateado.services.UsuarioService;

@Controller
@CrossOrigin
public class LoginController {

	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private SimpMessagingTemplate template;
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ResponseEntity<String> setSession(@RequestBody Usuario usuario, HttpSession session){
		System.out.println("Usuário "+ usuario.getCodigo()+ " com a senha " + usuario.getSenha());
		boolean valid = usuarioService.validaUsuario(usuario);
		if(valid){
			usuarioService.setStatusUsuario(usuario, "S", usuario.getIdConversa());
//			usuario.setSenha("");
			try{
				session.setAttribute("usuario", new ObjectMapper().writeValueAsString(usuario));
			}catch(Exception e){
				e.printStackTrace();
			}
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}
		System.out.println("Usuário "+ usuario.getCodigo()+ " com a senha " + usuario.getSenha() + " não autorizado");
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value="/login/logout", method=RequestMethod.POST)
	public ResponseEntity<String> logout(@RequestBody Usuario usuario, HttpSession session){
		Long idConversa = usuario.getIdConversa();
		usuarioService.setStatusUsuario(usuario, "N", 0L);
		
		try{
			session.invalidate();
			template.convertAndSend("/server/usuariolist/" + idConversa, UsuarioDao.findListByIdConversa(idConversa));
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
}
