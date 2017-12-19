var urlWithParam = "/sjk-market-admin";
var urlWithParamImage = "http://app.sjk.ijinshan.com/market/img/tag/";

$(document).ready(function() {

	$('#keyword').bind('keydown', function(e) {
		if (e.which == 13) {
			SearchEve();
		}
	});
	$('#search').click(function() {
		SearchEve();
	});
	var tagId = queryString("tagId");
	if (tagId == null || tagId == "" || tagId == undefined) {
		tagId = 0;
	}
	if (tagId != 0) {
		$('#dataList tbody').hide();
	}
	LoadTags(tagId);
	loadGridTable(tagId);
	var p = $('#dataList').datagrid('getPager');
	$(p).pagination({
		onBeforeRefresh : function() {
		}
	});
	BindSort();
    setTTagType();
    setkeyTagType();
	BindGetApp();
	

	//fixDiv('.datagrid:first','.datagrid-pager-top:first','fixedMove');
	
});

function setkeyTagType() {
    $("#keyTagType").change(function() {
        var pid = $(this).val();
        if(pid != null && pid != "") {
            LoadTags(pid);
        }
    });
}
function setTTagType() {
    $("#sltTagType").change(function() {
        var pid = $(this).val();
        if(pid != null && pid != "") {
            loadsltTagId(pid, 0);
        }
    });
}
// 应用提示
function BindGetApp() {
	$("#txtAppId").bind('blur', function() {
		var appId = $(this).val();
		$.ajax({
			type : "GET",
			url : urlWithParam + "/admin/app/" + appId + ".getapp.json",
			dataType : 'json',
			success : function(data) {
				if (data != null && data.data != null) {
					$("#txtAppName").text(data.data.name);
					$("#txtShortDesc").val(data.data.shortDesc);
				} else {
					$("#txtAppName").text("");
					$("#txtShortDesc").val("");
				}

			}
		});
	});
}
// 只取一个：
function queryString(key) {
	return (document.location.search.match(new RegExp("(?:^\\?|&)" + key
			+ "=(.*?)(?=&|$)")) || [ '', null ])[1];
}

// 加载DataGrid控件
function loadGridTable(tagId) {
	var url = urlWithParam + "/admin/tagapp/topic-search.json";
	if (tagId != 0) {
		url = urlWithParam + "/admin/tagapp/topic-search.json?tagId=" + tagId;
	}
	$('#dataList')
			.datagrid(
					{
						loadMsg : '正在加载...',
						iconCls : 'icon-save',
						width : '950',
						nowrap : false,
						striped : true,
						collapsible : false,
						url : url,
						pageSize : 30,
						sortName : 'rank',
						sortOrder : 'desc',
						idField : "id",
						onClickRow : function(rowIndex, rowData) {
							var index = rowIndex;
							var row = rowData;
						},
						columns : [ [
								{
									field : 'id',
									title : 'id',
									sortable : true,
									width : 50,
									formatter : function(value, rec, index) {
										var edit = '<a name="ids" ids="'
												+ rec.id
												+ '" rank="'
												+ rec.rank
												+ '" class="mr_10 co_bl f_l" onclick="Edit('
												+ rec.id + ',' + rec.tagId
												+ ',' + rec.appId + ','
												+ rec.rank + ',' + rec.tagType
												+ ',\'' + rec.shortDesc
												+ '\');">' + value + '</a>';
										return edit;
									}
								},
								{
									field : 'appId',
									title : '应用编号',
									sortable : true,
									width : 80,
									formatter : function(value, rec, index) {
										var edit = '<a class="mr_10 co_bl f_l" onclick="Edit('
												+ rec.id
												+ ','
												+ rec.tagId
												+ ','
												+ rec.appId
												+ ','
												+ rec.rank
												+ ','
												+ rec.tagType
												+ ',\''
												+ rec.shortDesc
												+ '\');">' + value + '</a>';
										return edit;
									}
								},
								{
									field : 'tagName',
									title : '专题名称',
									sortable : true,
									width : 150
								},
								{
									field : 'appName',
									title : '应用名称',
									sortable : true,
									width : 250,
									formatter : function(value, rec, index) {
										var url = "";
										if (rec.appCatalog == 100) {
											url = '"../admin/biggame/'
													+ rec.appId + '.d?id='
													+ rec.appId + '"';
										} else {
											url = '"../admin/app/' + rec.appId
													+ '.merge.d"';

										}
										var ids = '<img class="w_20 h_20 f_l mt_4 mr_10" src="'
												+ rec.logoUrl
												+ '"/><a name="softIds" class="co_bl f_l" value="'
												+ rec.appId
												+ '" href='
												+ url
												+ ' target="_blank">'
												+ value
												+ '</a>';
										return ids;
									}
								},
								{
									field : 'rank',
									title : '排序',
									sortable : true,
									width : 100
								},
								{
									field : 'marketName',
									title : '市场名称',
									sortable : true,
									width : 100
								},
								{
									field : 'operate',
									title : '操作',
									width : 100,
									formatter : function(value, rec, index) {
										var del = '<a class="mr_15 co_bl f_l" onclick="Delete('
												+ rec.id
												+ ','
												+ index
												+ ')">删除</a>';
										var edit = '<a class="mr_10 co_bl f_l" onclick="Edit('
												+ rec.id
												+ ','
												+ rec.tagId
												+ ','
												+ rec.appId
												+ ','
												+ rec.rank
												+ ','
												+ rec.tagType
												+ ',\''
												+ rec.shortDesc
												+ '\');">修改</a>';
										return edit + del;
									}
								} ] ],
						pagination : true,
						pagePosition:'both',//上下显示分页效果
						pagerFixedTop:true,//滚动固定上栏分页
						rownumbers : false,
						onLoadSuccess:function(){
							
						}
					});
}

// 搜索
function SearchEve() {
	var keywords = $.trim($('#keyword').val());

	var tagId = $("#keyTagId option:selected").val();
	var queryParams = $('#dataList').datagrid('options').queryParams;
	var url = urlWithParam + "/admin/tagapp/topic-search.json";
	if (keywords !== "") {
		queryParams.keywords = keywords;
	} else {
		queryParams.keywords = '';
	}
	if (tagId == "" || tagId == null) {
		$('#dataList').datagrid('options').url = url;
	} else {
		url = url + '?tagId=' + tagId;
		$('#dataList').datagrid('options').url = url;
	}
	queryParams.page = 1;

	// 主显示使用

	$('#dataList').datagrid('options').pageNumber = 1;
	// page对象 中的pageNumber对象
	var pager = $('#dataList').datagrid('getPager');
	$(pager).pagination('options').pageNumber = 1;
	$('#dataList').datagrid('reload');
}

// 搜索
function SearchEveEx(tagId) {
	var keywords = $.trim($('#keyword').val());
	var queryParams = $('#dataList').datagrid('options').queryParams;
	queryParams.url = urlWithParam + "/admin/tagapp/topic-search.json";
	if (keywords !== "") {
		queryParams.keywords = keywords;
	} else {
		queryParams.keywords = '';
	}
	if (tagId == "" || tagId == null) {
		queryParams.tagId = '';
	} else {
		queryParams.tagId = tagId;
	}
	queryParams.page = 1;

	// 主显示使用
	$('#dataList').datagrid('options').pageNumber = 1;
	// page对象 中的pageNumber对象
	var pager = $('#dataList').datagrid('getPager');
	$(pager).pagination('options').pageNumber = 1;
	$('#dataList').datagrid('reload');
}

function LoadTags(tagId) {
	$keyTagId = $("#keyTagId");

	$("#keyTagId option").removeAttr("selected");
	$("#keyTagId option[value='" + tagId + "']").attr("selected", "selected");
	$.ajax({
		type : "GET",
		url : urlWithParam + "/admin/tag/topic-list.json",
		dataType : 'json',
		success : function(data) {
			if (data != null) {
				var data = data.rows;
				$keyTagId.data("tagIdData", data)
				getSelectData(data, $keyTagId, '');
			}
			$keyTagId.bind({
				change : function() {
					SearchEve()
				}
			})
		}
	});

}

function loadTagsData(keyTagId, sltTagsId, value) {
	$keyTagId = $("#" + keyTagId);
	tags = $keyTagId.data("tagIdData");
	sltTags = $("#" + sltTagsId);
	if (tags == null || tags == "" || tags == undefined) {
		$.ajax({
			type : "GET",
			url : urlWithParam + "/admin/tag/topic-list.json",
			dataType : 'json',
			success : function(data) {
				if (data != null) {
					var data = data.rows;
					$keyTagId.data("tagIdData", data)
					getSelectData(data, sltTags, value);
				}
			}
		});
	} else {
		getSelectData(tags, sltTags, value);
	}

}

// 添加
function Add() {
	Edit(0, 0, 0, 999999999, 1, "");
}

// 添加
function Edit() {
	var id = arguments[0];
	var tagId = arguments[1];
	var appId = arguments[2];
	var rank = arguments[3];
	var tagType = arguments[4];
	var shortDesc = arguments[5];
	$editPanl = $("#editPanl");
	var title = "添加专题应用";
	if (id > 0) {
		var title = "修改专题应用";
	}
	$editPanl.window({
		title : title,
		top : 200,
		width : 600,
		modal : true,
		shadow : true,
		closed : false,
		height : 350
	});
	$editPanl.show();
	initHtml(id, tagId, appId, rank, tagType, shortDesc);
}

function initHtml() {
	var id = arguments[0];
	var tagId = arguments[1];
	var appId = arguments[2];
	var rank = arguments[3];
	var tagType = arguments[4];
	var shortDesc = arguments[5];
	$id = $("#id");
	$appId = $("#txtAppId");
	$tagType = $("#sltTagType");
	// 下拉框
	$tagId = $("#sltTagId");
	// 下拉框
	$shortDesc = $("#txtShortDesc");
	$rank = $("#txtRank");

	$id.val(id);
	$appId.val(appId);
	$rank.val(rank);

	// TODO:多个是完善功能
	// $tagType.val(tagType);
	$shortDesc.val(((shortDesc == null ? "" : shortDesc) == 'null' ? ""
			: shortDesc));
	loadTagsData("keyTagId", "sltTagId", tagId);
	$tagsPost = $("#tagsPost");
	/* POST请求 */ 
	$tagsPost.unbind('click');
	$tagsPost.click(function() {
		tagId = $tagId.find("option:selected").val();
		tagName = $tagId.find("option:selected").text();
		tagType = $tagType.find("option:selected").val();
		tagName = tagName.replace(/^\+--/g, "");
		tagName = tagName.replace(/^\-/g, "");
		if ($appId.val() == "" || $appId.val() == null) {
			$("#errorAppId").html("请输入应用编号。");
			return false;
		} else {
			$("#errorAppId").empty();
		}
		if (tagId == null || tagId == "") {
			$("#errorTagId").html("请选择专题标签。");
			return false;
		} else {
			$("#errorTagId").empty();
		}
		$.ajax({
			type : "POST",
			url : urlWithParam + "/admin/tagapp/save.d",
			traditional : true,
			data : {
				"id" : $id.val(),
				"appId" : $appId.val(),
				"rank" : $rank.val(),
				"shortDesc" : $shortDesc.val(),
				"tagId" : tagId,
				"tagType" : tagType,
				"tagName" : tagName
			},
			dataType : 'json',
			success : function(data) {
				if (data.result.msg == "OK!") {
					$.messager.show({
						msg : '修改成功',
						title : '提示',
						showType : 'slide',
						timeout : 3000
					});
					$('#dataList').datagrid('unselectAll');
					$('#dataList').datagrid('reload');
				}
			}
		});
		$(this).parent().parent().parent().parent().parent().parent().parent()
				.parent().hide();
		$("#editPanl").hide();
		$(".window-mask").hide();
	});
}

// 排序
function BindSort() {
	var $btnUp = $("#btnUp"), $btnDown = $("#btnDown"), keyTagId = 0, rowLen = 0, appId = 0, rank = 0, appIdUd = 0, rankUd = 0;

	$btnUp
			.bind({
				click : function() {
					var $UpTr = $(
							".datagrid-body tr[class*='datagrid-row-selected']")
							.prev("tr");
					rowLen = $(".datagrid-body tr[class*='datagrid-row-selected']").length;
					keyTagId = $('#keyTagId option:selected').val();


					if (rowLen == 1 && keyTagId !== "" && $UpTr.html() != null) {
						id = parseInt($(
								".datagrid-body tr[class*='datagrid-row-selected']")
								.find("a[name='ids']").attr("ids"));
						rank = parseInt($(
								".datagrid-body tr[class*='datagrid-row-selected']")
								.find("a[name='ids']").attr("rank"));
						idUd = parseInt($UpTr.find("a[name='ids']").attr("ids"));
						rankUd = parseInt($UpTr.find("a[name='ids']").attr(
								"rank"));

						$.ajax({
							type : "POST",
							url : urlWithParam + "/admin/tagapp/sort.json",
							traditional : true,
							data : {
								"ids" : [ idUd, id ],
								"ranks" : [ rank, rankUd ]
							},
							dataType : 'json',
							success : function(data) {
								if (data.result.msg == "OK!") {
									$.messager.show({
										msg : '移动 成功',
										title : '提示',
										showType : 'slide',
										timeout : 3000
									});
									$('#dataList').datagrid('unselectAll');
									$('#dataList').datagrid('reload');
								}
							}
						});
					} else {
						alert("请选择专题");
					}
				}
			})

	$btnDown
			.bind({
				click : function() {
					var $DownTr = $(
							".datagrid-body tr[class*='datagrid-row-selected']")
							.next("tr");
					rowLen = $(".datagrid-body tr[class*='datagrid-row-selected']").length;
					keyTagId = $('#keyTagId option:selected').val();

					if (rowLen == 1 && keyTagId !== "" && $DownTr.html() !=null ) {
						id = parseInt($(
								".datagrid-body tr[class*='datagrid-row-selected']")
								.find("a[name='ids']").attr("ids"));
						rank = parseInt($(
								".datagrid-body tr[class*='datagrid-row-selected']")
								.find("a[name='ids']").attr("rank"));
						idUd = parseInt($DownTr.find("a[name='ids']").attr(
								"ids"));
						rankUd = parseInt($DownTr.find("a[name='ids']").attr(
								"rank"));

						$.ajax({
							type : "POST",
							url : urlWithParam + "/admin/tagapp/sort.json",
							traditional : true,
							data : {
								"ids" : [ idUd, id ],
								"ranks" : [ rank, rankUd ]
							},
							dataType : 'json',
							success : function(data) {
								if (data.result.msg == "OK!") {
									$.messager.show({
										msg : '移动 成功',
										title : '提示',
										showType : 'slide',
										timeout : 3000
									});
									$('#dataList').datagrid('unselectAll');
									$('#dataList').datagrid('reload');
								}
							}
						});
					} else {
						alert("请选择专题");
					}
				}
			})
}

// 删除
function Delete() {
	var id = arguments[0];
	$.messager.confirm('确认', '您确认要删除这条记录吗?', function(row) {
		if (row) {
			$.ajax({
				type : "POST",
				url : urlWithParam + "/admin/tagapp/del.json",
				traditional : true,
				data : {
					"id" : id
				},
				dataType : 'json',
				success : function(data) {
					if (data.result.msg == "OK!") {
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

function getTagName(str) {
	if (str != null && str != "" && str.length >= 3) {
		// var reSpace = var reSpace=/^+--$/;
		// var temp =str.substring(0,3);
		// if(temp=="+--"){
		// temp.re
		// }else{
		// if(str.indexOf("-")!=-1){
		//
		// }else{
		// return str;
		// }
		// }
	}
}