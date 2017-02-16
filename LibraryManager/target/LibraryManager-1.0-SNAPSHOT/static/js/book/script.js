$(function () {
    $('.date').datetimepicker({
        format: 'dd.mm.yyyy',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0});
    var df = $("#closeDeliveryFormButton");
    var df2 = $("#deliveryFormButton");
    $("#closeDeliveryFormButton").click(function () {
        $("#closingDeliveryForm").toggle(300)
    });
    /**
     * Устанавливает обработчик кнопки создания нового изменения.
     */
    $("#deliveryFormButton").click(function () {
        $("#deliveryForm").toggle(300)
    });

    $("#deliveryForm").hide();
    $("#closingDeliveryForm").hide();
    /*
     * Устанавливает на элемент закладки переключение размера блока перечисления изменений. 
     */
    $("#bookmark").click(function (e) {
        if ($("#documentTable").hasClass("small-block")) {
            $("#documentTable").removeClass("small-block");
            $("#documentTable").addClass("average-block", 500);
        } else {
             $("#documentTable").addClass("small-block", 500);
            $("#documentTable").removeClass("average-block",500);
           
        }
    });
});



$('.nav-tabs a').click(function (e) {
    e.preventDefault()
    $(this).tab('show')
});
