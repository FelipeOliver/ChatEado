package br.com.chateado.entities;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class Usuario extends User{
	
	public Usuario(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}
	public Usuario(){
		super("user", "password", false, false, false, false, Arrays.asList(new SimpleGrantedAuthority("USER")));
	}
	private static final long serialVersionUID = 1L;
	
	//	private String codigo;
	//	private String senha;
	private boolean status;
	private Long idConversa;
	private List<Conexao> conexoes;
	
//	public Usuario(String codigo, String senha, String status) {
//		this.setCodigo(codigo);
//		this.setSenha(senha);
//		this.setStatusString(status);
//	}
//	public Usuario(){
//		
//	}
//	public String getCodigo() {
//		return codigo;
//	}
//	public void setCodigo(String codigo) {
//		this.codigo = codigo;
//	}
	public boolean getStatus() {
		return status;
	}
	public void setStatusString(String status) {
		this.status = status == "S";
	}
	public void setStatus(boolean status){
		this.status = status;
	}
//	public String getSenha() {
//		return senha;
//	}
//	public void setSenha(String senha) {
//		this.senha = senha;
//	}
	public Long getIdConversa() {
		return idConversa;
	}
	public void setIdConversa(Long idConversa) {
		this.idConversa = idConversa;
	}
	public List<Conexao> getConexoes() {
		return conexoes;
	}
	public void setConexoes(List<Conexao> conexoes) {
		this.conexoes = conexoes;
	}

}
