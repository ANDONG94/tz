/**
 * Created by len on 2018-03-14.
 */
$(function () {
    queryCompanyInfo();//查询公司信息
    queryHotProduct();//查询热销产品信息
    queryProductImgInAroud(); //查询首页产品中心照片
    queryHomePageBigImg();  //查询首页大屏图片
    queryNews();//查询行业新闻信息
    getip();//获取电脑ip地址
    queryExperter();//查询专家情况
    queryDisplayImg();//查询展会图片

})

/**
 * 查询首页大屏 图片
 */
function queryHomePageBigImg(){
    $.ajax({
        url:"/web/homePageBigImage",    //请求的url地址
        dataType:"json",   //返回格式为json
        async:false,//请求是否异步，默认为异步，这也是ajax重要特性
        data:{"imageType":"SHOUYE"},    //参数值
        type:"POST",   //请求方式
        beforeSend:function(){
            //请求前的处理
        },
        success:function(data){
            //请求成功时处理
            if(data != null && data != undefined){
                var li = "";
                for(var i=0;i<data.length;i++){
                    //拼li  设置值，内容等
                    li += "<li>";
                    li += "<img src='"+data[i].imagepath+"' alt='Tradeaux'>";
                    li += "<div class='caption'>";
                    li += "<h3>Welcome to <span>SuperTOTON</span></h3>";
                    li += "<p>九洲铂泰联系方式：029-8982-0019</p>";
                    li += "</div>";
                    li += "</li>";
                }
                $("#slider").html(li);
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
 * 查询产品图片
 * 滚动屏幕处的产品图片
 */
function queryProductImgInAroud(){
    $.ajax({
        url:"/web/homePageProductImageAround",    //请求的url地址
        dataType:"json",   //返回格式为json
        async:false,//请求是否异步，默认为异步，这也是ajax重要特性
        data:{"imageType":"PRODUCT"},    //参数值
        type:"POST",   //请求方式
        beforeSend:function(){
            //请求前的处理
        },
        success:function(data){
            //请求成功时处理
            if(data != null && data != undefined){

                for(var i=0;i<data.length;i++){
                    //拼div  设置值，内容等
                    var div = "";
                    div += "<div class=‘item'>";
                    div += "<img style='height: 200px;width: 351px' onclick='imgview()' src='"+data[i].imagepath+"' name='"+data[i].imagename+"'>";
                    div += "</div>";
                    $("#owl-demo").append(div);
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


/**
 * 查询产品介绍
 * 我们的热销产品
 */
function queryHotProduct(){
    $.ajax({
        url:"/web/queryHotProduct",    //请求的url地址
        dataType:"json",   //返回格式为json
        async:false,//请求是否异步，默认为异步，这也是ajax重要特性
        data:{},    //参数值
        type:"POST",   //请求方式
        beforeSend:function(){
            //请求前的处理
        },
        success:function(data){
            //请求成功时处理
            if(data != null && data != undefined){
                for(var i=0;i<data.length;i++){
                    //拼div  设置值，内容等
                    var div = "";
                    div += '<div class="col-md-4 cover-top-grid slideanim">';
                    div += '<div class="column-wrap clearfix">'
                    div += '<div class="column">';
                    div += "<a class='caption caption-3' onclick=\"queryCospalyArticle('"+data[i].id+"')\" target='_blank' data-title='"+data[i].imagename+"' data-description='"+data[i].imagedesc+"'><img src='"+data[i].imagepath+"' alt='"+data[i].imagedesc+"'></a>";
                    div += '</div></div></div>';
                    $("#cover_grid_div").append(div);
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


/**
 * 查询行业新闻
 *
 * */
function queryNews(){
    $.ajax({
        url:"/web/queryNews",    //请求的url地址
        dataType:"json",   //返回格式为json
        async:false,//请求是否异步，默认为异步，这也是ajax重要特性
        data:{},    //参数值
        type:"POST",   //请求方式
        beforeSend:function(){
            //请求前的处理
        },
        success:function(data){
            //请求成功时处理
            if(data != null && data != undefined){

                for(var i=0;i<data.length;i++){
                    //拼div  设置值，内容等
                    var div = "";
                        div += "<div class='news-icon' style='margin-left: 10%'>";
                        div += "<img src='/images/n2.png' style='width:16px;height: 16px;'>"
                        div += "</div>";
                        div += "<div class='news-info'  style='padding-left: 16%'>";
                        div += "<a href='/web/scanNews?articleId="+data[i].id+"' style='font-size: 14px'  target='_blank'>"+data[i].title+"</a> <span style='margin-left: 10%;'>"+data[i].createddate+"</span>";
                        div += "</div>";
                        div += "<div class='clearfix'></div>"
                    $("#web_article_div").append(div);
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


/**
 * 查询滚动图片 轮播问题
 */
function  imgview() {
    window.open("/web/lunboproductImg");
}


/**
 * 获取电脑地址ip   省市
 */
function getip(){
    var myip = returnCitySN["cip"];

    $.getScript('http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js&ip='+myip, function(_result) {
        if (remote_ip_info.ret == '1') {
            $.ajax({
                url:"/web/saveIpAddr",    //请求的url地址
                dataType:"json",   //返回格式为json
                async:false,//请求是否异步，默认为异步，这也是ajax重要特性
                data:{"ip":myip,"country":remote_ip_info.country,"city":remote_ip_info.city,"region":remote_ip_info.province},    //参数值
                type:"POST",   //请求方式
                beforeSend:function(){
                    //请求前的处理
                },
                success:function(data){
                    //请求成功时处理

                },
                complete:function(){
                    //请求完成的处理
                },
                error:function(){
                    //请求出错处理
                }
            });
        } else {
            alert('错误', '没有找到匹配的 IP 地址信息！');
        }
    })

}

/**
 * 首页点击联系我们进行跳转的页面
 */
function queryCompanyAddr() {
    window.open("/map/queryCompanyAddrMap");
}


/**
 * 查询我们的专家，以及专家介绍
 *
 * */
function queryExperter(){
    $.ajax({
        url:"/web/queryExperter",    //请求的url地址
        dataType:"json",   //返回格式为json
        async:false,//请求是否异步，默认为异步，这也是ajax重要特性
        data:{},    //参数值
        type:"POST",   //请求方式
        beforeSend:function(){
            //请求前的处理
        },
        success:function(data){
            //请求成功时处理
            if(data != null && data != undefined){
                for(var i=0;i<data.length;i++){
                    //拼div  设置值，内容等
                    var div = "";
                    div += '<div class="col-md-6 col-sm-6 offer1 slideanim">';
                    div += '<div class="offer-icon">';
                    div += "<img style='width: 100px;height: 100px' src='"+data[i].imagepath+"' alt='"+data[i].realname+"'>";
                    div += '</div>';
                    div += '<div class="offer-info">';
                    div += '<h4>'+data[i].realname+'</h4>';
                    div += '<p>'+data[i].comment+'</p>';
                    div += '</div>';
                    div += '<div class="clearfix"></div>';
                    div += '</div>';
                    $("#expert_div").append(div);
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


/**
 * 查询展会情况 图片
 */
function queryDisplayImg(){
    $.ajax({
        url:"/web/queryDisplayImg",    //请求的url地址
        dataType:"json",   //返回格式为json
        async:false,//请求是否异步，默认为异步，这也是ajax重要特性
        data:{"imageType":"WEBMAIN"},    //参数值
        type:"POST",   //请求方式
        beforeSend:function(){
            //请求前的处理
        },
        success:function(data){
            //请求成功时处理
            if(data != null && data != undefined){
                for(var i=0;i<data.length;i++){
                    var div = "";
                    div += '<div class="col-md-4 col-sm-4 air slideanim">';
                    div += '<div class="area-image">';
                    div += "<img onclick=\"queryCospalyArticle('"+data[i].id+"')\" src='"+data[i].imagepath+"' alt='"+data[i].imagename+"'>";
                    div += "<div>";
                    div += "<div class='area-info'>";
                    div += "<h4>"+data[i].imagename+"</h4>";
                    div += "<p>"+data[i].imagename+"</p>";
                    div += "</div>";
                    div += "</div>";
                    $("#areal_div").append(div);
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


/**
 * 点击展会图片  查看展会文章信息
 */
function queryCospalyArticle(imgId){
   $.ajax({
        url:"/web/queryArticleByImgId",    //请求的url地址  根据点击的图片id查看文章id
        dataType:"text",   //返回格式为json
        async:false,//请求是否异步，默认为异步，这也是ajax重要特性
        data:{"imgId":imgId},    //参数值
        type:"POST",   //请求方式
        beforeSend:function(){
            //请求前的处理
        },
        success:function(data){
            //请求成功时处理
            if(data != null && data != undefined){
                window.open("/web/scanNews?articleId="+data);
            }
        },
        complete:function(){

        },
        error:function(){
            //请求出错处理
        }
    });
}


    /**
     * 查询公司信息
     */
    function queryCompanyInfo(){
        $.ajax({
            url:"/web/queryCompanyInfo",    //请求的url地址
            dataType:"json",   //返回格式为json
            async:false,//请求是否异步，默认为异步，这也是ajax重要特性
            data:"",    //参数值
            type:"POST",   //请求方式
            beforeSend:function(){
                //请求前的处理
            },
            success:function(data){
                //请求成功时处理
                if(data != null && data != undefined){
                    var li = "";
                    li += "<li>"+data.address+"</li>";
                    li += "<li>"+data.compantype+"</li>";
                    li += "<li>"+data.detail+"</li>";
                    li += "<li>联系方式 : "+data.contectperson1+"  "+data.mobilephome1+"</li>";
                    if(data.contectperson2 != null && data.contectperson2 != undefined && data.contectperson2 != ''){
                        li += "<li>联系方式 : "+data.contectperson2+"  "+data.mobilephome2+"</li>";
                    }
                    li += "<li>固定电话 :"+data.phone+"</li>";
                    li += "<li>Email :"+data.email+"</li>";
                    $("#company_ul").append(li);
                    $("#company_tel").text(data.phone);//公司座机电话
                    $("#company_qq").text(data.qq);//公司qq号码

                }
            },
            complete:function(){

            },
            error:function(){
                //请求出错处理
            }
        });
    }




