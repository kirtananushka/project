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
            var passRep = document.getElementById('passwordRepeated');
            var passRepLab = document.getElementById('passwordRepeatedLabel');
            var subm = document.getElementById('submit');
            var regexp = /^[\w]{8,20}$/;
            if (!(obj.value).match(regexp)) {
                obj.classList.add("red-border");
                obj.classList.remove("green-border");
            } else {
                obj.classList.add("green-border");
                obj.classList.remove("red-border");
            }

            if (obj.value !== passRep.value && passRep.value !== '') {
                subm.disabled = true;
                if (obj.value.length >= 8) {
                    passRepLab.style.display = 'inline-block';
                    passRep.style.backgroundColor = 'pink';
                }
            } else {
                passRepLab.style.display = 'none';
                subm.disabled = false;
                passRep.style.backgroundColor = 'inherit';
            }
        };

    document.getElementById('passwordRepeated').onkeyup =
        function () {
            var obj = document.getElementById('passwordRepeated');
            var pass = document.getElementById('password');
            var passRepLab = document.getElementById('passwordRepeatedLabel');
            var subm = document.getElementById('submit');
            var regexp = /^[\w]{8,20}$/;
            if (!(obj.value).match(regexp)) {
                obj.classList.add("red-border");
                obj.classList.remove("green-border");
            } else {
                obj.classList.add("green-border");
                obj.classList.remove("red-border");
            }

            if (obj.value !== pass.value && pass.value !== '') {
                subm.disabled = true;
                if (obj.value.length >= 8) {
                    passRepLab.style.display = 'inline-block';
                    obj.style.backgroundColor = 'pink';
                }
            } else {
                passRepLab.style.display = 'none';
                subm.disabled = false;
                obj.style.backgroundColor = 'inherit';
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
