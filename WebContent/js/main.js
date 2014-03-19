/**
 * 
 */

$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};


$.ajaxSetup({
    error: function(jqXHR, e) {
		var msg = '';
    	if(jqXHR.status==0){
    		msg = 'You are offline!!\n Please Check Your Network.';
        }else if(jqXHR.status==404){
        	msg = 'Requested URL not found.';
        }else if(jqXHR.status==500){
        	msg = 'Internal Server Error.<br/>'+jqXHR.responseText;
        }else if(e=='parsererror'){
        	msg = 'Error: Parsing JSON Request failed.';
        }else if(e=='timeout'){
        	msg = 'Request Time out.';
        }else {
        	msg = 'Unknow Error.<br/>'+x.responseText;
        }
    	$('#allresult').empty();
    	console.log('error: '+jqXHR.responseText);
    	$('#allresult').append('Error: '+msg);
    	$('#allresult').show();	 
    }
});


$(function() {
	  $('input[name=date]').datepicker({dateFormat: "yy-mm-dd"});
	  $('input[name=startDate]').datepicker({
		  dateFormat: "yy-mm-dd",		  
		  maxDate: "+60D",      
	      numberOfMonths: 2,
	      onClose: function( selectedDate ) {
	        $('input[name=endDate]').datepicker( "option", "minDate", selectedDate );
	        /* $('#startresult').empty();
	        $('#startresult').hide(); */
	      }
	    });
	  $('input[name=endDate]').datepicker({
		  dateFormat: "yy-mm-dd",		  
		  maxDate: "+60D",      
	      numberOfMonths: 2,
	      onClose: function( selectedDate ) {
	        $('input[name=startDate]').datepicker( "option", "maxDate", selectedDate );
	      }
	    });
});


$("input[name=endDate]").datepicker({
	dateFormat: "yy-mm-dd",
    onSelect: function(date, instance){
		if($("input[name=startDate]").val().length < 1 ){
			$("div#strtDateErr").show();
			return;
		}
			
    	$.ajax({
            url: $("#myForm").attr("action"),
            type: $("#myForm").attr("method"),                                           
            dataType: 'json',                 
            data: $("#myForm").serialize(),                                 
            success: function(responseJson) {
            	$('#startresult').empty();                	
            	if ( responseJson.length == 0 ) {
                    console.log("NO DATA!");
                    $('#startresult').append('No DATA! try different.');                        
                }else{
                    $('#startresult').append('<h4>Result:</h4>');
            		loadTable(responseJson,$('#startresult'));                		
              }
            	$('#startresult').show();
           }
          });
    }
});

$('a#showAll').click(function(e){
	$.ajax({
        url: $(this).attr('href'),
        type: 'GET',                                           
        dataType: 'json',                                        
        success: function(responseJson) {
        	$('#allresult').empty();
        	if ( responseJson.length == 0 ) {
                console.log("NO DATA!");
                $('#allresult').append('No DATA! try different.');                   
            }else{
            	$('#allresult').append('<h4>All Records:</h4>');
        		loadTable(responseJson,$('#allresult'));            		
          }
         $('#allresult').show();
       }
      });
    e.preventDefault();
});   

function loadTable(responseJson, tableLocation){
	var $table = $('<table>').appendTo(tableLocation);
	$table.addClass('table table-striped table-hover');
	var $thead = $('<thead>').appendTo($table);
	var $tbody = $('<tbody>').appendTo($table);
	$('<tr>').appendTo($thead)
		.append($('<th>').text('Id'))
		.append($('<th>').text('date'))
		.append($('<th>').text('name'))
		.append($('<th>').text('address'))    		
		.append($('<th>').text('kmDistanceAddr'))
		.append($('<th>').text('timeToTravel'))
		.append($('<th>').text('dayHours'))
		.append($('<th>').text('dayMinutes'))
		.append($('<th>').text('allDayHours'));
	$.each(responseJson, function(index, workData) { // Iterate over the JSON array. 
		$('<tr id='+workData.pId+'>').appendTo($tbody)
		.append($('<td>').text(workData.pId))
        .append($('<td>').text(workData.date))
        .append($('<td>').text(workData.name))
        .append($('<td>').text(workData.address))                                                
        .append($('<td>').text(workData.kmDistanceAddr))                        
        .append($('<td>').text(workData.timeToTravel))                     
        .append($('<td>').text(workData.dayHours))                        
        .append($('<td>').text(workData.dayMinutes))
        .append($('<td>').text(workData.allDayHours));                   
    });
}


$('button#addWorkDataSubmit').click(function(e){
	
	$.post( $('#addWorkForm').attr('action'), {jsonNewWorkData : JSON.stringify($('#addWorkForm').serializeObject())}, function(workData) {
		$('#myModal').modal('hide');
		
		var $newTr = $('<tr id='+workData.pId+'>').appendTo($('#allresult table > tbody:last'))
		.append($('<td>').text(workData.pId))
        .append($('<td>').text(workData.date))
        .append($('<td>').text(workData.name))
        .append($('<td>').text(workData.address))                                                
        .append($('<td>').text(workData.kmDistanceAddr))                        
        .append($('<td>').text(workData.timeToTravel))                     
        .append($('<td>').text(workData.dayHours))                        
        .append($('<td>').text(workData.dayMinutes))
        .append($('<td>').text(workData.allDayHours));

		$('html,body').animate({scrollTop: $newTr.offset().top});
		$newTr.effect("highlight", {}, 4000);		
	});
	
	e.preventDefault();
});