var urlWithParam = "/sjk-market-admin";

$(document).ready(function() {
    $('#keyword').bind('keydown', function(e) {
        if(e.which == 13) {
            SearchEve();
        }
    });
    $('#search').click(function() {
        SearchEve();
    });
    $('#stateBtn_0').click(function() {
        BatchState(0)
    });
    $('#stateBtn_1').click(function() {
        BatchState(1)
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
    $('#dataList').datagrid({
        loadMsg : '正在加载...',
        iconCls : 'icon-save',
        width : '1200',
        nowrap : false,
        striped : true,
        collapsible : false,
        url : urlWithParam + "/admin/top/seach.d",
        pageSize : 20,
        sortOrder : 'desc',
        idField : "id",
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
            field : 'appId',
            title : 'appId',
            sortable : true,
            width : 80
        }, {
            field : 'name',
            title : '旧名称',
            sortable : true,
            width : 350,
            formatter : function(value, rec, index) {
                var html = '<a class="mr_10 co_bl f_l" name="softIds" data-id="' + rec.id + '" onclick="EditTop(\'' + rec.id + '\',\'' + rec.appId + '\',\'' + rec.name + '\',\'' + rec.pkname + '\',\'' + rec.newName + '\',\'' + rec.state + '\')">' + value + '</a>';
                return html;
            }
        }, {
            field : 'newName',
            title : '新名称',
            sortable : true,
            width : 200
        }, {
            field : 'state',
            title : '状态',
            sortable : true,
            width : 80,
            formatter : function(value, rec, index) {
                if(value == 0) {
                    return "无更新";
                } else {
                    return "有更新";
                }
            }
        }, {
            field : 'pkname',
            title : 'pkname',
            sortable : true,
            width : 250
        }, {
            field : 'lastUpdateTime',
            title : '修改时间',
            sortable : true,
            width : 90,
            formatter : function(value, rec, index) {
                return value.substring(0, 10);
            }
        }, {
            field : 'operate',
            title : '操作',
            width : 100,
            formatter : function(value, rec, index) {
                var del = '<a class="mr_15 co_bl f_l" onclick="Delete(' + rec.id + ')">删除</a>';
                var edit = '<a class="mr_10 co_bl f_l" onclick="EditTop(\'' + rec.id + '\',\'' + rec.appId + '\',\'' + rec.name + '\',\'' + rec.pkname + '\',\'' + rec.newName + '\',\'' + rec.state + '\')">修改</a>';
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
        rowStyler : function(index, rec) {
            if(!rec.name) {
                return 'display:none';
            }
            if(rec.state == 1) {
                return 'background-color:red';
            }
        }
    });
}

// 搜索
function SearchEve() {
    var keywords = $.trim($('#keyword').val());
    var id = $.trim($('#keyId').val());
    var queryParams = $('#dataList').datagrid('options').queryParams;

    if(keywords !== "") {
        queryParams.keywords = keywords;
    } else {
        queryParams.keywords = '';
    }
    if(id !== "") {
        queryParams.id = id;
    } else {
        queryParams.id = '';
    }
    queryParams.page = 1;

    $('#dataList').datagrid('options').pageNumber = 1;
    var pager = $('#dataList').datagrid('getPager');
    $(pager).pagination('options').pageNumber = 1;
    $('#dataList').datagrid('reload');
}

//修改
function EditTop() {
    var $editTop = $("#editTop"), $topPost = $("#topPost"), id = arguments[0], appId = arguments[1], name = arguments[2], pkname = arguments[3], newName = arguments[4], state = arguments[5];

    $editTop.window({
        title : '修改',
        top : 200,
        width : 550,
        height : 300,
        modal : true,
        shadow : false,
        closed : false
    });

    $editTop.find("input[name=id]").val(id);
    $editTop.find("input[name=appId]").val(appId);
    $editTop.find("input[name=name]").val(name);
    $editTop.find("input[name=pkname]").val(pkname);
    $editTop.find("input[name=newName]").val(newName);
    $editTop.find("input[name=state][value=" + state + "]").attr('checked', 'checked');

    $editTop.show();
    $topPost.unbind('click').bind('click', function() {
        id = $editTop.find("input[name=id]").val();
        appId = $editTop.find("input[name=appId]").val();
        name = $editTop.find("input[name=name]").val();
        pkname = $editTop.find("input[name=pkname]").val();
        newName = $editTop.find("input[name=newName]").val();
        state = $editTop.find("input[name=state]:checked").val();
        lastUpdateTime = fomateDate(new Date(), 'YYYY-MM-DD hh:mm:ss')

        $.ajax({
            url : '/sjk-market-admin/admin/top/save.d',
            type : 'POST',
            data : {
                "id" : id,
                "appId" : appId,
                "name" : name,
                "pkname" : pkname,
                "newName" : newName,
                "state" : state,
                "lastUpdateTime" : lastUpdateTime
            },
            success : function() {
                $.messager.show({
                    msg : '修改成功',
                    title : '提示',
                    showType : 'slide',
                    timeout : 3000
                });
                $('#dataList').datagrid('unselectAll');
                $('#dataList').datagrid('reload');

                $(this).parent().parent().parent().parent().parent().parent().parent().parent().hide()
                $editTop.window('close');
            }
        })
    })
}

// 删除
function Delete() {
    var id = arguments[0];
    $.messager.confirm('确认', '您确认要删除这条记录吗?', function(row) {
        if(row) {
            $.ajax({
                type : "POST",
                url : urlWithParam + "/admin/top/" + id + ".del.d",
                traditional : true,
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
                        $(".window-mask").hide();
                    }
                }
            });
        }
    });
}

function BatchState(state) {
    var inputs = $(".datagrid-body tr[class*='datagrid-row-selected']").length, type = "", types = [];

    if(inputs == 0) {
        return false
    }

    for(var i = 0; i < inputs; i++) {
        type = parseInt($(".datagrid-body tr[class*='datagrid-row-selected']").eq(i).find("a[name='softIds']").data("id"));
        types[i] = type;
    }
    $.messager.confirm('确认', '您确认要修改状态吗?', function(row) {
        if(row) {
            $.ajax({
                type : "POST",
                url : urlWithParam + "/admin/top/change-state.d",
                traditional : true,
                data : {
                    "ids" : types,
                    "state" : state
                },
                dataType : 'json',
                success : function(data) {
                    if(data.result.msg == "OK!") {
                        $.messager.show({
                            msg : '修改状态成功',
                            title : '提示',
                            showType : 'slide',
                            timeout : 3000
                        });
                        $('#dataList').datagrid('unselectAll');
                        $('#dataList').datagrid('reload');
                        $(".window-mask").hide();
                    }
                }
            });
        }
    });
}

function fomateDate(oDate, sFomate, bZone) {
    sFomate = sFomate.replace("YYYY", oDate.getFullYear());
    sFomate = sFomate.replace("YY", String(oDate.getFullYear()).substr(2))
    sFomate = sFomate.replace("MM", oDate.getMonth() + 1)
    sFomate = sFomate.replace("DD", oDate.getDate());
    sFomate = sFomate.replace("hh", oDate.getHours());
    sFomate = sFomate.replace("mm", oDate.getMinutes());
    sFomate = sFomate.replace("ss", oDate.getSeconds());
    if(bZone)
        sFomate = sFomate.replace(/\b(\d)\b/g, '0$1');
    return sFomate;
}