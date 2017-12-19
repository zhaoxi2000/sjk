var urlWithParam = "/sjk-market-admin";

$(document).ready(function() { 
	setCatalogMap();
	bindCatalog();
	$('#keyword').bind('keydown', function(e) {
		if (e.which == 13) {
			SearchEve();
		}
	});

	$('#search').click(function() {
		SearchEve();
	});
	$('#hideBtn').click(function() {
		BatchHideEve()
	});
	$('#hideBtnBom').click(function() {
		BatchHideEve()
	});
	$('#idsBtn').click(function() {
		editIdsType()
	});
	$('#idsBtnBom').click(function() {
		editIdsType()
	});
	loadGridTable();
	var p = $('#dataList').datagrid('getPager');
	$(p).pagination({
		onBeforeRefresh : function() {
		}
	});

});

// 加载DataGrid控件
function loadGridTable() {
	$('#dataList')
			.datagrid(
					{
						loadMsg : '正在加载...',
						iconCls : 'icon-save',
						width : '880',
						nowrap : false,
						striped : true,
						collapsible : false,
						url : urlWithParam + "/admin/ijinshan/search.json",
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
						columns : [ [
								{
									field : 'ck',
									checkbox : true,
									width : 50
								},
								{
									field : 'id',
									title : 'id',
									sortable : true,
									width : 50
								},
								{
									field : 'name',
									title : '软件名称',
									sortable : true,
									width : 260,
									formatter : function(value, rec, index) {
										var url = urlWithParam
												+ '/admin/ijinshan/' + rec.id
												+ '.edit.d';
										var ids = '<img class="w_20 h_20 f_l mt_4 mr_10" src="'
												+ rec.logoUrl
												+ '"/>'
												+ '<a name="softIds" class="co_bl f_l" value="'
												+ rec.id
												+ '" href="'
												+ url
												+ '"  target="_blank">'
												+ value
												+ '</a>';
										return ids;
									}
								},
								{
									field : 'downloads',
									title : '下载次数',
									sortable : true,
									width : 80
								},
								{
									field : 'lastUpdateTime',
									title : '最后修改时间',
									sortable : true,
									width : 100,
									formatter : function(value, entity) {
										return value.substring(0, 10);
									},
								},
								{
									field : 'subCatalog',
									title : '分类',
									sortable : false,
									width : 110,
									formatter : function(value, rec, index) {
										var audit = "";
										var len = catalogMap.rows.length
										for ( var i = 0; i < len; i++) {
											if (catalogMap.rows[i].id == rec.subCatalog) {
												var typeName = catalogMap.rows[i].name
											}
										}
										var edit = '<a class="mr_10 co_bl f_l" onclick="editType('
												+ rec.id
												+ ','
												+ rec.subCatalog
												+ ')">' + typeName + '</a>';
										if (rec.auditCatalog == true) {
											audit = '<span class="f_l co_green">已修改</span>'
										}

										return edit + audit;
									}
								},
								{
									field : 'marketName',
									title : '来源',
									sortable : true,
									width : 80
								},
								{
									field : 'operate',
									title : '操作',
									width : 100,
									formatter : function(value, rec, index) {
										var auhide = "";
										var hide = '<a class="mr_15 co_bl f_l" onclick="hideEve('
												+ rec.id
												+ ','
												+ index
												+ ')">隐藏</a>';
										// var tagapp = '<a class="mr_15 co_bl
										// f_l" onclick="tagappEve(' + rec.id +
										// ',' + index + ')">添加摇一摇</a>';
										if (rec.hidden == true) {
											auhide = '<span class="f_l co_green">已隐藏</span>'
										}
										var viewmarket = '<a name="softIds" class="co_bl f_l" value="'
												+ rec.id
												+ '" href="'
												+ rec.detailUrl
												+ '"  target="_blank">市场预览</a>'
										return hide + auhide + viewmarket;
									}
								} ] ],
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
    var keywords = $.trim($('#keyword').val());var catalog = $('#catalogKeyColumn option:selected').val();
	var subCatalog = $('#subCatalogKeyColumn option:selected').val();
	var queryParams = $('#dataList').datagrid('options').queryParams;

	if (keywords !== "") {
		queryParams.keywords = keywords;
	} else {
		queryParams.keywords = '';
	}
	if (catalog == null) {
		queryParams.catalog = '';
	} else {
		queryParams.catalog = catalog;
	}

	if (subCatalog == null) {
		queryParams.subCatalog = '';
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

// 隐藏
function hideEve() {
	var softId = arguments[0];
	$.messager.confirm('确认', '您确认要隐藏这条记录吗?', function(row) {
		if (row) {
			$.ajax({
				type : "POST",
				url : urlWithParam + "/admin/app/hide.d",
				traditional : true,
				data : {
					"ids" : softId
				},
				dataType : 'json',
				success : function(data) {
					if (data.result.msg == "OK!") {
						$.messager.show({
							msg : '隐藏 成功',
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

// 批量隐藏
function BatchHideEve() {
	var inputs = $(".datagrid-body tr[class*='datagrid-row-selected']").length;
	var type = "";
	var types = [];
	for ( var i = 0; i < inputs; i++) {
		type = parseInt($(".datagrid-body tr[class*='datagrid-row-selected']")
				.eq(i).find("a[name='softIds']").attr("value"));
		types[i] = type;
	}
	$.messager.confirm('确认', '您确认要隐藏这些记录吗?', function(row) {
		if (row) {
			$.ajax({
				type : "POST",
				url : urlWithParam + "/admin/app/hide.d",
				traditional : true,
				data : {
					"ids" : types
				},
				dataType : 'json',
				success : function(data) {
					if (data.result.msg == "OK!") {
						$.messager.show({
							msg : '隐藏 成功',
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

function BatchTagappEve() {
	var inputs = $(".datagrid-body tr[class*='datagrid-row-selected']").length;
	var type = "";
	var types = [];
	for ( var i = 0; i < inputs; i++) {
		type = parseInt($(".datagrid-body tr[class*='datagrid-row-selected']")
				.eq(i).find("a[name='softIds']").attr("value"));
		types[i] = type;
	}
	$.messager.confirm('确认', '您确认要添加这些记录吗?', function(row) {
		if (row) {
			$.ajax({
				type : "POST",
				url : urlWithParam + "/admin/tagapp/save-rollinfo.d",
				traditional : true,
				data : {
					"ids" : types
				},
				dataType : 'json',
				success : function(data) {
					if (data.result.msg == "OK!") {
						$.messager.show({
							msg : '添加 成功',
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

var catalogMap = [];

function bindCatalog() {
	$catalogColumn = $("#catalogKeyColumn");
	$catalogColumn.bind({
		change : function() {
			 var catalog= $(this).find("option:selected").attr("value");
			 loadType(catalog);
		}
	})
}

function setCatalogMap(){
	$.ajax({
		type : "GET",
		url : urlWithParam + "/admin/catalog/list.json?pid=-1",   
		dataType : 'json', 
		success : function(data) {
			catalogMap = data; 
		}
	});
}
// 加载分类
function loadType(pid) {
	var $keyColumn = $("#subCatalogKeyColumn");
	var $keyword = $("#keyword");
	var $idsBtn = $("#idsBtn");
	if (pid == null || pid == undefined || pid == "") {
		pid = 1;
	}
	$.ajax({
		type : "GET",
		url : urlWithParam + "/admin/catalog/list.json?pid=" + pid,
		dataType : 'json',
		success : function(data) {
			catalogMap = data;
			var num = data.rows.length;
			var ohtml = "<option value=''>全部</option>";

			for ( var i = 0; i < num; i++) {
				ohtml += "<option value=" + data.rows[i].id + ">"
						+ data.rows[i].name + "</option>"
			}
			$keyColumn.empty();
			$keyColumn.append(ohtml);
			$keyColumn.bind({
				change : function() {
					$keyword.val("");
					$idsBtn.attr("value", $(this).find("option:selected").attr(
							"value"));
					SearchEve()
				}
			})
		}
	});
}

// 修改分类
function editIdsType() {
	var $editIds = $("#editIds");
	$editIds.window({
		title : '修改分类',
		width : 510,
		modal : true,
		shadow : false,
		closed : false,
		height : 280
	});
	$editIds.show();
	loadIdsMenu();
	UpIdsType()
}

// 加载分类信息
function loadIdsMenu() {
	var softwareArr = [];
	var typeid = parseInt($("#idsBtn").attr("value"));

	var len = catalogMap.rows.length
	for ( var i = 0; i < len; i++) {
		softwareArr.push([ "<li><a value='", catalogMap.rows[i].id, "'>",
				catalogMap.rows[i].name, "</a></li>" ].join(''));
	}

	var html = [];
	html.push(softwareArr.join(""));
	$("#IdsMenu").html(html.join(""));
	$("#IdsMenu a[value='" + typeid + "']").addClass("cur")
}

// 更新分类
function UpIdsType() {
	var $IdsMenu_li_a = $("#IdsMenu li a");
	var $editIds = $("#editIds");

	$IdsMenu_li_a
			.click(function(e, index) {
				var typeIds = parseInt($(this).attr("value"));
				var inputs = $(".datagrid-body tr[class*='datagrid-row-selected']").length;
				var type = "";
				var types = [];
				for ( var i = 0; i < inputs; i++) {
					type = parseInt($(
							".datagrid-body tr[class*='datagrid-row-selected']")
							.eq(i).find("a[name='softIds']").attr("value"));
					types[i] = type;
				}
				if (inputs !== "") {
					$.ajax({
						type : "POST",
						url : urlWithParam + "/admin/app/edit-catalog.d",
						traditional : true,
						data : {
							"catalog" : 1,
							"subCatalog" : typeIds,
							"ids" : types
						},
						dataType : 'json',
						success : function(data) {
							if (data.result.msg == "OK!") {
								$.messager.show({
									msg : '修改 成功',
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
				;
				$(this).parent().parent().parent().parent().parent().hide()
				$editIds.hide();
				$(".window-mask").hide();
			})
}

// 修改分类
function editType(softId, typeid) {
	var softId = arguments[0];
	var $editType = $("#editType");
	$editType.window({
		title : '修改分类',
		width : 510,
		modal : true,
		shadow : false,
		closed : false,
		height : 280
	});
	$editType.show();
	initTypeMenu(typeid);
	UpType(softId)
}

// 加载分类信息
function initTypeMenu(typeid) {
	var softwareArr = [];
	var len = catalogMap.rows.length
	for ( var i = 0; i < len; i++) {
		softwareArr.push([ "<li><a value='", catalogMap.rows[i].id, "'>",
				catalogMap.rows[i].name, "</a></li>" ].join(''));
	}
	var html = [];
	html.push(softwareArr.join(""));

	$("#TypeMenu").html(html.join(""));
	$("#TypeMenu a[value='" + typeid + "']").addClass("cur")
}

// 更新分类
function UpType(softId) {
	var $TypeMenu_li_a = $("#TypeMenu li a");
	var $editType = $("#editType");

	$TypeMenu_li_a.click(function() {
		var typeIds = parseInt($(this).attr("value"));

		if (typeIds !== "") {
			$.ajax({
				type : "POST",
				url : urlWithParam + "/admin/app/edit-catalog.d",
				traditional : true,
				data : {
					"catalog" : 1,
					"subCatalog" : typeIds,
					"ids" : softId
				},
				dataType : 'json',
				success : function(data) {
					if (data.result.msg == "OK!") {
						$.messager.show({
							msg : '修改 成功',
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
		$(this).parent().parent().parent().parent().parent().hide()
		$editType.hide();
		$(".window-mask").hide();
	})
}