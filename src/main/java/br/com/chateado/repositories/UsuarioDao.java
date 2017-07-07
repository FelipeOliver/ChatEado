package br.com.chateado.repositories;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.chateado.entities.Usuario;
import br.com.chateado.utils.Base64Helper;

@Component
public class UsuarioDao {

	public static final List<Usuario> usuarios = Arrays.asList(new Usuario("fbispo", Base64Helper.encodeToken("123456"), "N"), new Usuario("foliver", Base64Helper.encodeToken("654321"), "N"));

//	public static List<Usuario> getListUsuario() {
//		return Arrays.asList(new Usuario("fbispo", Base64Helper.encodeToken("123456"), "N"), new Usuario("foliver", Base64Helper.encodeToken("654321"), "N"));
//	}

	public boolean existsByCodigoAndSenha(Usuario usuario) {
		boolean valido = false;
		Usuario u = findUser(usuario);
		if(u.getSenha().equals(usuario.getSenha())){
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
	
	public void setStatus(Usuario usuario, String status, Long idConversa) {
		Usuario u = findUser(usuario);
		u.setStatusString(status);
		u.setIdConversa(idConversa);
	}
	
	public Usuario findUser(Usuario usuario){
		List<Usuario> list = usuarios
				.stream()
					.filter(x -> x.getCodigo().equals(usuario.getCodigo()) == true)
						.collect(Collectors.toList());
		return list.get(0);
	}
}
