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
    $('#smsBtn').click(function() {
        BatchSmsToUser()
    });
    loadGridTable();
    MonAll();
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
        width : '1500',
        nowrap : false,
        striped : true,
        collapsible : false,
        url : urlWithParam + "/admin/monchannel/search.json",
        pageSize : 20,
        sortOrder : 'desc',
        idField : "autoCover",
        columns : [[{
            field : 'ck',
            checkbox : true,
            width : 50
        }, {
            field : 'appName',
            title : '应用名称',
            sortable : true,
            width : 150,
            formatter : function(value, rec, index) {
                return '<span title="' + value + '">' + value + ' </span>'
            }
        }, {
            field : 'devName',
            title : '开发者',
            sortable : true,
            width : 100,
            formatter : function(value, rec, index) {
                return '<span title="' + value + '">' + value + ' </span>'
            }
        }, {
            field : 'marketName',
            title : '市场名称',
            sortable : true,
            width : 200,
            formatter : function(value, rec, index) {
                var html = value + '<br/>[版本:' + rec.version + ' 版本Code:' + rec.versionCode + ']';
                return html;
            }
        }, {
            field : 'coverMarketName',
            title : '被覆盖的市场',
            sortable : true,
            width : 200,
            formatter : function(value, rec, index) {
                var html = '';
                if(value != '' && value != null && value && value != undefined && value != 'undefined') {
                    html = value + '<br/>[版本:' + rec.coverVerson + ' 版本Code:' + rec.coverVersionCode + ']';
                }
                return html;
            }
        }, {
            field : 'autoCover',
            title : '是否覆盖',
            sortable : true,
            width : 60,
            formatter : function(value, rec, index) {
                if(value) {
                    return "<span class='co_red'>是</span>"
                } else {
                    return "否"
                }
            }
        }, {
            field : 'lastFetchTime',
            title : '覆盖时间',
            sortable : true,
            width : 80,
            formatter : function(value, rec, index) {
                if(value != '' && value != null && value && value != undefined) {
                    return value.substring(0, 10);
                } else {
                    return '';
                }

            }
        }, {
            field : 'userName',
            title : '联系人',
            sortable : true,
            width : 80
        }, {
            field : 'phone',
            title : '手机',
            sortable : true,
            width : 80
        }, {
            field : 'acceptSms',
            title : '是否发短信',
            sortable : true,
            width : 80,
            formatter : function(value, rec, index) {
                if(value) {
                    return "是"
                } else {
                    return "否"
                }
            }
        }, {
            field : 'sMailStatus',
            title : '发送状态',
            sortable : true,
            width : 60,
            formatter : function(value, rec, index) {
                var html = "";
                if(value == 0) {
                    return '发送成功';
                } else if(value == 1) {
                    return '发送成功';
                } else if(value == -1) {
                    return '无效用户名';
                } else if(value == -2) {
                    return '加密认证错误';
                } else if(value == -3) {
                    return '无效ip';
                } else if(value == -4) {
                    return '达到发送上限';
                } else if(value == -5) {
                    return '短信平台故障';
                } else if(value == -6) {
                    return '非法手机号码';
                } else if(value == -10) {
                    return '短信平台故障';
                } else if(value == -11) {
                    return '系统错误';
                } else if(value == 2) {
                    return '';
                } else {
                    return '';
                }
            }
        }, {
            field : 'optTime',
            title : '短信发送时间',
            sortable : true,
            width : 110,
            formatter : function(value, rec, index) {
                if(value != '' && value != null && value && value != undefined) {
                    return value.substring(0, 15);
                } else {
                    return '';
                }
            }
        }, {
            field : 'operate',
            title : '操作',
            width : 120,
            formatter : function(value, rec, index) {
                var sms = '<a class="mr_10 co_bl f_l" name="softIds" data-id="' + rec.id + '" onclick="smsToUser(' + rec.id + ')">发送短信</a>';
                var del = '<a class="co_bl f_l" onclick="Delete(' + rec.id + ')">删除</a>';
                var edit = '<a class="mr_10 co_bl f_l" onclick="EditMon(\'' + rec.id + '\',\'' + rec.appName + '\',\'' + rec.devName + '\',\'' + rec.userName + '\',\'' + rec.marketName + '\',\'' + rec.apkId + '\',\'' + rec.phone + '\',\'' + rec.acceptSms + '\',\'' + rec.email + '\',\'' + rec.acceptMail + '\',\'' + rec.status + '\',\'' + rec.autoCover + '\')">修改</a>';
                return edit + sms + del;
            }
        }]],
        pagination : true,
        pagePosition : 'both', // 上下显示分页效果
        pagerFixedTop : true, // 滚动固定上栏分页
        rownumbers : false,
        onLoadError : function() {
            var params = arguments;
            if(params[0].status == 403) {
                alert("请登录.");
                window.parent.location.href = "../login.html";
            }
        }
    });
}

// 搜索
function SearchEve() {
    var keywords = $.trim($('#keyword').val());
    var queryParams = $('#dataList').datagrid('options').queryParams;

    if(keywords !== "") {
        queryParams.keyword = keywords;
    } else {
        queryParams.keyword = '';
    }
    queryParams.page = 1;
    $('#dataList').datagrid('options').pageNumber = 1;
    var pager = $('#dataList').datagrid('getPager');
    $(pager).pagination('options').pageNumber = 1;
    $('#dataList').datagrid('reload');
}

// 添加
function AddMon() {
    var $editMon = $("#editMon"), $monPost = $("#monPost");

    $editMon.window({
        title : '添加',
        top : 100,
        width : 550,
        height : 550,
        modal : true,
        shadow : false,
        closed : false
    });

    $editMon.show();
    $monPost.unbind('click').bind('click', function() {
        var appName = $editMon.find("input[name=appName]").val(), devName = $editMon.find("input[name=devName]").val(), marketName = $editMon.find("input[name=marketName]").val(), apkId = $editMon.find("input[name=apkId]").val(), userName = $editMon.find("input[name=userName]").val(), phone = $editMon.find("input[name=phone]").val(), acceptSms = $editMon.find("input[name=acceptSms]:checked").val(), email = $editMon.find("input[name=email]").val(), acceptMail = $editMon.find("input[name=acceptMail]:checked").val(), status = $editMon.find("input[name=status]:checked").val(), autoCover = $editMon.find("input[name=autoCover]:checked").val();

        $.ajax({
            url : '/sjk-market-admin/admin/monchannel/save.d',
            type : 'POST',
            data : {
                "id" : 0,
                "appName" : appName,
                "devName" : devName,
                "userName" : userName,
                "marketName" : marketName,
                "apkId" : apkId,
                "phone" : phone,
                "acceptSms" : acceptSms,
                "email" : email,
                "acceptMail" : acceptMail,
                "status" : status,
                "autoCover" : autoCover
            },
            success : function() {
                $.messager.show({
                    msg : '添加成功',
                    title : '提示',
                    showType : 'slide',
                    timeout : 3000
                });
                $('#dataList').datagrid('reload');

                $(this).parent().parent().parent().parent().parent().parent().parent().parent().hide()
                $editMon.window('close');
            }
        })
    })
}

// 修改
function EditMon() {
    var $editMon = $("#editMon"), $monPost = $("#monPost"), id = arguments[0], appName = arguments[1], devName = arguments[2], userName = arguments[3], marketName = arguments[4], apkId = arguments[5], phone = arguments[6], acceptSms = arguments[7], email = arguments[8], acceptMail = arguments[9], status = arguments[10], autoCover = arguments[11];

    $editMon.window({
        title : '修改',
        top : 100,
        width : 550,
        height : 550,
        modal : true,
        shadow : false,
        closed : false
    });

    if(userName == "null") {
        userName = ""
    }
    ;
    if(phone == "null") {
        phone = ""
    }
    ;
    if(email == "null") {
        email = ""
    }
    ;

    $editMon.find("input[name=id]").val(id);
    $editMon.find("input[name=appName]").val(appName);
    $editMon.find("input[name=devName]").val(devName);
    $editMon.find("input[name=marketName]").val(marketName);
    $editMon.find("input[name=apkId]").val(apkId);
    $editMon.find("input[name=userName]").val(userName);
    $editMon.find("input[name=phone]").val(phone);
    $editMon.find("input[name=acceptSms][value=" + acceptSms + "]").attr('checked', 'checked');
    $editMon.find("input[name=email]").val(email);
    $editMon.find("input[name=acceptMail][value=" + acceptMail + "]").attr('checked', 'checked');
    $editMon.find("input[name=status][value=" + status + "]").attr('checked', 'checked');
    $editMon.find("input[name=autoCover][value=" + autoCover + "]").attr('checked', 'checked');

    $editMon.show();
    $monPost.unbind('click').bind('click', function() {
        id = $editMon.find("input[name=id]").val();
        appName = $editMon.find("input[name=appName]").val();
        devName = $editMon.find("input[name=devName]").val();
        marketName = $editMon.find("input[name=marketName]").val();
        apkId = $editMon.find("input[name=apkId]").val(); userName = $editMon.find("input[name=userName]").val(), phone = $editMon.find("input[name=phone]").val();
        acceptSms = $editMon.find("input[name=acceptSms]:checked").val();
        email = $editMon.find("input[name=email]").val();
        acceptMail = $editMon.find("input[name=acceptMail]:checked").val();
        status = $editMon.find("input[name=status]:checked").val();
        autoCover = $editMon.find("input[name=autoCover]:checked").val();

        $.ajax({
            url : '/sjk-market-admin/admin/monchannel/edit.d',
            type : 'POST',
            data : {
                "id" : id,
                "appName" : appName,
                "devName" : devName,
                "userName" : userName,
                "marketName" : marketName,
                "apkId" : apkId,
                "phone" : phone,
                "acceptSms" : acceptSms,
                "email" : email,
                "acceptMail" : acceptMail,
                "status" : status,
                "autoCover" : autoCover
            },
            success : function() {
                $.messager.show({
                    msg : '修改成功',
                    title : '提示',
                    showType : 'slide',
                    timeout : 3000
                });
                $('#dataList').datagrid('reload');

                $(this).parent().parent().parent().parent().parent().parent().parent().parent().hide()
                $editMon.window('close');
            }
        })
    })
}

function MonAll() {
    var $monallBtn = $("#monallBtn");

    $monallBtn.bind({
        click : function() {
            $.ajax({
                url : '/sjk-market-admin/admin/monchannel/mon-all.d',
                type : 'GET',
                success : function() {
                    $.messager.show({
                        msg : '检测完成',
                        title : '提示',
                        showType : 'slide',
                        timeout : 3000
                    });
                    $('#dataList').datagrid('reload');
                }
            })
        }
    })
}

// 删除
function Delete() {
    var id = arguments[0];
    $.messager.confirm('确认', '您确认要删除这条记录吗?', function(row) {
        if(row) {
            $.ajax({
                type : "POST",
                url : urlWithParam + "/admin/monchannel/del.d",
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
                        $(".window-mask").hide();
                    }
                }
            });
        }
    });
}

// 发送短信
function smsToUser() {
    var id = arguments[0];
    $.messager.confirm('确认', '您确认要发送短信?', function(row) {
        if(row) {
            $.ajax({
                type : "POST",
                url : urlWithParam + "/admin/monchannel/sms.d",
                traditional : true,
                data : {
                    "ids" : id
                },
                dataType : 'json',
                success : function(data) {
                    if(data.result.msg == "OK!") {
                        $.messager.show({
                            msg : '发送短信成功',
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

function BatchSmsToUser() {
    var inputs = $(".datagrid-body tr[class*='datagrid-row-selected']").length, type = "", types = [];

    if(inputs == 0) {
        return false
    }

    for(var i = 0; i < inputs; i++) {
        type = parseInt($(".datagrid-body tr[class*='datagrid-row-selected']").eq(i).find("a[name='softIds']").data("id"));
        types[i] = type;
    }
    $.messager.confirm('确认', '您确认要发送短信吗?', function(row) {
        if(row) {
            $.ajax({
                type : "POST",
                url : urlWithParam + "/admin/monchannel/sms.d",
                traditional : true,
                data : {
                    "ids" : types
                },
                dataType : 'json',
                success : function(data) {
                    if(data.result.msg == "OK!") {
                        $.messager.show({
                            msg : '发送短信成功',
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