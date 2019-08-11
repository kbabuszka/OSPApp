$(document).ready(function() {
	makeDatePickers();
});

function makeDatePickers() {
	
    //Textarea auto growth
    autosize($('textarea.auto-growth'));

    //Datetimepicker plugin
    $('.datetimepicker').bootstrapMaterialDatePicker({
        format: 'dddd DD MMMM YYYY - HH:mm',
        clearButton: true,
        weekStart: 1
    });

    $('.datepicker').bootstrapMaterialDatePicker({
        format: 'dddd DD MMMM YYYY',
        clearButton: true,
        weekStart: 1,
        time: false
    });

    $('.timepicker').bootstrapMaterialDatePicker({
        format: 'HH:mm',
        clearButton: true,
        date: false
    });  
    
    // Date pickers for "Add/Edit firefighter" page
    $('#bs_datepicker_birth_date').datepicker({
    	format: 'dd.mm.yyyy',
    	weekStart: 1,
        autoclose: true,
        container: '#bs_datepicker_birth_date',
        language: 'pl'
    });
    
    $('#bs_datepicker_join_date').datepicker({
    	format: 'dd.mm.yyyy',
    	weekStart: 1,
        autoclose: true,
        todayHighlight: true,
        container: '#bs_datepicker_join_date',
        language: 'pl'
    });  
    
    $('#bs_datepicker_health_exam').datepicker({
    	format: 'dd.mm.yyyy',
    	weekStart: 1,
        autoclose: true,
        container: '#bs_datepicker_health_exam',
        language: 'pl'
    }); 
    
    for(i=0;i<=trainingsamount;i++)
    {
	    $('#bs_datepicker_training_valid_until_'+i+' input').datepicker({
	    	format: 'dd.mm.yyyy',
	    	weekStart: 1,
	        autoclose: true,
	        clearBtn: true,
	        container: '#bs_datepicker_training_valid_until_'+i,
	        language: 'pl'
	    }); 
    }
    
    for(i=0;i<=trainingsamount;i++)
    {
	    $('#bs_datepicker_training_obtain_date_'+i+' input').datepicker({
	    	format: 'dd.mm.yyyy',
	    	weekStart: 1,
	        autoclose: true,
	        clearBtn: true,
	        container: '#bs_datepicker_training_obtain_date_'+i,
	        language: 'pl'
	    }); 
    }
};