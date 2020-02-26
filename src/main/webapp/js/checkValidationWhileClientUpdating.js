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

    document.getElementById('name').onkeyup =
        function () {
            var obj = document.getElementById('name');
            var regexp = /^[A-z\u0430-\u044f -]{1,255}$/gi;
            if (!(obj.value).match(regexp)) {
                obj.classList.add("red-border");
                obj.classList.remove("green-border");
            } else {
                obj.classList.add("green-border");
                obj.classList.remove("red-border");
            }
        };

    document.getElementById('surname').onkeyup =
        function () {
            var obj = document.getElementById('surname');
            var regexp = /^[A-z\u0430-\u044f -]{1,255}$/gi;
            if (!(obj.value).match(regexp)) {
                obj.classList.add("red-border");
                obj.classList.remove("green-border");
            } else {
                obj.classList.add("green-border");
                obj.classList.remove("red-border");
            }
        };

    document.getElementById('phone').onkeyup =
        function () {
            var obj = document.getElementById('phone');
            obj.value = obj.value.replace(/^80/, '375');
            var regexp = /^375\d{9}$/;
            if (!(obj.value).match(regexp)) {
                obj.classList.add("red-border");
                obj.classList.remove("green-border");
            } else {
                obj.classList.add("green-border");
                obj.classList.remove("red-border");
            }
        };

    document.getElementById('email').onkeyup =
        function () {
            var obj = document.getElementById('email');
            var regexp = /^([\w-]+\.)*[\w-]+@[\w-]+(\.[\w-]+)*\.[a-z]{2,6}$/;
            if (!(obj.value).match(regexp)) {
                obj.classList.add("red-border");
                obj.classList.remove("green-border");
            } else {
                obj.classList.add("green-border");
                obj.classList.remove("red-border");
            }
        }
};
