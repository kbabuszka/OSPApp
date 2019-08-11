$(document).ready(function () {
	var firefighterId = document.getElementById("firefighter_id").value;
    var next = trainingsamount-1;
    if(trainingsamount == 0) {
    	next = 0;
    } 
    
    $("#add-training").click(function(e){
        e.preventDefault();
        var addto = "#trainingform_" + next;
        if(trainingsamount == 0) {
        	next = 0;
        } else {
        	next = next + 1;
        }
        
        var options = '';
        for(var i=0; i < trainingTypes[0].length; i++) {
        	options = options + '<option value="' + trainingTypes[0][i] + '">' + trainingTypes[1][i] + '</option>\n';
        }
        
        var newIn = ' <div  class="row clearfix" id="trainingform_'+ next +'">' +
	        '<input type="hidden" id="trainings' + next + '.firefighterId" name="trainings[' + next + '].firefighterId" value="' + firefighterId + '">' +
	        '<div class="col-lg-3 col-md-3 col-sm-3 col-xs-6">' +
 	            '<select id="trainings' + next + '.training"  name="trainings[' + next + '].training" class="form-control show-tick">' +
	            	'<option value="">-- wybierz --</option>' +
	               	options +
	            '</select>' +
	        '</div>' +
	        '<div class="col-lg-2 col-md-2 col-sm-2 col-xs-6">' +
	        	'<div class="form-group form-float">' +
	            	'<div class="form-line">' +
	                	'<input type="text" class="form-control" id="trainings' + next + '.certificateNumber" name="trainings[' + next + '].certificateNumber">' +
	                	'<label class="form-label">Numer świadectwa</label>' +
	            	'</div>' +
	        	'</div>' +
	        '</div>' +
            '<div class="col-lg-1 col-md-1 col-sm-1 col-xs-6">' +
	            '<div class="form-group form-float" id="bs_datepicker_training_obtain_date_' + next + '">' +
	                '<div class="form-line">' +
	                    '<input type="text" class="form-control" id="trainings' + next + '.obtainDate" name="trainings[' + next + '].obtainDate">' +
	                    '<label class="form-label">Data uzyskania</label>' +
	                '</div>' +
	            '</div>' +
	        '</div>' +
            '<div class="col-lg-2 col-md-2 col-sm-2 col-xs-6">' +
	            '<div class="form-group form-float">' +
	                '<div class="form-line">' +
	                    '<input type="text" class="form-control" id="trainings' + next + '.issuedBy" name="trainings[' + next + '].issuedBy" value="">' +
	                    '<label class="form-label">Wydany przez</label>' +
	                '</div>' +
	            '</div>' +
            '</div>' +	        
           	'<div class="col-lg-1 col-md-1 col-sm-1 col-xs-6">' +
	            '<div class="form-group form-float" id="bs_datepicker_training_valid_until_' + next + '">' +
	                '<div class="form-line">' +
	                    '<input type="text" class="form-control" id="trainings' + next + '.validUntil" name="trainings[' + next + '].validUntil">' +
	                    '<label class="form-label">Ważne do</label>' +
	                '</div>' +
	            '</div>' +
	        '</div>' +   	        
            '<div class="col-lg-1 col-md-1 col-sm-1 col-xs-6">' +
	            '<input name="trainings[' + next + '].isExpiring" type="checkbox" id="training_isexpiring_' + next + '" class="filled-in">' +
	            '<label for="training_isexpiring_' + next + '">Terminowe?</label>' +
	        '</div>' +  
            '<div class="col-lg-1 col-md-1 col-sm-1 col-xs-12">' +
            	'<button id="delete_trainingform_' + next + '" class="btn btn-danger btn-lg m-l-15 waves-effect" type="button">USUŃ</button>' +
	        '</div>' +	        
        '</div>';
        
        var newInput = $(newIn);
        
        if(trainingsamount < 1) {
        	$("#no-trainings").after(newInput);
        } else {
        	$(addto).after(newInput);
        }
        trainingsamount = trainingsamount+1;
        makeDatePickers();
                
        $("#trainingform_" + next).attr('data-source',$(addto).attr('data-source'));
        $("#count").val(next);  
        
        $("#delete_trainingform_" + next).click(function(e){
            e.preventDefault();
            var fieldNum = this.id.split("_")[2];
            $("#trainingform_" + fieldNum).remove();
            trainingsamount = trainingsamount-1;
            next = next - 1;
        });
    });

});