window.onload = function () {

    document.getElementById('msgName').onkeyup =
        function () {
            var obj = document.getElementById('msgName');
            var regexp = /^[A-z\u0430-\u044f -]{1,255}$/gi;
            if (!(obj.value).match(regexp)) {
                obj.classList.add("red-border");
                obj.classList.remove("green-border");
            } else {
                obj.classList.add("green-border");
                obj.classList.remove("red-border");
            }
        };

    document.getElementById('msgSurname').onkeyup =
        function () {
            var obj = document.getElementById('msgSurname');
            var regexp = /^[A-z\u0430-\u044f -]{1,255}$/gi;
            if (!(obj.value).match(regexp)) {
                obj.classList.add("red-border");
                obj.classList.remove("green-border");
            } else {
                obj.classList.add("green-border");
                obj.classList.remove("red-border");
            }
        };

    document.getElementById('msgPhone').onkeyup =
        function () {
            var obj = document.getElementById('msgPhone');
            obj.value = obj.value.replace(/^80/, '375');
            obj.value = obj.value.replace(/\+375/, '375');
            var regexp = /^375\d{9}$/;
            if (!(obj.value).match(regexp)) {
                obj.classList.add("red-border");
                obj.classList.remove("green-border");
            } else {
                obj.classList.add("green-border");
                obj.classList.remove("red-border");
            }
        };

    document.getElementById('msgEmail').onkeyup =
        function () {
            var obj = document.getElementById('msgEmail');
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
