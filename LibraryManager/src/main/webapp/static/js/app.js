'use strict';

var App = angular.module('myApp', []);

var showAlert = function (errResponse) {
    console.log(JSON.stringify(errResponse));
    $.smkAlert({
        text: errResponse,
        type: 'danger',
        position: 'top-center',
        time: 10
    });
};

var showAlertSuccess = function (errResponse) {
    console.log(JSON.stringify(errResponse));
    $.smkAlert({
        text: errResponse,
        type: 'success',
        position: 'top-center',
        time: 10
    });
};

$(function(){
    $("[data-toogle='toollip']").tooltip();
});
function getUrlVars() {
    var vars = {};
    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
        vars[key] = value;
    });
    return vars;
};



