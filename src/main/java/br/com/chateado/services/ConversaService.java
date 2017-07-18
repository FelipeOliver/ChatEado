package br.com.chateado.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.chateado.entities.Conversa;
import br.com.chateado.entities.Message;
import br.com.chateado.entities.Usuario;
import br.com.chateado.repositories.ConversaRepository;

@Service
public class ConversaService {

	@Autowired
	private ConversaRepository conversaRepository;
	@Autowired
	private UsuarioService usuarioService;

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
	
	public void entrar(String username, Long idConversa){
		Conversa conversa = this.conversaRepository.findOne(idConversa);
		if(conversa != null){
			System.out.println(conversa.toString());
			if(conversa.getUsuariosOn() == null){
				conversa.setUsuariosOn(new ArrayList<Usuario>());
			}
			List<Usuario> list = conversa.getUsuariosOn().stream().filter(x -> x.getUsername().equals(username)).collect(Collectors.toList());
			if(list.size() <= 0){
				Usuario usuario = usuarioService.findOne(username);
				this.sair(usuario.getUsername(), usuario.getIdConversa());
				usuario.setIdConversa(idConversa);
				this.usuarioService.save(usuario);
				conversa.getUsuariosOn().add(usuario);
				this.conversaRepository.save(conversa);
			}
		}
	}
	
	public void sair(String username, Long idConversa){
		if(idConversa != null){
			Conversa conversa = this.conversaRepository.findOne(idConversa);
			if(conversa != null){
				if(conversa.getUsuariosOn() != null){
					List<Usuario> list = conversa.getUsuariosOn().stream().filter(x -> x.getUsername().equals(username)).collect(Collectors.toList());
					for (Usuario usuario : list) {
						System.out.println(usuario.getUsername());
						int index = conversa.getUsuariosOn().indexOf(usuario);
						conversa.getUsuariosOn().remove(index);
						
					}
					this.conversaRepository.save(conversa);
				}
			}
		}
	}
	
	public List<Usuario> findUsersOn(Long idConversa){
		Conversa conversa = this.conversaRepository.findOne(idConversa);
		if(conversa != null && conversa.getUsuariosOn() != null){
			return conversa.getUsuariosOn();
		}
		return new ArrayList<Usuario>();
	}
}
