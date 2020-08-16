/**
 * Created by len on 2019-04-28.
 */
$(function () {

    //树节点的初始化事件
    $.fn.zTree.init($("#tree-div"), sys_perm_setting);

    queryParam('EDC441','phd013');//易地搬迁
    queryParam('AAC004','phd004');//性别
    queryParam('AAC004','plf007');//性别
    queryParam('AAA012','phd014');//脱贫状态
    queryParam('AAC011','plf009');//文化程度
    queryParam('AAA001','plf014');//苏陕协作
    queryParam('AAA002','plf011');//就业创业培训状态
    queryParam('AAA004','plf010');//就业创业意愿
    queryParam('EDC441','phd016');//就业创业意愿
    queryParam('EDC441','plf023');//就业创业意愿
    queryParam('PLF024','plf024');//培训教育状态

    queryParam('AAA016','plf018');//劳动能力
    queryParam('AAA017','plf015');//上学状态
    queryParam('AAA018','eys005');//岗位工种
    queryParam('AAA019','epp003');//创业项目
    queryParam('AAA019','tsn004');//培训工种
    queryParam('EDC441','epp010');//登记注册
    queryParam('AAA020','epp013');//享受政策





    queryParam('AAA013','eys003');//就业渠道
    queryParam('AAA008','eys012');//就业区域
    queryParam('AAA006','eys004');//就业类型
    queryParam('ECC207','eys007');//就业单位性质
    queryParam('AAA005','eys011');//特色就业

    queryParam('AAA007','tsn003');//培训类型


    queryParam('AAA008','ths009');//院校区域
    queryParam('EDC441','ths010');//是否苏陕协作

    queryParam('AAA003','plf012');// 培训教育意愿
    queryParam('AAA011','plf017');//就业意向

    queryParam('EDC441','tsn012');//是否苏陕协作


    $("#plf010").attr("disabled",true);
    $("#plf012").attr("disabled",true);
    //日期控件   就业起始日期
    laydate({
        elem: '#eys009', //对应id    就业起始日期
        format: 'YYYY-MM-DD', //日期格式 // 分隔符可以任意定义，该例子表示只显示年月
        min: '1900-01-01', //设定最小日期为当前日期
        max: '2099-06-16', //最大日期
        festival: true, //显示节日
        istime: false,   //是否显示时分秒
        istoday: true, //是否是今天
        choose: function(datas){ //选择日期完毕的回调
            /*end.min = datas; //开始日选好后，重置结束日的最小日期
             end.start = datas //将结束日的初始值设定为开始日*/
        }
    });

    //日期控件   就业终止日期
    laydate({
        elem: '#eys010', //对应id    就业起始日期
        format: 'YYYY-MM-DD', //日期格式 // 分隔符可以任意定义，该例子表示只显示年月
        min: '1900-01-01', //设定最小日期为当前日期
        max: '2099-06-16', //最大日期
        festival: true, //显示节日
        istime: false,   //是否显示时分秒
        istoday: true, //是否是今天
        choose: function(datas){ //选择日期完毕的回调
            /*end.min = datas; //开始日选好后，重置结束日的最小日期
             end.start = datas //将结束日的初始值设定为开始日*/
        }
    });

    //日期控件   创业起始日期
    laydate({
        elem: '#epp006', //对应id    就业起始日期
        format: 'YYYY-MM-DD', //日期格式 // 分隔符可以任意定义，该例子表示只显示年月
        min: '1900-01-01', //设定最小日期为当前日期
        max: '2099-06-16', //最大日期
        festival: true, //显示节日
        istime: false,   //是否显示时分秒
        istoday: true, //是否是今天
        choose: function(datas){ //选择日期完毕的回调
            /* end.min = datas; //开始日选好后，重置结束日的最小日期
             end.start = datas //将结束日的初始值设定为开始日*/
        }
    });

    //日期控件   创业终止日期
  /*  laydate({
        elem: '#epp007', //对应id    就业起始日期
        format: 'YYYY-MM-DD', //日期格式 // 分隔符可以任意定义，该例子表示只显示年月
        min: '1900-01-01', //设定最小日期为当前日期
        max: '2099-06-16', //最大日期
        festival: true, //显示节日
        istime: false,   //是否显示时分秒
        istoday: true, //是否是今天
        choose: function(datas){ //选择日期完毕的回调
            /!*end.min = datas; //开始日选好后，重置结束日的最小日期
             end.start = datas //将结束日的初始值设定为开始日*!/
        }
    });*/

    //日期控件   培训起始日期
    laydate({
        elem: '#tsn007', //对应id    就业起始日期
        format: 'YYYY-MM-DD', //日期格式 // 分隔符可以任意定义，该例子表示只显示年月
        min: '1900-01-01', //设定最小日期为当前日期
        max: '2099-06-16', //最大日期
        festival: true, //显示节日
        istime: false,   //是否显示时分秒
        istoday: true, //是否是今天
        choose: function(datas){ //选择日期完毕的回调
            /*end.min = datas; //开始日选好后，重置结束日的最小日期
             end.start = datas //将结束日的初始值设定为开始日*/
        }
    });

    //日期控件   培训终止日期
    laydate({
        elem: '#tsn008', //对应id    就业起始日期
        format: 'YYYY-MM-DD', //日期格式 // 分隔符可以任意定义，该例子表示只显示年月
        min: '1900-01-01', //设定最小日期为当前日期
        max: '2099-06-16', //最大日期
        festival: true, //显示节日
        istime: false,   //是否显示时分秒
        istoday: true, //是否是今天
        choose: function(datas){ //选择日期完毕的回调
            /* end.min = datas; //开始日选好后，重置结束日的最小日期
             end.start = datas //将结束日的初始值设定为开始日*/
        }
    });

    //日期控件   培训后就业年月
    laydate({
        elem: '#tsn002', //对应id    就业起始日期
        format: 'YYYYMM', //日期格式 // 分隔符可以任意定义，该例子表示只显示年月
        min: '1900-01-01', //设定最小日期为当前日期
        max: '2099-06-16', //最大日期
        festival: true, //显示节日
        istime: false,   //是否显示时分秒
        istoday: true, //是否是今天
        choose: function(datas){ //选择日期完毕的回调
            /* end.min = datas; //开始日选好后，重置结束日的最小日期
             end.start = datas //将结束日的初始值设定为开始日*/
        }
    });




    //日期控件   入学日期
    laydate({
        elem: '#ths005', //对应id    就业起始日期
        format: 'YYYY-MM-DD', //日期格式 // 分隔符可以任意定义，该例子表示只显示年月
        min: '1900-01-01', //设定最小日期为当前日期
        max: '2099-06-16', //最大日期
        festival: true, //显示节日
        istime: false,   //是否显示时分秒
        istoday: true, //是否是今天
        choose: function(datas){ //选择日期完毕的回调
            /*end.min = datas; //开始日选好后，重置结束日的最小日期
             end.start = datas //将结束日的初始值设定为开始日*/
        }
    });

    //日期控件   毕业日期
    laydate({
        elem: '#ths006', //对应id    就业起始日期
        format: 'YYYY-MM-DD', //日期格式 // 分隔符可以任意定义，该例子表示只显示年月
        min: '1900-01-01', //设定最小日期为当前日期
        max: '2099-06-16', //最大日期
        festival: true, //显示节日
        istime: false,   //是否显示时分秒
        istoday: true, //是否是今天
        choose: function(datas){ //选择日期完毕的回调
            /*end.min = datas; //开始日选好后，重置结束日的最小日期
             end.start = datas //将结束日的初始值设定为开始日*/
        }
    });


    //日期控件   毕业后就业日期
    laydate({
        elem: '#ths008', //对应id    就业起始日期
        format: 'YYYYMM', //日期格式 // 分隔符可以任意定义，该例子表示只显示年月
        min: '1900-01-01', //设定最小日期为当前日期
        max: '2099-06-16', //最大日期
        festival: true, //显示节日
        istime: false,   //是否显示时分秒
        istoday: true, //是否是今天
        choose: function(datas){ //选择日期完毕的回调
            /*end.min = datas; //开始日选好后，重置结束日的最小日期
             end.start = datas //将结束日的初始值设定为开始日*/
        }
    });

    //日期控件   在苏就业年月
    laydate({
        elem: '#tsn013', //对应id    就业起始日期
        format: 'YYYYMM', //日期格式 // 分隔符可以任意定义，该例子表示只显示年月
        min: '1900-01-01', //设定最小日期为当前日期
        max: '2099-06-16', //最大日期
        festival: true, //显示节日
        istime: false,   //是否显示时分秒
        istoday: true, //是否是今天
        choose: function(datas){ //选择日期完毕的回调
            /*end.min = datas; //开始日选好后，重置结束日的最小日期
             end.start = datas //将结束日的初始值设定为开始日*/
        }
    });

    //日期控件   毕在陕就业年月
    laydate({
        elem: '#tsn014', //对应id    就业起始日期
        format: 'YYYYMM', //日期格式 // 分隔符可以任意定义，该例子表示只显示年月
        min: '1900-01-01', //设定最小日期为当前日期
        max: '2099-06-16', //最大日期
        festival: true, //显示节日
        istime: false,   //是否显示时分秒
        istoday: true, //是否是今天
        choose: function(datas){ //选择日期完毕的回调
            /*end.min = datas; //开始日选好后，重置结束日的最小日期
             end.start = datas //将结束日的初始值设定为开始日*/
        }
    });

    //日期控件   在陕创业年月
    laydate({
        elem: '#tsn015', //对应id    就业起始日期
        format: 'YYYYMM', //日期格式 // 分隔符可以任意定义，该例子表示只显示年月
        min: '1900-01-01', //设定最小日期为当前日期
        max: '2099-06-16', //最大日期
        festival: true, //显示节日
        istime: false,   //是否显示时分秒
        istoday: true, //是否是今天
        choose: function(datas){ //选择日期完毕的回调
            /*end.min = datas; //开始日选好后，重置结束日的最小日期
             end.start = datas //将结束日的初始值设定为开始日*/
        }
    });




    //点击叉号调用的事件   贫困户modal
    $('#useradd').on('hide.bs.modal', function () {
        var aab301 = $("#aab301").val();
        $("#useradd input").val('');
        $("#phd013").prop("value","");
        $("#phd004").prop("value","");
        $("#phd014").prop("value","");
        $('#worker_table').dataTable().fnClearTable();//清空datatables
        $("#add_laodongli_div").hide();//劳动力
        $("#aab301").val(aab301);
    });

    //点击叉号调用的事件   贫困劳动力modal
    $('#workeradd').on('hide.bs.modal', function () {
        $("#workeradd input").val('');
        $("#plf007").prop("value","");
        $("#plf009").prop("value","");
        $("#plf011").prop("value","");
        $("#plf014").prop("value","");
        $("#plf018").prop("value","");
        $("#plf015").prop("value","");
        $("#plf012").prop("value","");
        $("#plf017").prop("value","");
        $('#jiuye_table').dataTable().fnClearTable();//清空datatables
        $('#chuangye_table').dataTable().fnClearTable();//清空datatables
        $('#peixun_table').dataTable().fnClearTable();//清空datatables
        $('#school_table').dataTable().fnClearTable();//清空datatables*
        $("#jineng").hide();    //技能

    });
    //点击叉号调用的事件   就业modal
    $('#jiuyeadd').on('hide.bs.modal', function () {
        var plf001 = $("#plf001").val();//劳动力id
        $("#jiuyeadd input").val('');
        $("#eys003").prop("value","");
        $("#eys012").prop("value","");
        $("#eys004").prop("value","");
        $("#eys007").prop("value","");
        $("#eys011").prop("value","");
        $("#eys002").val(plf001);
        $("#plf001").val(plf001);//劳动力id
        $("input[name='eys022']").attr("checked",false);//清空复选框
    });

    //点击叉号调用的事件   创业modal
    $('#chuangyeadd').on('hide.bs.modal', function () {
        var plf001 = $("#plf001").val();//劳动力id
        $("#chuangyeadd input").val('');
        $("#epp002").val(plf001);
    });
    //点击叉号调用的事件   培训modal
    $('#peixunadd').on('hide.bs.modal', function () {
        var plf001 = $("#plf001").val();//劳动力id
        $("#peixunadd input").val('');
        $("#tsn010").val(plf001);
    });
    //点击叉号调用的事件   技工院校modal
    $('#schooladd').on('hide.bs.modal', function () {
        var plf001 = $("#plf001").val();//劳动力id
        $("#schooladd input").val('');
        $("#ths002").val(plf001);
    });

    //设置div高度
      $("#ceshi_many").css("height", $("#workeradd").height()-260);

})

//树配置
var sys_perm_setting = {
    edit: {
        drag:{
            prev: true,
            next: true,
            inner:true
        },
        enable: true,
        showRemoveBtn: false,
        showRenameBtn: false
    },
    view: {
        showLine: true,
        nameIsHTML: true
    },
    check: {
        enable: false,
        chkboxType:  { "Y": "s", "N": "s" } //chkboxType: { “Y”: “ps”, “N”: “ps” } Y 属性定义 checkbox 被勾选后的情况;N 属性定义 checkbox 取消勾选后的情况;“p” 表示操作会影响父级节点;“s” 表示操作会影响子级节点;请注意大小写，不要改变
    },
    data: {
        simpleData: {
            enable: true,
            pIdKey: "parentid"
        }
    },
    callback: {
        onClick: onClick,
        onAsyncSuccess:getTreeFirstNodeId
    },
    async: {
        enable: true,
        url:"/ztree/treedata",
        autoParam:["groupid"]
    }
};

//树节点的点击事件
function onClick (event, treeId, treeNode, clickFlag) {
    $("#aab301").val(treeNode.groupid);
    poorwork_datatable.ajax.reload();
}


//树默认展开第一级节点
function getTreeFirstNodeId(){
    var treeObj = $.fn.zTree.getZTreeObj("tree-div");
    var nodes = treeObj.getNodes();
    var firstNode = nodes[0];
    treeObj.expandNode(nodes[0], true);
    $("#aab301").val(firstNode.groupid);
    queryCentralizedSettlements();
}


var poorwork_datatable;//贫困户列表
var worker_datatable;//劳动力列表
var jiuye_datatable;//就业列表
var chuangye_datatable;//创业列表
var peixun_datatable;//培训列表
var school_datatable;//技工院校列表
var lang ;
$(function () {

    //提示信息  初始化设置 一般不需要改
    lang = {
        "sProcessing": "处理中...",
        "sLengthMenu": "每页 _MENU_ 项",
        "sZeroRecords": "没有匹配结果",
        "sInfo": "当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项。",
        "sInfoEmpty": "当前显示第 0 至 0 项，共 0 项",
        "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
        "sInfoPostFix": "",
        "sSearch": "搜索:",
        "sUrl": "",
        "sEmptyTable": "表中数据为空",
        "sLoadingRecords": "载入中...",
        "sInfoThousands": ",",
        "oPaginate": {
            "sFirst": "首页",
            "sPrevious": "上页",
            "sNext": "下页",
            "sLast": "末页",
            "sJump": "跳转"
        },
        "oAria": {
            "sSortAscending": ": 以升序排列此列",
            "sSortDescending": ": 以降序排列此列"
        }
    };

//加载贫困户表格
    loadpoor();
});


/**
 * 加载贫困户表格
 */
function  loadpoor() {
    //重要修改 表格内容的自定义，需要根据业务定制
    poorwork_datatable = $("#poorwork_table").dataTable({
        language:lang,  //提示信息
        autoWidth: false,  //禁用自动调整列宽
        ordering:false,//禁止排序
        scrollY:$(window).height()-170,//表窗口高度-100px
        destroy: true,
        stripeClasses: ["odd", "even"],  //为奇偶行加上样式，兼容不支持CSS伪类的场合
        processing: false,  //隐藏加载提示,自行处理
        serverSide: true,  //启用服务器端分页
        bLengthChange:false,//去掉下面的没有多少条
        aLengthMenu:[10,20,50,100],  //用户可自选每页展示数量 5条或10条
        searching: false,  //禁用原生搜索
        orderMulti: true,  //启用多列排序
        order: [],  //取消默认排序查询,否则复选框一列会出现小箭头
        renderer: "bootstrap",  //渲染样式：Bootstrap和jquery-ui
        pagingType: "full_numbers",  //分页样式：simple,simple_numbers,full,full_numbers
        columnDefs: [{
            "targets": 'nosort',  //列的样式名
            "orderable": false    //包含上样式名‘nosort’的禁止排序
        }],
        ajax: function (data, callback, settings) {

            $("#loading_div").modal('show');
            //封装请求参数
            var param = {};
            param.length= data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
            param.start = data.start;//开始的记录序号
            param.pageIndex = (data.start / data.length);//当前页码
            param.phd002_scan = $("#phd002_scan").val();
            param.phd003_scan = $("#phd003_scan").val();
            param.phd012_scan = $("#phd012_scan").val();
            param.treeid = $("#aab301").val();
            //ajax请求数据方法
            $.ajax({
                type: "POST",
                url: "/poorwork/query",//url请求的地址
                cache: false,  //禁用缓存
                data: param,  //传入组装的参数
                dataType: "json",
                success: function (result) {
                    //封装返回数据重要
                    var returnData = {};
                    //这里直接自行返回了draw计数器,应该由后台返回
                    //returnData.draw = data.draw;
                    //返回数据全部记录
                    returnData.recordsTotal = result.recordsTotal;
                    //后台不实现过滤功能，每次查询均视作全部结果
                    returnData.recordsFiltered = result.recordsFiltered;
                    //返回的数据列表
                    returnData.data = result.data;

                    //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                    //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                    callback(returnData);

                    //清除modal框
                    $("#loading_div").modal('hide');//清除加载动画
                }
            });
        },
        //列表表头字段
        columns: [
            { "data": "phd002" },
            { "data": "phd013" },
            { "data": "phd014" },
            { "data": "phd012" },
            { "data": "phd004" },
            { "data": "phd003" },
            { "data": "phd008" },
            { "data": "phd006" },
            /*{ "data": "phd007" },*/
            {"data":"phd001","width":"120px","render":function(data, type, full, meta){
                return	data="<button onclick=\"updatePoorWork('"+data+"')\"  class='btn btn-sm btn-primary btn-edit' data-toggle='modal' data-target='#useradd' >修 改</button>   <button  class='btn btn-sm btn-danger btn-del' onclick=\"delPoorwork('"+data+"')\">删 除</button>  ";
            }}]
    }).api();
    //此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象
}

/**
 * 点击添加贫困户按钮
 */
function useradd_onclick() {
    $("#addupdate").text("新增贫困户信息");
}

/**
 * 点击添加劳动力按钮，加载表格
 * 劳动力表格
 */
function addWorker(){
    //先判断贫困户信息是否已经登记，如果登记可以点击，否则不能点击，给个提示
    var plf002 = $("#plf002").val();
    if(plf002 != null && plf002 != ''){
        //打开模态框
        $("#workeradd").css({"display":"block","padding-left":"6px"});
        $("#workeradd_head").text("添加劳动力信息");

    }else{
        //提示先保存贫困户信息
        swal({
            title: "请您先保存贫困户信息!",
            text: "您好！必须先保存贫困户信息",
            timer: 2000,
            showConfirmButton: false
        });

    }
}

/**
 * 查询贫困户信息
 */
function searchPoor() {
    poorwork_datatable.ajax.reload();
}

/**
 * 加载劳动力表格
 */
function loadWorker(){
//重要修改 表格内容的自定义，需要根据业务定制
    worker_datatable = $("#worker_table").dataTable({
        language:lang,  //提示信息
        destroy: true,
        autoWidth: false,  //禁用自动调整列宽
        ordering:false,//禁止排序
        stripeClasses: ["odd", "even"],  //为奇偶行加上样式，兼容不支持CSS伪类的场合
        processing: false,  //隐藏加载提示,自行处理
        serverSide: false,  //启用服务器端分页
        aLengthMenu:[10,20,50,100],  //用户可自选每页展示数量 5条或10条
        searching: false,  //禁用原生搜索
        orderMulti: false,  //启用多列排序
        order: [],  //取消默认排序查询,否则复选框一列会出现小箭头
        renderer: "bootstrap",  //渲染样式：Bootstrap和jquery-ui
        pagingType: "full_numbers",  //分页样式：simple,simple_numbers,full,full_numbers
        paging: false,//禁止分页
        info:false,
        columnDefs: [{
            "targets": 'nosort',  //列的样式名
            "orderable": false    //包含上样式名‘nosort’的禁止排序
        }],
        ajax: function (data, callback, settings) {

            //封装请求参数
            var plf002 = $("#plf002").val();
            var phd001 = $("#phd001").val();//贫困户model中的id
            var aac002 = "";
            if(plf002 != null && plf002 !=''){
                aac002 = plf002;
            }else if(phd001 != null && phd001 !=''){
                aac002 = phd001;
            }
            $("#plf002").val(aac002);
            var param = {"aac002":aac002};

            //ajax请求数据方法
            $.ajax({
                type: "POST",
                url: "/poorwork/queryWorkerByPlf002",//url请求的地址
                cache: false,  //禁用缓存
                data: param,  //传入组装的参数
                dataType: "json",
                success: function (result) {
                    //封装返回数据重要
                    var returnData = {};
                    //返回的数据列表
                    returnData.data = result;
                    //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                    //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                    callback(returnData);
                }
            });
        },
        //列表表头字段
        columns: [
            { "data": "plf004" },
            { "data": "plf005" },
            { "data": "plf007" },
            { "data": "plf008" },
            { "data": "plf018" },
            { "data": "plf015" },
            { "data": "plf010" },
            { "data": "plf011" },
            { "data": "plf009" },
            { "data": "plf012" },
            {"data":"plf001","width":"120px", "render":function(data, type, full, meta){
                return	data="<button onclick=\"updateWorker('"+data+"')\"  class='btn btn-sm btn-primary btn-edit' data-toggle='modal' data-target='#workeradd' >修 改</button>   <button  class='btn btn-sm btn-danger btn-del' onclick=\"delworker('"+data+"')\">删 除</button>  ";
            }}]
    }).api();
    //此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象
}



/**
 * 保存贫困户信息
 */
function savePoorWork(){
    if(checkAll_poor() == 1){
        $.ajax({
            url:"/poorwork/savePoorWork",    //请求的url地址
            dataType:"text",   //返回格式为json
            async:false,//请求是否异步，默认为异步，这也是ajax重要特性
            data:$('#commentForm').serialize(),    //参数值
            type:"POST",   //请求方式
            beforeSend:function(){
                //请求前的处理
            },
            success:function(data){
                /*$("input").val('');
                 $("#phd013").prop("value","");
                 $("#phd004").prop("value","");
                 $("#phd014").prop("value","");*/
                //请求成功时处理
                if(data != null && data != undefined){
                    if(data == 'update'){
                        loadpoor();
                        swal("修改成功！", "您已经成功修改了这条信息。", "success");
                    }else{
                        loadpoor();
                        $("#plf002").val(data);
                        $("#add_laodongli_div").show();
                        swal("保存成功！", "您已经成功保存了这条信息。", "success");
                    }
                }
            },
            complete:function(){

            },
            error:function(){
                //请求出错处理
            }
        });
    }

}

/**
 * 修改贫困户信息
 */
function updatePoorWork(id){

    $("#addupdate").text("修改贫困户信息");
    $.ajax({
        url:"/poorwork/updatePoorWork",    //请求的url地址
        dataType:"json",   //返回格式为json
        async:false,//请求是否异步，默认为异步，这也是ajax重要特性
        data:{"phd001":id},    //参数值
        type:"POST",   //请求方式
        beforeSend:function(){
            //请求前的处理
        },
        success:function(data){
            $("#phd001").val(data.phd001);
            $("#phd002").val(data.phd002);
            $("#phd003").val(data.phd003);
            $("#phd006").val(data.phd006);
            $("#phd005").val(data.phd005);
            $("#phd004").prop("value",data.phd004);
            $("#phd013").prop("value",data.phd013);
            $("#phd010").prop("value",data.phd010);
            $("#phd014").prop("value",data.phd014);
            $("#phd012").val(data.phd012);
            $("#phd007").val(data.phd007);
            $("#aab301").val(data.aab301);
            $("#phd011").val(data.phd011);//备注
            $("#phd016").prop("value",data.phd016);//是都残疾人
            if(data.phd002 != null &&  data.phd002 != ''){
                loadWorker();//加载劳动力表格
            }
            $("#add_laodongli_div").show();//显示出劳动力的添加按钮

        },
        complete:function(){
            //请求完成的处理
        },
        error:function(){
            //请求出错处理
        }
    });
}

/**
 *删除贫困户信息
 */
function delPoorwork(id){
    swal({
        title: "您确定要删除这条信息吗",
        text: "删除后将无法恢复，请谨慎操作！",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        cancelButtonText:"取消",
        confirmButtonText: "删除",
        closeOnConfirm: false
    }, function () {
        $.ajax({
            url:"/poorwork/delPoorWork",    //请求的url地址
            dataType:"text",   //返回格式为json
            async:false,//请求是否异步，默认为异步，这也是ajax重要特性
            data:{"phd001":id},    //参数值
            type:"POST",   //请求方式
            beforeSend:function(){
                //请求前的处理
            },
            success:function(data){
                //请求成功时处理
                if(data != null && data != undefined){
                    if(data == 'yes'){
                        loadpoor();//加载劳动力表格
                        swal("删除成功！", "您已经永久删除了这条信息。", "success");
                    }else if(data == 'no'){
                        swal("删除失败！", "您未删除这条信息。", "fail");
                    }
                }
            },
            complete:function(){

            },
            error:function(){
                //请求出错处理
            }
        });

    });
}


//*******************************以下是劳动力信息**********************************************************


/**
 * 户主也是劳动力的点击方法
 */
function poorAlsoWorker() {
    //将户主的基本信息放在劳动力的下拉框中
    var id = $("#phd001").val();
    $.ajax({
        url:"/poorwork/updatePoorWork",    //请求的url地址
        dataType:"json",   //返回格式为json
        async:false,//请求是否异步，默认为异步，这也是ajax重要特性
        data:{"phd001":id},    //参数值
        type:"POST",   //请求方式
        beforeSend:function(){
            //请求前的处理
        },
        success:function(data){
            $("#plf002").val(data.phd001);  //贫困户id
            $("#plf004").val(data.phd002);  //姓名
            $("#plf005").val(data.phd003);  //身份证号
            $("#plf007").prop("value",data.phd004);//性别
            $("#plf006").val(data.phd006);  //电话
            $("#plf008").val(data.phd005);  //年龄
            if(data.phd002 != null &&  data.phd002 != ''){
                loadWorker();//加载劳动力表格
            }

        },
        complete:function(){
            //请求完成的处理
        },
        error:function(){
            //请求出错处理
        }
    });
}


/**
 * 保存劳动力信息
 */
function saveWorker(){
    if(checkAll_worker() == 1){
        $.ajax({
            url:"/poorwork/saveWorker?workeraab301="+$("#aab301").val(),    //请求的url地址
            dataType:"text",   //返回格式为json
            async:false,//请求是否异步，默认为异步，这也是ajax重要特性
            data:$('#workerForm').serialize(),    //参数值
            type:"POST",   //请求方式
            beforeSend:function(){
                //请求前的处理
            },
            success:function(data){
                //请求成功时处理
                if(data != null && data != undefined){
                    if(data == 'update'){
                        //请求完成的处理

                        loadWorker();
                        swal("修改成功！", "您已经成功修改了这条信息。", "success");
                    }else{  //说明是新增
                        //请求完成的处理
                        loadWorker();
                        $("#plf001").val(data); //劳动力主键  因为要给劳动力下添加技能情况，所以需要劳动力主键信息
                        $("#jineng").show();
                        swal("保存成功！", "您已经成功保存了这条信息。", "success");
                    }
                }
            },
            complete:function(){

            },
            error:function(){
                //请求出错处理
            }
        });
    }

}

/**
 * 修改劳动力信息
 */
function updateWorker(id){
    $("#plf001").val(id);
    //点击修改劳动力信息时，加载该劳动力下的所有表格
    loadJiuYeTable()//就业信息表格
    loadChuangYeTable()//创业信息表格
    loadPeiXunTable()//培训信息表格
    loadSchoolTable()//技工院校表格
    $("#workeradd_head").text("修改劳动力信息");
    $.ajax({
        url:"/poorwork/updateWorker",    //请求的url地址
        dataType:"json",   //返回格式为json
        async:false,//请求是否异步，默认为异步，这也是ajax重要特性
        data:{"plf001":id},    //参数值
        type:"POST",   //请求方式
        beforeSend:function(){
            //请求前的处理
        },
        success:function(data){
            $("#plf001").val(data.plf001);
            $("#plf002").val(data.plf002);
            $("#plf004").val(data.plf004);
            $("#plf005").val(data.plf005);
            $("#plf007").prop("value",data.plf007);
            $("#plf009").prop("value",data.plf009);
            $("#plf010").prop("value",data.plf010);
            $("#plf006").val(data.plf006);
            $("#plf011").prop("value",data.plf011);
            $("#plf012").prop("value",data.plf012);
            $("#plf017").prop("value",data.plf017);
            $("#plf014").prop("value",data.plf014);
            $("#plf015").prop("value",data.plf015);
            $("#plf018").prop("value",data.plf018);
            $("#plf008").val(data.plf008);
            $("#plf019").val(data.plf019);
            $("#plf023").prop("value",data.plf023);
            $("#plf024").prop("value",data.plf024);

            $("#jineng").show();//显示出按钮
        },
        complete:function(){
            //请求完成的处理
        },
        error:function(){
            //请求出错处理
        }
    });
}



/**
 *删除劳动力信息
 */
function delworker(id){
    swal({
        title: "您确定要删除这条信息吗",
        text: "删除后将无法恢复，请谨慎操作！",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "删除",
        cancelButtonText:"取消",
        closeOnConfirm: false
    }, function () {
        $.ajax({
            url:"/poorwork/deleteWorker",    //请求的url地址
            dataType:"text",   //返回格式为json
            async:false,//请求是否异步，默认为异步，这也是ajax重要特性
            data:{"plf001":id},    //参数值
            type:"POST",   //请求方式
            beforeSend:function(){
                //请求前的处理
            },
            success:function(data){
                //请求成功时处理
                if(data != null && data != undefined){
                    if(data == 'yes'){
                        worker_datatable.ajax.reload();
                        swal("删除成功！", "您已经永久删除了这条信息。", "success");
                    }else if(data == 'no'){
                        swal("删除失败！", "您未删除这条信息。", "fail");
                    }
                }
            },
            complete:function(){

            },
            error:function(){
                //请求出错处理
            }
        });

    });
}

//***************************************以下是就业信息*******************************************************

/**
 * 加载就业信息表格
 * 就业表格
 */
function loadJiuYeTable(){
    //重要修改 表格内容的自定义，需要根据业务定制
    jiuye_datatable = $("#jiuye_table").dataTable({
        destroy: true,
        autoWidth: false,  //禁用自动调整列宽
        ordering:false,//禁止排序
        stripeClasses: ["odd", "even"],  //为奇偶行加上样式，兼容不支持CSS伪类的场合
        processing: false,  //隐藏加载提示,自行处理
        serverSide: false,  //启用服务器端分页
        aLengthMenu:[10,20,50,100],  //用户可自选每页展示数量 5条或10条
        searching: false,  //禁用原生搜索
        orderMulti: false,  //启用多列排序
        order: [],  //取消默认排序查询,否则复选框一列会出现小箭头
        renderer: "bootstrap",  //渲染样式：Bootstrap和jquery-ui
        pagingType: "full_numbers",  //分页样式：simple,simple_numbers,full,full_numbers
        paging: false,//禁止分页
        info:false,
        columnDefs: [{
            "targets": 'nosort',  //列的样式名
            "orderable": false    //包含上样式名‘nosort’的禁止排序
        }],
        ajax: function (data, callback, settings) {

            //封装请求参数
            var plf001 = $("#plf001").val();//劳动力id
            $("#eys002").val(plf001);
            var param = {"eys002":plf001};
            //根据劳动力id 查询就业信息
            //ajax请求数据方法
            $.ajax({
                type: "POST",
                url: "/poorwork/queryJiuYeByEys002",//url请求的地址
                cache: false,  //禁用缓存
                data: param,  //传入组装的参数
                dataType: "json",
                success: function (result) {
                    //封装返回数据重要
                    var returnData = {};
                    //返回的数据列表
                    returnData.data = result;
                    //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                    //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                    callback(returnData);
                }
            });
        },
        //列表表头字段
        columns: [
            { "data": "eys003" },
            { "data": "eys004" },
            { "data": "eys008" },
            { "data": "eys012" },
            { "data": "eys006" },
            { "data": "eys009" },
            { "data": "eys010" },
            {"data":"eys001", "render":function(data, type, full, meta){
                return	data="<button onclick=\"updateJiuye('"+data+"')\"  class='btn btn-sm btn-primary btn-edit' data-toggle='modal' data-target='#jiuyeadd' >修 改</button>   <button  class='btn btn-sm btn-danger btn-del' onclick=\"deleteJiuye('"+data+"')\">删 除</button>  ";
            }}]
    }).api();
    //此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象
}

/**
 *添加就业按钮的点击方法，给就业modal 隐藏域中房劳动力id
 */
function jiuyeadd_onclick() {
    var plf001 = $("#plf001").val();//获取劳动力id
    $("#eys002").val(plf001);
}


/**
 * 保存就业信息
 */
function saveJiuYe(){
    if(checkAll_jiuye() == 1){
        $.ajax({
            url:"/poorwork/saveJiuYe?jiuyeaab301="+$("#aab301").val(),    //请求的url地址
            dataType:"text",   //返回格式为json
            async:false,//请求是否异步，默认为异步，这也是ajax重要特性
            data:$('#jiuyeForm').serialize(),    //参数值
            type:"POST",   //请求方式
            beforeSend:function(){
                //请求前的处理
            },
            success:function(data){
                var plf001 = $("#plf001").val();//劳动力id
                $("#jiuyeadd input").val('');
                $("#eys003").prop("value","");
                $("#eys012").prop("value","");
                $("#eys004").prop("value","");
                $("#eys007").prop("value","");
                $("#eys011").prop("value","");
                $("#eys005").prop("value","");

                $("#eys002").val(plf001);
                $("#plf001").val(plf001);//劳动力id
                //请求成功时处理
                if(data != null && data != undefined){
                    if(data == 'update'){
                        //请求完成的处理
                        loadJiuYeTable();
                        swal("修改成功！", "您已经成功修改了这条信息。", "success");
                    }else{  //说明是新增
                        //请求完成的处理
                        loadJiuYeTable();
                        swal("保存成功！", "您已经成功保存了这条信息。", "success");
                    }
                }
            },
            complete:function(){

            },
            error:function(){
                //请求出错处理
            }
        });
    }

}

/**
 * 修改就业信息
 */
function updateJiuye(id){

    $("#jiuye_head").text("修改就业信息");
    $.ajax({
        url:"/poorwork/updateJiuye",    //请求的url地址
        dataType:"json",   //返回格式为json
        async:false,//请求是否异步，默认为异步，这也是ajax重要特性
        data:{"eys001":id},    //参数值
        type:"POST",   //请求方式
        beforeSend:function(){
            //请求前的处理
        },
        success:function(data){
            $("#eys001").val(data.eys001);
            $("#eys002").val(data.eys002);
            $("#eys003").prop("value",data.eys003);
            $("#eys012").prop("value",data.eys012);
            $("#eys004").prop("value",data.eys004);
            $("#eys015").val(data.eys015);
            $("#eys006").val(data.eys006);
            $("#eys007").prop("value",data.eys007);
            $("#eys005").val(data.eys005);
            $("#eys011").prop("value",data.eys011);
            $("#eys009").val(data.eys009);
            $("#eys010").val(data.eys010);
            $("#eys013").val(data.eys013);
            $("#eys014").val(data.eys014);
            $("#eys008").val(data.eys008);//就业地址
            $("#eys019").val(data.eys019);
            $("#eys020").val(data.eys020);
            $("#eys021").val(data.eys021);
            $("#eys016").val(data.eys016);
            $("#eys017").val(data.eys017);
            $("#eys018").val(data.eys018);
            if(data.eys022 != null && data.eys022 !=''){
                var eys022 = data.eys022;
                var flavors = eys022.split(",");
                //对复选框进行选中操作
                $.each(flavors,function(i,item){
                    $("input[name='eys022'][value="+item+"]").attr("checked","checked");
                });
            }

            if(data.eys019 !=null && data.eys019 !=''){
                $("#eys019").attr("readonly",false);//将input元素设置为readonly
            }
            if(data.eys020 !=null && data.eys020 !=''){
                $("#eys020").attr("readonly",false);//将input元素设置为readonly
            }
            if(data.eys021 !=null && data.eys021 !=''){
                $("#eys021").attr("readonly",false);//将input元素设置为readonly
            }

            if(data.eys011=="5" || data.eys011=="6" || data.eys011=="7"){
                $("#jiuye_type").show();
            }else{
                $("#jiuye_type").hide();
            }


        },
        complete:function(){
            //请求完成的处理
        },
        error:function(){
            //请求出错处理
        }
    });
}

/**
 * 就业
 * 点击清空按钮
 */
function clean_input_jiuye() {
    var eys001 = $("#eys001").val();
    var plf001 = $("#plf001").val();//劳动力id
    $("#jiuyeadd input").val('');
    $("input[name='eys022']").attr("checked",false);//清空复选框
    $("#eys003").prop("value","");
    $("#eys012").prop("value","");
    $("#eys004").prop("value","");
    $("#eys007").prop("value","");
    $("#eys011").prop("value","");
    $("#eys005").prop("value","");
    $("#eys002").val(plf001);
    $("#plf001").val(plf001);//劳动力id
    if(eys001 != null && eys001 !=''){
        updateJiuye(eys001);
    }else{
    }

}

/**
 *删除就业信息
 */
function deleteJiuye(id){
    swal({
        title: "您确定要删除这条信息吗",
        text: "删除后将无法恢复，请谨慎操作！",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "删除",
        cancelButtonText:"取消",
        closeOnConfirm: false
    }, function () {
        $.ajax({
            url:"/poorwork/deleteJiuye",    //请求的url地址
            dataType:"text",   //返回格式为json
            async:false,//请求是否异步，默认为异步，这也是ajax重要特性
            data:{"eys001":id},    //参数值
            type:"POST",   //请求方式
            beforeSend:function(){
                //请求前的处理
            },
            success:function(data){
                //请求成功时处理
                if(data != null && data != undefined){
                    if(data == 'yes'){
                        loadJiuYeTable();
                        swal("删除成功！", "您已经永久删除了这条信息。", "success");
                    }else if(data == 'no'){
                        swal("删除失败！", "您未删除这条信息。", "fail");
                    }
                }
            },
            complete:function(){

            },
            error:function(){
                //请求出错处理
            }
        });

    });
}

//************************************以下是创业信息***************************************************************


function loadChuangYeTable() {
    //重要修改 表格内容的自定义，需要根据业务定制
    chuangye_datatable = $("#chuangye_table").dataTable({
        destroy: true,
        autoWidth: false,  //禁用自动调整列宽
        ordering:false,//禁止排序
        stripeClasses: ["odd", "even"],  //为奇偶行加上样式，兼容不支持CSS伪类的场合
        processing: false,  //隐藏加载提示,自行处理
        serverSide: false,  //启用服务器端分页
        aLengthMenu:[10,20,50,100],  //用户可自选每页展示数量 5条或10条
        searching: false,  //禁用原生搜索
        orderMulti: false,  //启用多列排序
        order: [],  //取消默认排序查询,否则复选框一列会出现小箭头
        renderer: "bootstrap",  //渲染样式：Bootstrap和jquery-ui
        pagingType: "full_numbers",  //分页样式：simple,simple_numbers,full,full_numbers
        paging: false,//禁止分页
        info:false,
        columnDefs: [{
            "targets": 'nosort',  //列的样式名
            "orderable": false    //包含上样式名‘nosort’的禁止排序
        }],
        ajax: function (data, callback, settings) {

            //封装请求参数
            var plf001 = $("#plf001").val();//劳动力id
            $("#epp002").val(plf001);
            var param = {"epp002":plf001};
            //根据劳动力id 查询就业信息
            //ajax请求数据方法
            $.ajax({
                type: "POST",
                url: "/poorwork/queryEntrepreneurshipByEpp002",//url请求的地址
                cache: false,  //禁用缓存
                data: param,  //传入组装的参数
                dataType: "json",
                success: function (result) {
                    //封装返回数据重要
                    var returnData = {};
                    //返回的数据列表
                    returnData.data = result;
                    //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                    //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                    callback(returnData);
                }
            });
        },
        //列表表头字段
        columns: [
            { "data": "epp003" },
            { "data": "epp004" },
            { "data": "epp005" },
            { "data": "epp009" },
            { "data": "epp006" },
            {"data":"epp001", "render":function(data, type, full, meta){
                return	data="<button onclick=\"updateChuangye('"+data+"')\"  class='btn btn-sm btn-primary btn-edit' data-toggle='modal' data-target='#chuangyeadd' >修 改</button>   <button  class='btn btn-sm btn-danger btn-del' onclick=\"delChuangye('"+data+"')\">删 除</button>  ";
            }}]
    }).api();
    //此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象
}


/**
 *添加创业按钮的点击方法，给就业modal 隐藏域中房劳动力id
 */
function chuangyeadd_onclick() {
    var plf001 = $("#plf001").val();//获取劳动力id
    $("#epp002").val(plf001);
}


/**
 * 保存创业信息
 */
function saveChuangye(){
    if(checkAll_chuangye() == 1){
        $.ajax({
            url:"/poorwork/saveChuangYe?chuangyeaab301="+$("#aab301").val(),    //请求的url地址
            dataType:"text",   //返回格式为json
            async:false,//请求是否异步，默认为异步，这也是ajax重要特性
            data:$('#chuangyeForm').serialize(),    //参数值
            type:"POST",   //请求方式
            beforeSend:function(){
                //请求前的处理
            },
            success:function(data){
                //请求成功时处理
                if(data != null && data != undefined){
                    var plf001 = $("#plf001").val();//劳动力id
                    $("#chuangyeadd input").val('');
                    $("#epp002").val(plf001);
                    if(data == 'update'){
                        //请求完成的处理
                        loadChuangYeTable();
                        swal("修改成功！", "您已经成功修改了这条信息。", "success");
                    }else{  //说明是新增
                        //请求完成的处理
                        loadChuangYeTable();
                        swal("保存成功！", "您已经成功保存了这条信息。", "success");
                    }
                }
            },
            complete:function(){

            },
            error:function(){
                //请求出错处理
            }
        });
    }

}

/**
 * 修改创业信息
 */
function updateChuangye(id){

    $("#chuangye_head").text("修改创业信息");
    $.ajax({
        url:"/poorwork/updateChuangYe",    //请求的url地址
        dataType:"json",   //返回格式为json
        async:false,//请求是否异步，默认为异步，这也是ajax重要特性
        data:{"epp001":id},    //参数值
        type:"POST",   //请求方式
        beforeSend:function(){
            //请求前的处理
        },
        success:function(data){
            $("#epp001").val(data.epp001);
            $("#epp002").val(data.epp002);
            $("#epp003").prop("value",data.epp003);
            $("#epp004").val(data.epp004);
            $("#epp005").val(data.epp005);
            $("#epp006").val(data.epp006);
            $("#epp008").val(data.epp008);
            $("#epp009").val(data.epp009);
            $("#epp010").val(data.epp010);
            $("#epp011").val(data.epp011);
            $("#epp012").val(data.epp012);
            var epp013 = data.epp013;
            if(epp013 != null && epp013 !=''){
                var flavors = epp013.split(",");
                //对复选框进行选中操作
                $.each(flavors,function(i,item){
                    $("input[name='epp013'][value="+item+"]").attr("checked","checked");
                });
            }
            if(data.epp014 !=null && data.epp014 !=''){
                $("#epp014").attr("readonly",false);//将input元素设置为readonly
            }
            if(data.epp015 !=null && data.epp015 !=''){
                $("#epp015").attr("readonly",false);//将input元素设置为readonly
            }
            $("#epp014").val(data.epp014);
            $("#epp015").val(data.epp015);
        },
        complete:function(){
            //请求完成的处理
        },
        error:function(){
            //请求出错处理
        }
    });
}


/**
 * 创业
 * 点击清空按钮
 */
function clean_input_chuangye() {
    var epp001 = $("#epp001").val();
    var plf001 = $("#plf001").val();//劳动力id
    $("#chuangyeadd input").val('');
    $("#epp002").val(plf001);
    if(epp001 != null && epp001 !=''){
        updateChuangye(epp001);
    }else{
    }
}


/**
 *删除创业信息
 */
function delChuangye(id){
    swal({
        title: "您确定要删除这条信息吗",
        text: "删除后将无法恢复，请谨慎操作！",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "删除",
        cancelButtonText:"取消",
        closeOnConfirm: false
    }, function () {
        $.ajax({
            url:"/poorwork/deleteChuangYe",    //请求的url地址
            dataType:"text",   //返回格式为json
            async:false,//请求是否异步，默认为异步，这也是ajax重要特性
            data:{"epp001":id},    //参数值
            type:"POST",   //请求方式
            beforeSend:function(){
                //请求前的处理
            },
            success:function(data){
                //请求成功时处理
                if(data != null && data != undefined){
                    if(data == 'yes'){
                        loadChuangYeTable();
                        swal("删除成功！", "您已经永久删除了这条信息。", "success");
                    }else if(data == 'no'){
                        swal("删除失败！", "您未删除这条信息。", "fail");
                    }
                }
            },
            complete:function(){

            },
            error:function(){
                //请求出错处理
            }
        });

    });
}

//****************************************以下是培训信息**************************************************************


/**
 * 加载培训表格
 * 培训表格
 */
function loadPeiXunTable(){
    //重要修改 表格内容的自定义，需要根据业务定制
    peixun_datatable = $("#peixun_table").dataTable({
        destroy: true,
        autoWidth: false,  //禁用自动调整列宽
        ordering:false,//禁止排序
        stripeClasses: ["odd", "even"],  //为奇偶行加上样式，兼容不支持CSS伪类的场合
        processing: false,  //隐藏加载提示,自行处理
        serverSide: false,  //启用服务器端分页
        aLengthMenu:[10,20,50,100],  //用户可自选每页展示数量 5条或10条
        searching: false,  //禁用原生搜索
        orderMulti: false,  //启用多列排序
        order: [],  //取消默认排序查询,否则复选框一列会出现小箭头
        renderer: "bootstrap",  //渲染样式：Bootstrap和jquery-ui
        pagingType: "full_numbers",  //分页样式：simple,simple_numbers,full,full_numbers
        paging: false,//禁止分页
        info:false,
        columnDefs: [{
            "targets": 'nosort',  //列的样式名
            "orderable": false    //包含上样式名‘nosort’的禁止排序
        }],
        ajax: function (data, callback, settings) {

            //封装请求参数
            var plf001 = $("#plf001").val();//劳动力id
            $("#tsn010").val(plf001);
            var param = {"tsn010":plf001};
            //根据劳动力id 查询就业信息
            //ajax请求数据方法
            $.ajax({
                type: "POST",
                url: "/poorwork/queryTrainingSituationByTsn010",//url请求的地址
                cache: false,  //禁用缓存
                data: param,  //传入组装的参数
                dataType: "json",
                success: function (result) {
                    //封装返回数据重要
                    var returnData = {};
                    //返回的数据列表
                    returnData.data = result;
                    //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                    //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                    callback(returnData);
                }
            });
        },
        //列表表头字段
        columns: [
            { "data": "tsn003" },
            { "data": "tsn004" },
            { "data": "tsn005" },
            { "data": "tsn006" },
            { "data": "tsn007" },
            { "data": "tsn008" },
            { "data": "tsn002" },
            {"data":"tsn001", "render":function(data, type, full, meta){
                return	data="<button onclick=\"updatePeiXun('"+data+"')\"  class='btn btn-sm btn-primary btn-edit' data-toggle='modal' data-target='#peixunadd' >修 改</button>   <button  class='btn btn-sm btn-danger btn-del' onclick=\"deletePeiXun('"+data+"')\">删 除</button>  ";
            }}]
    }).api();
    //此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象
}


/**
 *添加培训按钮的点击方法，给就业modal 隐藏域中房劳动力id
 */
function peixunadd_onclick() {
    var plf001 = $("#plf001").val();//获取劳动力id
    $("#tsn010").val(plf001);
}


/**
 * 保存培训信息
 */
function savePeiXun(){
    if(checkAll_peixun() == 1){
        $.ajax({
            url:"/poorwork/savePeiXun?peixunaab301="+$("#aab301").val(),    //请求的url地址
            dataType:"text",   //返回格式为json
            async:false,//请求是否异步，默认为异步，这也是ajax重要特性
            data:$('#peixunForm').serialize(),    //参数值
            type:"POST",   //请求方式
            beforeSend:function(){
                //请求前的处理
            },
            success:function(data){
                //请求成功时处理
                if(data != null && data != undefined){
                    var plf001 = $("#plf001").val();//劳动力id
                    $("#peixunadd input").val('');
                    $("#tsn010").val(plf001);

                    if(data == 'update'){
                        //请求完成的处理
                        loadPeiXunTable();
                        swal("修改成功！", "您已经成功修改了这条信息。", "success");
                    }else{  //说明是新增
                        //请求完成的处理
                        loadPeiXunTable();
                        swal("保存成功！", "您已经成功保存了这条信息。", "success");
                    }
                }
            },
            complete:function(){

            },
            error:function(){
                //请求出错处理
            }
        });
    }

}

/**
 * 修改培训信息
 */
function updatePeiXun(id){

    $("#peixun_head").text("修改培训信息");
    $.ajax({
        url:"/poorwork/updatePeiXun",    //请求的url地址
        dataType:"json",   //返回格式为json
        async:false,//请求是否异步，默认为异步，这也是ajax重要特性
        data:{"tsn001":id},    //参数值
        type:"POST",   //请求方式
        beforeSend:function(){
            //请求前的处理
        },
        success:function(data){
            $("#tsn001").val(data.tsn001);
            $("#tsn010").val(data.tsn010);
            $("#tsn005").val(data.tsn005);
            $("#tsn006").val(data.tsn006);
            $("#tsn003").prop("value",data.tsn003);
            $("#tsn004").prop("value",data.tsn004);
            $("#tsn007").val(data.tsn007);
            $("#tsn008").val(data.tsn008);
            $("#tsn011").val(data.tsn011)
            $("#tsn002").val(data.tsn002)
            $("#tsn012").prop("value",data.tsn012);
            $("#tsn013").val(data.tsn013)
            $("#tsn014").val(data.tsn014)
            $("#tsn015").val(data.tsn015)
            $("#tsn016").val(data.tsn016)
            changetsn003(data.tsn003);
            tsn012change(data.tsn012);
            $("#tsn009").val(data.tsn009)
        },
        complete:function(){
            //请求完成的处理
        },
        error:function(){
            //请求出错处理
        }
    });
}

/**
 * 培训
 * 点击清空按钮
 */
function clean_input_peixun() {
    var tsn001 = $("#tsn001").val();
    var plf001 = $("#plf001").val();//劳动力id
    $("#peixunadd input").val('');
    $("#tsn010").val(plf001);
    if(tsn001 != null && tsn001 !=''){
        updatePeiXun(tsn001);
    }else{
    }
}

/**
 *删除培训信息
 */
function deletePeiXun(id){
    swal({
        title: "您确定要删除这条信息吗",
        text: "删除后将无法恢复，请谨慎操作！",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "删除",
        cancelButtonText:"取消",
        closeOnConfirm: false
    }, function () {
        $.ajax({
            url:"/poorwork/deletePeiXun",    //请求的url地址
            dataType:"text",   //返回格式为json
            async:false,//请求是否异步，默认为异步，这也是ajax重要特性
            data:{"tsn001":id},    //参数值
            type:"POST",   //请求方式
            beforeSend:function(){
                //请求前的处理
            },
            success:function(data){
                //请求成功时处理
                if(data != null && data != undefined){
                    if(data == 'yes'){
                        loadPeiXunTable();
                        swal("删除成功！", "您已经永久删除了这条信息。", "success");
                    }else if(data == 'no'){
                        swal("删除失败！", "您未删除这条信息。", "fail");
                    }
                }
            },
            complete:function(){

            },
            error:function(){
                //请求出错处理
            }
        });

    });
}

//***************************************以下是技工院校信息***********************************************************

/**
 * 加载技工院校息表格
 * 技工表格
 */
function loadSchoolTable(){
    //重要修改 表格内容的自定义，需要根据业务定制
    school_datatable = $("#school_table").dataTable({
        destroy: true,
        autoWidth: false,  //禁用自动调整列宽
        ordering:false,//禁止排序
        stripeClasses: ["odd", "even"],  //为奇偶行加上样式，兼容不支持CSS伪类的场合
        processing: false,  //隐藏加载提示,自行处理
        serverSide: false,  //启用服务器端分页
        aLengthMenu:[10,20,50,100],  //用户可自选每页展示数量 5条或10条
        searching: false,  //禁用原生搜索
        orderMulti: false,  //启用多列排序
        order: [],  //取消默认排序查询,否则复选框一列会出现小箭头
        renderer: "bootstrap",  //渲染样式：Bootstrap和jquery-ui
        pagingType: "full_numbers",  //分页样式：simple,simple_numbers,full,full_numbers
        paging: false,//禁止分页
        info:false,
        columnDefs: [{
            "targets": 'nosort',  //列的样式名
            "orderable": false    //包含上样式名‘nosort’的禁止排序
        }],
        ajax: function (data, callback, settings) {

            //封装请求参数
            var plf001 = $("#plf001").val();//劳动力id
            $("#ths002").val(plf001);
            var param = {"ths002":plf001};
            //根据劳动力id 查询就业信息
            //ajax请求数据方法
            $.ajax({
                type: "POST",
                url: "/poorwork/queryTechnicalSchoolsByThs002",//url请求的地址
                cache: false,  //禁用缓存
                data: param,  //传入组装的参数
                dataType: "json",
                success: function (result) {
                    //封装返回数据重要
                    var returnData = {};
                    //返回的数据列表
                    returnData.data = result;
                    //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                    //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                    callback(returnData);
                }
            });
        },
        //列表表头字段
        columns: [
            { "data": "ths003" },
            { "data": "ths004" },
            { "data": "ths007" },
            { "data": "ths005" },
            { "data": "ths006" },
            { "data": "ths008" },
            {"data":"ths001", "render":function(data, type, full, meta){
                return	data="<button onclick=\"updateSchool('"+data+"')\"  class='btn btn-sm btn-primary btn-edit' data-toggle='modal' data-target='#schooladd' >修 改</button>   <button  class='btn btn-sm btn-danger btn-del' onclick=\"deleteSchool('"+data+"')\">删 除</button>  ";
            }}]
    }).api();
    //此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象
}


/**
 *添加技工院校按钮的点击方法，给就业modal 隐藏域中房劳动力id
 */
function schooladd_onclick() {
    var plf001 = $("#plf001").val();//获取劳动力id
    $("#ths002").val(plf001);
}

/**
 * 保存技工院校信息
 */
function saveSchool(){
    if(checkAll_school() == 1){
        $.ajax({
            url:"/poorwork/saveSchool?schoolaab301="+$("#aab301").val(),    //请求的url地址
            dataType:"text",   //返回格式为json
            async:false,//请求是否异步，默认为异步，这也是ajax重要特性
            data:$('#schoolForm').serialize(),    //参数值
            type:"POST",   //请求方式
            beforeSend:function(){
                //请求前的处理
            },
            success:function(data){
                var plf001 = $("#plf001").val();//劳动力id
                $("#schooladd input").val('');
                $("#ths002").val(plf001);
                //请求成功时处理
                if(data != null && data != undefined){
                    if(data == 'update'){
                        //请求完成的处理
                        loadSchoolTable();
                        swal("修改成功！", "您已经成功修改了这条信息。", "success");
                    }else{  //说明是新增
                        //请求完成的处理
                        loadSchoolTable();
                        swal("保存成功！", "您已经成功保存了这条信息。", "success");
                    }
                }
            },
            complete:function(){

            },
            error:function(){
                //请求出错处理
            }
        });
    }
}

/**
 * 修改技工院校信息
 */
function updateSchool(id){

    $("#school_head").text("修改技工院校信息");
    $.ajax({
        url:"/poorwork/updateSchool",    //请求的url地址
        dataType:"json",   //返回格式为json
        async:false,//请求是否异步，默认为异步，这也是ajax重要特性
        data:{"ths001":id},    //参数值
        type:"POST",   //请求方式
        beforeSend:function(){
            //请求前的处理
        },
        success:function(data){
            $("#ths001").val(data.ths001);
            $("#ths002").val(data.ths002);
            $("#ths003").val(data.ths003);
            $("#ths004").val(data.ths004);
            $("#ths005").val(data.ths005);
            $("#ths006").val(data.ths006);
            $("#ths007").val(data.ths007);
            $("#ths008").val(data.ths008);
            $("#ths009").prop("value",data.ths009);
            $("#ths010").prop("value",data.ths010);
            $("#ths011").val(data.ths011);
            $("#ths013").val(data.ths013);
            $("#ths014").val(data.ths014);
            $("#ths015").val(data.ths015);
            $("#ths016").val(data.ths016);
        },
        complete:function(){
            //请求完成的处理
        },
        error:function(){
            //请求出错处理
        }
    });
}

/**
 * 技工院校
 * 点击清空按钮
 */
function clean_input_school() {
    var ths001 = $("#ths001").val();
    var plf001 = $("#plf001").val();//劳动力id
    $("#schooladd input").val('');
    $("#ths002").val(plf001);
    $("#ths009").prop("value","");
    $("#ths010").prop("value","");
    if(ths001 != null && ths001 !=''){
        updateSchool(ths001);
    }else{

    }
}


/**
 *删除技工院校信息
 */
function deleteSchool(id){
    swal({
        title: "您确定要删除这条信息吗",
        text: "删除后将无法恢复，请谨慎操作！",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "删除",
        cancelButtonText:"取消",
        closeOnConfirm: false
    }, function () {
        $.ajax({
            url:"/poorwork/deleteSchool",    //请求的url地址
            dataType:"text",   //返回格式为json
            async:false,//请求是否异步，默认为异步，这也是ajax重要特性
            data:{"ths001":id},    //参数值
            type:"POST",   //请求方式
            beforeSend:function(){
                //请求前的处理
            },
            success:function(data){
                //请求成功时处理
                if(data != null && data != undefined){
                    if(data == 'yes'){
                        loadSchoolTable();
                        swal("删除成功！", "您已经永久删除了这条信息。", "success");
                    }else if(data == 'no'){
                        swal("删除失败！", "您未删除这条信息。", "fail");
                    }
                }
            },
            complete:function(){

            },
            error:function(){
                //请求出错处理
            }
        });

    });
}



/**
 * 查询参数，获取对应的下拉值
 */
function queryParam(aaa100,id){
    $("#"+id).empty();
    $.ajax({
        url:"/aa10/queryAa10",    //请求的url地址
        dataType:"json",   //返回格式为json
        async:true,//请求是否异步，默认为异步，这也是ajax重要特性
        data:{"aaa100":aaa100},    //参数值
        type:"POST",   //请求方式
        beforeSend:function(){
            //请求前的处理
        },
        success:function(data){
            //请求成功时处理
            if(data != null && data != undefined){
                var options ="";
                options +="<option value=''>-- 请选择 --</option>";
                for(var i=0;i<data.length;i++){
                    options +="<option value='"+data[i].aaa102+"'>"+data[i].aaa103+"</option>";
                }
                $("#"+id).append(options);
            }
        },
        complete:function(){
            //请求完成的处理
        },
        error:function(){
            //请求出错处理
        }
    });
}




//贫困户   保存前校验必填项是否都填写
function checkAll_poor() {
    var phd002 = $("#phd002").val();
    var phd003 = $("#phd003").val();
    var phd006 = $("#phd006").val();
    var phd013 = $("#phd013").val();
    var phd010 = $("#phd010").val();//集中安置点名称
    var phd007 = $("#phd007").val();
    var flag = 0;
    if(phd002 =="" || phd002 == null){
        swal({
            title: "请输入户主姓名!",
            text: "您好！户主姓名不能为空",
            timer: 2000,
            showConfirmButton: false
        });
        return flag;
    }
    if(phd003 =="" || phd003 == null){
        swal({
            title: "请输入身份证号!",
            text: "您好！身份证号不能为空",
            timer: 2000,
            showConfirmButton: false
        });
        return flag;
    }
    if(phd006 =="" || phd006 == null){
        swal({
            title: "请输入户主电话!",
            text: "您好！户主电话不能为空",
            timer: 2000,
            showConfirmButton: false
        });
        return flag;
    }
    if(phd013 =="" || phd013 == null){
        swal({
            title: "请输入易地扶贫搬迁!",
            text: "您好！易地扶贫搬迁不能为空",
            timer: 2000,
            showConfirmButton: false
        });
        return flag;
    }
    if(phd013 =="1"){   //说明选中的是   易地搬迁
        if(phd010 =="" || phd010 == null){
            swal({
                title: "请输入安置点名称!",
                text: "您好！安置点名称不能为空",
                timer: 2000,
                showConfirmButton: false
            });
            return flag;
        }
    }
    if(phd007 =="" || phd007 == null){
        swal({
            title: "请输入详细住址!",
            text: "您好！详细住址不能为空",
            timer: 2000,
            showConfirmButton: false
        });
        return flag;
    }
    flag = 1;
    return flag;
}


//劳动力   保存前校验必填项是否都填写
function checkAll_worker() {
    var plf004 = $("#plf004").val();
    var plf005 = $("#plf005").val();
    var plf006 = $("#plf006").val();
    var plf009 = $("#plf009").val();
    var plf011 = $("#plf011").val();
    var plf017 = $("#plf017").val();
    var flag = 0;
    if(plf004 =="" || plf004 == null){
        swal({
            title: "请输入劳动力姓名!",
            text: "您好！劳动力姓名不能为空",
            timer: 2000,
            showConfirmButton: false
        });
        return flag;
    }
    if(plf005 =="" || plf005 == null){
        swal({
            title: "请输入身份证号!",
            text: "您好！身份证号不能为空",
            timer: 2000,
            showConfirmButton: false
        });
        return flag;
    }
    if(plf006 =="" || plf006 == null){
        swal({
            title: "请输入劳动力电话!",
            text: "您好！劳动力电话不能为空",
            timer: 2000,
            showConfirmButton: false
        });
        return flag;
    }
    if(plf009 =="" || plf009 == null){
        swal({
            title: "请输入文化程度!",
            text: "您好！文化程度不能为空",
            timer: 2000,
            showConfirmButton: false
        });
        return flag;
    }
    if(plf011 =="" || plf011 == null){
        swal({
            title: "请输入就业创业状态!",
            text: "您好！就业创业状态不能为空",
            timer: 2000,
            showConfirmButton: false
        });
        return flag;
    }
    if(plf010 =="" || plf010 == null){
        swal({
            title: "请输入就业创业意愿!",
            text: "您好！就业创业意愿不能为空",
            timer: 2000,
            showConfirmButton: false
        });
        return flag;
    }

    flag = 1;
    return flag;
}


//就业信息   保存前校验必填项是否都填写
function checkAll_jiuye() {
    var eys003 = $("#eys003").val();
    var eys012 = $("#eys012").val();
    var eys004 = $("#eys004").val();
    var eys009 = $("#eys009").val();
    var eys008 = $("#eys008").val();
    var eys015 = $("#eys015").val();

    var flag = 0;

    if(eys003 =="" || eys003 == null){
        swal({
            title: "请输入就业渠道!",
            text: "您好！就业渠道不能为空",
            timer: 2000,
            showConfirmButton: false
        });
        return flag;
    }
    if(eys012 =="" || eys012 == null){
        swal({
            title: "请输入就业地域!",
            text: "您好！就业地域不能为空",
            timer: 2000,
            showConfirmButton: false
        });
        return flag;
    }
    if(eys004 =="" || eys004 == null){
        swal({
            title: "请输入就业类型!",
            text: "您好！就业类型不能为空",
            timer: 2000,
            showConfirmButton: false
        });
        return flag;
    }else{
        if(eys004 == '3'){//如果是农业就业，则上年度农业人均年纯收入  不能为空
            /*if(eys015 != '' && eys015 != null){
                if(eys015 < 3100){
                    swal("您好！上年度农业人均年纯收入必须大于3100!");
                    return flag;
                }
            }else{
                swal("您好！农业就业，必须输入农业人均年纯收入并且金额必须大于3100!");
                return flag;
            }*/

        }
    }
    if(eys009 =="" || eys009 == null){
        swal({
            title: "请输入就业起始日期!",
            text: "您好！就业起始日期不能为空",
            timer: 2000,
            showConfirmButton: false
        });
        return flag;
    }
    if(eys008 =="" || eys008 == null){
        swal({
            title: "请输入就业地址!",
            text: "您好！就业地址不能为空",
            timer: 2000,
            showConfirmButton: false
        });
        return flag;
    }

    flag = 1;
    return flag;
}


//创业信息   保存前校验必填项是否都填写
function checkAll_chuangye() {
    var epp003 = $("#epp003").val();
    var epp005 = $("#epp005").val();
    var epp006 = $("#epp006").val();
    var flag = 0;
    if(epp003 =="" || epp003 == null){
        swal({
            title: "请输入创业项目!",
            text: "您好！创业项目不能为空",
            timer: 2000,
            showConfirmButton: false
        });
        return flag;
    }
    if(epp005 =="" || epp005 == null){
        swal({
            title: "请输入创业地址!",
            text: "您好！创业地址不能为空",
            timer: 2000,
            showConfirmButton: false
        });
        return flag;
    }
    if(epp006 =="" || epp006 == null){
        swal({
            title: "请输入创业起始日期!",
            text: "您好！创业起始日期不能为空",
            timer: 2000,
            showConfirmButton: false
        });
        return flag;
    }

    flag = 1;
    return flag;
}


//培训信息   保存前校验必填项是否都填写
function checkAll_peixun() {
    var tsn005 = $("#tsn005").val();
    var tsn006 = $("#tsn006").val();
    var tsn003 = $("#tsn003").val();
    var tsn007 = $("#tsn007").val();
    var flag = 0;
    if(tsn005 =="" || tsn005 == null){
        swal({
            title: "请输入培训单位!",
            text: "您好！培训单位不能为空",
            timer: 2000,
            showConfirmButton: false
        });
        return flag;
    }
    if(tsn006 =="" || tsn006 == null){
        swal({
            title: "请输入培训地址!",
            text: "您好！培训地址不能为空",
            timer: 2000,
            showConfirmButton: false
        });
        return flag;
    }
    if(tsn003 =="" || tsn003 == null){
        swal({
            title: "请输入培训类型!",
            text: "您好！培训类型不能为空",
            timer: 2000,
            showConfirmButton: false
        });
        return flag;
    }
    if(tsn007 =="" || tsn007 == null){
        swal({
            title: "请输入培训起始日期!",
            text: "您好！培训起始日期不能为空",
            timer: 2000,
            showConfirmButton: false
        });
        return flag;
    }

    flag = 1;
    return flag;
}


//技工院校   保存前校验必填项是否都填写
function checkAll_school() {
    var ths003 = $("#ths003").val();
    var ths004 = $("#ths004").val();
    var ths005 = $("#ths005").val();
    var ths009 = $("#ths009").val();
    var ths010 = $("#ths010").val();
    var ths008 = $("#ths008").val();//毕业后就业年月
    var flag = 0;
    if(ths003 =="" || ths003 == null){
        swal({
            title: "请输入技工院校名称!",
            text: "您好！技工院校名称不能为空",
            timer: 2000,
            showConfirmButton: false
        });
        return flag;
    }
    if(ths004 =="" || ths004 == null){
        swal({
            title: "请输入技工院校地址!",
            text: "您好！技工院校地址不能为空",
            timer: 2000,
            showConfirmButton: false
        });
        return flag;
    }
    if(ths005 =="" || ths005 == null){
        swal({
            title: "请输入入学日期!",
            text: "您好！入学日期不能为空",
            timer: 2000,
            showConfirmButton: false
        });
        return flag;
    }
    /*if(ths009 =="" || ths009 == null){
        swal({
            title: "请输入院校区域!",
            text: "您好！院校区域不能为空",
            timer: 2000,
            showConfirmButton: false
        });
        return flag;
    }*/
    if(ths010 =="" || ths010 == null){
        swal({
            title: "请输入是否苏陕协作!",
            text: "您好！是否苏陕协作不能为空",
            timer: 2000,
            showConfirmButton: false
        });
        return flag;
    }

    flag = 1;
    return flag;


    //手机号码校验
    function huzhuphone(tel,inputid){
        var re = /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/;
        if(!re.test(tel)){
            swal({
                title: "手机号码错误!",
                text: "您好！请输入正确的手机号码",
                timer: 2000,
                showConfirmButton: false
            });
            $("#"+inputid).focus();
        }
    }


    //身份证号码校验
    function idcardval(card,inputid){
        var pattern = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
        if(!pattern.test(card)){
            swal({
                title: "身份证错误!",
                text: "您好！请输入正确的身份证号码",
                timer: 2000,
                showConfirmButton: false
            });
            $("#"+inputid).val('');
            $("#"+inputid).focus();
        }
    }



/**
 * 根据身份证号码查询  性别年龄
 * 身份证号   性别   年龄
 */
function countPhd003(phd003){

    var pattern = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
    if(!pattern.test(phd003)){
        $("#phd003").val('');
        $("#phd003").focus;
        swal({
            title: "身份证错误!",
            text: "您好！请输入正确的身份证号码",
            timer: 2000,
            showConfirmButton: false
        });
    }else{
        if(phd003 != null && phd003 != ''){
                $.ajax({
                    url:"/poorwork/queryPoorByIdCard",    //请求的url地址
                    dataType:"text",   //返回格式为json
                    async:false,//请求是否异步，默认为异步，这也是ajax重要特性
                    data:{"phd003":phd003},    //参数值
                    type:"POST",   //请求方式
                    beforeSend:function(){
                        //请求前的处理
                    },
                    success:function(data){
                        //请求成功时处理
                        if(data != null && data != undefined){
                            if(data == 'yes'){
                                $("#phd003").val('');
                                $("#phd002").val('');
                                $("#phd003").focus();
                                //说明该贫困户已经登记，不能重复登记
                                swal({
                                    title: "该贫困户已经登记!",
                                    text: "您好！该贫困户信息已经登记，不能重复登记",
                                    timer: 2000,
                                    showConfirmButton: false
                                });
                            }else if(data=='no'){
                                //获取出生日期
                                //var birthday = phd003.substring(6, 10) + "-" + phd003.substring(10, 12) + "-" + phd003.substring(12, 14);
                                //获取性别
                                if (parseInt(phd003.substr(16, 1)) % 2 == 1) {
                                    $("#phd004").prop("value",1);
                                } else {
                                    $("#phd004").prop("value",2);//女
                                }
                                //获取年龄
                                var myDate = new Date();
                                var month = myDate.getMonth() + 1;
                                var day = myDate.getDate();

                                var age = myDate.getFullYear() - phd003.substring(6, 10) - 1;
                                if (phd003.substring(10, 12) < month || phd003.substring(10, 12) == month && phd003.substring(12, 14) <= day) {
                                    age++;
                                }
                                $("#phd005").val(age);
                            }
                        }
                        return flag;
                    },
                    complete:function(){
                        //请求完成的处理
                    },
                    error:function(){
                        //请求出错处理
                    }
                });
        }
    }
}


function countPlf005(plf005){
    var pattern = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
    if(!pattern.test(plf005)){
        $("#plf005").val('');
        $("#plf005").focus;
        swal({
            title: "身份证错误!",
            text: "您好！请输入正确的身份证号码",
            timer: 2000,
            showConfirmButton: false
        });
    }else{
        if(plf005 != null && plf005 != ''){

            $.ajax({
                url:"/poorwork/queryWorkerByIdCard",    //请求的url地址
                dataType:"text",   //返回格式为json
                async:true,//请求是否异步，默认为异步，这也是ajax重要特性
                data:{"plf005":plf005},    //参数值
                type:"POST",   //请求方式
                beforeSend:function(){
                    //请求前的处理
                },
                success:function(data){
                    //请求成功时处理
                    if(data != null && data != undefined){
                        if(data == 'yes'){
                            $("#plf005").val('');
                            $("#plf004").val('');
                            $("#plf005").focus();
                            //说明该贫困户已经登记，不能重复登记
                            swal({
                                title: "该劳动力已经登记!",
                                text: "您好！该劳动力信息已经登记，不能重复登记",
                                timer: 2000,
                                showConfirmButton: false
                            });
                        }else{
                            //获取出生日期
                            //var birthday = phd003.substring(6, 10) + "-" + phd003.substring(10, 12) + "-" + phd003.substring(12, 14);
                            //获取性别
                            if (parseInt(plf005.substr(16, 1)) % 2 == 1) {
                                $("#plf007").prop("value",1);
                            } else {
                                $("#plf007").prop("value",2);//女
                            }
                            //获取年龄
                            var myDate = new Date();
                            var month = myDate.getMonth() + 1;
                            var day = myDate.getDate();

                            var age = myDate.getFullYear() - plf005.substring(6, 10) - 1;
                            if (plf005.substring(10, 12) < month || plf005.substring(10, 12) == month && plf005.substring(12, 14) <= day) {
                                age++;
                            }
                            $("#plf008").val(age);
                        }
                    }
                },
                complete:function(){
                    //请求完成的处理
                },
                error:function(){
                    //请求出错处理
                }
            });



        }
    }

}


/**
 * 根据身份证号码查询该劳动力是否已经登记
 * 劳动力
 */
function queryWorkerByIdCard(plf005) {
    var flag = 1;

}


/**
 * 选择就业创业状态
 */
function jiuye_status() {
    var plf011 = $("#plf011").val(); //就业创业状态
    var plf010 = $("#plf010").val();//就业创业意愿
    if(plf011!='1'){
        $("#plf010").removeAttrs("disabled");
        $("#plf010").val("");

    }else{
        $("#plf010").attr("disabled",true);

    }
    if(plf011 =='1' && plf010 == '1'){
        $("#plf017_span").show();//就业意向显示出来
    }else{
        $("#plf017_span").hide();//就业意向隐藏
    }
}


/**
 * 培训教育
 */
function educate() {
    var plf024 = $("#plf024").val(); //就业创业状态

    if(plf024=='6'){
        $("#plf012").removeAttrs("disabled");
        $("#plf012").val("");
    }else{
        $("#plf012").attr("disabled",true);
    }
}


/**
 * 就业创业意愿
 */
function chuangye_status(){
    var plf011 = $("#plf011").val(); //就业创业状态
    var plf010 = $("#plf010").val();//就业创业意愿
    if(plf011 =='1' && plf010 == '1'){
        $("#plf017_span").show();//就业意向显示出来
    }else{
        $("#plf017_span").hide();//就业意向隐藏
    }
}

/**
 * 根据就业类型判断，如果是农业就业则  上年度农业人均年纯收入必须大于3100
 */
function geteys015(){
    var eys004 = $("#eys004").val();//获取就业类型
    var eys015 = $("#eys015").val();
    if(eys004 != "" && eys004 != null && eys015 !='' && eys015 !=null){
        /*if(eys004 == '3' && eys015 < 3100){  //说明是农业就业
            swal("您好！上年度农业人均年纯收入必须大于3100!");
            $("#eys015").val('');
            $("#eys015").focus();
        }*/
    }
}

//就业类型
function geteys004(){
    var eys004 = $("#eys004").val();//获取就业类型
    var eys015 = $("#eys015").val();
    if(eys004 != "" && eys004 != null && eys015 !='' && eys015 !=null){
        /*if(eys004 == '3' && eys015 < 3100){  //说明是农业就业
            swal("您好！上年度农业人均年纯收入必须大于3100!");
            $("#eys015").val('');
            $("#eys015").focus();
        }*/
    }

}


/**
 * 特色就业选择执行的方法
 * 选公益专岗、城镇公岗、特设公岗后，其后均应出现岗位补贴（）元、工资发放周期（）个月
 */
function eys011change(value){
    if(value == '5' || value == '6' || value == '7'){
        $("#jiuye_type").show();
    }else{
        $("#jiuye_type").hide();
        $("#eys017").val('');
        $("#eys018").val('');
    }
}

/**
 * 培训类型的选择   培训添加
 */
function changetsn003(value) {
    if(value == '1'){   //说明是就业
        $("#tsn002_span").show();
        $("#tsn002_label").text('培训后就业年月');
    }else if(value == '4'){ //说明是创业培训
        $("#tsn002_span").show();
        $("#tsn002_label").text('培训后成功创业年月');
    }else{
        $("#tsn002_span").hide();
    }
}

/**
 * 培训弹窗的  苏陕协作下拉
 */
function tsn012change(value) {
    if(value == '1'){   //说明是
        $("#tsn013_span").show();
        $("#tsn015_span").show();
    }else if(value == '2'){ //说明否
        $("#tsn013_span").hide();
        $("#tsn015_span").hide();
        $("#tsn013").val("");
        $("#tsn014").val("");
        $("#tsn015").val("");
    }else{
        $("#tsn013_span").hide();
        $("#tsn015_span").hide();
        $("#tsn013").val("");
        $("#tsn014").val("");
        $("#tsn015").val("");
    }
}

/**
 *  培训模块
 * 如果苏陕协作选择是  则只允许填一个日期
 */
function changetsn013(value,id1,id2) {

    if(value != null && value !=''){
        $("#"+id1).val('');
        $("#"+id2).val('');
    }
    
}