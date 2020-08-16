/**
 * Created by len on 2018-01-02.
 */
$(function(){
    //调用查询列表的方法
    queryDc11List();
})


/**
 * 查询dc11列表信息
 */
function queryDc11List(){
    var dc11_grid = $("#dc11_grid").DataTable({
        "lengthMenu":[10,20,30,40], //改变每页显示条数列表的选项
        "searching":false,//禁用搜索
        "lengthChange":true,//允许改变每页显示的数据条数
        "paging": true,//允许表格分页
        "Processing" : true,//显示加载信息
        "serverSide" : true,//开启服务器模式
        "autoWidth" : false,//让Datatables自动计算宽度
        //"sort" : "position",
        "deferRender":true,//延迟渲染可以提高Datatables的加载速度
        "stateSave" : false, //是否允许浏览器缓存Datatables，以便下次恢复之前的状态    true代表，如果在第二页修改数据保存之后，刷新还在第二页不会自动跳转到第一页
        "pageLength" : 10,  //初始每页显示多少条
        "displayStart" : 0, //初始分页起始点
        "dom": '<l<\'#topPlugin\'>f>rt<ip><"clear">',
        // "dom": 'T<"clear">lfrtip',
        // "dom": 'iCflrtp',
//	     "tableTools": {
//	            "sSwfPath": contextPath +"/website/copy_csv_xls_pdf.swf",
//	            "sRowSelector": "td:not(:first-child)"
//	           ,
////	            "aButtons": [
////	                 {sExtends: "csv", oSelectorOpts: {page: "current" }},
////	                 "print"],
//	            "sRowSelect": "os",
//	        },
        "ordering": false,//全局禁用排序
        "ajax": {
            "type": "POST",
            //"dataType": "jsonp",
            "url":"/dc11/dc11List"
            // "data":function(d){
            //     d.state=$("#state").val();
            //     d.deptname=$("#deptname").val().trim();
            // }
        },
        "columns" : [
            {
                "data" : "aac002",
                "orderable" : false, // 禁用排序  开启或关闭某列的排序
                "defaultContent" : "",
                "width" : "10%",

            },
            {
                "data" : "aae101",
                "orderable" : false, // 禁用排序
                "defaultContent" : "",
                "width" : "10%"

            },
            {
                "data": "aae009",
                "orderable": false, // 禁用排序
                "defaultContent": "",
                "width": "10%"
            }  ,{
                "data" : "aae203",
                "orderable" : false, // 禁用排序
                "defaultContent" : "",
                "width" : "3%",
                "render" : function(data, type, full, meta) {
//						if(data==1){
//							return data ='有效';
//						}else{
//							return data ='无效';
//						}
                    //return data == 1 ? '有效' : '<font color="red">无效</font>';
                    // if(data==1){
                    //     data ="<a href='#' class='upOrderStatus' data-id="+full.id+">无效</a>";
                    // }else{
                    //     data ="<a href='#' class='upOrderStatus' data-id="+full.id+"><font color='red'>有效</font></a>";
                    // }
                    if(data==1){
                        data ="无效";
                    }else{
                        data ="<font color='red'>有效</font>";
                    }
                    return	 data;
                }
            },
            /*{
                "data" : "createtime",
                "orderable" : false, // 禁用排序
                "defaultContent" : "",
                "width" : "7%",
                "render" : function(data, type, full, meta) {
                    //时间格式化
                    var ret = moment(data).format("YYYY-MM-DD HH:mm:ss");
                    return ret == 'Invalid date' ? '' : ret;
                }
            },*/
            {
                "data" : "aac002",
                "orderable" : false, // 禁用排序
                "defaultContent" : '',
                "width" : "3%",
                "render":function(data, type, full, meta){
                    // <button id="editOne" class="btn btn-info btn-sm" data-id='+data+'>编 辑</button>
                    return	data='<button id="deleteOne" class="btn btn-danger btn-sm" data-id='+data+'>删 除</button>';

                }
            }],
        // "columnDefs" :
        //     [{
        //         "orderable" : false, // 禁用排序
        //         "targets" : [0], // 指定的列
        //         "data" : "id",
        //         "render" : function(data, type, full, meta) {
        //             return '<input type="checkbox" value="'+ data + '" name="id"/>';
        //         }
        //     }],
        "language" : { // 国际化配置
            "processing" : "正在获取数据，请稍后...", //显示正在处理字符串
            "lengthMenu" : "显示 _MENU_ 条",//页面显示数据条数选项
            "zeroRecords" : "没有找到数据", //过滤后空的结果字符串
            "info" : "从 _START_ 到  _END_ 条记录 总记录数为 _TOTAL_ 条",//表格主要信息显示字符串
            "infoEmpty" : "记录数为0",   //当表格没有数据和搜索记录时，表格主要信息显示字符串
            "infoFiltered" : "(全部记录数 _MAX_ 条)",//当使用搜索功能后，表格主要信息处追加的字符
            "infoPostFix" : "", //追加到所有其他主要信息字符串之后
            "search" : "搜索",  //搜索框显示字符串
            "url" : "", //从远程加载语言信息文件url
            "paginate" : {
                "first" : "第一页",
                "previous" : "上一页",
                "next" : "下一页",
                "last" : "最后一页"
            }
        },
        // initComplete:initComplete,//表格加载完成回调函数
        drawCallback: function( settings ) { //表格重绘的时候回调函数
            //  $('input[name=allChecked]')[0].checked=false;//取消全选状态
//	        function initComplete(data){
//	        	//上方topPlugin DIV中追加HTML
//	        	var topPlugin='<button id="addButton" class="btn btn-success btn-sm" data-toggle="modal" data-target="#addUser" style="display:block;">' +
//	        				  '<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>批量删除</button>';
//
//	        	//删除用户按钮的HTMLDOM
//	            var deleteHtml='<button id="deleteButton" class="btn btn-danger btn-sm" style="flot:left;margin-right:10px;">批量删除用户</button>' +
//	    					       '<span name="deleteTips" style="flot:left;margin-right:10px;color:green;"></span><div style="clear:left;"></div>';
//
//	    		$("#topPlugin").append(topPlugin);//在表格上方topPlugin DIV中追加HTML
//	           // $("#deleteDep").append(deleteHtml);//表格下方的按钮操作区
//	           // $("#deleteButton").on("click", deleteUser);//给下方按钮绑定事件
//
//	        }
        }

    });

}