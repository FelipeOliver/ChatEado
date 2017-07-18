app.controller('ChatController', ['$scope', '$http',
					function($scope, $http){
	var self = this;
	self.stompClient = null;
	self.messages = [];
	self.isConnected = false;
	self.idConversa = 1;
	self.conversasAtivas = [];
	
	self.reconnectChat = function(){
		//Config
		var socket = new SockJS('/chat/chat');
		self.stompClient = Stomp.over(socket);
		self.stompClient.debug = null
		//Variavel de controle de DOM
		self.setConnected(true);
		
		self.stompClient.connect({}, function (frame) {
			//Inscrição na lista de mensagens da conversa
			self.stompClient.subscribe('/server/sendmessage/' + self.idConversa, function (greeting) {
	    		self.setMessages(JSON.parse(greeting.body));
	    	});
			//Inscrição na lista de usuários Online 
			self.stompClient.subscribe('/server/usuariolist/' + self.idConversa, function (list) {
	    		self.setUsuariosList(list.body);
	    	});
			
			$http.get('/chat/conversa/entrar/' + self.idConversa +'/' + self.user.username)
	    	.then(function(resp){
	    		self.stompClient.send( "/app/usuario/change/status/" + self.idConversa, {}, {})
	    	}, function(e){
	    		alert(e);
	    	})
	    	
	    	$http.get('/chat/conversa/' + self.idConversa +'/findall')
	    	.then(function(resp){
	    		self.setMessages(resp.data);
	    	}, function(e){
	    		alert(e);
	    	})
	    });
	}
	
	self.closeConversation = function(idConversa){
		var result = $.grep(self.conversasAtivas, function(e){ return e.idConversa == idConversa; });
		var s = self.conversasAtivas.indexOf(result[0]);
		if(s > -1){
			self.conversasAtivas.splice(s, 1);
		}
		if(self.conversasAtivas[0]){
			self.setConversation(self.conversasAtivas[0]);
		}else{
			self.idConversa = undefined;
			self.desconnectChat();
		}
	}
	
	self.setConversation = function(conv){
		
		var result = $.grep(self.conversasAtivas, function(e){ return e.idConversa == conv.idConversa; });
		var s = self.conversasAtivas.indexOf(result[0]);
		if(s == -1){
			self.conversasAtivas.push(conv)
		}
		
		$http.get('/chat/conversa/sair/' + self.idConversa +'/' + self.user.username)
    	.then(function(resp){
			if (self.stompClient != undefined && self.stompClient != null) {
				self.stompClient.send( "/app/usuario/change/status/" + self.idConversa, {}, {})
				self.stompClient.disconnect();
		    }
    		self.idConversa = conv.idConversa;
    		self.setUsuariosList("{}");
    		self.setMessages("{}");
    		self.setConnected(false);
    		self.reconnectChat();
    	}, function(e){
    		alert(e);
    	})
	}
	
	self.getMessages = function(id){
		return self.messages;
	}
	
	self.setMessages = function(messages){
		if(messages != null && messages != undefined && messages != "{}" && messages != ""){
			self.messages = messages;
			$scope.$apply();
			var mensagem = self.messages[self.messages.length - 1];    
			var height = $("#scrollMessages")[0].scrollHeight;
			$("#scrollMessages")[0].scrollTop = height;
			
			if(mensagem != null && mensagem != undefined && mensagem != ''){
				if($.trim(mensagem.usuario) != $.trim(self.user.username)){
					self.sendNotification(mensagem.usuario, mensagem.corpo);
				}
			}
		}else{
			self.messages = [];
		}
	}
	
	self.setUsuariosList = function(list){
		if(list != null && list != undefined && list != "{}" && list != ""){
			self.usersOn = JSON.parse(list);
			$scope.$apply();
		}else{
			self.usersOn = [];
		}
	}
	
	self.desconnectChat = function() {
		$http.get('/chat/conversa/sair/' + self.idConversa +'/' + self.user.username)
    	.then(function(resp){
    		if (self.stompClient != null) {
    			self.stompClient.send( "/app/usuario/change/status/" + self.idConversa, {}, {})
    			self.stompClient.disconnect();
    		}
    		self.setUsuariosList("{}");
    		self.setMessages("{}");
    		self.setConnected(false);

    	}, function(e){
    		console.log(e);
    	})
	}
	
	self.sendMessage = function(corpo) {
		if(corpo == null || corpo == '' || corpo == undefined){
			return;
		}
		self.stompClient.send( "/app/message/" + self.idConversa, {}, JSON.stringify({'corpo': corpo, 'usuario':self.user.username }));
	    self.textSend = "";
	}
	
	self.setConnected = function(bool){
		self.isConnected = bool;
	}
	
	self.enterSendMessage = function(e, message){
		if(e.keyCode == 13){
			self.sendMessage(message);
		}
	}
	
	self.sendNotification = function(usuario, corpo){
	    if(window.webkitNotifications != undefined && window.webkitNotifications != null && window.webkitNotifications != '' ){
		    var havePermission = window.webkitNotifications.checkPermission();
		    if (havePermission == 0) {
		      var notification = window.webkitNotifications.createNotification(
		        '/chat/css/img/messages5.png',
		        'Nova Mensagem!',
		        usuario + ": " + corpo
		      );
	
		      notification.onclick = function () {
		    	window.focus();
		        notification.close();
		      }
		      notification.show();
		    } else {
		        window.webkitNotifications.requestPermission();
		    }
	    }else{
		    Notification.requestPermission(function() {
		        var notification = new Notification("Nova Mensagem!", {
				    icon: '/chat/css/img/messages5.png',
				    body: usuario + ": " + corpo
				});
		        notification.onclick = function() {
		        	window.focus();
		        	notification.close();
		        }
		    });	    		
	    }		
	}
	
	self._initUser = function(u){
		self.user = Object.assign({}, u);
		self.userInput = u;
	}
	
}])