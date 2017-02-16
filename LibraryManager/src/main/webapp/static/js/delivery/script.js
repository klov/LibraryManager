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
});

$('.nav-tabs a').click(function (e) {
    e.preventDefault()
    $(this).tab('show')
});
