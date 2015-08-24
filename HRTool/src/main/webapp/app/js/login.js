onSignIn = function onSignIn(googleUser) {

	  	var profile = googleUser.getBasicProfile();
			
		var id_token = googleUser.getAuthResponse().id_token;  
		  
	  	console.log('ID: ' + profile.getId());
	  	console.log('Name: ' + profile.getName());
	  	console.log('Image URL: ' + profile.getImageUrl());
	  	console.log('Email: ' + profile.getEmail());
	  	console.log('id_token: '+ id_token);
	  	
	  	var request;
	  	
	  	request = $.ajax({
	        url: "/signin/login",
	        type: "POST",
	        data: {"idtoken":id_token},
	  	
	  	
	  	success: function(data, textStatus, xhr) {

            console.log('success : status: '+xhr.status);
            console.log('success : data: '+data);
            console.log('success : textStatus: '+textStatus)
            
            var split = data.split(":");
            var first = split[0];
            var second = split[1];
            
            console.log("f: "+first);
            console.log("s: "+second);
            
            if(first == 'redirect'){
            	top.location.href = second;
            }
           
        },
        
        complete: function(data, xhr, textStatus) {
        	
            console.log('complete : status: ' +xhr.status);
            console.log('complete : data: '+data);
            console.log('complete : textStatus: '+textStatus)

        } 
	  	
	    });
 	
}


signOut = function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    
    var request;
  	
  	request = $.ajax({
        url: "/signin/logout",
        type: "GET"
    });
    
    auth2.signOut().then(function () {
      console.log('User signed out.');
    });
  }

revokeAllScopes = function revokeAllScopes() {
	  var auth2 = gapi.auth2.getAuthInstance();
	  
	  
	    auth2.signOut().then(function () {
	      console.log('User signed out.');
	    });
	  auth2.disconnect();
	  
	  request = $.ajax({
	        url: "/employees/logout",
	        type: "GET" 	
	    });
	}