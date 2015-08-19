var index_tabs;
var index_tabsMenu;
var index_layout;

$(function() {
	index_layout = $('#index_layout').layout({
		fit : true
	});
	/* index_layout.layout('collapse', 'east'); */

	index_tabs = $('#index_tabs').tabs({
		fit : true,
		border : false,
		// 当一个 tab panel 被右键点击时触发
		onContextMenu : function(e, title) {
			e.preventDefault();
			index_tabsMenu.menu('show', {
				left : e.pageX,
				top : e.pageY
			}).data('tabTitle', title);
		},
		onAdd:function(title,index){
			//var target = this;
			//var opts = $(target).tabs('tabs');
			//alert($('#index_tabs').tabs('getSelected').panel('options').title);
            //获取tab的iframe对象  
		}
	});

	index_tabsMenu = $('#index_tabsMenu')
			.menu(
					{
						onClick : function(item) {
							var curTabTitle = $(this).data('tabTitle');
							var type = $(item.target).attr('title');

							if (type === 'refresh') {
								index_tabs.tabs('getTab', curTabTitle).panel(
										'refresh');
								return;
							}
							if (type === 'close') {
								var t = index_tabs.tabs('getTab', curTabTitle);
								if (t.panel('options').closable) {
									index_tabs.tabs('close', curTabTitle);
								}
								return;
							}
							var allTabs = index_tabs.tabs('tabs');
							var closeTabsTitle = [];

							$.each(allTabs,
									function() {
										var opt = $(this).panel('options');
										if (opt.closable
												&& opt.title != curTabTitle
												&& type === 'closeOther') {
											closeTabsTitle.push(opt.title);
										} else if (opt.closable
												&& type === 'closeAll') {
											closeTabsTitle.push(opt.title);
										}
									});
							for (var i = 0; i < closeTabsTitle.length; i++) {
								index_tabs.tabs('close', closeTabsTitle[i]);
							}
						}
					});
	
	//初始化login


});

function opentabs(text, url) {
	if ($('#index_tabs').tabs('exists', text)) {
		$('#index_tabs').tabs('select', text);
	} else {
		// url = url + ".jsp";
		var content = '<iframe id="a" scrolling="auto" frameborder="0"  src="' + url
				+ '" style="width:100%;height:100%;"></iframe>';
		$('#index_tabs').tabs('add', {
			id : text,
			title : text,
			// href : url ,
			content : content,
			closable : true
		});
	}
}

function logoutFun(b) {
	$.getJSON('/logout', {
		t : new Date()
	}, function(result) {
		if (b) {
			location.replace('/');
		} else {
			$('#loginDialog').dialog('open');
		}
	});
}

function editCurrentUserPwd() {
	parent.$.modalDialog({
		title : '修改密码',
		width : 300,
		height : 250,
		href : '/userController/editCurrentUserPwdPage',
		buttons : [ {
			text : '修改',
			handler : function() {
				var f = parent.$.modalDialog.handler.find('#editCurrentUserPwdForm');
				f.submit();
			}
		} ]
	});
}

//用户登录
$('#loginDialog').dialog({
    title: '用户登录',
    width: 400,
    height: 200,
    closed: false,
    cache: false,
    href: 'get_content.php',
    modal: true
});

// 解决iframe方式内存泄露问题，tab关闭时自动调用
$.fn.panel.defaults = $.extend({}, $.fn.panel.defaults, {
	onBeforeDestroy : function() {
		var frame = $('iframe', this);
		if (frame.length > 0) {
			frame[0].contentWindow.document.write('');
			frame[0].contentWindow.close();
			frame.remove();
			if ($.browser.msie) {
				CollectGarbage();
			}
		}
	}
});