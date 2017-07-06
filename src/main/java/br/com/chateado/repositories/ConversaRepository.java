package br.com.chateado.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.chateado.entities.Conversa;

public interface ConversaRepository extends MongoRepository<Conversa, Long>{

}