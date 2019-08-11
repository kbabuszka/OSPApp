$(function () {
    $('.js-sweetalert button').on('click', function () {
        var type = $(this).data('type');
        var id = $(this).val();
        if (type === 'delete-training-type') {
            showDeleteTrainingType(id);
        }
    });
});


function showDeleteTrainingType(id) {
    swal({
        title: "Na pewno?",
        text: "Ten rodzaj szkolenia zostanie trwale usunięty z bazy danych. Tej operacji nie można cofnąć!" +
        		"<br /><span class='col-red'><strong>UWAGA!</strong> usunięcie szkolenia z tej listy spowoduje również usunięcie tego szkolenia z profilu wszystkich strażaków, którze mają je przypisane!</span>",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Tak, usuń je!",
        cancelButtonText: "Nie usuwaj",
        closeOnConfirm: false,
        closeOnCancel: false,
        html: true
    }, function (isConfirm) {
        if (isConfirm) {
        	var url =  "/manage/trainings/delete/" + id;
        	$(location).attr('href',url);
        } else {
            swal("Anulowano", "Rodzaj szkolenia nie został usunięty", "error");
        }
    });
}