package br.com.chateado.repositories;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import br.com.chateado.entities.Usuario;
import br.com.chateado.utils.Base64Helper;

@Component
public class UsuarioDao {

	public static final List<Usuario> usuarios 
		= Arrays.asList(
				new Usuario("fbispo", Base64Helper.encodeToken("123456"), false, false, false, false, Arrays.asList(new SimpleGrantedAuthority("USER"))), 
				new Usuario("foliver", Base64Helper.encodeToken("654321"), false, false, false, false, Arrays.asList(new SimpleGrantedAuthority("USER"))),
				new Usuario("fsantaniello", Base64Helper.encodeToken("11092017"), false, false, false, false, Arrays.asList(new SimpleGrantedAuthority("USER"))));

	public boolean existsByCodigoAndSenha(String username, String password) {
		boolean valido = false;
		Usuario u = findUser(username);
		if(u.getPassword().equals(password)){
			valido = true && !u.getStatus();
		}
		return valido;
	}

	public static List<Usuario> findListByIdConversa(Long idConversa){
		List<Usuario> list = usuarios
				.stream()
					.filter(x -> x.getIdConversa() == idConversa)
						.collect(Collectors.toList());
		return list;
	}
	
	public void setStatus(String usuario, String status, Long idConversa) {
		Usuario u = findUser(usuario);
		u.setStatusString(status);
		u.setIdConversa(idConversa);
	}
	
	public Usuario findUser(String username){
		List<Usuario> list = usuarios
				.stream()
					.filter(x -> x.getUsername().equals(username) == true)
						.collect(Collectors.toList());
		return list.get(0);
	}
}
