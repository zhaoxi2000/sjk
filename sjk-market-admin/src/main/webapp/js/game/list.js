var urlWithParam = "/sjk-market-admin";

$(document).ready(function() {
    loadType();

    $('#keyword').bind('keydown', function(e) {
        if(e.which == 13) {
            SearchEve();
        }
    });

    $('#search').click(function() {
        SearchEve();
    });
    $('#hideBtn').click(function() {
        BatchHideEve()
    });
    $('#idsBtn').click(function() {
        editIdsType()
    });
    $('#idsBtnBom').click(function() {
        editIdsType()
    });
    $('#hideBtnBom').click(function() {
        BatchHideEve()
    });
    loadGridTable();
    var p = $('#gameList').datagrid('getPager');
    $(p).pagination({
        onBeforeRefresh : function() {
        }
    });


	setALLBtnDisabled(true);
});
function setALLBtnDisabled(disabled){ 
	setBtnDisabled("idsBtn",disabled);
	setBtnDisabled("hideBtn",disabled); 
	setBtnDisabled("idsBtnBom",disabled);
	setBtnDisabled("hideBtnBom",disabled); 
}
function setBtnDisabled(btnId,disabled){
	if(disabled){ 
		$("#"+btnId).attr("style","color:#999").attr("disabled","disabled"); 
	}else{  
		$("#"+btnId).removeAttr("style").removeAttr("disabled");  
	}
}
// 加载DataGrid控件
function loadGridTable() {
    $('#gameList').datagrid({
        loadMsg : '正在加载...',
        iconCls : 'icon-save',
        width : '1100',
        nowrap : false,
        striped : true,
        collapsible : false,
        url : urlWithParam + "/admin/app/search.json?catalog=2",
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
		onLoadSuccess : function (data){
			$("input[name='ck']").click(function(){ 
				var length =$("input[name='ck']:checked").length ;
				if(length == null || length== "" || length ==undefined || length==0){ 
					setALLBtnDisabled(true);
				}
			});
			$(".datagrid-header-row input[type='checkbox']").click(function(){ 
				if($(this).attr("checked")=='checked'){
					setALLBtnDisabled(false);
				}else{
					setALLBtnDisabled(true);
				} 
			});
			setBtnDisabled("search", false);
		},
		onClickRow : function(index,data){ 
				var length = $(".datagrid-body tr[class*='datagrid-row-selected']").length;
			if(length == null || length== "" || length ==undefined || length==0){ 
				setALLBtnDisabled(true);
			}
		},
		onSelect:function(index,data){
			setALLBtnDisabled(false);
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
            field : 'name',
            title : '游戏名称',
            sortable : true,
            width : 260,
            formatter : function(value, rec, index) {
            	var url =urlWithParam+ '/admin/app/'+ rec.id+'.merge.d';
                var ids = '<img class="w_20 h_20 f_l mt_4 mr_10" src="' + rec.logoUrl + '"/>'+
                '<a name="softIds" class="co_bl f_l" value="' + rec.id + '" href="' +url+ '"  target="_blank">' + value + '</a>';
                return ids;
            }
        }, {
            field : 'downloads',
            title : '下载次数',
            sortable : true,
            width : 80
        }, {
            field : 'lastUpdateTime',
            title : '最后修改时间',
            sortable : true,
            width : 100,
            formatter : function(value, entity) {
                return value.substring(0, 10);
            },
        }, {
            field : 'subCatalog',
            title : '分类',
            sortable : false,
            width : 110,
            formatter : function(value, rec, index) {
                var audit="";
                var len = catalogMap.rows.length
                for(var i = 0; i < len; i++) {
                    if(catalogMap.rows[i].id == rec.subCatalog) {
                        var typeName = catalogMap.rows[i].name
                    }
                }

                var edit = '<a class="mr_10 co_bl f_l" onclick="editType(' + rec.id + ',' + rec.subCatalog + ')">' + typeName + '</a>';
                
                if(rec.auditCatalog==true){
                    audit = '<span class="f_l co_green">已修改</span>'
                }
                
                return edit+audit;
            }
        }, {
			field : 'autoCover',
			title : '自动覆盖',
			sortable : true,
			width : 80,
			formatter : function(value, rec, index) {
				if(rec.autoCover == true){
					return "<span  class=\"co_red\">是</span>"; 
				}else{
					return "<span class=\"co_dg\">否</span>";
				}
			}
		},{
            field : 'marketName',
            title : '来源',
            sortable : true,
            width : 80
        }, {
            field : 'operate',
            title : '操作',
            width : 180,
            formatter : function(value, rec, index) {
                var auhide ="";
                //var hide = '<a class="mr_10 co_bl f_l" onclick="hideEve(' + rec.id + ',' + index + ')">隐藏</a>';
                var hide =rec.hidden == true?'<a class="mr_10 co_green f_l" onclick="showEve(' + rec.id + ',' + index + ')">显示</a>': '<a class="mr_10 co_degr f_l" onclick="hideEve(' + rec.id + ',' + index + ')">隐藏</a>';
                
                if(rec.hidden==true){
                    auhide = '<span class="f_l co_dg">已隐藏</span>'
                }
                var viewmarket =  '<a name="softIds" class="co_bl f_l" value="' + rec.id + '" href="' + rec.detailUrl + '"  target="_blank">市场预览</a>'
                return hide+auhide+viewmarket;
                return hide+auhide;
            }
        }]],
        pagination : true,
        rownumbers : false
    });
}

// 搜索
function SearchEve() {
	setBtnDisabled("search", true);
    var keywords = $.trim($('#keyword').val());
    var subCatalog = $('#keyColumn option:selected').val();
    var queryParams = $('#gameList').datagrid('options').queryParams;
    var id = $.trim($('#keyId').val());
	if (id !== "") {
		if(isNaN(id)){
			alert("Id必需为数字");
		}else{ 
			queryParams.id = id;
		}
	} else {
		queryParams.id = '';
	}
    if(keywords !== "") {
        queryParams.keywords = keywords;
    }else{
        queryParams.keywords = '';
    }
    queryParams.catalog = 2;

    if(subCatalog == null) {
        queryParams.subCatalog = '';
    } else {
        queryParams.subCatalog = subCatalog;
    }

    queryParams.page = 1;

    // 主显示使用
    $('#gameList').datagrid('options').pageNumber = 1;
    // page对象 中的pageNumber对象
    var pager = $('#gameList').datagrid('getPager');
    $(pager).pagination('options').pageNumber = 1;
    $('#gameList').datagrid('reload');
}

//显示
function showEve() {
	var softId = arguments[0];
	$.messager.confirm('确认', '您确认要显示这条记录吗?', function(row) {
		if (row) {
			$.ajax({
				type : "POST",
				url : urlWithParam + "/admin/app/show.d",
				traditional : true,
				data : {
					"ids" : softId
				},
				dataType : 'json',
				success : function(data) {
					if (data.result.code ==0 || data.result.code =="0") {
						$.messager.show({
							msg : data.result.msg,
							title : '提示',
							showType : 'slide',
							timeout : 5000
						});
						$('#gameList').datagrid('unselectAll');
						$('#gameList').datagrid('reload');
					}
				}
			});
		}
	});
}

//显示多条记录
function BatchShowEve() {
	var inputs = $(".datagrid-body tr[class*='datagrid-row-selected']").length;
	var type = "";
	var types = [];
	for ( var i = 0; i < inputs; i++) {
		type = parseInt($(".datagrid-body tr[class*='datagrid-row-selected']")
				.eq(i).find("a[name='softIds']").attr("value"));
		types[i] = type;
	}
	$.messager.confirm('确认', '您确认要显示这些记录吗?', function(row) {
		if (row) {
			$.ajax({
				type : "POST",
				url : urlWithParam + "/admin/app/show.d",
				traditional : true,
				data : {
					"ids" : types
				},
				dataType : 'json',
				success : function(data) {
					if (data.result.msg == "OK!") {
						$.messager.show({
							msg : '显示 成功',
							title : '提示',
							showType : 'slide',
							timeout : 3000
						});
						$('#gameList').datagrid('unselectAll');
						$('#gameList').datagrid('reload');
					}
				}
			});
		}
	});
}
// 隐藏
function hideEve() {
    var softId = arguments[0];
    $.messager.confirm('确认', '您确认要隐藏这条记录吗?', function(row) {
        if(row) {
            $.ajax({
                type : "POST",
                url : urlWithParam + "/admin/app/hide.d",
                traditional : true,
                data : {
                    "ids" : softId
                },
                dataType : 'json',
                success : function(data) {
                    if(data.result.msg == "OK!") {
                        $.messager.show({
                            msg : '隐藏 成功',
                            title : '提示',
                            showType : 'slide',
                            timeout : 3000
                        });
                        $('#gameList').datagrid('unselectAll');
                        $('#gameList').datagrid('reload');
                    }
                }
            });
        }
    });
}

// 隐藏
function BatchHideEve() {
    var inputs = $(".datagrid-body tr[class*='datagrid-row-selected']").length;
    var type = "";
    var types = [];
    for(var i = 0; i < inputs; i++) {
        type = parseInt($(".datagrid-body tr[class*='datagrid-row-selected']").eq(i).find("a[name='softIds']").attr("value"));
        types[i] = type;
    }
    $.messager.confirm('确认', '您确认要隐藏这些记录吗?', function(row) {
        if(row) {
            $.ajax({
                type : "POST",
                url : urlWithParam + "/admin/app/hide.d",
                traditional : true,
                data : {
                    "ids" : types
                },
                dataType : 'json',
                success : function(data) {
                    if(data.result.msg == "OK!") {
                        $.messager.show({
                            msg : '隐藏 成功',
                            title : '提示',
                            showType : 'slide',
                            timeout : 3000
                        });
                        $('#gameList').datagrid('unselectAll');
                        $('#gameList').datagrid('reload');
                    }
                }
            });
        }
    });
}

var catalogMap = [];

//加载分类
function loadType() {
    var $keyColumn = $("#keyColumn");
    var $keyword = $("#keyword");
    var $idsBtn = $("#idsBtn");

    $.ajax({
        type : "GET",
        url : urlWithParam + "/admin/catalog/list.json?pid=2",
        dataType : 'json',
        success : function(data) {
            catalogMap = data;
            var num = data.rows.length;
            var ohtml = "";

            for(var i = 0; i < num; i++) {
                ohtml += "<option value=" + data.rows[i].id + ">" + data.rows[i].name + "</option>"
            }
            $keyColumn.append(ohtml);
            $keyColumn.bind({
                change : function() {
                    $keyword.val("");
                    $idsBtn.attr("value", $(this).find("option:selected").attr("value"));
                    SearchEve()
                }
            })
        }
    });
}

//修改分类
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
    for(var i = 0; i < len; i++) {
        softwareArr.push(["<li><a value='", catalogMap.rows[i].id, "'>", catalogMap.rows[i].name, "</a></li>"].join(''));
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

    $IdsMenu_li_a.click(function(e, index) {
        var typeIds = parseInt($(this).attr("value"));
        var inputs = $(".datagrid-body tr[class*='datagrid-row-selected']").length;
        var type = "";
        var types = [];
        for(var i = 0; i < inputs; i++) {
            type = parseInt($(".datagrid-body tr[class*='datagrid-row-selected']").eq(i).find("a[name='softIds']").attr("value"));
            types[i] = type;
        }
        if(inputs !== "") {
            $.ajax({
                type : "POST",
                url : urlWithParam + "/admin/app/edit-catalog.d",
                traditional : true,
                data : {
                    "catalog" : 2,
                    "subCatalog" : typeIds,
                    "ids" : types
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
                        $('#gameList').datagrid('unselectAll');
                        $('#gameList').datagrid('reload');
                    }
                }
            });
        };
        $(this).parent().parent().parent().parent().parent().hide()
        $editIds.hide();
        $(".window-mask").hide();
    })
}

//修改分类
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
    for(var i = 0; i < len; i++) {
        softwareArr.push(["<li><a value='", catalogMap.rows[i].id, "'>", catalogMap.rows[i].name, "</a></li>"].join(''));
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

        if(typeIds !== "") {
            $.ajax({
                type : "POST",
                url : urlWithParam + "/admin/app/edit-catalog.d",
                traditional : true,
                data : {
                    "catalog" : 2,
                    "subCatalog" : typeIds,
                    "ids" : softId
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
                        $('#gameList').datagrid('unselectAll');
                        $('#gameList').datagrid('reload');
                    }
                }
            });
        }
        $(this).parent().parent().parent().parent().parent().hide()
        $editType.hide();
        $(".window-mask").hide();
    })
}