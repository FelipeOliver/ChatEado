package br.com.chateado.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.chateado.entities.Conexao;

@Repository
public interface ConexaoRepository extends MongoRepository<Conexao, Long>{

}
