var urlWithParam = "/sjk-market-admin";

$(document).ready(function() {
	loadType(1); 
	$("#keyCatalog").bind("change",function(e){
	    var catalog= $(this).find("option:selected").val();
	    loadType(catalog); 
	});
    $('#keyword').bind('keydown', function(e) {
        if(e.which == 13) {
            SearchEve();
        }
    });

    $('#search').click(function() {
        SearchEve();
    });
    
    setTimeout(function(){ 
        loadGridTable();
        var p = $('#dataList').datagrid('getPager');
        $(p).pagination({
            onBeforeRefresh : function() {
            }
        });
        
    },500)
    

});
// 加载DataGrid控件
function loadGridTable() {
	
    $('#dataList').datagrid({
        loadMsg : '正在加载...',
        iconCls : 'icon-save',
        width : '800',
        nowrap : false,
        striped : true,
        collapsible : false,
        url : urlWithParam + "/admin/catalog/search.json",
        pageSize : 20,
        sortOrder : 'desc',
        idField : "id",
        onLoadError : function() {
            var params = arguments;
            if(params[0].status == 403) {
                alert("请登录.");
                window.parent.location.href = "../login.html";
            }
        },
        columns : [[{
            field : 'ck',
            checkbox : true,
            width : 50
        }, {
            field : 'id',
            title : '编号',
            sortable : true,
            width : 50
        }, {
            field : 'name',
            title : '类别名称',
            sortable : true,
            width : 120 
        }, {
            field : 'pid',
            title : '上级类别',
            sortable : true,
            width : 60,
            formatter:function(value,rec,index){
            	if(value==1 || value==17) {
            		return "软件";
            	}else if(value==2 || value==34){
            		return "游戏";
            	}else{ 
                	return "大型游戏";
            	}
            }
        }, {
            field : 'rank',
            title : '排序',
            sortable : true,
            width : 120 
        }, {
            field : 'keywords',
            title : '关键字',
            sortable : true,
            width : 120 
        }, {
            field : 'description',
            title : '描述',
            sortable : true,
            width : 150, 
        }  , {
            field : 'operate',
            title : '操作',
            width : 120,
            formatter : function(value, rec, index) {  
            	 var del = '<a class="mr_15 co_bl f_l" onclick="Delete(' + rec.id + ',' + index + ')">删除</a>';
                 var edit = '<a class="mr_10 co_bl f_l" onclick="Edit(' + rec.id + ',' + rec.pid + ',\'' + rec.name + '\',' + rec.rank + ',\'' + rec.keywords + '\',\'' + rec.description + '\');">修改</a>';
                 return edit + del;
            }
        }]],
        pagination : true,
        rownumbers : false,
		onLoadError : function() {
            var params = arguments;
            if(params[0].status == 403) {
                alert("请登录.");
                window.parent.location.href = "../login.html";
            }
        },
		onLoadSuccess : function() {
			setBtnDisabled("search",false);
			}
    });
}
function setBtnDisabled(btnId, disabled) {
    if(disabled) {
        $("#" + btnId).attr("style", "color:#999").attr("disabled", "disabled");
    } else {
        $("#" + btnId).removeAttr("style").removeAttr("disabled");
    }
}
// 搜索
function SearchEve() {
	setBtnDisabled("search", true);
    var keywords = $.trim($('#keyword').val());var catalog = $('#keyCatalog option:selected').val();
    var subCatalog = $('#keySubCatalog option:selected').val();
    var queryParams = $('#dataList').datagrid('options').queryParams;

    if(keywords !== "") {
        queryParams.keywords = keywords;
    }else{
        queryParams.keywords = '';
    }
    queryParams.marketName ="";

    if(catalog == null||catalog=="") {
        queryParams.catalog = 0;
    } else {
        queryParams.catalog = catalog;
    }
    if(subCatalog == null||subCatalog=="") {
        queryParams.subCatalog =0;
    } else {
        queryParams.subCatalog = subCatalog;
    }

    queryParams.page = 1;

    // 主显示使用
    $('#dataList').datagrid('options').pageNumber = 1;
    // page对象 中的pageNumber对象
    var pager = $('#dataList').datagrid('getPager');
    $(pager).pagination('options').pageNumber = 1;
    $('#dataList').datagrid('reload');
}

//加载分类
function loadType(catalog) { 
	var cacheName ="cacheCatalog"+catalog;
	var dataCache = $("#dataList").data(cacheName);
	if(dataCache==null || dataCache== undefined || dataCache=="") {
		$.ajax({
	        type : "GET",
	        url : urlWithParam + "/admin/catalog/list.json?pid="+catalog,
	        dataType : 'json', 
	        success : function(data) {
	            if(data!=null && data.rows&&  data.rows.length>0){
	            	dataCache = data.rows;
	            	$("#dataList").data(cacheName,data.rows); 
	        		loadSubCatalog(dataCache)
	            }
	        }
	    });
	}else{ 
		loadSubCatalog(dataCache)
	}
} 


function loadSubCatalog(data){
	$("#keySubCatalog").empty();
	$("#keySubCatalog").append("<option value=\"0\">全部</option>");
	$.each(data,function(i,rs){ 
		$("#keySubCatalog").append("<option value=\""+rs.id+"\">"+rs.name+"</option>");
	});
}

function loadMarkets(){
	var $markets = $("#markets");
	$.ajax({
        type : "GET",
        url : urlWithParam + "/admin/catalogconvertor/market.list.json",
        dataType : 'json', 
        success : function(data) { 
            if(data!=null){
            	var data= data.data;
            	$markets.html("");
            	var html="";
            	$.each(data, function(i,row){   
            			var url =urlWithParam+ '/admin/catalogconvertor/'+row+'.convertor.d';
            			html +='<span  class="ml_10 mr_10"><label for="marketId1'+i+'">'+row+'<a   class="ml_5 mr_5"  href="'+url+'" >批量处理</a></label></span>'
      		      		//html +='<span  class="ml_10 mr_10"><input type="checkbox" name="markNames" id="marketId'+i+'"  value="'+row+'" /><label for="marketId1'+i+'">'+row+'<a   href="'+url+'" >批量处理</a></label></span>'
            	}); 
            	$markets.html(html); 
            }
        }
    });
}


//添加
function Add(){ 
	Edit(0,0,"",0,"","");
}

//添加
function Edit(){ 
	var id = arguments[0];
	var pId  = arguments[1];
	var name  = arguments[2];
	var rank = arguments[3];
	var keywords = arguments[4];
	var description = arguments[5];
	
	 $editPanl = $("#editPanl");
	 var title="添加专题应用";
	 if(id>0){
		 var title="修改专题应用";
	 }
	 $editPanl.window({
	        title : title,
	        top:200,
	        width : 600,
	        modal : true,
	        shadow : true,
	        closed : false,
	        height :350
	 });
	 $editPanl.show();
	 initHtml(id,pId,name,rank,keywords,description);
}
function initHtml(){ 
	var id = arguments[0];
	var pId  = arguments[1];
	var name  = arguments[2];
	var rank = arguments[3];
	var keywords = arguments[4];
	var description = arguments[5];
	
	var $id =$("#id"),  
	$name =$("#txtName"),
	$keywords =$("#txtKeywords"),
	$rank =$("#txtRank"),
	$description =$("#txtDescription"),
	$pId =$("#sltCatalog"),
	$editPanl = $("#editPanl");
	
	$("#sltCatalog option").removeAttr("selected");
	$("#sltCatalog option[value='"+(pId==0?1:pId)+"']").attr("selected","selected");
//	$("#sltCatalog option").each(function(){
//		var option = $(this); 
//		if(parseInt(option.val()) == pId){
//			option.attr("selected","selected");
//		}else{
//			option.removeAttr("selected");
//		}
//	}); 
	
	$id.val(id); 
	$rank.val(rank); 
	$name.val(name);
	$keywords.val(keywords);
	$description.val(((description==null?"":description)=='null'?"":description));
 
	$tagsPost =$("#tagsPost"); 
	/*POST请求*/
	$tagsPost.click(function(){   
		pId = $("#sltCatalog option:selected").val();
		name= $name.val(); 
		if(pId==null||pId=="" || pId=="0" || pId == 0 ){
			$("#errorCatalog").html("请在上级类别名称。");
			return false;
		}else{
			$("#errorCatalog").empty();
		} 
		if(name=="" || name==null){
			$("#errorName").html("请输入类别名称。");
			return false;
		}else{
			$("#errorName").empty();
		}
		var postUrl ="";
		var msg ="修改成功";
		if(id>0){
			postUrl=  urlWithParam + "/admin/catalog/edit.json"
		}else{
			postUrl=  urlWithParam + "/admin/catalog/add.json"
			msg ="添加成功";
		}
		$.ajax({
          type : "POST",
          url : postUrl,
          traditional : true,
          data : {
              "id" : $id.val(),
              "name":name,
              "rank":$rank.val(),
              "description":$description.val(),
              "pid":pId,
              "keywords":$keywords.val()
          },
          dataType : 'json',
          success : function(data) {
              if(data.result.msg == "OK!") {
                  $.messager.show({
                      msg : msg,
                      title : '提示',
                      showType : 'slide',
                      timeout : 3000
                  });
                  $('#dataList').datagrid('unselectAll');
                  $('#dataList').datagrid('reload'); 
                  $fromPost.parent().parent().parent().hide();  
                  $(".window-mask").hide();  
              }
          }
      });
        $(this).parent().parent().parent().parent().parent().parent().parent().parent().hide();
        $editPanl.hide();
        $(".window-mask").hide();
	});
}


//删除
function Delete() {
var id = arguments[0];
$.messager.confirm('确认', '您确认要删除这条记录吗?', function(row) {
   if(row) {
       $.ajax({
           type : "POST",
           url : urlWithParam + "/admin/catalog/del.json",
           traditional : true,
           data : {
               "id" : id
           },
           dataType : 'json',
           success : function(data) {
               if(data.result.msg == "OK!") {
                   $.messager.show({
                       msg : '删除成功',
                       title : '提示',
                       showType : 'slide',
                       timeout : 3000
                   });
                   $('#dataList').datagrid('unselectAll');
                   $('#dataList').datagrid('reload');
               }else{
            	   $.messager.show({
                       msg : data.result.msg,
                       title : '提示',
                       showType : 'slide',
                       timeout : 3000
                   });
               }
           }
       });
   }
});
}

      