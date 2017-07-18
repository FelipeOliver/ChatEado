package br.com.chateado.utils;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.chateado.ChatEadoApplication;
import br.com.chateado.entities.Conexao;
import br.com.chateado.entities.Usuario;
import br.com.chateado.repositories.ConexaoRepository;
import br.com.chateado.repositories.UsuarioRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ChatEadoApplication.class)
public class PopulaBaseMongoDB {

	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private ConexaoRepository conexaoRepository;
	
	@Test
	public void popula(){

		Usuario fbispo = new Usuario("fbispo", Base64Helper.encodeToken("123456"), false, false, false, false, Arrays.asList(new SimpleGrantedAuthority("USER")));
		Usuario foliver = new Usuario("foliver", Base64Helper.encodeToken("654321"), false, false, false, false, Arrays.asList(new SimpleGrantedAuthority("USER")));
		Usuario fsantaniello = new Usuario("fsantaniello", Base64Helper.encodeToken("11092017"), false, false, false, false, Arrays.asList(new SimpleGrantedAuthority("USER")));

		Conexao conexao1 = new Conexao(1L, "fbispoFoliver", 	  1L);
		conexaoRepository.save(conexao1);
		Conexao conexao2 = new Conexao(2L, "fbispoFsantaniello",  2L);
		conexaoRepository.save(conexao2);
		Conexao conexao3 = new Conexao(3L, "foliverFsantaniello", 3L);
		conexaoRepository.save(conexao3);
		Conexao conexao4 = new Conexao(4L, "Grupo 1", 			  4L);
		conexaoRepository.save(conexao4);
		
		List<Conexao> fbispoConversas = Arrays.asList(conexao1, conexao2, conexao4);
		fbispo.setConexoes(fbispoConversas);
		usuarioRepository.save(fbispo);
		
		List<Conexao> foliverConversas = Arrays.asList(conexao1, conexao3, conexao4);
		foliver.setConexoes(foliverConversas);
		usuarioRepository.save(foliver);

		List<Conexao> fsantanielloConversas = Arrays.asList(conexao2, conexao3, conexao4);
		fsantaniello.setConexoes(fsantanielloConversas);
		usuarioRepository.save(fsantaniello);
	}
}
