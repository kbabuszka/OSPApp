$(function () {
    $('.js-sweetalert button').on('click', function () {
        var type = $(this).data('type');
        var id = $(this).val();
        if (type === 'delete-user') {
            showDeleteUser(id);
        } 
    });
});


function showDeleteUser(id) {
    swal({
        title: "Na pewno?",
        text: "Ten użytkownik zostanie trwale usunięty z bazy danych. Tej operacji nie można cofnąć!",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Tak, usuń go!",
        cancelButtonText: "Nie usuwaj",
        closeOnConfirm: false,
        closeOnCancel: false,
    }, function (isConfirm) {
        if (isConfirm) {
        	var url =  "/manage/users/delete/" + id;
        	$(location).attr('href',url);
        } else {
            swal("Anulowano", "Użytkownik zostaje w bazie danych :)", "error");
        }
    });
}