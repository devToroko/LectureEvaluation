/*!
  * for join website at the index page
  */
let send = function() {
	
let spinner = $('#loader');
spinner.show();
let query = $('#joinModal form').serialize();
$.ajax({
    url:'/DevToroko_MVC_Pattern_WEB/joinAjax.do',
    type:'POST',
    data:query,
    datatype:'json',
    success:function(data){
    	spinner.hide();
        if(data.hasOwnProperty('fail')) {
                alert(data.fail); 
        }
        else if(data.hasOwnProperty('success')) {
        	$('#joinModal').modal("hide");
        	alert(data.success);
			let email = data.userEmail;
			let userID = data.userID;
			$.ajax({
				url:'/DevToroko_MVC_Pattern_WEB/emailAjaxSend.do',
				data: {"userID" : userID, "userEmail" : email},
				type:'POST',
				datatype:'json',
				success:function(data) {
                    if(data.hasOwnProperty('errors')) {
                    	console.log(data); 
                    }else if(data.hasOwnProperty('success')) {
						console.log(data.success);
					}
                }
			});
			location.reload();
        }
    },
    error:function(e,j,k){console.log(e);}
})
}