$(document).ready(function () {
    $('.table ').on("click","tr",function () {
        $(".table tr").removeClass('selectlines');
        $(this).toggleClass('selectlines');
    });
   $('.date').datetimepicker({
        format: 'dd.mm.yyyy',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0});
    $(".glyphicon").hide();
});

$('.nav-tabs a').click(function (e) {
    e.preventDefault()
    $(this).tab('show')
});



