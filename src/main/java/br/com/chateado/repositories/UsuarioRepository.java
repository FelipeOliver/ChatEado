package br.com.chateado.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.chateado.entities.Usuario;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String>{

	Usuario findByUsername(String username);

}
