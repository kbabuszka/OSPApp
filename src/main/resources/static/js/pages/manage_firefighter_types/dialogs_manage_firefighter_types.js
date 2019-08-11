$(function () {
    $('.js-sweetalert button').on('click', function () {
        var type = $(this).data('type');
        var id = $(this).val();
        if (type === 'delete-firefighter-type') {
            showDeleteTrainingType(id);
        }
    });
});


function showDeleteTrainingType(id) {
    swal({
        title: "Na pewno?",
        text: "Ten rodzaj strażaka zostanie trwale usunięty z bazy danych. Tej operacji nie można cofnąć!",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Tak, usuń go!",
        cancelButtonText: "Nie usuwaj",
        closeOnConfirm: false,
        closeOnCancel: false,
        html: true
    }, function (isConfirm) {
        if (isConfirm) {
        	var url =  "/manage/firefighter-types/delete/" + id;
        	$(location).attr('href',url);
        } else {
            swal("Anulowano", "Rodzaj strażaka nie został usunięty", "error");
        }
    });
}