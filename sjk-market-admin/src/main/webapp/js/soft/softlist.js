var urlWithParam = "/sjk-market-admin";

$(document).ready(function() {
    loadType();
    LoadTag();
    LoadTopics();
    LoadMoTopics();
    LoadMoTag();

    $('#keyword').bind('keydown', function(e) {
        if(e.which == 13) {
            SearchEve();
        }
    });

    $('#search').click(function() {
        SearchEve();
    });
    
    //批量隐藏
    $('#hideBtn,#hideBtnBom').click(function() {
        BatchHideEve()
    });
    
    //批量修改分类
    $('#idsBtn,#idsBtnBom').click(function() {
        editIdsType()
    });

    //批量修改标签
    $('#tagsBtn,#tagsBtnBom').click(function() {
        editIdsTag()
    });
    
    //批量修改专题
    $('#TopicssBtn,#TopicssBtnBom').click(function() {
        editIdsTopics()
    });
    
    //批量修改手机专题
    $('#MoTopicssBtn,#MoTopicssBtnBom').click(function() {
        editMoTopics()
    });
    
    //批量修改手机标签
    $('#MoTagsBtn,#MoTagsBtnBom').click(function() {
        editMoTag()
    });
    
    //批量修改审核状态
    $('#MoStatusBtn,#MoStatusBtnBom').click(function() {
        editMoStatus()
    });

    loadGridTable();
    var p = $('#softList').datagrid('getPager');
    $(p).pagination({
        onBeforeRefresh : function() {
        }
    });

    setALLBtnDisabled(true);
});
function setALLBtnDisabled(disabled) {
    setBtnDisabled("idsBtn", disabled);
    setBtnDisabled("hideBtn", disabled);
    setBtnDisabled("tagsBtn", disabled);
    setBtnDisabled("TopicssBtn", disabled);
    setBtnDisabled("idsBtnBom", disabled);
    setBtnDisabled("hideBtnBom", disabled);
    setBtnDisabled("tagsBtnBom", disabled);
    setBtnDisabled("TopicssBtnBom", disabled);
    setBtnDisabled("MoTopicssBtn", disabled);
    setBtnDisabled("MoTagsBtn", disabled);
    setBtnDisabled("MoTopicssBtnBom", disabled);
    setBtnDisabled("MoTagsBtnBom", disabled);
    setBtnDisabled("MoStatusBtn", disabled);
    setBtnDisabled("MoStatusBtnBom", disabled);
}

function setBtnDisabled(btnId, disabled) {
    if(disabled) {
        $("#" + btnId).attr("style", "color:#999").attr("disabled", "disabled");
    } else {
        $("#" + btnId).removeAttr("style").removeAttr("disabled");
    }
}

// 加载DataGrid控件
function loadGridTable() {
    $('#softList').datagrid({
        iconCls : 'icon-save',
        width : '1720',
        nowrap : false,
        striped : true,
        collapsible : false,
        url : urlWithParam + "/admin/app/search.json?catalog=1",
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
        onLoadSuccess : function(data) {
            $("input[name='ck']").click(function() {
                var length = $("input[name='ck']:checked").length;
                if(length == null || length == "" || length == undefined || length == 0) {
                    setALLBtnDisabled(true);
                }
            });
            $(".datagrid-header-row input[type='checkbox']").click(function() {
                if($(this).attr("checked") == 'checked') {
                    setALLBtnDisabled(false);
                } else {
                    setALLBtnDisabled(true);
                }
            });
            setBtnDisabled("search", false);
        },
        onClickRow : function(index, data) {
            var length = $(".datagrid-body tr[class*='datagrid-row-selected']").length;
            if(length == null || length == "" || length == undefined || length == 0) {
                setALLBtnDisabled(true);
            }
        },
        onSelect : function(index, data) {
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
            width : 60
        }, {
            field : 'name',
            title : '软件名称',
            sortable : true,
            width : 280,
            formatter : function(value, rec, index) {
                var ids = '<img class="w_20 h_20 f_l mt_4 mr_10" src="' + rec.logoUrl + '"/><a name="softIds" class="co_bl f_l" value="' + rec.id + '" href="../admin/app/' + rec.id + '.merge.d"  target="_blank">' + value + '</a>';
                return ids;
            }
        }, {
            field : 'version',
            title : '版本',
            sortable : true,
            width : 90
        }, {
            field : 'size',
            title : '大小',
            sortable : true,
            width : 90
        }, {
            field : 'lastUpdateTime',
            title : '最后修改时间',
            sortable : true,
            width : 100,
            formatter : function(value, entity) {
                return value.substring(0, 10);
            }
        }, {
            field : 'downloads',
            title : '下载量',
            sortable : true,
            width : 80
        }, {
            field : 'realDownload',
            title : '实际下载量',
            sortable : true,
            width : 80
        }, {
            field : 'deltaDownload',
            title : '下载修正增减',
            sortable : true,
            width : 80,
            formatter : function(value, rec, index) {
                var deltaDownload = '<a class="mr_15 co_bl f_l" onclick="editDelta(' + rec.id + ')">' + value + '</a>';

                return deltaDownload;
            }
        }, {
            field : 'subCatalog',
            title : '分类',
            sortable : false,
            width : 120,
            formatter : function(value, rec, index) {
                var audit = "";
                var len = catalogMap.rows.length
                for(var i = 0; i < len; i++) {
                    if(catalogMap.rows[i].id == rec.subCatalog) {
                        var typeName = catalogMap.rows[i].name
                    }
                }

                var edit = '<a class="mr_10 co_bl f_l" onclick="editType(' + rec.id + ',' + rec.subCatalog + ')">' + typeName + '</a>';

                if(rec.auditCatalog == true) {
                    audit = '<span class="f_l co_green">已修改</span>'
                }
                return edit + audit;
            }
        }, {
            field : 'keywords',
            title : '关键字',
            sortable : true,
            width : 40,
            formatter : function(value, rec, index) {
                return '<a  class="co_bl" onclick="editKeywords(\'' + value + '\',\'' + rec.id + '\')">编辑<a/>';
            }
        }, {
            field : 'auditStatus',
            title : '审核状态',
            sortable : false,
            width : 60,
            formatter : function(value, rec, index) {
                if(rec.auditStatus == 1) {
                    return '<span class="f_l co_green">已审核</span>';
                } else if(rec.auditStatus == 2) {
                    return '<span class="f_l co_bl">免审核</span>';
                } else {
                    return '<span class="f_l">未审核</span>';
                }
            }
        }, {
            field : 'official',
            title : '是否官网',
            sortable : false,
            width : 60,
            formatter : function(value, rec, index) {
                if(rec.signatureSha1 != "" && rec.signatureSha1 == rec.officialSigSha1) {
                    return "官方"
                } else {
                    return "非官方"
                }
            }
        }, {
            field : 'topics',
            title : '专题管理',
            width : 80,
            formatter : function(value, rec, index) {
                var topics = '<a class="mr_15 co_bl f_l" onclick="editTopics(' + rec.id + ',' + index + ')">专题管理</a>';

                return topics;
            }
        }, {
            field : 'tag',
            title : '标签',
            width : 80,
            formatter : function(value, rec, index) {
                var tag = '<a class="mr_15 co_bl f_l" onclick="editTag(' + rec.id + ',' + index + ')">标签管理</a>';

                return tag;
            }
        }, {
            field : 'autoCover',
            title : '自动覆盖',
            sortable : true,
            width : 80,
            formatter : function(value, rec, index) {
                if(rec.autoCover == true) {
                    return "<span  class=\"co_red\">是</span>";
                } else {
                    return "<span class=\"co_dg\">否</span>";
                }
            }
        }, {
            field : 'marketName',
            title : '来源',
            sortable : true,
            width : 80
        }, {
            field : 'operate',
            title : '操作',
            width : 180,
            formatter : function(value, rec, index) {
                var edit = '<a name="softIds" class="co_bl f_l mr_15" value="' + rec.id + '" href="../admin/app/' + rec.id + '.merge.d"  target="_blank">编辑</a>';
                var del = '<a class="mr_15 co_bl f_l" onclick="del(' + rec.id + ',' + index + ')">删除</a>';
                var auhide = "";
                var hide = rec.hidden == true ? '<a class="mr_10 co_green f_l" onclick="showEve(' + rec.id + ',' + index + ')">显示</a>' : '<a class="mr_10 co_degr f_l" onclick="hideEve(' + rec.id + ',' + index + ')">隐藏</a>';
                if(rec.hidden == true) {
                    auhide = '<span class="f_l co_dg">已隐藏</span>'
                }
                var viewmarket = '<a name="softIds" class="co_bl f_l" value="' + rec.id + '" href="' + rec.detailUrl + '"  target="_blank">市场预览</a>'
                return del + hide + auhide + viewmarket;
            }
        }]],
        pagination : true,
        pagePosition:'both',//上下显示分页效果
		pagerFixedTop:true,//滚动固定上栏分页
        rownumbers : false
    });
}

function strToDate(str) {
    var arys = new Array();
    arys = str.split('-');
    // 1月份是用0来表示的，所以2002年10月12日，应表示为2002-9-12
    var newDate = new Date(arys[0], arys[1] - 1, arys[2]);
    return newDate;

}

// 搜索
function SearchEve() {
    setBtnDisabled("search", true);
    var keywords = $.trim($('#keyword').val());
    var subCatalog = $('#keyColumn option:selected').val();
    var statusColumn = $('#statusColumn option:selected').val();
    var official = $('#official option:selected').val();
    var queryParams = $('#softList').datagrid('options').queryParams;
    var id = $.trim($('#keyId').val());
    var startDate = $('#startDate').next().find('input.combo-value').attr("value");
    var endDate = $('#endDate').next().find('input.combo-value').attr("value");
    if(id !== "") {
        if(isNaN(id)) {
            alert("Id必需为数字");
        } else {
            queryParams.id = id;
        }
    } else {
        queryParams.id = '';
    }
    if(keywords !== "") {
        queryParams.keywords = keywords;
    } else {
        queryParams.keywords = '';
    }
    queryParams.catalog = 1;

    if(subCatalog == null) {
        queryParams.subCatalog = '';
    } else {
        queryParams.subCatalog = subCatalog;
    }

    if(startDate != null && startDate != '' && endDate != null && endDate != '') {
        queryParams.startdatestr = startDate;
        queryParams.enddatestr = endDate;
    }

    if(statusColumn == null || statusColumn == '') {
        queryParams.audit = '';
    } else {
        queryParams.audit = statusColumn;
    }

    if(official == null || official == '') {
        queryParams.official = '';
    } else {
        queryParams.official = official;
    }

    queryParams.page = 1;

    // 主显示使用
    $('#softList').datagrid('options').pageNumber = 1;
    // page对象 中的pageNumber对象
    var pager = $('#softList').datagrid('getPager');
    $(pager).pagination('options').pageNumber = 1;
    $('#softList').datagrid('reload');
}

//显示
function showEve() {
    var softId = arguments[0];
    $.messager.confirm('确认', '您确认要显示这条记录吗?', function(row) {
        if(row) {
            $.ajax({
                type : "POST",
                url : urlWithParam + "/admin/app/show.d",
                traditional : true,
                data : {
                    "ids" : softId
                },
                dataType : 'json',
                success : function(data) {
                    if(data.result.code == 0 || data.result.code == "0") {
                        $.messager.show({
                            msg : data.result.msg,
                            title : '提示',
                            showType : 'slide',
                            timeout : 5000
                        });
                        $('#softList').datagrid('unselectAll');
                        $('#softList').datagrid('reload');
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
    for(var i = 0; i < inputs; i++) {
        type = parseInt($(".datagrid-body tr[class*='datagrid-row-selected']").eq(i).find("a[name='softIds']").attr("value"));
        types[i] = type;
    }
    $.messager.confirm('确认', '您确认要显示这些记录吗?', function(row) {
        if(row) {
            $.ajax({
                type : "POST",
                url : urlWithParam + "/admin/app/show.d",
                traditional : true,
                data : {
                    "ids" : types
                },
                dataType : 'json',
                success : function(data) {
                    if(data.result.msg == "OK!") {
                        $.messager.show({
                            msg : '显示 成功',
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
                        $('#softList').datagrid('unselectAll');
                        $('#softList').datagrid('reload');
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
                        $('#softList').datagrid('unselectAll');
                        $('#softList').datagrid('reload');
                    }
                }
            });
        }
    });
}

var catalogMap = [], tagsMap = [], topicsMap = [], moTopicsMap = [], moTagMap = [];

//加载分类
function loadType() {
    var $keyColumn = $("#keyColumn");
    var $keyword = $("#keyword");
    var $idsBtn = $("#idsBtn");

    $.ajax({
        type : "GET",
        url : urlWithParam + "/admin/catalog/list.json?pid=1",
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

//加载标签
function LoadTag() {
    $.ajax({
        type : "POST",
        url : "../admin/tag/normaltag-list.json",
        traditional : true,
        dataType : 'json',
        success : function(data) {
            var len = data.rows.length

            for(var i = 0; i < len; i++) {
                if(data.rows[i].catalog == 1) {
                    tagsMap.push(data.rows[i])
                }
            }
        }
    });
}

//加载专题数据
function LoadTopics() {
    $.ajax({
        type : "POST",
        url : "../admin/tag/topic-list.json",
        traditional : true,
        dataType : 'json',
        success : function(data) {
            var len = data.rows.length

            for(var i = 0; i < len; i++) {
                if(data.rows[i].catalog == 1) {
                    topicsMap.push(data.rows[i])
                }
            }
        }
    });
}

//批量修改分类
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

// 批量修改审核状态
function editMoStatus() {
    var $editMoStatus = $("#editMoStatus");
    $editMoStatus.window({
        title : '修改审核状态',
        width : 510,
        modal : true,
        shadow : false,
        closed : false,
        height : 280
    });
    $editMoStatus.show();
    loadStatusMenu();
    UpStatus()
}

// 加载审核信息
function loadStatusMenu() {
    var softwareArr = [];
    var typeid = parseInt($("#MoStatusBtn").attr("value"));
    moStausMap = {
        "rows" : [{
            "id" : "1",
            "name" : '批量审核'
        }, {
            "id" : "2",
            "name" : '批量免审核'
        }, {
            "id" : "0",
            "name" : '批量取消审核'
        }]

    }
    var len = moStausMap.rows.length
    for(var i = 0; i < len; i++) {
        softwareArr.push(["<li><a value='", moStausMap.rows[i].id, "'>", moStausMap.rows[i].name, "</a></li>"].join(''));
    }

    var html = [];
    html.push(softwareArr.join(""));
    $("#MoStatusMenu").html(html.join(""));
    $("#MoStatusMenu a[value='" + typeid + "']").addClass("cur")
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

// 批量更新分类
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
                    "catalog" : 1,
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
                        $('#softList').datagrid('unselectAll');
                        $('#softList').datagrid('reload');
                    }
                }
            });
        };
        $(this).parent().parent().parent().parent().parent().hide()
        $editIds.hide();
        $(".window-mask").hide();
    })
}

// 批量更新审核状态
function UpStatus() {
    var $MoStatusMenu_li_a = $("#MoStatusMenu li a");
    var $editMoStatus = $("#editMoStatus");
    $MoStatusMenu_li_a.click(function(e, index) {
        var typeIds = parseInt($(this).attr("value"));
        var url = "";
        if(typeIds == 1 || typeIds == '1') {
            url = urlWithParam + "/admin/app/audit.d";
        } else if(typeIds == 2 || typeIds == '2') {
            url = urlWithParam + "/admin/app/need-audit.d";
        } else if(typeIds == 0 || typeIds == '0') {
            url = urlWithParam + "/admin/app/off-audit.d";
        }
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
                url : url,
                traditional : true,
                data : {
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
                        $('#softList').datagrid('unselectAll');
                        $('#softList').datagrid('reload');
                    }
                }
            });
        }
        ;
        $(this).parent().parent().parent().parent().parent().hide()
        $editMoStatus.hide();
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
                    "catalog" : 1,
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
                        $('#softList').datagrid('unselectAll');
                        $('#softList').datagrid('reload');
                    }
                }
            });
        }
        $(this).parent().parent().parent().parent().parent().hide()
        $editType.hide();
        $(".window-mask").hide();
    })
}

//修改标签
function editTag(softId, typeid) {
    var softId = arguments[0];
    var $editTag = $("#editTag");
    $editTag.window({
        title : '修改标签',
        top : 200,
        width : 710,
        modal : true,
        shadow : false,
        closed : false,
        height : 380
    });
    $editTag.show();
    initTagMenu(softId)
}

// 加载标签信息
function initTagMenu(softId) {
    var tagwareArr = [], len = tagsMap.length, $TagMenu = $("#TagMenu");
    for(var i = 0; i < len; i++) {
        tagwareArr.push(["<li><a tagid='", tagsMap[i].id, "' tagname='", tagsMap[i].name, "' tagType='", tagsMap[i].tagType, "'>", tagsMap[i].name, "</a></li>"].join(''));
    }
    var html = [];
    html.push(tagwareArr.join(""));

    $TagMenu.html(html.join(""));

    $.ajax({
        type : "POST",
        url : urlWithParam + "/admin/tagapp/tags/" + softId + ".d",
        traditional : true,
        dataType : 'json',
        success : function(data) {
            var lentag = data.data.length;

            for(var t = 0; t < lentag; t++) {
                $TagMenu.find("a[tagid='" + data.data[t].tagId + "']").attr("value", 1).addClass("cur")
            }
            UpTag(softId)
        }
    })
}

//更新标签
function UpTag(softId) {
    var $TagMenu_li_a = $("#TagMenu li a"), $TagMenu = $("#TagMenu"), $editTag = $("#editTag"), $tagPost = $("#tagPost");

    $TagMenu_li_a.bind({
        click : function() {
            if(parseInt($(this).attr("value")) !== 1) {
                $(this).attr("value", 1).addClass("cur");
            } else {
                $(this).attr("value", 0).removeClass("cur");
            }
        }
    });

    $tagPost.bind({
        click : function() {
            var tagsLen = $TagMenu.find("a[value=1]").length, tagsid = "", tagsids = [], tagname = "", tagnames = [], tagType = "", tagTypes = [];

            for(var id = 0; id < tagsLen; id++) {
                tagsid = parseInt($TagMenu.find("a[value=1]").eq(id).attr("tagid"));
                tagsids[id] = tagsid;
                tagname = $TagMenu.find("a[value=1]").eq(id).attr("tagname");
                tagnames[id] = tagname;
                tagType = $TagMenu.find("a[value=1]").eq(id).attr("tagType");
                tagTypes[id] = tagType;
            };

            $.ajax({
                type : "POST",
                url : urlWithParam + "/admin/tagapp/save-tagapp.d",
                traditional : true,
                data : {
                    "appId" : softId,
                    "tagId" : tagsids,
                    "tagName" : tagnames,
                    "tagType" : tagTypes
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
                        $tagPost.unbind()
                    }
                }
            })
            $(this).parent().parent().parent().hide()
            $editTag.hide();
            $(".window-mask").hide();
        }
    })
}

//批量修改标签
function editIdsTag() {
    var $editTags = $("#editTags");
    $editTags.window({
        title : '批量修改标签',
        top : 200,
        width : 710,
        modal : true,
        shadow : false,
        closed : false,
        height : 380
    });
    $editTags.show();
    initIdsTagMenu();
    UpIdsTag();
}

//加载批量标签信息
function initIdsTagMenu() {
    var tagwareArr = [];
    var len = tagsMap.length
    for(var i = 0; i < len; i++) {
        tagwareArr.push(["<li><a tagid='", tagsMap[i].id, "' tagname='", tagsMap[i].name, "' tagType='", tagsMap[i].tagType, "'>", tagsMap[i].name, "</a></li>"].join(''));
    }
    var html = [];
    html.push(tagwareArr.join(""));

    $("#TagsMenu").html(html.join(""));
}

//批量更新标签
function UpIdsTag() {
    var $TagsMenu_li_a = $("#TagsMenu li a"), $TagsMenu = $("#TagsMenu"), $editTags = $("#editTags"), $tagsPost = $("#tagsPost");

    $TagsMenu_li_a.bind({
        click : function() {
            if(parseInt($(this).attr("value")) !== 1) {
                $(this).attr("value", 1).addClass("cur");
            } else {
                $(this).attr("value", 0).removeClass("cur");
            }
        }
    });

    $tagsPost.bind({
        click : function() {
            var inputs = $(".datagrid-body tr[class*='datagrid-row-selected']").length, softId = "", softIds = [], tagsLen = $TagsMenu.find("a[value=1]").length, tagsid = "", tagsids = [], tagname = "", tagnames = [], tagType = "", tagTypes = [];

            for(var i = 0; i < inputs; i++) {
                softId = parseInt($(".datagrid-body tr[class*='datagrid-row-selected']").eq(i).find("a[name='softIds']").attr("value"));
                softIds[i] = softId;
            };

            for(var id = 0; id < tagsLen; id++) {
                tagsid = parseInt($TagsMenu.find("a[value=1]").eq(id).attr("tagid"));
                tagsids[id] = tagsid;
                tagname = $TagsMenu.find("a[value=1]").eq(id).attr("tagname");
                tagnames[id] = tagname;
                tagType = $TagsMenu.find("a[value=1]").eq(id).attr("tagType");
                tagTypes[id] = tagType;
            };

            $.ajax({
                type : "POST",
                url : urlWithParam + "/admin/tagapp/save-tagapp.d",
                traditional : true,
                data : {
                    "appId" : softIds,
                    "tagId" : tagsids,
                    "tagName" : tagnames,
                    "tagType" : tagTypes
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
                        $tagsPost.unbind()
                    }
                }
            })
            $(this).parent().parent().parent().hide()
            $editTags.hide();
            $(".window-mask").hide();
        }
    })
}

//修改专题
function editTopics(softId, typeid) {
    var softId = arguments[0];
    var $editTopics = $("#editTopics");
    $editTopics.window({
        title : '修改专题',
        top : 200,
        width : 710,
        modal : true,
        shadow : false,
        closed : false,
        height : 380
    });
    $editTopics.show();
    initTopicsMenu(softId)
}

// 加载专题信息
function initTopicsMenu(softId) {
    var topicsArr = [], len = topicsMap.length, $TopicsMenu = $("#TopicsMenu");
    for(var i = 0; i < len; i++) {
        topicsArr.push(["<li><a tagid='", topicsMap[i].id, "' tagname='", topicsMap[i].name, "' tagType='", topicsMap[i].tagType, "'>", topicsMap[i].name, "</a></li>"].join(''));
    }
    var html = [];
    html.push(topicsArr.join(""));

    $TopicsMenu.html(html.join(""));

    $.ajax({
        type : "POST",
        url : urlWithParam + "/admin/tagapp/tags/" + softId + ".d",
        traditional : true,
        dataType : 'json',
        success : function(data) {
            var lentag = data.data.length;

            for(var t = 0; t < lentag; t++) {
                $TopicsMenu.find("a[tagid='" + data.data[t].tagId + "']").attr("value", 1).addClass("cur")
            }
            UpTopics(softId)
        }
    })
}

//更新专题
function UpTopics(softId) {
    var $TopicsMenu_li_a = $("#TopicsMenu li a"), $TopicsMenu = $("#TopicsMenu"), $editTopics = $("#editTopics"), $TopicsPost = $("#TopicsPost");

    $TopicsMenu_li_a.bind({
        click : function() {
            if(parseInt($(this).attr("value")) !== 1) {
                $(this).attr("value", 1).addClass("cur");
            } else {
                $(this).attr("value", 0).removeClass("cur");
            }
        }
    });

    $TopicsPost.bind({
        click : function() {
            var topicsLen = $TopicsMenu.find("a[value=1]").length, tagsid = "", tagsids = [], tagname = "", tagnames = [], tagType = "", tagTypes = [];

            for(var id = 0; id < topicsLen; id++) {
                tagsid = parseInt($TopicsMenu.find("a[value=1]").eq(id).attr("tagid"));
                tagsids[id] = tagsid;
                tagname = $TopicsMenu.find("a[value=1]").eq(id).attr("tagname");
                tagnames[id] = tagname;
                tagType = $TopicsMenu.find("a[value=1]").eq(id).attr("tagType");
                tagTypes[id] = tagType;
            };

            $.ajax({
                type : "POST",
                url : urlWithParam + "/admin/tagapp/save-tagapp.d",
                traditional : true,
                data : {
                    "appId" : softId,
                    "tagId" : tagsids,
                    "tagName" : tagnames,
                    "tagType" : tagTypes
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
                        $TopicsPost.unbind()
                    }
                }
            })
            $(this).parent().parent().parent().hide()
            $editTopics.hide();
            $(".window-mask").hide();
        }
    })
}

//批量修改专题
function editIdsTopics() {
    var $editTopicss = $("#editTopicss");
    $editTopicss.window({
        title : '批量修改专题',
        top : 200,
        width : 710,
        modal : true,
        shadow : false,
        closed : false,
        height : 380
    });
    $editTopicss.show();
    initIdsTopicsMenu();
    UpIdsTopics();
}

//加载批量专题信息
function initIdsTopicsMenu() {
    var topicsArr = [];
    var len = topicsMap.length
    for(var i = 0; i < len; i++) {
        topicsArr.push(["<li><a tagid='", topicsMap[i].id, "' tagname='", topicsMap[i].name, "' tagType='", topicsMap[i].tagType, "'>", topicsMap[i].name, "</a></li>"].join(''));
    }
    var html = [];
    html.push(topicsArr.join(""));

    $("#TopicssMenu").html(html.join(""));
}

//批量更新专题
function UpIdsTopics() {
    var $TopicssMenu_li_a = $("#TopicssMenu li a"), $TopicssMenu = $("#TopicssMenu"), $editTopicss = $("#editTopicss"), $TopicssPost = $("#TopicssPost");

    $TopicssMenu_li_a.bind({
        click : function() {
            if(parseInt($(this).attr("value")) !== 1) {
                $(this).attr("value", 1).addClass("cur");
            } else {
                $(this).attr("value", 0).removeClass("cur");
            }
        }
    });

    $TopicssPost.bind({
        click : function() {
            var inputs = $(".datagrid-body tr[class*='datagrid-row-selected']").length, softId = "", softIds = [], tagsLen = $TopicssMenu.find("a[value=1]").length, tagsid = "", tagsids = [], tagname = "", tagnames = [], tagType = "", tagTypes = [];

            for(var i = 0; i < inputs; i++) {
                softId = parseInt($(".datagrid-body tr[class*='datagrid-row-selected']").eq(i).find("a[name='softIds']").attr("value"));
                softIds[i] = softId;
            };

            for(var id = 0; id < tagsLen; id++) {
                tagsid = parseInt($TopicssMenu.find("a[value=1]").eq(id).attr("tagid"));
                tagsids[id] = tagsid;
                tagname = $TopicssMenu.find("a[value=1]").eq(id).attr("tagname");
                tagnames[id] = tagname;
                tagType = $TopicssMenu.find("a[value=1]").eq(id).attr("tagType");
                tagTypes[id] = tagType;
            };

            $.ajax({
                type : "POST",
                url : urlWithParam + "/admin/tagapp/save-tagapp.d",
                traditional : true,
                data : {
                    "appId" : softIds,
                    "tagId" : tagsids,
                    "tagName" : tagnames,
                    "tagType" : tagTypes
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
                        $TopicssPost.unbind()
                    }
                }
            })
            $(this).parent().parent().parent().hide()
            $editTopicss.hide();
            $(".window-mask").hide();
        }
    })
}

//加载手机专题数据
function LoadMoTopics() {
    $.ajax({
        type : "POST",
        url : "../admin/motag/topic-list.json",
        traditional : true,
        dataType : 'json',
        success : function(data) {
            var len = data.rows.length

            for(var i = 0; i < len; i++) {
                if(data.rows[i].catalog == 1) {
                    moTopicsMap.push(data.rows[i])
                }
            }
        }
    });
}

//批量修改手机专题
function editMoTopics() {
    var $editMoTopicss = $("#editMoTopicss");
    $editMoTopicss.window({
        title : '批量修改手机专题',
        top : 200,
        width : 710,
        modal : true,
        shadow : false,
        closed : false,
        height : 380
    });
    $editMoTopicss.show();
    initMoTopicssMenu();
    UpMoTopicss();
}

//加载批量手机专题信息
function initMoTopicssMenu() {
    var moTopicsArr = [];
    var len = moTopicsMap.length
    for(var i = 0; i < len; i++) {
        moTopicsArr.push(["<li><a tagid='", moTopicsMap[i].id, "' tagname='", moTopicsMap[i].name, "' tagType='", moTopicsMap[i].tagType, "'>", moTopicsMap[i].name, "</a></li>"].join(''));
    }
    var html = [];
    html.push(moTopicsArr.join(""));

    $("#MoTopicssMenu").html(html.join(""));
}

//批量更新手机专题
function UpMoTopicss() {
    var $MoTopicssMenu_li_a = $("#MoTopicssMenu li a"), $MoTopicssMenu = $("#MoTopicssMenu"), $editMoTopicss = $("#editMoTopicss"), $MoTopicssPost = $("#MoTopicssPost");

    $MoTopicssMenu_li_a.bind({
        click : function() {
            if(parseInt($(this).attr("value")) !== 1) {
                $(this).attr("value", 1).addClass("cur");
            } else {
                $(this).attr("value", 0).removeClass("cur");
            }
        }
    });

    $MoTopicssPost.bind({
        click : function() {
            var inputs = $(".datagrid-body tr[class*='datagrid-row-selected']").length, softId = "", softIds = [], tagsLen = $MoTopicssMenu.find("a[value=1]").length, tagsid = "", tagsids = [], tagname = "", tagnames = [], tagType = "", tagTypes = [];

            for(var i = 0; i < inputs; i++) {
                softId = parseInt($(".datagrid-body tr[class*='datagrid-row-selected']").eq(i).find("a[name='softIds']").attr("value"));
                softIds[i] = softId;
            };

            for(var id = 0; id < tagsLen; id++) {
                tagsid = parseInt($MoTopicssMenu.find("a[value=1]").eq(id).attr("tagid"));
                tagsids[id] = tagsid;
                tagname = $MoTopicssMenu.find("a[value=1]").eq(id).attr("tagname");
                tagnames[id] = tagname;
                tagType = $MoTopicssMenu.find("a[value=1]").eq(id).attr("tagType");
                tagTypes[id] = tagType;
            };

            $.ajax({
                type : "POST",
                url : urlWithParam + "/admin/mo-tagapp/save-tagapp.d",
                traditional : true,
                data : {
                    "appId" : softIds,
                    "tagId" : tagsids,
                    "tagName" : tagnames,
                    "tagType" : tagTypes
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
                        $MoTopicssPost.unbind()
                    }
                }
            })
            $(this).parent().parent().parent().hide()
            $editMoTopicss.hide();
            $(".window-mask").hide();
        }
    })
}

//加载手机标签
function LoadMoTag() {
    $.ajax({
        type : "POST",
        url : "../admin/motag/normaltag-list.json",
        traditional : true,
        dataType : 'json',
        success : function(data) {
            var len = data.rows.length

            for(var i = 0; i < len; i++) {
                if(data.rows[i].catalog == 1) {
                    moTagMap.push(data.rows[i])
                }
            }
        }
    });
}

//批量修改手机标签
function editMoTag() {
    var $editMoTags = $("#editMoTags");
    $editMoTags.window({
        title : '批量修改手机专题',
        top : 200,
        width : 710,
        modal : true,
        shadow : false,
        closed : false,
        height : 380
    });
    $editMoTags.show();
    initMoTagsMenu();
    UpMoTags();
}

//加载批量手机标签信息
function initMoTagsMenu() {
    var moTagArr = [];
    var len = moTagMap.length
    for(var i = 0; i < len; i++) {
        moTagArr.push(["<li><a tagid='", moTagMap[i].id, "' tagname='", moTagMap[i].name, "' tagType='", moTagMap[i].tagType, "'>", moTagMap[i].name, "</a></li>"].join(''));
    }
    var html = [];
    html.push(moTagArr.join(""));

    $("#MoTagsMenu").html(html.join(""));
}

//批量更新手机标签
function UpMoTags() {
    var $MoTagsMenu_li_a = $("#MoTagsMenu li a"), $MoTagsMenu = $("#MoTagsMenu"), $editMoTags = $("#editMoTags"), $MoTagsPost = $("#MoTagsPost");

    $MoTagsMenu_li_a.bind({
        click : function() {
            if(parseInt($(this).attr("value")) !== 1) {
                $(this).attr("value", 1).addClass("cur");
            } else {
                $(this).attr("value", 0).removeClass("cur");
            }
        }
    });

    $MoTagsPost.bind({
        click : function() {
            var inputs = $(".datagrid-body tr[class*='datagrid-row-selected']").length, softId = "", softIds = [], tagsLen = $MoTagsMenu.find("a[value=1]").length, tagsid = "", tagsids = [], tagname = "", tagnames = [], tagType = "", tagTypes = [];

            for(var i = 0; i < inputs; i++) {
                softId = parseInt($(".datagrid-body tr[class*='datagrid-row-selected']").eq(i).find("a[name='softIds']").attr("value"));
                softIds[i] = softId;
            };

            for(var id = 0; id < tagsLen; id++) {
                tagsid = parseInt($MoTagsMenu.find("a[value=1]").eq(id).attr("tagid"));
                tagsids[id] = tagsid;
                tagname = $MoTagsMenu.find("a[value=1]").eq(id).attr("tagname");
                tagnames[id] = tagname;
                tagType = $MoTagsMenu.find("a[value=1]").eq(id).attr("tagType");
                tagTypes[id] = tagType;
            };

            $.ajax({
                type : "POST",
                url : urlWithParam + "/admin/mo-tagapp/save-tagapp.d",
                traditional : true,
                data : {
                    "appId" : softIds,
                    "tagId" : tagsids,
                    "tagName" : tagnames,
                    "tagType" : tagTypes
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
                        $MoTagsPost.unbind()
                    }
                }
            })
            $(this).parent().parent().parent().hide()
            $editMoTags.hide();
            $(".window-mask").hide();
        }
    })
}

//修改增减量
function editDelta(softId) {
    var $editDelta = $("#editDelta"), $addDelta = $("#addDelta"), $closeDelta = $("#closeDelta"), $deltaDownload = $("#deltaDownload"), deltaDownload = 0;

    $editDelta.window({
        title : '修改增减量',
        top : 200,
        width : 550,
        modal : true,
        shadow : false,
        closed : false,
        height : 250
    });
    $editDelta.show();

    $addDelta.bind({
        click : function() {
            deltaDownload = parseInt($deltaDownload.val());

            $.ajax({
                type : "POST",
                url : urlWithParam + "/admin/app/download/update.d",
                traditional : true,
                data : {
                    "id" : softId,
                    "deltaDownload" : deltaDownload
                },
                dataType : 'json',
                success : function(data) {
                    $.messager.show({
                        msg : '修改 成功',
                        title : '提示',
                        showType : 'slide',
                        timeout : 3000
                    });
                    $('#softList').datagrid('unselectAll');
                    $('#softList').datagrid('reload');
                    $addDelta.unbind()
                }
            })
            $(this).parent().parent().parent().parent().parent().parent().parent().hide()
            $editDelta.hide();
            $(".window-mask").hide();
        }
    })

    $closeDelta.bind({
        click : function() {
            $(this).parent().parent().parent().parent().parent().parent().parent().hide()
            $editDelta.hide();
            $(".window-mask").hide();
        }
    })
}

// 删除
function del() {
    var softId = arguments[0];
    $.messager.confirm('确认', '您确认要彻底删除这条记录吗?', function(row) {
        if(row) {
            var selectedRow = $('#softList').datagrid('getSelected');
            $.ajax({
                type : "POST",
                url : urlWithParam + "/admin/app/" + softId + ".del.d",
                success : function() {
                    $.messager.show({
                        msg : '删除 成功',
                        title : '提示',
                        showType : 'slide',
                        timeout : 3000
                    });
                    $('#softList').datagrid('reload');
                }
            });
        }
    })
}

//编辑关键字
function editKeywords(keywords, id) {
    if(!id)
        return false;
    var $box = $('#editKeywordsBox'), form = $('#formEditKeywordsData'), btn = $('#editKeywordsPost'), keyText = $('#editKeywordsText'), keyId = $('#keywordsId');
    keyText.val(keywords || '');
    keyId.val(id);
    $box.window({
        title : '修改关键字',
        top : 200,
        width : 550,
        height : 300,
        modal : true,
        shadow : false,
        closed : false
    });

    $box.show();
    btn.unbind('click').bind('click', function() {
        $.ajax({
            url : '/sjk-market-admin/admin/app/edit-keywords.d',
            type : 'POST',
            data : {
                "id" : id,
                "keywords" : keyText.val().replace(/^[　\s]+|[　\s]+$/g, '')
            },
            success : function() {
                $.messager.show({
                    msg : '关键字更新成功',
                    title : '提示',
                    showType : 'slide',
                    timeout : 3000
                });
                $('#softList').datagrid('reload');

                $(this).parent().parent().parent().parent().parent().parent().parent().parent().hide()
                $box.window('close');
            }
        });

    })
}

function splitText() {
    var keyText = $('#editKeywordsText');
    var text = keyText.val();
    text = text.replace(/^[　\s]+|[　\s]+$/g, '').replace(/\s+/g, ',');
    keyText.val(text);

}