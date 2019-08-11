$(function () {
    $('.js-sweetalert button').on('click', function () {
        var type = $(this).data('type');
        var id = $(this).val();
        if (type === 'delete-firefighter') {
            showDeleteFirefighter(id);
        } else if(type === 'delete-firefighter-training') {
        	showDeleteTraining(id);
        }
    });
});


function showDeleteFirefighter(id) {
    swal({
        title: "Na pewno?",
        text: "Ten strażak zostanie trwale usunięty z bazy danych. Tej operacji nie można cofnąć!",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Tak, usuń go!",
        cancelButtonText: "Nie usuwaj",
        closeOnConfirm: false,
        closeOnCancel: false,
    }, function (isConfirm) {
        if (isConfirm) {
        	var url =  "/firefighters/delete/" + id;
        	$(location).attr('href',url);
        } else {
            swal("Anulowano", "Strażak zostaje w bazie danych :)", "error");
        }
    });
}

function showDeleteTraining(id) {
    swal({
        title: "Na pewno?",
        text: "Szkolenie zostanie trwale usunięte z bazy danych. Tej operacji nie można cofnąć!",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Tak, usuń!",
        cancelButtonText: "Nie usuwaj",
        closeOnConfirm: false,
    }, function (isConfirm) {
        if (isConfirm) {
        	var firefighterId = $('#firefighter_id').val();
        	var url = "/firefighters/edit/" + firefighterId + "/deletetraining/" + id;
        	$(location).attr('href',url);
        }
    });
}