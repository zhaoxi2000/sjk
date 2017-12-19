var urlWithParam = "/sjk-market-admin";
$(document).ready(function() { 
	BindCatalogData();
	LoadMartketCatalogs();
	PostCheck();
});

function LoadMartketCatalogs(){
	$("#dataList tr td[name='OldCatalog']").each(function(i,rs){
		 var result="";
		 var value = $(this).attr("tag"); 
		 if(value==null || value=="" || value == undefined) 
			 value="0";  
    	  var result=GetCatalogNames(parseInt(value));
    	  $(this).html('['+value+']'+result);
	});
}


//绑定类别
function BindCatalogData(){  
		$("select[name='targetSubCatalog']").each(function(i,rs){
			var targetSubCatalogObj =$(this);
			var targetSubCatalog = targetSubCatalogObj.attr("tag");   
			var PidObj = targetSubCatalogObj.parent().parent().find("td>select[name='targetCatalog']"); 
			var Pid = PidObj.find("option:selected").val();
			if(Pid==0)
				Pid=88;
			PidObj.bind("change",function(e){ 
			    var catalog= $(this).find("option:selected").val(); 
			    loadCatalog(targetSubCatalogObj,"",catalog);
			    checkTdSelect($(this))
			});
			loadCatalog(targetSubCatalogObj,targetSubCatalog,Pid);
		});
};

//加载分类
function loadCatalog(targetSubCatalogObj,targetSubCatalog,Pid) {  
	var cacheName ="cacheCatalog"+Pid;
	var dataCache = $("#dataList").data(cacheName);
	if(dataCache==null || dataCache== undefined || dataCache=="") {
		$.ajax({
	        type : "GET",
	        url : urlWithParam + "/admin/catalog/list.json?pid="+Pid,
	        dataType : 'json',
	        async:false,
	        success : function(data) {
	            if(data!=null && data.rows&&  data.rows.length>0){
	            	dataCache = data.rows;
	            	$("#dataList").data(cacheName,data.rows);
	        		SetLoadCatalog(targetSubCatalogObj,dataCache,targetSubCatalog);
	            }
	        }
	    });
	}else{ 
		SetLoadCatalog(targetSubCatalogObj,dataCache,targetSubCatalog);
	}
} 

//加载绑定类别
function SetLoadCatalog(targetSubCatalogObj,data,targetSubCatalog){
	if(data==null || data==""){
		targetSubCatalogObj.empty();
		targetSubCatalogObj.append("<option value=\"\" selected=\"selected\">选择类别</option>");
		return;
	}
	targetSubCatalogObj.empty();
	targetSubCatalogObj.append("<option value=\"\" selected=\"selected\">选择类别</option>");
	$.each(data,function(i,rs){
			if(rs!=null || rs!=""){ 
				if($.trim(rs.id) ==$.trim(targetSubCatalog)){ 
					targetSubCatalogObj.append("<option value=\""+rs.id+"\"   selected=\"selected\">"+rs.name+"</option>");
				}else{ 
					targetSubCatalogObj.append("<option value=\""+rs.id+"\">"+rs.name+"</option>");
				}
			}
	});
	targetSubCatalogObj.bind("change",function(e){  
	    checkTdSelect($(this))
	});  
}


//验证POST
function PostCheck(){ 
	$btnPost = $("#postdata");
	$targetCatalogs = $("#dataList tr td select[name='targetCatalog']");
	$targetSubCatalogs = $("#dataList tr td select[name='targetSubCatalog']");  
	$btnPost.click(function(){ 
			var result =0; 
			 $.each($targetCatalogs,function(i,rs){
				 var obj =$(this);
				 result=result+checkTdSelect(obj);
			 })
			 $.each($targetSubCatalogs,function(i,rs){
				 var obj =$(this);
				 result=result+checkTdSelect(obj);
			 })  
			 if(result>0){ 
				 $.messager.confirm('确认', '类别对应不完整，您确定要保存吗?', function(row) {
				     if(row) {
				    	 return true ;
				     }else{ 
						 return false;
				     }
				 }); 
			 }else{
				 return true;
			 }
	}); 
}

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