var urlWithParam = "/sjk-market-admin";
$(document).ready(function() { 
	loadType(1);
	loadType(2);
	loadMarkets();
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
        
    },500);
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
        url : urlWithParam + "/admin/catalogconvertor/search.json",
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
            title : 'id',
            sortable : true,
            width : 50
        }, {
            field : 'marketName',
            title : '商场名称',
            sortable : true,
            width : 120,
            formatter : function(value, rec, index) {
            	var url =urlWithParam+ '/admin/app/'+ rec.id+'.merge.d';
                var html =  '<a name="softIds" class="co_bl f_l" value="' + rec.id + '" href="' +url+ '"  target="_blank">' + value + '</a>';
                return html;
            }
        }, {
            field : 'catalog',
            title : '一级类别',
            sortable : true,
            width : 60,
            formatter:function(value,rec,index){
            	return GetCatalogNames(value);
            }
        }, {
            field : 'subCatalog',
            title : '二级类别',
            sortable : true,
            width : 120,
            formatter:function(value,rec,index){ 
            	return rec.subCatalogName;
            }
        }, {
            field : 'aaa',
            title : '对关系',
            sortable : true,
            width : 120,
            formatter:function(value,rec,index){ 
            	var html = "["+rec.catalog +"-"+rec.subCatalog+"]<--->"+"["+rec.targetCatalog +"-"+rec.targetSubCatalog+"]";
            		return html;
            }
        }, {
            field : 'targetCatalog',
            title : '平台一级类别',
            sortable : true,
            width : 70,
            formatter:function(value,rec,index){
            	if(value==1 || value=="0") {
            		return "软件";
            	}else if(value==2 || value=="2"){
            		return "游戏";
            	}else{ 
                	return "大型游戏";
            	}
            }
        }
        
        , {
            field : 'targetSubCatalog',
            title : '平台二级类别',
            sortable : true,
            width : 80,
            formatter:function(value,rec,index){ 
            	var html ="";
            	var cacheName = "cacheCatalog"+rec.targetCatalog;
            	var dataCache = $("#dataList").data(cacheName); 
            	if(dataCache!=null){
            		$.each(dataCache, function(i,row){      
          		      	if(row.id==value){ 
          		      		 html =  row.name;
          		      	}
                	}); 
            	}  
            	return html ;
            }
        } , {
            field : 'operate',
            title : '操作',
            width : 120,
            formatter : function(value, rec, index) {  
            	var del = '<a class="mr_15 co_bl f_l" onclick="Delete(' + rec.id + ',' + index + ')">删除</a>';
                var edit = '<a class="mr_10 co_bl f_l"   onclick="edtiCatalog(' + rec.id + ',\''  +rec.marketName + '\','  +rec.catalog + ','  +rec.subCatalog + ',\''  +rec.subCatalogName + '\','  + rec.targetCatalog + ','  + rec.targetSubCatalog + ')">修改关系</a>'; 
                return edit+del;
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
    var keywords = $.trim($('#keyword').val());var marketName = $('#keyMarketName option:selected').val();
    var catalog = $('#keyCatalog option:selected').val();
    var queryParams = $('#dataList').datagrid('options').queryParams;

    if(keywords !== "") {
        queryParams.keywords = keywords;
    }else{
        queryParams.keywords = '';
    }
    queryParams.marketName =""; 
    if(catalog == null || catalog =="") {
        queryParams.catalog =0;
    } else {
        queryParams.catalog = catalog;
    } 
    if(marketName == null) {
        queryParams.marketName = '';
    } else {
        queryParams.marketName = marketName;
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
	        async:false,
	        success : function(data) {
	            if(data!=null && data.rows&&  data.rows.length>0){
	            	dataCache = data.rows;
	            	$("#dataList").data(cacheName,data.rows);
	            }
	        }
	    });
	}
} 

function loadMarkets(){
	var $markets = $("#markets");
 $("#keyMarketName").empty();
 $("#keyMarketName").append("<option value=\"\" selected=\"selected\">选择市场</option>");
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
            		$("#keyMarketName").append("<option value=\""+row+"\">"+row+"</option>");
            			var url =urlWithParam+ '/admin/catalogconvertor/'+row+'.convertor.d';
            			html +='<span  class="ml_10 mr_10"><label for="marketId1'+i+'">'+row+'<a   class="ml_5 mr_5"  href="'+url+'" >批量处理</a></label></span>'
      		      		//html +='<span  class="ml_10 mr_10"><input type="checkbox" name="markNames" id="marketId'+i+'"  value="'+row+'" /><label for="marketId1'+i+'">'+row+'<a   href="'+url+'" >批量处理</a></label></span>'
            	}); 
            	$markets.html(html); 
            }
        }
    });
}


/*
 * 修改对应关系
 * */ 
function edtiCatalog(){ 
	var id = arguments[0];
	var marketName  = arguments[1];
	var Catalog  = arguments[2];
	var subCatalog = arguments[3];
	var subCatalogName = arguments[4];
	var targetCatalog = arguments[5];
	var targetSubCatalog = arguments[6];
	$editPanlCatalog = $("#editPanlCatalog");
	$editPanlCatalog.window({
	        title : '修改类别对于关系',
	        top:200,
	        width : 500,
	        modal : true,
	        shadow : true,
	        closed : false,
	        height : 300
	    });
	$editPanlCatalog.show(); 
	initHtmlCatalog(id,marketName,Catalog,subCatalog,subCatalogName,targetCatalog,targetSubCatalog);
}
/*
 * 初始化HTML数据
 * */
function initHtmlCatalog(){   
	var id = arguments[0];
	var marketName  = arguments[1];
	var catalog  = arguments[2];
	var subCatalog = arguments[3];
	var subCatalogName = arguments[4];
	var targetCatalog = arguments[5];
	var targetSubCatalog = arguments[6];
	$Id = $("#id");
	$catalog = $("#catalog");
	$subCatalog = $("#subCatalog");
	$subCatalogName = $("#subCatalogName");
	$targetCatalog = $("select[name='targetCatalog']"); 
	$targetSubCatalog = $("select[name='targetSubCatalog']");
	$targetCatalog.parent().removeClass("bd_Col_Corner"); 
	$targetSubCatalog.parent().removeClass("bd_Col_Corner"); 
	
	$("#editMarketName").html(marketName +"市场");
	$Id.val(id);
	$catalog.val(catalog);
	$subCatalog.val(subCatalog);
	$subCatalogName.val(subCatalogName);
	$("#editCatalog").html(GetCatalogNames(catalog));
	$("#editSubCatalogName").html(subCatalogName); 
	$targetCatalog.find("option").removeAttr("selected");
	$targetCatalog.find("option[value='"+targetCatalog+"']").attr("selected","selected"); 
	$targetCatalog.bind("change",function(e){  
		 var catalogValue= $(this).find("option:selected").val();  
		 loadEditSubCatalog($targetSubCatalog,catalogValue,0); 
		 checkTdSelect($(this));
	});
	loadEditSubCatalog($targetSubCatalog,targetCatalog,targetSubCatalog);
	$fromPost = $("#tagsPost");
	/*POST请求*/
	$fromPost.click(function(){ 
		targetCatalog = $("select[name='targetCatalog'] option:selected").val(); 
		targetSubCatalog = $("select[name='targetSubCatalog'] option:selected").val(); 
		
		if(marketName=="" || marketName ==null ){
			alert("市场名称不能为空！");
			return false; 
		}
		if(subCatalogName=="" || subCatalogName ==null ){
			alert(marketName+"市场二类别不能为空！");
			return false; 
		}
		if(parseInt(catalog)<=0){
			alert(marketName+"市场一类别不能为空！");
			return false;
		}
		if(parseInt(subCatalog)<=0){
			alert(marketName+"市场二类别不能为空！");
			return false;
		}	 
		if(parseInt(targetCatalog)<=0){
			alert("手机控平台一类别不能为空！");	 
			return false;
		}
		if(parseInt(targetCatalog)<=0){
			alert("手机控平台二类别不能为空！");	
			return false;
		}  		
		 $.ajax({
             type : "POST",
             url : urlWithParam + "/admin/catalogconvertor/edit.json",
             traditional : true,
             data : {
            	 "id":id,
            	 "marketName":marketName,	
                 "catalog" : catalog,
                 "subCatalog" : subCatalog,
                 "subCatalogName":subCatalogName, 
                 "targetCatalog" : targetCatalog,
                 "targetSubCatalog" : targetSubCatalog 
             },
             dataType : 'json',
             success : function(data) {
                 if(data.result.msg == "OK!") {
                     $.messager.show({
                         msg : '修改 成功',
                         title : '提示',
                         showType : 'slide',
                         timeout : 3000
                     });
                     $('#dataList').datagrid('unselectAll');
                     $('#dataList').datagrid('reload'); 
                     $fromPost.parent().parent().parent().hide(); 
                     //$("#editPanlCatalog").hide();
                     $(".window-mask").hide();  
                 }
             }
         }); 
	});
}
/*
 * 加载修改类别下拉控件
 * */
function loadEditSubCatalog($targetSubCatalog,targetCatalog,targetSubCatalog){
	var cacheName = "cacheCatalog"+targetCatalog;
	var dataCache = $("#dataList").data(cacheName);  
	$targetSubCatalog.empty(); 
	$targetSubCatalog.append("<option value=\"\" selected=\"selected\">--选择--</option>");
	if(dataCache!=null){
		$.each(dataCache, function(i,row){   
		    if(row.id==targetSubCatalog){ 
		    	$targetSubCatalog.append("<option value=\""+row.id+"\" selected=\"selected\">"+row.name+"</option>");
		     }else{ 
		    	$targetSubCatalog.append("<option value=\""+row.id+"\">"+row.name+"</option>");
		     }
    	}); 
		checkTdSelect($targetSubCatalog);
		$targetSubCatalog.bind("change",function(e){   
			 checkTdSelect($(this));
		}); 
		
	} else{
		$targetSubCatalog.parent().addClass("bd_Col_Corner"); 
	}
}
/*
 * 严重控件是否选择了值如果，如果没有则设置红色
 * */
function checkTdSelect(obj){
	 var value = obj.find("option:selected").val(); 
	 	if(value==null||value==""||parseInt(value)<=0){
	 		obj.parent().addClass("bd_Col_Corner"); 
	 		return 1;
	 	}else{ 
	 		obj.parent().removeClass("bd_Col_Corner"); 
	 		return 0;
	 	} 
}



//隐藏
function Delete() {
 var id = arguments[0];
 $.messager.confirm('确认', '您确认要删除这条记录吗?', function(row) {
     if(row) {
         $.ajax({
             type : "POST",
             url : urlWithParam + "/admin/catalogconvertor/del.d",
             traditional : true,
             data : {
                 "ids" : id
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
                 }
             }
         });
     }
 });
}
 