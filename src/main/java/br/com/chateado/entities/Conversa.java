package br.com.chateado.entities;

import java.util.List;

import org.springframework.data.annotation.Id;

public class Conversa {

	@Id
	private Long id;
	private List<Message> mensagens;
	private List<Usuario> usuariosOn;
	
	public Conversa(long id) {
		this.id = id;
	}
	public Conversa(){}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<Message> getMensagens() {
		return mensagens;
	}
	public void setMensagens(List<Message> mensagens) {
		this.mensagens = mensagens;
	}
	public List<Usuario> getUsuariosOn() {
		return usuariosOn;
	}
	public void setUsuariosOn(List<Usuario> usuariosOn) {
		this.usuariosOn = usuariosOn;
	}
	@Override
	public String toString() {
		return "Conversa [id=" + id + ", mensagens=" + mensagens + ", usuariosOn=" + usuariosOn + "]";
	}
}
