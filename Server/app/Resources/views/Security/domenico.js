$(document).ready(function(){
        // click on button submit
        $("#ciaociao").on('click', function(){
            // send ajax
            $.ajax({
                url: "http://localhost:8000/login",, // url where to submit the request
                type : "POST", // type of action POST || GET
                dataType : 'json', // data type
                data : $("#buongiorno").serialize(), // post data || get data
                success : function(result) {
                    // you can see the result from the console
                    // tab of the developer tools
                    console.log(result);
                },
                error: function(xhr, resp, text) {
                    console.log(xhr, resp, text);
                }
            })
        });
    });
/*
$("ciaociao").click(function(event){
	event.preventDefault();
	$.ajax({
		type: "POST",
		url: "http://localhost:8000/login",
		data: formData,
		success: function(){},
		dataType: "json",
		contentType : "application/json"
	});
});

*/