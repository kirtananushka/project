function checkUploadImage() {
    var file = $('#file').val();

    if (file.length != 0) {
        $('#upload-submit').removeAttr('disabled');
    } else {
        $('#upload-submit').attr('disabled', 'disabled');
    }
}