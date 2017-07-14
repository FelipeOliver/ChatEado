app.controller('ChatController', ['$scope', '$http',
					function($scope, $http){
	var self = this;
	self.stompClient = null;
	self.messages = [];
	self.isConnected = false;
	self.idConversa = 1;
	self.conversasAtivas = [{'desc':'Sala 1', 'id':1}, {'desc':'Sala 2', 'id':2}];
	
//	self.connectChat = function() {
//		if( self.userInput.codigo == undefined || self.userInput.codigo == null || self.userInput.codigo == ''){
//			alert('Informe um código de usuário');
//			return;
//		}
//		if( self.userInput.senha == undefined || self.userInput.senha == null || self.userInput.senha == ''){
//			alert('Informe uma senha para o usuário');
//			return;
//		}
//		self.user = Object.assign({}, self.userInput);
//		self.user.senha = btoa(self.user.senha);
//		self.user.idConversa = self.idConversa;
//		$http.post('/chat/login', self.user)
//    	.then(function(resp){
//    		var socket = new SockJS('/chat/chat');
//    		stompClient = Stomp.over(socket);
//    		self.setConnected(true);
//    	    stompClient.connect({}, function (frame) {
//    	    	stompClient.subscribe('/server/sendmessage/' + self.idConversa, function (greeting) {
//    	    		console.log(greeting);
//    	    		self.setMessages(JSON.parse(greeting.body));
//    	    	});
//    	    	stompClient.subscribe('/server/usuariolist/' + self.idConversa, function (list) {
//    	    		self.setUsuariosList(list.body);
//    	    	});
//    	    	stompClient.send( "/app/usuario/change/status/" + self.idConversa, {}, {})
//    	    	$http.get('/chat/conversa/' + self.idConversa +'/findall')
//    	    	.then(function(resp){
//    	    		console.log(resp);
//    	    		self.setMessages(resp.data);
//    	    	}, function(e){
//    	    		console.log(e);
//    	    	})
//    	    	console.log('Connected: ' + frame);
//    	    });
//    	}, function(e){
//    		console.log(e);
//    		alert("O usuário não foi autenticado!");
//    	})
//
//	}
	
	self.reconnectChat = function(){
		var socket = new SockJS('/chat/chat');
		stompClient = Stomp.over(socket);
		self.setConnected(true);
	    stompClient.connect({}, function (frame) {
	    	stompClient.subscribe('/server/sendmessage/' + self.idConversa, function (greeting) {
	    		console.log(greeting);
	    		self.setMessages(JSON.parse(greeting.body));
	    	});
	    	stompClient.subscribe('/server/usuariolist/' + self.idConversa, function (list) {
	    		self.setUsuariosList(list.body);
	    	});
	    	$http.get('/chat/conversa/entrar/' + self.idConversa +'/' + self.user.username)
	    	.then(function(resp){
	    		stompClient.send( "/app/usuario/change/status/" + self.idConversa, {}, {})
	    	}, function(e){
	    		console.log(e);
	    	})
	    	$http.get('/chat/conversa/' + self.idConversa +'/findall')
	    	.then(function(resp){
	    		self.setMessages(resp.data);
	    		console.log(resp);
	    	}, function(e){
	    		console.log(e);
	    	})
	    	console.log('Connected: ' + frame);		
	    });
	}
	
	self.closeConversation = function(idConversa){
		var result = $.grep(self.conversasAtivas, function(e){ return e.id == idConversa; });
		console.log(result[0]);
		var s = self.conversasAtivas.indexOf(result[0]);
		console.log(s);
		if(s > -1){
			self.conversasAtivas.splice(s, 1);
		}
		if(self.conversasAtivas[0]){
			self.idConversa = self.conversasAtivas[0].id;
		}else{
			self.idConversa = undefined;
		}
	}
	
	self.setConversation = function(conv){
		if (stompClient != null) {
	        stompClient.disconnect();
	    }
		self.idConversa = conv;
		self.setUsuariosList("{}");
		self.setMessages("{}");
		self.setConnected(false);
		console.log("Disconnected");
		console.log(conv);
		self.reconnectChat();
	}
	
	self.getMessages = function(id){
		return self.messages;
	}
	
	self.setMessages = function(messages){
		if(messages != null && messages != undefined && messages != "{}" && messages != ""){
			self.messages = messages;
			var mensagem = self.messages[self.messages.length - 1];    
			var height = $("#scrollMessages")[0].scrollHeight;
			$("#scrollMessages")[0].scrollTop = height;
			
			if(mensagem != null && mensagem != undefined && mensagem != ''){
				if($.trim(mensagem.usuario) != $.trim(self.user.codigo)){
					self.sendNotification(mensagem.usuario, mensagem.corpo);
				}
			}
			$scope.$apply();
		}else{
			self.messages = [];
		}
	}
	
	self.setUsuariosList = function(list){
		if(list != null && list != undefined && list != "{}" && list != ""){
			console.log(list);
			self.usersOn = JSON.parse(list);
			$scope.$apply();
		}else{
			self.usersOn = [];
		}
	}
	
	self.desconnectChat = function() {
	    if (stompClient != null) {
	        stompClient.disconnect();
	    }
		self.setUsuariosList("{}");
		self.setMessages("{}");
		self.setConnected(false);
		console.log("Disconnected");
	}
	
	self.sendMessage = function(corpo) {
		if(corpo == null || corpo == '' || corpo == undefined){
			return;
		}
	    stompClient.send( "/app/message/" + self.idConversa, {}, JSON.stringify({'corpo': corpo, 'usuario':self.user.username }));
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
		console.log(u);
		self.user = Object.assign({}, u);
		self.userInput = u;
		self.reconnectChat();
	}
	
}])