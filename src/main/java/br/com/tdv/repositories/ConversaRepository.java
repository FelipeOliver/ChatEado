package br.com.tdv.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.tdv.entities.Conversa;

public interface ConversaRepository extends MongoRepository<Conversa, Long>{

}