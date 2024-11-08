$(document).ready(function () {
    $("#logoutfunction").click(() => {
        localStorage.setItem('token', "");
    });
});