app.controller('ChatController', ['$scope', '$http',
					function($scope, $http){
	var self = this;
	self.stompClient = null;
	self.messages = [];
	self.isConnected = false;
	self.idConversa = 1;
	self.conversasAtivas = [{'desc':'Sala 1', 'id':1}, {'desc':'Sala 2', 'id':2}];
	
	self.connectChat = function() {
		if( self.user == undefined || self.user == null || self.user == ''){
			alert('Informe um usuário');
			return;
		}
	    var socket = new SockJS('/chat/chat');
	    stompClient = Stomp.over(socket);
	    stompClient.connect({}, function (frame) {
	    	$http.get('/chat/login/' + self.user)
	    	.then(function(resp){
	    		self.setConnected(true);
	    		console.log('Connected: ' + frame);
	    	}, function(e){
	    		console.log(e);
	    	})
	    	stompClient.subscribe('/server/sendmessage/' + self.idConversa, function (greeting) {
	    		console.log(greeting);
	    		self.setMessages(JSON.parse(greeting.body));
	    	});
	    	stompClient.subscribe('/server/usuariolist', function (list) {
	    		self.setUsuariosList(list.body);
	    	});
	    	stompClient.send( "/app/usuario/change/status", {}, {})
	    	$http.get('/chat/conversa/' + self.idConversa +'/findall')
	    	.then(function(resp){
	    		console.log(resp);
	    		self.setMessages(resp.data);
	    	}, function(e){
	    		console.log(e);
	    	})
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
		console.log(conv);
		self.idConversa = conv;
		self.desconnectChat();
		self.connectChat();
	}
	
	self.getMessages = function(id){
		return self.messages;
	}
	
	self.setMessages = function(messages){
//		var mensagem = JSON.parse(messages);
//		self.messages.push(mensagem);
		self.messages = messages;
		$scope.$apply();
		var mensagem = self.messages[self.messages.length - 1];    
		var height = $("#scrollMessages")[0].scrollHeight;
		$("#scrollMessages")[0].scrollTop = height;
		
		if(mensagem != null && mensagem != undefined && mensagem != ''){
			if($.trim(mensagem.usuario) != $.trim(self.user)){
				self.sendNotification(mensagem.usuario, mensagem.corpo);
			}
		}
	}
	
	self.setUsuariosList = function(list){
		self.usersOn = JSON.parse(list);
		$scope.$apply();
		console.log(self.usersOn);
	}
	
	self.desconnectChat = function() {
	    if (stompClient != null) {
	        stompClient.disconnect();
	    }
	    self.setConnected(false);
	    console.log("Disconnected");
	}
	
	self.sendMessage = function(corpo) {
		if(corpo == null || corpo == '' || corpo == undefined){
			return;
		}
	    stompClient.send( "/app/message/" + self.idConversa, {}, JSON.stringify({'corpo': corpo, 'usuario':self.user }));
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
	
//	Notification.requestPermission();
}])