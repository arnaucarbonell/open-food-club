$(document).ready(function () {
    $("#loginfunction").click(() => {

        var settings = {
            "url": "http://localhost:8080/login",
            "method": "POST",
            "timeout": 0,
            "data": JSON.stringify( {
                "username": $("#username").val(),
                "password": $("#password").val()
            }),
            "success": function (data, textStatus, request) {
                localStorage.setItem('user_ID',$("#username").val());
                localStorage.setItem('token', request.getResponseHeader('authorization'))
                console.log('success: ',  request.getAllResponseHeaders());
                window.location = 'home.html';
            },
            "error": function (request, textStatus, errorThrown) {
                console.log('error: ' + textStatus + ' headers: ' + request.getAllResponseHeaders() + ' ErrorThrown: ' + errorThrown);
            }
        };

        $.ajax(settings).done(function (data, textStatus, request) {
            console.log('Done Response. Data: ', request.getResponseHeader('authorization'));
        });
        return false; 
    });
});