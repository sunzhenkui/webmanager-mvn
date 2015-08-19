$(function() {
    var $dialog = $('<div/>');
    var $formBody = $('#form-body');
    $dialog.dialog({
        height: 300,
        width: 500,
        content: $formBody.show(),
        noheader: true,
        buttons: [{
                id: 'loginBtn',
                text: '登陆',
                handler:function(){
                	$('#form-body').submit();
                }
            }]
    });
    $formBody.after($('#logo').show());
    
    $(window).resize(function() {
        $dialog.dialog('center');
    });
});