var urlWithParam = "/sjk-market-admin";
var urlWithParamImage = "http://app.sjk.ijinshan.com/market/img/tag/";

$(document).ready(function() {

    $('#keyword').bind('keydown', function(e) {
        if(e.which == 13) {
            SearchEve();
        }
    });
    $('#search').click(function() {
        SearchEve();
    });
    var tagId = queryString("tagId");
    if(tagId == null || tagId == "" || tagId == undefined) {
        tagId = 0;
    }
    if(tagId != 0) {
        $('#dataList tbody').hide();
    }
    loadCatalog("keyCatalog");
    $("#keyCatalog").bind('change', function(e) {
        var value = $(this).val();
        LoadTags(0, value);
    });
    LoadTags(tagId, 0);
    loadGridTable(tagId);
    var p = $('#dataList').datagrid('getPager');
    $(p).pagination({
        onBeforeRefresh : function() {
        }
    });
    BindSort();
});
// 只取一个：
function queryString(key) {
    return (document.location.search.match(new RegExp("(?:^\\?|&)" + key
    + "=(.*?)(?=&|$)")) || [ '', null ])[1];
}

// 加载DataGrid控件
function loadGridTable(tagId) {
    var url = urlWithParam + "/admin/tagapp/normaltag-search.json";
    if(tagId != 0) {
        url = urlWithParam + "/admin/tagapp/normaltag-search.json?tagId=" + tagId;
    }
    $('#dataList').datagrid({
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
        columns : [[{
            field : 'id',
            title : 'id',
            sortable : true,
            width : 50
        }, {
            field : 'appId',
            title : '应用编号',
            sortable : true,
            width : 80,
            formatter : function(value, rec, index) {
                var edit = '<a class="mr_10 co_bl f_l" onclick="Edit(' + rec.id + ',' + rec.tagId + ',' + rec.appId + ',' + rec.rank + ',' + rec.tagType + ');">' + value + '</a>';
                return edit;
            }
        }, {
            field : 'tagId',
            title : '标签编号',
            sortable : true,
            width : 60
        }, {
            field : 'tagName',
            title : '标签名称',
            sortable : true,
            width : 200,
            formatter : function(value, rec, index) {
                return getCatalogName(rec.catalog) + '--' + value;
            }
        }, {
            field : 'appName',
            title : '应用名称',
            sortable : true,
            width : 250,
            formatter : function(value, rec, index) {
                var url = "";
                if(rec.appCatalog == 100) {
                    url = '"../admin/biggame/' + rec.appId + '.d?id=' + rec.appId + '"';
                } else {
                    url = '"../admin/app/' + rec.appId + '.merge.d"';

                }
                var ids = '<img class="w_20 h_20 f_l mt_4 mr_10" src="' + rec.logoUrl + '"/><a name="softIds" class="co_bl f_l" value="' + rec.appId + '" href=' + url + ' target="_blank">' + value + '</a>';
                return ids;
            }
        }, {
            field : 'rank',
            title : '排序',
            sortable : true,
            width : 100
        }, {
            field : 'marketName',
            title : '市场名称',
            sortable : true,
            width : 100
        }, {
            field : 'operate',
            title : '操作',
            width : 100,
            formatter : function(value, rec, index) {
                var del = '<a class="mr_15 co_bl f_l" onclick="Delete(' + rec.id + ',' + index + ')">删除</a>';
                var edit = '<a class="mr_10 co_bl f_l" onclick="Edit(' + rec.id + ',' + rec.tagId + ',' + rec.appId + ',' + rec.rank + ',' + rec.tagType + ');">修改</a>';
                return edit + del;
            }
        }]],
        pagination : true,
        pagePosition:'both',//上下显示分页效果
		pagerFixedTop:true,//滚动固定上栏分页
        rownumbers : false
    });
}

// 搜索
function SearchEve() {
    var keywords = $.trim($('#keyword').val());
    var keyCatalog = $("#keyCatalog option:selected").val();
    var tagId = $("#keyTagId option:selected").val();
    var queryParams = $('#dataList').datagrid('options').queryParams;
    var url = urlWithParam + "/admin/tagapp/normaltag-search.json";
    if(keywords !== "") {
        queryParams.keywords = keywords;
    } else {
        queryParams.keywords = '';
    }
    if(keyCatalog != "") {
        queryParams.catalog = keyCatalog;
    } else {
        queryParams.catalog = 0;
    }

    if(tagId == "" || tagId == null) {
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
    queryParams.url = urlWithParam + "/admin/tagapp/normaltag-search.json";
    if(keywords !== "") {
        queryParams.keywords = keywords;
    } else {
        queryParams.keywords = '';
    }
    if(tagId == "" || tagId == null) {
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

function getCatalogName(catalogId) {
    var data = data_ptCatalogs.rows;
    var len = data.length;
    for(var i = 0; i < len; i++) {
        var rs = data[i];
        if(catalogId == rs.id) {
            result = rs.name;
            break;
        }
    }
    return result;
}

function loadCatalog(objId) {
    $obj = $("#" + objId);
    $obj.empty();
    $obj.append("<option value=\"\">-请选择类别-</option>");
    $.each(data_ptCatalogs.rows, function(i, row) {
        $obj.append("<option value=\"" + row.id + "\"   >" + row.name + "</option>");
    });
    $obj.bind({
        change : function() {
            SearchEve()
        }
    })
}

function LoadTags(tagId, catalogId) {
    $keyTagId = $("#keyTagId");

    $("#keyTagId option").removeAttr("selected");
    $("#keyTagId option[value='" + tagId + "']").attr("selected", "selected");
    $.ajax({
        type : "GET",
        url : urlWithParam + "/admin/tag/normaltag-list.json",
        dataType : 'json',
        success : function(data) {
            if(data != null) {
                var data = data.rows;
                $keyTagId.data("tagIdData", data)
                $keyTagId.empty();
                $keyTagId.append("<option value=\"\">-请选择标签-</option>");
                $.each(data, function(i, row) {
                    if(catalogId != null && catalogId != "" && catalogId != undefined && catalogId != 0) {
                        if(parseInt(row.catalog) == parseInt(catalogId)) {
                            if(parseInt(row.id) == parseInt(tagId)) {
                                $keyTagId.append("<option value=\"" + row.id + "\"  selected=\"selected\">" + row.name + "</option>");
                            } else {
                                $keyTagId.append("<option value=\"" + row.id + "\">" + row.name + "</option>");
                            }
                        }
                    } else {
                        if(parseInt(row.id) == parseInt(tagId)) {
                            $keyTagId.append("<option value=\"" + row.id + "\"  selected=\"selected\">" + row.name + "</option>");
                        } else {
                            $keyTagId.append("<option value=\"" + row.id + "\">" + row.name + "</option>");
                        }
                    }
                });
                $keyTagId.bind({
                    change : function() {
                        SearchEve()
                    }
                })
            }
        }
    });

}

function loadTagsData(keyTagId, sltTagsId, value) {
    $keyTagId = $("#" + keyTagId);
    tags = $keyTagId.data("tagIdData");
    sltTags = $("#" + sltTagsId);
    if(tags == null || tags == "" || tags == undefined) {
        $.ajax({
            type : "GET",
            url : urlWithParam + "/admin/tag/normaltag-list.json",
            dataType : 'json',
            success : function(data) {
                if(data != null) {
                    var data = data.rows;
                    $keyTagId.data("tagIdData", data)
                    sltTags.empty();
                    sltTags.append("<option value=\"\">-请选择专题-</option>");
                    $.each(data, function(i, row) {
                        if(row.tagType == 2) {
                            if(value == row.id) {
                                sltTags.append("<option value=\"" + row.id + "\"  selected=\"selected\">" + getCatalogName(row.catalog) + '--' + row.name + "</option>");
                            } else {
                                sltTags.append("<option value=\"" + row.id + "\">" + getCatalogName(row.catalog) + '--' + row.name + "</option>");
                            }
                        }
                    });
                }
            }
        });
    } else {
        sltTags.empty();
        sltTags.append("<option value=\"\">-请选择专题-</option>");
        $.each(tags, function(i, row) {
            if(row.tagType == 2) {
                if(value == row.id) {
                    sltTags.append("<option value=\"" + row.id + "\"  selected=\"selected\">" + getCatalogName(row.catalog) + '--' + row.name + "</option>");
                } else {
                    sltTags.append("<option value=\"" + row.id + "\">" + getCatalogName(row.catalog) + '--' + row.name + "</option>");
                }
            }
        });
    }
}

// 添加
function Add() {
    Edit(0, 0, 0, 999999999, 1);
}

// 添加
function Edit() {
    var id = arguments[0];
    var tagId = arguments[1];
    var appId = arguments[2];
    var rank = arguments[3];
    var tagType = arguments[4];
    $editPanl = $("#editPanl");
    var title = "添加标签应用";
    if(id > 0) {
        var title = "修改标签应用";
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
    initHtml(id, tagId, appId, rank, tagType);
}

function initHtml() {
    var id = arguments[0];
    var tagId = arguments[1];
    var appId = arguments[2];
    var rank = arguments[3];
    var tagType = arguments[4];
    $id = $("#id");
    $appId = $("#txtAppId");
    $tagType = $("#sltTagType");
    // 下拉框
    $tagId = $("#sltTagId");
    // 下拉框
    $rank = $("#txtRank");

    $id.val(id);
    $appId.val(appId);
    $rank.val(rank);

    // TODO:多个是完善功能
    // $tagType.val(tagType);
    loadTagsData("keyTagId", "sltTagId", tagId);
    $tagsPost = $("#tagsPost");
    /* POST请求 */
    $tagsPost.unbind("click");
    $tagsPost.click(function() {
        tagId = $tagId.find("option:selected").val();
        tagName = $tagId.find("option:selected").text();
        tagType = $tagType.find("option:selected").val();
        if($appId.val() == "" || $appId.val() == null) {
            $("#errorAppId").html("请输入应用编号。");
            return false;
        } else {
            $("#errorAppId").empty();
        }
        if(tagId == null || tagId == "") {
            $("#errorTagId").html("请选择标签。");
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
                "shortDesc" : "",
                "tagId" : tagId,
                "tagType" : tagType,
                "tagName" : tagName
            },
            dataType : 'json',
            success : function(data) {
                if(data.result.msg == "OK!") {
                    $.messager.show({
                        msg : '添加成功',
                        title : '提示',
                        showType : 'slide',
                        timeout : 3000
                    });
                    $('#dataList').datagrid('unselectAll');
                    $('#dataList').datagrid('reload');
                }
            }
        });
        $(this).parent().parent().parent().parent().parent().parent().parent().parent().hide();
        $("#editPanl").hide();
        $(".window-mask").hide();
    });
}

// 排序
function BindSort() {
    var $btnUp = $("#btnUp"), $btnDown = $("#btnDown"), keyTagId = 0, rowLen = 0, appId = 0, rank = 0, appIdUd = 0, rankUd = 0;

    $btnUp.bind({
        click : function() {
            var $UpTr = $(".datagrid-body tr[class*='datagrid-row-selected']").prev("tr");
            rowLen = $(".datagrid-body tr[class*='datagrid-row-selected']").length;
            keyTagId = $('#keyTagId option:selected').val();

            if(rowLen == 1 && keyTagId !== "") {
                appId = parseInt($(".datagrid-body tr[class*='datagrid-row-selected']").find("a[name='appId']").attr("appId"));
                rank = parseInt($(".datagrid-body tr[class*='datagrid-row-selected']").find("a[name='appId']").attr("rank"));
                appIdUd = parseInt($UpTr.find("a[name='appId']").attr("appId"));
                rankUd = parseInt($UpTr.find("a[name='appId']").attr("rank"));

                $.ajax({
                    type : "POST",
                    url : urlWithParam + "/admin/tagapp/sort.json",
                    traditional : true,
                    data : {
                        "ids" : [appIdUd, appId],
                        "ranks" : [rank, rankUd]
                    },
                    dataType : 'json',
                    success : function(data) {
                        if(data.result.msg == "OK!") {
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
                alert("请选择标签");
            }
        }
    })

    $btnDown.bind({
        click : function() {
            var $DownTr = $(".datagrid-body tr[class*='datagrid-row-selected']").next("tr");
            rowLen = $(".datagrid-body tr[class*='datagrid-row-selected']").length;
            keyTagId = $('#keyTagId option:selected').val();

            if(rowLen == 1 && keyTagId !== "") {
                appId = parseInt($(".datagrid-body tr[class*='datagrid-row-selected']").find("a[name='appId']").attr("appId"));
                rank = parseInt($(".datagrid-body tr[class*='datagrid-row-selected']").find("a[name='appId']").attr("rank"));
                appIdUd = parseInt($DownTr.find("a[name='appId']").attr("appId"));
                rankUd = parseInt($DownTr.find("a[name='appId']").attr("rank"));

                $.ajax({
                    type : "POST",
                    url : urlWithParam + "/admin/tagapp/sort.json",
                    traditional : true,
                    data : {
                        "ids" : [appIdUd, appId],
                        "ranks" : [rank, rankUd]
                    },
                    dataType : 'json',
                    success : function(data) {
                        if(data.result.msg == "OK!") {
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
                alert("请选择标签");
            }
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
                url : urlWithParam + "/admin/tagapp/del.json",
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
                    }
                }
            });
        }
    });
}