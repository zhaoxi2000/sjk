var urlWithParam = "/sjk-market-admin";

$(document).ready(function() {
    loadMetroList("", 0);
    picTypeChecked("addMetro","AddPics","AddBigPics");
    picTypeChecked("editMetro","EditPics","EditBigPics")

    $('#addBtn').click(function() {
        AddMetro()
    });

    $("#keyType").change(function() {
        var type = $("#keyType option:selected").val();
        var hidden = $("#keyHidden option:selected").val();
        if(!isNull(hidden)) {
            loadMetroList(type, hidden, null);
        } else {
            loadMetroList(type, null, null);
        }
    });
    $("#keyHidden").change(function() {
        var hidden = parseInt($(this).val());
        if(hidden == 0) {
            loadMetroList(0, "");
        } else if(hidden == 1) {
            loadMetroList(1, "");
        } else {
            loadMetroList("", "");
        }
    });

    $('#btnDeleted').toggle(function() {
        loadMetroList("", 1);
        $(this).text("显示所有");
    }, function() {
        loadMetroList("", 0);
        $(this).text("显示已刪除");
    });
});
var metroMap = [];

function loadMetroList(hidden, deleted) {
    var $List = $("#List"), html = "", src = "", sum = 0, len = 0, list = [];
    url = urlWithParam + "/admin/featuredapp/search.json?page=1&rows=50&hidden=" + hidden + "&deleted=" + deleted;

    $.ajax({
        type : "POST",
        url : url,
        dataType : 'json',
        success : function(data) {
            metroMap = data;
            len = data.rows.length;

            for(var i = 0; i < len; i++) {
                
                if(data.rows[i].picType==3){
                    src = data.rows[i].bigPics;
                }else{
                    src = data.rows[i].pics;
                }
                
                list = [];
                if(src){
                    src = src.split(',');
                    sum = src.length;
                    for(var b = 0; b < sum; b++) {
                        if(src[b] != null && src[b] != "") {
                            list.push("<img class='wh_40 ml_0 mr_10 mt_10 mb_10' src='" + src[b] + "' />");
                        }
                    }
                }
                
                html += '<tr>';
                html += '    <td>' + getTimeStr(data.rows[i].opTime) + '</td>';
                html += '    <td>' + data.rows[i].id + '</td>';

                if(data.rows[i].type == 1) {
                    html += '    <td>单个应用</td>';
                } else if(data.rows[i].type == 2) {
                    html += '    <td>专题</td>';
                }

                if(data.rows[i].name) {
                    html += '    <td>' + data.rows[i].name + '</td>';
                } else {
                    html += '    <td></td>';
                }

                if(data.rows[i].picType == 1) {
                    html += '    <td>大图</td>';
                } else if(data.rows[i].picType == 2) {
                    html += '    <td>小图</td>';
                } else if(data.rows[i].picType == 3) {
                    html += '    <td>中图</td>';
                }
                html += '    <td>' + list.join('') + '</td>';

                if(data.rows[i].hidden == true) {
                    html += '    <td><span class="co_dg f_l">隐藏</span></td>';
                } else {
                    html += '    <td><span class="co_green f_l">显示</span></td>';
                }
                html += '    <td>' + data.rows[i].rank + '</td>';
                if(data.rows[i].hidden == true) {
                    html += '<td>' + '  <a onclick="ShowEve(' + data.rows[i].id + ',' + data.rows[i].type + ')" class="co_dg f_l mr_15">显示</a>' + '<a onclick="editMetro(' + data.rows[i].id + ')" class="co_bl f_l mr_15">编辑</a>';
                    if(data.rows[i].deleted == true) {
                        html += '<a onclick="DelBack(' + data.rows[i].id + ',' + data.rows[i].type + ',' + data.rows[i].hidden + ')" class="co_bl f_l">还原删除</a>';
                    } else {
                        html += '<a onclick="DelEve(' + data.rows[i].id + ',' + data.rows[i].type + ',' + data.rows[i].hidden + ')" class="co_bl f_l">删除</a>';
                    }
                    html += '</td>';
                } else {
                    html += '<td>' + '  <a onclick="HideEve(' + data.rows[i].id + ',' + data.rows[i].type + ')" class="co_green f_l mr_15">隐藏</a>' + '<a onclick="editMetro(' + data.rows[i].id + ')" class="co_bl f_l mr_15">编辑</a>';
                    if(data.rows[i].deleted == true) {
                        html += '<a onclick="DelBack(' + data.rows[i].id + ',' + data.rows[i].type + ',' + data.rows[i].deleted + ')" class="co_bl f_l">还原删除</a>';
                    } else {
                        html += '<a onclick="DelEve(' + data.rows[i].id + ',' + data.rows[i].type + ',' + data.rows[i].hidden + ')" class="co_bl f_l">删除</a>';
                    }
                    html += '</td>';
                }
                html += '</tr>';
            }

            $List.html(html);
            $List.find("tr:odd").addClass("trhover")
        }
    })
}

function getTimeStr(value) {
    var time = new Date();
    var mm = time.getMonth() + 1;
    if(mm < 9) {
        mm = "0" + mm
    }
    ;
    var dd = time.getDate();
    if(dd < 10) {
        dd = "0" + dd
    }
    ;
    var mmdd = mm + "-" + dd;
    var showValmmdd = value.substring(5, 10);
    var showVal = value.substring(2, 16);
    if(mmdd == showValmmdd) {
        return showVal + "<span class='co_red'>新</span>"
    } else {
        if(showVal != "") {
            return showVal;
        } else {
            return value.substring(2, 16)
        }
    }
}

// 新增推荐
function AddMetro() {
    var $addMetro = $("#addMetro"), $addPost = $("#addPost"), $closePost = $("#closePost"), $formData = $("#formData");

    $addMetro.window({
        title : '新增推荐',
        top : 'center',
        width : 600,
        modal : true,
        shadow : false,
        closed : false,
        height : 450
    });
    $addMetro.show();
    $addPost.bind({
        click : function() {
            $formData.submit();

            $(this).parent().parent().parent().parent().parent().parent().parent().parent().hide();
            $addMetro.hide();
            $(".window-mask").hide();
            setTimeout(function() {
                window.location.href = './list.html'
            }, 1000)
        }
    })

    $closePost.bind({
        click : function() {
            $(this).parent().parent().parent().parent().parent().parent().parent().parent().hide();
            $addMetro.hide();
            $(".window-mask").hide();
            setTimeout(function() {
                $addMetro.find("input").val("")
            }, 200)
        }
    })

    appOrTagName("addtype", "addappOrTagName")
}

// 修改推荐
function editMetro(id) {
    var $editMetro = $("#editMetro"), $EditPics = $("#EditPics"), $EditBigPics = $("#EditBigPics"), $formEditData = $("#formEditData"), $editPost = $("#editPost"), $editClose = $("#editClose"), pic = "", plen = 0, name="", len = metroMap.rows.length;

    $editMetro.window({
        title : '修改推荐',
        top : 'center',
        width : 600,
        modal : true,
        shadow : false,
        closed : false,
        height : 450
    });
    $editMetro.show();

    for(var i = 0; i < len; i++) {
        if(metroMap.rows[i].id == id) {
            $editMetro.find("input[name=id]").val(id);
            $editMetro.find("input[name=strImageUrls]").val(metroMap.rows[i].pics);
            $editMetro.find("input[name=shortDesc]").val(metroMap.rows[i].shortDesc);
            $editMetro.find("input[name=name]").val(metroMap.rows[i].name);
            $editMetro.find("input[name=rank]").val(metroMap.rows[i].rank);
            $editMetro.find("input[name=appOrTagId]").val(metroMap.rows[i].appOrTagId);
            $editMetro.find("input[name=type][value=" + metroMap.rows[i].type + "]").attr('checked', 'checked');
            $editMetro.find("input[name=picType][value=" + metroMap.rows[i].picType + "]").attr('checked', 'checked');
            $editMetro.find("input[name=hidden][value=" + metroMap.rows[i].hidden + "]").attr('checked', 'checked');
            
            if(metroMap.rows[i].picType==3){
                pic = metroMap.rows[i].bigPics;
                name = "bigPics";
                
                $EditPics.hide();
                $EditBigPics.show();
            }else{
                pic = metroMap.rows[i].pics;
                name = "pics";
                
                $EditPics.show();
                $EditBigPics.hide();
            }
            
            if(pic){
                pic = pic.split(',');
                plen = pic.length;
    
                for(var b = 0; b < plen; b++) {
                    $editMetro.find("input[name='"+name+"']").eq(b).val(pic[b]);
                    $editMetro.find("img").eq(b).attr('src', pic[b]);
                    $editMetro.find("img").eq(b).show()
                }
            }
            
        }
    }

    $editPost.bind({
        click : function() {
            var len = $editMetro.find("input[name=picsFile]").length

            for(var i = 0; i < len; i++) {
                if($editMetro.find("input[name=picsFile]").eq(i).val()) {
                    $editMetro.find("input[name=picsFile]").eq(i).prev("input[name=pics]").attr("name", "")
                }
            }

            $formEditData.submit();

            $(this).parent().parent().parent().parent().parent().parent().parent().parent().hide();
            $editMetro.hide();
            $(".window-mask").hide();
            setTimeout(function() {
                window.location.href = './list.html'
            }, 1200)
        }
    })

    $editClose.bind({
        click : function() {
            $(this).parent().parent().parent().parent().parent().parent().parent().parent().hide();
            $editMetro.hide();
            $(".window-mask").hide();
            setTimeout(function() {
                $editMetro.find("input").val("")
            }, 200)
        }
    })

    appOrTagName("edittype", "editappOrTagName")
}

function appOrTagName(obj1, obj2) {
    var $type = $("#" + obj1 + ""), 
    $appOrTagName = $("#" + obj2 + "");

    $type.children("input").bind({
        click : function() {
            if(parseInt($(this).val()) == 1) {
                $appOrTagName.text("软件ID：")
            } else {
                $appOrTagName.text("专题ID：")
            }
        }
    })
}

function picTypeChecked(obj1,obj2,obj3) {
    var $obj1 = $("#" + obj1 + ""),
    $obj2 = $("#" + obj2 + ""),
    $obj3 = $("#" + obj3 + "");

    $obj1.find("input[name=picType]").bind({
        click : function() {
            if(parseInt($(this).val()) == 3) {
                $obj2.hide();
                $obj3.show()
            } else {
                $obj3.hide();
                $obj2.show()
            }
        }
    })
}

// 显示隐藏
function ShowEve(id, type) {
    var msg = "您确定要显示吗？";

    if(confirm(msg) == true) {
        $.ajax({
            type : "POST",
            url : urlWithParam + "/admin/featuredapp/show.d",
            traditional : true,
            data : {
                "id" : id,
                "type" : String(type)
            },
            dataType : 'json',
            success : function(data) {
                if(data.result.msg == "OK!") {
                    showMessager("显示操作成功");
                    setTimeout(function() {
                        window.location.href = './list.html'
                    }, 200)
                } else {
                    showMessager(data.result.msg);
                }
            }
        });
    } else {
        return false;
    }
}

function HideEve(id, type) {
    var msg = "您确定要隐藏吗？";
    if(confirm(msg) == true) {
        $.ajax({
            type : "POST",
            url : urlWithParam + "/admin/featuredapp/hide.d",
            traditional : true,
            data : {
                "id" : id,
                "type" : String(type)
            },
            dataType : 'json',
            success : function(data) {
                if(data.result.msg == "OK!") {
                    showMessager("隐藏操作成功");
                    setTimeout(function() {
                        window.location.href = './list.html'
                    }, 200)
                } else {
                    showMessager(data.result.msg);
                }
            }
        });
    } else {
        return false;
    }
}

// 删除
function DelEve(id, type, hidden) {
    var msg = "您确定要删除吗？";

    $.messager.confirm('确认', '您确认要删除这条记录吗?', function(row) {
        if(row) {
            $.ajax({
                type : "POST",
                url : urlWithParam + "/admin/featuredapp/deleted.d",
                traditional : true,
                data : {
                    "id" : id,
                    "type" : String(type),
                    "deleted" : true
                },
                dataType : 'json',
                success : function(data) {
                    if(data.result.msg == "OK!") {
                        setTimeout(function() {
                            window.location.href = './list.html'
                        }, 200)
                        showMessager("删除操作成功");
                    } else {
                        showMessager(data.result.msg);
                    }
                }
            });
        } else {
            return false;
        }
    });
}

// 还原删除
function DelBack(id, type, deleted) {
    var msg = "您确定要删除吗？";
    $.messager.confirm('确认', '您确认要把这条记录还原吗?', function(row) {
        if(row) {
            $.ajax({
                type : "POST",
                url : urlWithParam + "/admin/featuredapp/deleted.d",
                traditional : true,
                data : {
                    "id" : id,
                    "type" : String(type),
                    "deleted" : false
                },
                dataType : 'json',
                success : function(data) {
                    if(data.result.msg == "OK!") {
                        setTimeout(function() {
                            window.location.href = './list.html'
                        }, 200)
                        showMessager("还原操作成功");
                    } else {
                        showMessager(data.result.msg);
                    }
                }
            });
        } else {
            return false;
        }
    });
}

function showMessager(msg) {
    if(msg == null || msg == "" || msg == undefined)
        msg = "操作成功";
    $.messager.show({
        msg : msg,
        title : '提示',
        showType : 'slide',
        timeout : 3000
    });
}