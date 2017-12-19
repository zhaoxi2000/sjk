var urlWithParam = "/sjk-market-admin";

$(document).ready(function() {
    loadType();
    LoadTag();
    LoadTopics()

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
    $('#hideBtnBom').click(function() {
        BatchHideEve()
    });
    $('#idsBtn').click(function() {
        editIdsType()
    });
    $('#idsBtnBom').click(function() {
        editIdsType()
    });
    $('#tagsBtn').click(function() {
        editIdsTag()
    });
    $('#TopicssBtn').click(function() {
        editIdsTopics()
    });
    $('#iTopicssBtnBom').click(function() {
        editIdsTopics()
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
        loadMsg : '正在加载...',
        iconCls : 'icon-save',
        width : '1380',
        nowrap : false,
        striped : true,
        collapsible : false,
        url : urlWithParam + "/admin/app/search.json?catalog=100",
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
			setBtnDisabled("search",false);
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
            width : 320
            //,
            //									formatter : function(value, rec, index) {
            //										var ids = '<img class="w_20 h_20 f_l mt_4 mr_10" src="'
            //												+ rec.logoUrl
            //												+ '"/><a name="softIds" class="co_bl f_l" value="'
            //												+ rec.id
            //												+ '" href="../admin/biggame/'
            //												+ rec.id
            //												+ '.d?id='
            //												+ rec.id
            //												+ '"  target="_blank">'
            //												+ value
            //												+ '</a>';
            //										return ids;
            //									}
        }, {
            field : 'version',
            title : '版本',
            sortable : true,
            width : 100
        }, {
            field : 'lastUpdateTime',
            title : '最后修改时间',
            sortable : true,
            width : 100,
            formatter : function(value, entity) {
                return value.substring(0, 10);
            },
        }, {
            field : 'downloads',
            title : '下载次数',
            sortable : true,
            width : 100
        }, {
            field : 'realDownload',
            title : '实际下载次数',
            sortable : true,
            width : 100
        }, {
            field : 'deltaDownload',
            title : '下载修正增减',
            sortable : true,
            width : 100,
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
        },
        //								{
        //									field : 'autoCover',
        //									title : '自动覆盖',
        //									sortable : true,
        //									width : 100,
        //									formatter : function(value, rec, index) {
        //										if(value == true){
        //											return "<span>是</span>";
        //										}else{
        //											return "<span class=\"co_dg\">否</span>";
        //										}
        //									}
        //								},
        {
            field : 'marketName',
            title : '来源',
            sortable : true,
            width : 80
        }, {
            field : 'operate',
            title : '操作',
            width : 180,
            formatter : function(value, rec, index) {
                //										var edit = '<a name="softIds" class="co_bl f_l mr_15" value="'
                //												+ rec.id
                //												+ '" href="../admin/biggame/'
                //												+ rec.id
                //												+ '.d?id='
                //												+ rec.id
                //												+ '"  target="_blank">编辑</a>';
                var del = '<a class="mr_15 co_bl f_l" onclick="del(' + rec.id + ',' + index + ')">删除</a>';
                var auhide = "";
                var hide = "";
                if(rec.hidden == true) {
                    hide = '<a class="mr_15 co_green f_l" onclick="showEve(' + rec.id + ',' + index + ')">显示</a><span class="co_dg">已隐藏</span>';
                } else {
                    hide = '<a class="mr_15 co_degr f_l" onclick="hideEve(' + rec.id + ',' + index + ')">隐藏</a>';
                }
                return del + hide + auhide;
            }
        }]],
        pagination : true,
        rownumbers : false
    });
}

// 搜索
function SearchEve() {
	setBtnDisabled("search", true);
    var keywords = $.trim($('#keyword').val());var subCatalog = $('#keyColumn option:selected').val();
    var queryParams = $('#softList').datagrid('options').queryParams;
    var id = $.trim($('#keyId').val());
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


    queryParams.catalog = 100;

    if(subCatalog == null) {
        queryParams.subCatalog = '';
    } else {
        queryParams.subCatalog = subCatalog;
    }

    queryParams.page = 1;

    // 主显示使用
    $('#softList').datagrid('options').pageNumber = 1;
    // page对象 中的pageNumber对象
    var pager = $('#softList').datagrid('getPager');
    $(pager).pagination('options').pageNumber = 1;
    $('#softList').datagrid('reload');
}

// 显示
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

var catalogMap = [], tagsMap = [], topicsMap = [];

// 加载分类
function loadType() {
    var $keyColumn = $("#keyColumn");
    var $keyword = $("#keyword");
    var $idsBtn = $("#idsBtn");

    $.ajax({
        type : "GET",
        url : urlWithParam + "/admin/catalog/list.json?pid=100",
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

// 加载标签
function LoadTag() {
    $.ajax({
        type : "POST",
        url : "../admin/tag/normaltag-list.json",
        traditional : true,
        dataType : 'json',
        success : function(data) {
            var len = data.rows.length

            for(var i = 0; i < len; i++) {
                if(data.rows[i].catalog !== 1) {
                    tagsMap.push(data.rows[i])
                }
            }
        }
    });
}

// 加载专题数据
function LoadTopics() {
    $.ajax({
        type : "POST",
        url : "../admin/tag/topic-list.json",
        traditional : true,
        dataType : 'json',
        success : function(data) {
            var len = data.rows.length

            for(var i = 0; i < len; i++) {
                if(data.rows[i].catalog !== 1) {
                    topicsMap.push(data.rows[i])
                }
            }
        }
    });
}

// 批量修改分类
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
                    "catalog" : 100,
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
                    "catalog" : 100,
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

// 修改标签
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

// 更新标签
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
            }
            ;

            $.ajax({
                type : "POST",
                url : urlWithParam + "/admin/tagapp/save-tagapp.d",
                traditional : true,
                data : {
                    "appId" : softId,
                    "tagId" : tagsids,
                    "tagName" : tagnames,
                    "tagType" : tagTypes,
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

// 批量修改标签
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

// 加载批量标签信息
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

// 批量更新标签
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
            }
            ;

            for(var id = 0; id < tagsLen; id++) {
                tagsid = parseInt($TagsMenu.find("a[value=1]").eq(id).attr("tagid"));
                tagsids[id] = tagsid;
                tagname = $TagsMenu.find("a[value=1]").eq(id).attr("tagname");
                tagnames[id] = tagname;
                tagType = $TagsMenu.find("a[value=1]").eq(id).attr("tagType");
                tagTypes[id] = tagType;
            }
            ;

            $.ajax({
                type : "POST",
                url : urlWithParam + "/admin/tagapp/save-tagapp.d",
                traditional : true,
                data : {
                    "appId" : softIds,
                    "tagId" : tagsids,
                    "tagName" : tagnames,
                    "tagType" : tagTypes,
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

// 修改专题
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

// 更新专题
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
            }
            ;

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

// 批量修改专题
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

// 加载批量专题信息
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

// 批量更新专题
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
            }
            ;

            for(var id = 0; id < tagsLen; id++) {
                tagsid = parseInt($TopicssMenu.find("a[value=1]").eq(id).attr("tagid"));
                tagsids[id] = tagsid;
                tagname = $TopicssMenu.find("a[value=1]").eq(id).attr("tagname");
                tagnames[id] = tagname;
                tagType = $TopicssMenu.find("a[value=1]").eq(id).attr("tagType");
                tagTypes[id] = tagType;
            }
            ;

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

// 修改增减量
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