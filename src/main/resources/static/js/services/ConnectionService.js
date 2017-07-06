app.service('ConnectionService', [
									function(){
	var self = this;
	self.stompClient = null;
	
	self.connect = function(){
	    var socket = new SockJS('/chat/chat');
	    stompClient = Stomp.over(socket);
		stompClient.connect({}, function (frame) {
			console.log(frame);
		});
	}
	
	self.subscribeChat = function(){
    	stompClient.subscribe('/server/sendmessage/' + self.idConversa, function (greeting) {
    		console.log(greeting);
    		self.setMessages(JSON.parse(greeting.body));
    	});		
	}
	
	self.subscribeListUsuario = function(){
    	stompClient.subscribe('/server/usuariolist', function (list) {
    		self.setUsuariosList(list.body);
    	});		
	}
	
	
}])