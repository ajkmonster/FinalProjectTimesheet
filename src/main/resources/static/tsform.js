jQuery(function($){
    var $button = $('#add-row'),
        $row = $('.timesheet-row').clone();

    $button.click(function(){
        $row.clone().insertBefore( $button );
    });
});