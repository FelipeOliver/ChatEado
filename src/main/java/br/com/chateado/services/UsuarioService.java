package br.com.chateado.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.chateado.entities.Usuario;
import br.com.chateado.repositories.UsuarioDao;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioDao usuarioDao;
	
	public boolean validaUsuario(Usuario usuario) {
		return usuarioDao.existsByCodigoAndSenha(usuario);
	}

	public void setStatusUsuario(Usuario usuario, String status, Long idConversa) {
		usuarioDao.setStatus(usuario, status, idConversa);
	}

}
