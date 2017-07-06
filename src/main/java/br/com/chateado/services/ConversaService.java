package br.com.chateado.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.chateado.entities.Conversa;
import br.com.chateado.entities.Message;
import br.com.chateado.repositories.ConversaRepository;

@Service
public class ConversaService {

	@Autowired
	private ConversaRepository conversaRepository;

	public List<Message> addMensagem(Long idConversa, Message message) {
		Conversa conversa = this.conversaRepository.findOne(idConversa);
		if(conversa == null){
			conversa = new Conversa();
			conversa.setId(idConversa);
			conversa.setMensagens(new ArrayList<Message>());
			message.setId(1L);
		}else{
			if(conversa.getMensagens().size() > 1){
			    final Comparator<Message> comp = (p1, p2) -> Long.compare( p1.getId(), p2.getId());
			    Message recente = conversa.getMensagens().stream()
			                              .max(comp)
			                              .get();
			    
			    message.setId(recente.getId() + 1L);
			}else{
				Message recente = conversa.getMensagens().get(0);
				message.setId(recente.getId() + 1L);
			}
		}
		
		conversa.getMensagens().add(message);
		conversa = conversaRepository.save(conversa);
		return conversa.getMensagens();
	}

	public List<Message> findListOfMessages(Long idConversa) {
		Conversa conversa = this.conversaRepository.findOne(idConversa);
		if(conversa == null){
			return new ArrayList<Message>();
		} else{
			return conversa.getMensagens();
		}
	}
	
	
}
