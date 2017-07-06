package br.com.tdv.services;

import br.com.tdv.entities.Message;

public class ChatBot {

	private final static String charInitial = "#";
	
	public Message analiseMensagem(Message message){
		boolean haveCommand = false;
		Message mensagemRetorno = new Message();
		String corpo = message.getCorpo();
		String[] strings = corpo.split(" ");
		
		for(int i = 0; i < strings.length; i++){
			if( charInitial.equals(strings[i].substring(0, 1)) ){
				haveCommand = true;
				mensagemRetorno.setCorpo("Teste funcionou!");
			}
		}
		
		if(haveCommand){
			return mensagemRetorno;
		}else{
			return null;
		}
	}
}
