window.onload = function () {

    document.getElementById('oldPassword').onkeyup =
        function () {
            var obj = document.getElementById('oldPassword');
            var subm = document.getElementById('submit');
            var regexp = /^[\w]{8,20}$/;
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
        }
};
