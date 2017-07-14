package br.com.chateado.services;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import br.com.chateado.entities.Usuario;
import br.com.chateado.repositories.UsuarioDao;
import br.com.chateado.utils.Base64Helper;

@Service
public class UsuarioService implements AuthenticationProvider {

	@Autowired
	private UsuarioDao usuarioDao;
	
//	public boolean validaUsuario(Usuario usuario) {
	public boolean validaUsuario(String username, String password) {
		return usuarioDao.existsByCodigoAndSenha(username, password);
	}

	public void setStatusUsuario(String usuario, String status, Long idConversa) {
		usuarioDao.setStatus(usuario, status, idConversa);
	}

	public Authentication authenticate( Authentication authentication ) throws AuthenticationException {

	    String userName = authentication.getName().trim();
	    String password = authentication.getCredentials().toString().trim();
	    Authentication auth = null;

//	    String  role = login.getApplicationRole(userName, password, "ADMIN","DEVELOPER");
	    String  role = "USER";
	    System.out.println(userName + " - " + password);
	    boolean validaUsuario = this.validaUsuario(userName, Base64Helper.encodeToken(password));
	    System.out.println("O Usuario existe? " + validaUsuario);
        if (validaUsuario) {
	        Collection<GrantedAuthority> grantedAuths = Arrays.asList(new SimpleGrantedAuthority(role.trim()));
	        Usuario appUser = usuarioDao.findUser(userName);
	        auth = new UsernamePasswordAuthenticationToken(appUser, password, grantedAuths);
	        return auth;
        } else {
          return null;
        }
    }
	
    @Override
    public boolean supports(Class<? extends Object> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
