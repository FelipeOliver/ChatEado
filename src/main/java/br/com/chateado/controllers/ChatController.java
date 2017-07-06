package br.com.chateado.controllers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.chateado.entities.Message;
import br.com.chateado.entities.Usuario;
import br.com.chateado.repositories.UsuarioDao;
import br.com.chateado.services.ConversaService;

@Controller
@CrossOrigin
public class ChatController {
	
	@Autowired
	private ConversaService conversaService;
	
	@MessageMapping(value="/message")
	@SendTo(value="/server/sendmessage")
	public Message sendMessage(Message message){
		Calendar hoje = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		message.setDataEnvio(sdf.format(hoje.getTime()));
		return message;
	}
	
	@MessageMapping(value="/message/{idConversa}")
	@SendTo(value="/server/sendmessage/{idConversa}")
	public List<Message> sendMessageToUser(@DestinationVariable Long idConversa, Message message){
		Calendar hoje = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		message.setDataEnvio(sdf.format(hoje.getTime()));
		List<Message> listMessage = conversaService.addMensagem(idConversa, message);
		return listMessage;
	}

	@MessageMapping(value="/usuario/change/status")
	@SendTo(value="/server/usuariolist")
	public List<Usuario> changeStatus(){
		return UsuarioDao.usuarios;
	}
	
	@RequestMapping(value="/conversa/{id}/findall")
	public ResponseEntity<String> findAllMessagesOfConversa(@PathVariable("id") Long conversa){
		try {
			List<Message> list = this.conversaService.findListOfMessages(conversa);
			return new ResponseEntity<String>(new ObjectMapper().writeValueAsString(list ),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}