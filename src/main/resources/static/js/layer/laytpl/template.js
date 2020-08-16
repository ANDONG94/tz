(function(){
	/**
	 * 加载一个界面模板
	 */
	function laytplUrl(tmpUrl, data, callback){
		$.ajax({
			contentType : "text/text; charset=UTF-8",
			url : tmpUrl,
			dateType : "html",
			type : "GET",
			success : function(gettpl) {
				laytpl(gettpl).render(data, function(html) {
					if (callback && $.isFunction(callback)) {
						callback(data, html);
					}
				});
			},
			error : function(a, b, c) {

			}
		});
	}
	/**
	 * 加载一个js模板
	 */
	function laytplJs(tpl,data, callback){
		var tpl = document.getElementById(tpl).innerHTML;
		laytpl(tpl).render(data, function(html){
			if (callback && $.isFunction(callback)) {
				callback(data, html);
			}
		});
	}
	
	
	window.laytplUrl = laytplUrl;
	window.laytplJs = laytplJs;
})();