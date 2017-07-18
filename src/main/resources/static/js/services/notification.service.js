app.service('NotificationService', [ function(){
	var self = this;
	
	self.send = function(corpo, titulo, imagem){
		imagem = imagem ? imagem : '/chat/css/img/messages5.png'; 
	    if(window.webkitNotifications != undefined && window.webkitNotifications != null && window.webkitNotifications != '' ){
		    var havePermission = window.webkitNotifications.checkPermission();
		    if (havePermission == 0) {
		      var notification = window.webkitNotifications.createNotification(
		        imagem,
		        titulo,
		        corpo
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
		        var notification = new Notification(titulo, {
				    icon: imagem,
				    body: corpo
				});
		        notification.onclick = function() {
		        	window.focus();
		        	notification.close();
		        }
		    });	    		
	    }
	}
}])