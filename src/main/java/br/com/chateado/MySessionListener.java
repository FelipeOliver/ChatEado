package br.com.chateado;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.chateado.entities.Usuario;
import br.com.chateado.repositories.UsuarioDao;
import br.com.chateado.services.UsuarioService;

@Component
public class MySessionListener implements HttpSessionListener{

	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private SimpMessagingTemplate template;
	
	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		
		System.out.println(session.getAttribute("usuario"));
		try{
			Usuario usuario = new ObjectMapper().readValue(session.getAttribute("usuario").toString(), Usuario.class)  ;
			Long idConversa = usuario.getIdConversa();
			usuarioService.setStatusUsuario(usuario, "N", 0L);
			template.convertAndSend("/server/usuariolist/" + idConversa, UsuarioDao.usuarios);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
