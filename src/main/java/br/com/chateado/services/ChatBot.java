package br.com.chateado.services;

import org.springframework.stereotype.Component;

import br.com.chateado.entities.Message;

@Component
public class ChatBot {

	private final static String charInitial = "#";
	
	public Message analiseMensagem(Message message){
		boolean haveCommand = false;
		Message mensagemRetorno = new Message();
		String corpo = message.getCorpo();
		String[] strings = corpo.split(" ");
		
		for(int i = 0; i < strings.length; i++){
			if( charInitial.equals(strings[i].substring(0, 1)) ){
				mensagemRetorno.setUsuario("ChatEadoBot");
				mensagemRetorno.setCorpo("BOT:");
				
				if(strings[i].equalsIgnoreCase("#OlaMundo")){
					mensagemRetorno.setCorpo(" " + mensagemRetorno.getCorpo() + "Olá " + message.getUsuario());
					haveCommand = true;
				}
				if(strings[i].equalsIgnoreCase("#OlaAlguem")){
					if( ( i + 1 ) < strings.length){
						mensagemRetorno.setCorpo(" " + mensagemRetorno.getCorpo() + "Olá " + strings[i + 1]);
						haveCommand = true;
					}
				}

				if(strings[i].equalsIgnoreCase("#Help")){
					mensagemRetorno.setCorpo(mensagemRetorno.getCorpo() + "\n " + 
											" Os comandos existentes são: "
										  + " \n\t * #OlaMundo: devole um 'Olá seu_nome' "
										  + " \n\t * #OlaAlguem nome: devole um 'Olá nome_passado' ");
					haveCommand = true;
				}
			}
		}
		if(haveCommand){
			return mensagemRetorno;
		}else{
			return message;
		}
	}
}
