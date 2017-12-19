var urlWithParam = "/sjk-market-admin";

$(document).ready(function() {
    loadGridTable()
    $('#keyword').bind('keydown', function(e) {
        if(e.which == 13) {
            SearchEve();
        }
    });

    $('#delBtn').click(function() {
        BatchDelEve();
    });

    $('#unrecBtn').click(function() {
        BatchUnRecEve();
    });

    $('#recBtn').click(function() {
        BatchRecEve();
    });

    $('#search').click(function() {
        SearchEve();
    });

    $('#addBtn').click(function() {
        AddTageApp()
    });
    UpTageApp()

    var p = $('#softList').datagrid('getPager');
    $(p).pagination({
        onBeforeRefresh : function() {
        }
    });

    loadDicValues('keyDicVale', data_dicValues.rows, data_dic_recommendCountID);
    $("#keyDicVale").bind("change", function() {
        var number = $(this).val();
        $("span[name='setNumber']").text(number);
        $.messager.confirm('确认', '您确认要修改推广位个数 吗?', function(row) {
            if(row) {
                editDicValues(data_dic_recommendCountID, number);
            } else {
                location.reload();
            }
        });
    });
});
// 加载DataGrid控件
function loadGridTable() {
    $('#softList').datagrid({
        loadMsg : '正在加载...',
        iconCls : 'icon-save',
        width : '1200',
        nowrap : false,
        striped : true,
        collapsible : false,
        url : urlWithParam + "/admin/app/rolling/search.json",
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
            field : 'appId',
            title : 'id',
            sortable : true,
            width : 60
        }, {
            field : 'app',
            title : '软件名称',
            sortable : true,
            width : 320,
            formatter : function(value, rec, index) {
                if(rec.app != null) {
                    var url = ""
                    
                    if(rec.app.catalog==100){
                        url = '../admin/marketapp/' + rec.app.id + '.edit.d'
                    }else{
                        url = '../admin/app/' + rec.app.id + '.merge.d'
                    }
                    
                    var name = '<img class="w_20 h_20 f_l mt_4 mr_10" src="' + rec.app.logoUrl + '"/><a name="softIds" class="co_bl f_l" value="' + rec.app.id + '" href='+url+'  target="_blank">' + rec.app.name + '</a>';
                    return name;
                } else {
                    return rec.appid + "已被删除了.";
                }
            }
        }, {
            field : 'ICON',
            title : '原ICON',
            sortable : true,
            width : 100,
            formatter : function(value, rec, index) {
                if(rec.app != null) {
                    var logoUrl = '<img class="w_20 h_20 f_l mt_4 mr_10" src="' + rec.app.logoUrl + '"/>';
                    return logoUrl;
                }
                return "";
            }
        }, {
            field : 'recommend',
            title : '状态',
            sortable : true,
            width : 100,
            formatter : function(value, rec, index) {
                var recommend = "";
                if(value) {
                    recommend = "推广"
                } else {
                    recommend = "普通"
                }
                return recommend;
            }
        }, {
            field : 'open',
            title : '打开',
            sortable : false,
            width : 100,
            formatter : function(value, rec, index) {
                if(rec.app == null) {
                    return "";
                }
                var open = '<a class="co_bl f_l" href="' + rec.app.detailUrl + '"  target="_blank">打开</a>';
                return open;
            }
        }, {
            field : 'lastUpdateTime',
            title : '加入时间',
            width : 100,
            formatter : function(value, rec, index) {
                if(rec.app == null) {
                    return "";
                }
                return rec.app.lastUpdateTime.substring(0, 10);
            }
        }, {
            field : 'marketName',
            title : '来源',
            sortable : true,
            width : 100,
            formatter : function(value, rec, index) {
                if(rec.app == null) {
                    return "";
                }
                return rec.app.marketName;
            }
        }, {
            field : 'operate',
            title : '编辑',
            width : 180,
            formatter : function(value, rec, index) {
                var edit = '<a class="co_bl f_l mr_15" value="' + rec.appId + '" onclick="editLogo(' + rec.appId + ',' + index + ')">编辑</a>';
                var del = '<a class="mr_15 co_bl f_l" onclick="del(' + rec.appId + ',' + index + ')">删除</a>';
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
    var keywords = $.trim($('#keyword').val());var queryParams = $('#softList').datagrid('options').queryParams;

    if(keywords !== "") {
        queryParams.keywords = keywords;
    } else {
        queryParams.keywords = '';
    }

    queryParams.page = 1;

    // 主显示使用
    $('#softList').datagrid('options').pageNumber = 1;
    // page对象 中的pageNumber对象
    var pager = $('#softList').datagrid('getPager');
    $(pager).pagination('options').pageNumber = 1;
    $('#softList').datagrid('reload');
}

// 新增摇一摇
function AddTageApp() {
    var $addTagApp = $("#addTagApp");
    $addTagApp.window({
        title : '新增',
        top : 200,
        width : 400,
        modal : true,
        shadow : false,
        closed : false,
        height : 250
    });
    $addTagApp.show();
}

function UpTageApp() {
    var $addPost = $("#addPost"), $appid = $("#appid"), $catalog = $("#catalog"), appId = "", catalog = "";

    $addPost.bind({
        click : function() {
            appId = $appid.val();
            catalog = $catalog.find('option:selected').val();

            $.ajax({
                type : "POST",
                url : urlWithParam + "/admin/app/rolling/add.d",
                traditional : true,
                data : {
                    "appId" : appId,
                    "catalog" : catalog
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
                        $('#softList').datagrid('unselectAll');
                        $('#softList').datagrid('reload');
                    }
                }
            });
        }
    })
}

// 修改图标
function editLogo() {
    var $editLogo = $("#editLogo"),$LogoClose = $("#LogoClose"), $logoid = $("#logoid"), $addLogo = $("#addLogo"), $formData = $("#formData"), $remoteLogoUrl = $("#remoteLogoUrl"), $logofile = $("#logofile"), remoteLogoUrl = "", logofile = "";
    softId = arguments[0];
    $editLogo.window({
        title : '修改图标',
        top : 200,
        width : 400,
        modal : true,
        shadow : false,
        closed : false,
        height : 250
    });
    $editLogo.show();
    $logoid.val(softId);

    $addLogo.bind({
        click : function() {
            remoteLogoUrl = $remoteLogoUrl.val();
            logofile = $logofile.val();
            if(remoteLogoUrl || logofile) {
                if(!remoteLogoUrl) {
                    $remoteLogoUrl.removeAttr("name")
                }
                if(!logofile) {
                    $logofile.removeAttr("name")
                }

                $formData.submit();
                $('#softList').datagrid('reload');

                $(this).parent().parent().parent().parent().parent().parent().parent().parent().hide();
                $editLogo.hide();
                $(".window-mask").hide();
            }
        }
    })

    $LogoClose.bind({
        click : function() {
            $(this).parent().parent().parent().parent().parent().parent().parent().parent().hide()
            $editLogo.hide();
            $(".window-mask").hide();
        }
    })
}

// 推荐设置
function BatchUnRecEve() {
    var inputs = $(".datagrid-body tr[class*='datagrid-row-selected']").length;
    var type = "";
    var types = [];
    for(var i = 0; i < inputs; i++) {
        type = parseInt($(".datagrid-body tr[class*='datagrid-row-selected']").eq(i).find("a[name='softIds']").attr("value"));
        types[i] = type;
    }
    $.messager.confirm('确认', '您确认要设为普通吗?', function(row) {
        if(row) {
            $.ajax({
                type : "POST",
                url : urlWithParam + "/admin/app/rolling/set-unrecommand.d",
                traditional : true,
                data : {
                    "id" : types
                },
                dataType : 'json',
                success : function(data) {
                    if(data.result.msg == "OK!") {
                        $.messager.show({
                            msg : '设置 成功',
                            title : '提示',
                            showType : 'slide',
                            timeout : 3000
                        });
                        $('#softList').datagrid('unselectAll');
                        $('#softList').datagrid('reload');
                    }
                }
            });
        }
    });
}

function BatchRecEve() {
    var inputs = $(".datagrid-body tr[class*='datagrid-row-selected']").length;
    var type = "";
    var types = [];
    for(var i = 0; i < inputs; i++) {
        type = parseInt($(".datagrid-body tr[class*='datagrid-row-selected']").eq(i).find("a[name='softIds']").attr("value"));
        types[i] = type;
    }
    $.messager.confirm('确认', '您确认要设为推荐吗?', function(row) {
        if(row) {
            $.ajax({
                type : "POST",
                url : urlWithParam + "/admin/app/rolling/set-recommand.d",
                traditional : true,
                data : {
                    "id" : types
                },
                dataType : 'json',
                success : function(data) {
                    if(data.result.msg == "OK!") {
                        $.messager.show({
                            msg : '设置 成功',
                            title : '提示',
                            showType : 'slide',
                            timeout : 3000
                        });
                        $('#softList').datagrid('unselectAll');
                        $('#softList').datagrid('reload');
                    }
                }
            });
        }
    });
}

// 删除
function del() {
    var softId = arguments[0];
    $.messager.confirm('确认', '您确认要彻底删除这条记录吗?', function(row) {
        if(row) {
            var selectedRow = $('#softList').datagrid('getSelected');
            $.ajax({
                type : "POST",
                url : urlWithParam + "/admin/app/rolling/del.d",
                data : {
                    "id" : softId
                },
                success : function() {
                    $.messager.show({
                        msg : '删除 成功',
                        title : '提示',
                        showType : 'slide',
                        timeout : 3000
                    });
                    $('#softList').datagrid('unselectAll');
                    $('#softList').datagrid('reload');
                }
            });
        }
    })
}

function BatchDelEve() {
    var inputs = $(".datagrid-body tr[class*='datagrid-row-selected']").length;
    var type = "";
    var types = [];
    for(var i = 0; i < inputs; i++) {
        type = parseInt($(".datagrid-body tr[class*='datagrid-row-selected']").eq(i).find("a[name='softIds']").attr("value"));
        types[i] = type;
    }
    $.messager.confirm('确认', '您确认要删除这些记录吗?', function(row) {
        if(row) {
            $.ajax({
                type : "POST",
                url : urlWithParam + "/admin/app/rolling/del.d",
                traditional : true,
                data : {
                    "id" : types
                },
                dataType : 'json',
                success : function(data) {
                    if(data.result.msg == "OK!") {
                        $.messager.show({
                            msg : '删除 成功',
                            title : '提示',
                            showType : 'slide',
                            timeout : 3000
                        });
                        $('#softList').datagrid('unselectAll');
                        $('#softList').datagrid('reload');
                    }
                }
            });
        }
    });
}

function loadDicValues(sltId, dataEx, id) {
    $.ajax({
        type : "POST",
        url : urlWithParam + "/admin/sys/dict.d",
        traditional : true,
        data : {
            "id" : data_dic_recommendCountID
        },
        dataType : 'json',
        success : function(data) {
            if(data != null) {
                var intValue = data.result.data.intValue;
                $("span[name='setNumber']").text(intValue);
                loadSelet(sltId, dataEx, intValue);
            } else {
                loadSelet(sltId, dataEx, intValue);
                $("span[name='setNumber']").text("0");
            }
        }
    });
}

function editDicValues(id, value) {
    $.ajax({
        type : "POST",
        url : urlWithParam + "/admin/sys/dic-value-save.d",
        traditional : true,
        data : {
            "id" : id,
            "intValue" : value
        },
        dataType : 'json',
        success : function(data) {
            if(data.result.msg == "OK!") {
                $.messager.show({
                    msg : '保存成功！',
                    title : '提示',
                    showType : 'slide',
                    timeout : 3000
                });
            } else {
                $.messager.show({
                    msg : '保存失败！',
                    title : '提示',
                    showType : 'slide',
                    timeout : 3000
                });
            }
        }
    });
}