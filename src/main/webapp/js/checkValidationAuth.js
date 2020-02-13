window.onload = function () {
    document.getElementById('login').onkeyup =
        function () {
            var obj = document.getElementById('login');
            var regexp = /^[A-z][\w]{4,14}$/;
            if (!(obj.value).match(regexp)) {
                obj.classList.add("red-border");
                obj.classList.remove("green-border");
            } else {
                obj.classList.add("green-border");
                obj.classList.remove("red-border");
            }
        };

    document.getElementById('password').onkeyup =
        function () {
            var obj = document.getElementById('password');
            var regexp = /^[\w]{8,20}$/;
            if (!(obj.value).match(regexp)) {
                obj.classList.add("red-border");
                obj.classList.remove("green-border");
            } else {
                obj.classList.add("green-border");
                obj.classList.remove("red-border");
            }
        };
};
