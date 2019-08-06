jQuery(function($){
    var $button = $('#add-row'),
        $row = $('.timesheet-row').clone();

    $button.click(function(){
        $row.clone().insertBefore( $button );
    });
});

$(document).ready(function () {
    var counter = 0;
    counter = $('#myTable tr').length - 2;

    $("#addrow").on("click", function () {


        var newRow = $("<tr>");
        var cols = "";

        cols += '<td><input type="text" name="name' + counter + '"/></td>';
        cols += '<td><input type="text" name="price' + counter + '"/></td>';

        cols += '<td><input type="button" id="ibtnDel"  value="Delete"></td>';
        newRow.append(cols);

        $("table.order-list").append(newRow);
        counter++;
        if (counter == 5) $('#addrow').attr('disabled', true).prop('value', "You've reached the limit");
    });

    $("table.order-list").on("change", 'input[name^="price"]', function (event) {
        calculateRow($(this).closest("tr"));
        calculateGrandTotal();
    });



    $("table.order-list").on("click", "#ibtnDel", function (event) {
        $(this).closest("tr").remove();
        calculateGrandTotal();
        counter --;
        if (counter < 5) $('#addrow').attr("disabled", false).prop('value', "Add Row");
    });


});



function calculateRow(row) {
    var price = +row.find('input[name^="price"]').val();

}

function calculateGrandTotal() {
    var grandTotal = 0;
    $("table.order-list").find('input[name^="price"]').each(function () {
        grandTotal += +$(this).val();
    });
    $("#grandtotal").text(grandTotal.toFixed(2));
}