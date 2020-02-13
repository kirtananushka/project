$(document).ready(function () {
    $("#login").focusout(function () {
        var data = {};
        data = {"login": $("#login").val()};
        //
        $.ajax
        ({
            type: "POST",
            data: data,
            url: "LoginChecker",
            success: function (serverData) {
                if (serverData.loginInfo !== true) {
                    $("#submit").prop('disabled', true);
                    $("#loginExplanationError").removeClass("displayNone");
                    $("#loginExplanation").addClass("displayNone");
                    $("#login").css({'backgroundColor': 'pink'});
                } else {
                    $("#submit").prop('disabled', false);
                    $("#loginExplanationError").addClass("displayNone");
                    $("#loginExplanation").removeClass("displayNone");
                    $("#login").css({'backgroundColor': 'inherit'});
                }
            }
        });
    });
});