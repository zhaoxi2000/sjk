var urlWithParam = "/sjk-market-admin";

$(document).ready(function() {
    loadPid('keyPid', null, false);
    $('#keyword').bind('keydown', function(e) {
        if(e.which == 13) {
            SearchEve();
        }
    });
    $('#search').click(function() {
        SearchEve();
    });
    loadGridTable();
    var p = $('#dataList').datagrid('getPager');
    $(p).pagination({
        onBeforeRefresh : function() {
        }
    });
});
function loadPid(objId, value, isAddOrEdit) {
    $obj = $("#" + objId);
    $.ajax({
        type : "GET",
        url : urlWithParam + "/admin/tag/topic-list.json",
        dataType : 'json',
        success : function(data) {
            if(data != null) {
                var data = data.rows;
                $obj.empty();
                if(isAddOrEdit) {
                    $obj.append("<option value=\"0\">【无上级类别】</option>");
                } else {
                    $obj.append("<option value=\"\">-请选择上级类别-</option>");
                }
                $.each(data, function(i, row) {
                    if(row.pid == 0) {
                        if(value == row.id) {
                            $obj.append("<option value=\"" + row.id + "\"  selected=\"selected\">" + row.name + "</option>");
                        } else {
                            $obj.append("<option value=\"" + row.id + "\">" + row.name + "</option>");
                        }
                    }
                });
                $obj.bind({
                    change : function() {
                        SearchEve()
                    }
                })
            }
        }
    });

}

// 加载DataGrid控件
function loadGridTable() {
    $('#dataList').datagrid({
        loadMsg : '正在加载...',
        iconCls : 'icon-save',
        width : '950',
        nowrap : false,
        striped : true,
        collapsible : false,
        url : urlWithParam + "/admin/tag/search.json",
        pageSize : 50,
        sortOrder : 'desc',
        idField : "id",
        columns : [[{
            field : 'id',
            title : 'id',
            sortable : true,
            width : 50
        }, {
            field : 'name',
            title : '专题标签名称',
            sortable : true,
            width : 150,
            formatter : function(value, rec, index) {
                var url = urlWithParam + "/tagapp/index.html?tagId=" + rec.id;
                var html = '<a class="mr_10 co_bl f_l" href="' + url + '">' + value + '</a>';
                // var edit = '<a class="mr_10 co_bl
                // f_l" onclick="Edit(' + rec.id + ',\''
                // + rec.name + '\',\'' + rec.tagDesc +
                // '\',' + rec.catalog + ',' +
                // rec.tagType + ',\'' + rec.imgUrl +
                // '\');">' + value + '</a>';
                return html;
            }
        }, {
            field : 'pname',
            title : '上级专题',
            sortable : true,
            width : 150,
            formatter : function(value, rec, index) {
                if(value == null || value == "") {
                    return "【无】";
                } else {
                    return "[" + rec.pid + "]" + value;
                }
            }
        }, {
            field : 'rank',
            title : '排序',
            sortable : true,
            width : 50
        }, {
            field : 'tagDesc',
            title : '专题标签描述',
            sortable : true,
            width : 250,
            formatter : function(value, rec, index) {
                if(value.length > 20) {
                    return "<span title='" + value + "'>" + value.substring(0, 20) + "...</span>";
                } else {
                    return value;
                }
            }
        }, {
            field : 'showImg',
            title : '图片预览',
            sortable : true,
            width : 60,
            formatter : function(value, rec, index) {
                var html = '<img class="wh_20"  src=\"' + rec.imgUrl + '\" />';
                return html;
            }
        }, {
            field : 'imgUrl',
            title : '专题标签图片',
            sortable : true,
            width : 150,
            formatter : function(value, rec, index) {
                var html = '<span title="' + rec.imgUrl + '">' + rec.imgUrl + '</span>';
                return html;
            }
        }, {
            field : 'operate',
            title : '操作',
            width : 120,
            formatter : function(value, rec, index) {
                var del = '<a class="mr_15 co_bl f_l" onclick="Delete(' + rec.id + ',' + index + ')">删除</a>';
                var edit = '<a class="mr_10 co_bl f_l" onclick="Edit(' + rec.id + ',\'' + rec.name + '\',\'' + rec.tagDesc + '\',' + rec.catalog + ',' + rec.tagType + ',\'' + rec.imgUrl + '\',' + rec.pid + ');">修改</a>';
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
    var keywords = $.trim($('#keyword').val());var queryParams = $('#dataList').datagrid('options').queryParams;
    var pid = $.trim($("#keyPid option:selected").val());
    if(keywords !== "") {
        queryParams.keywords = keywords;
    } else {
        queryParams.keywords = '';
    }
    if(pid !== "") {
        queryParams.pid = pid;
    } else {
        queryParams.pid = '';
    }
    queryParams.page = 1;

    // 主显示使用
    $('#dataList').datagrid('options').pageNumber = 1;
    // page对象 中的pageNumber对象
    var pager = $('#dataList').datagrid('getPager');
    $(pager).pagination('options').pageNumber = 1;
    $('#dataList').datagrid('reload');
}

// 添加
function Add() {
    Edit(0, "", "", 1, 1, "", null, 0);
}

// 添加
function Edit() {
    var id = arguments[0];
    var name = arguments[1];
    var tagDesc = arguments[2];
    var catalog = arguments[3];
    var tagType = arguments[4];
    var imgUrl = arguments[5];
    var pid = arguments[6];
    var rank = arguments[7];
    $editPanl = $("#editPanl");
    var title = "添加专题标签";
    if(id > 0) {
        var title = "修改专题标签";
    }
    $editPanl.window({
        title : title,
        top : 200,
        width : 650,
        modal : true,
        shadow : true,
        closed : false,
        height : 380
    });
    $editPanl.show();
    initHtml(id, name, tagDesc, catalog, tagType, imgUrl, pid, rank);
}

function initHtml() {
    var id = arguments[0];
    var name = arguments[1];
    var tagDesc = arguments[2];
    var catalog = arguments[3];
    var tagType = arguments[4];
    var imgUrl = arguments[5];
    var pid = arguments[6];
    var rank = arguments[7];
    $id = $("#id");
    // $catalog = $("#catalog");
    $tagType = $("#tagType");
    $name = $("#txtTagName");
    $name = $("#txtTagName");
    $oldImgUrl = $("#txtOldImgUrl");
    $showImgUrl = $("#showImgUrl");
    $tagDesc = $("#txtTagDesc");
    $rank = $("#txtRank");

    loadPid('sltPId', pid, true);


    $id.val(id);
    $name.val(name);
    $tagType.val(tagType);
    // $catalog.val(catalog);
    $tagDesc.val(tagDesc);
    $rank.val(rank);
    $("#sltCatalog option").removeAttr("selected");
    $("#sltCatalog option[value='" + catalog + "']").attr("selected", "selected");
    if(imgUrl != null && imgUrl != "") {
        $showImgUrl.attr("src", imgUrl);
        $oldImgUrl.val(imgUrl);
    } else {
        $oldImgUrl.val("");
        $showImgUrl.attr("src", "");
    }
    $formData = $("#formData");
    $tagsPost = $("#tagsPost");
    var actionUrl = "";
    if(id == 0) {
        actionUrl = urlWithParam + "/admin/tag/save.d";
    } else {
        actionUrl = urlWithParam + "/admin/tag/edit.d";
    }
    $formData.attr("action", actionUrl);
    /* POST请求 */
    $tagsPost.click(function() {
        var catalog = $("#sltCatalog option:selected").val();
        if($name.val() == "" || $name.val() == null) {
            $("#errorName").html("很抱歉，您还没有输入专题标签名称。");
            return false;
        } else {
            $("#errorName").empty();
        }
        if(isNaN($rank.val())) {
            $("#errorRank").html("很抱歉，排序值必须是整数。");
            return false;
        } else {
            $("#errorRank").empty();
        }
        if(catalog == "" || catalog == null || $tagType.val() == "" || $tagType.val() == null) {
            alert("很抱歉，数据填写错误！");
            return false;
        }
        $formData.submit();
        
    });
}

// 删除
function Delete() {
    var id = arguments[0];
    $.messager.confirm('确认', '您确认要删除这条记录吗?', function(row) {
        if(row) {
            $.ajax({
                type : "POST",
                url : urlWithParam + "/admin/tag/del.d",
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