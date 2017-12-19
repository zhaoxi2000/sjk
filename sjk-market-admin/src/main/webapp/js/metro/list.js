var urlWithParam = "/sjk-market-admin";

$(document).ready(function() {
    loadMetroList("", null, "0");

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
        var type = $("#keyType option:selected").val();
        var hidden = $("#keyHidden option:selected").val();
        if(!isNull(hidden)) {
            loadMetroList(type, hidden, 0);
        } else {
            loadMetroList(type, null, 0);
        }
    });

    $('#btnShow').toggle(function() {
        loadMetroList("", 0, 0);
        $(this).text("显示所有");
    }, function() {
        loadMetroList("", null, 0);
        $(this).text("显示推荐");
    });
    $('#btnDeleted').toggle(function() {
        loadMetroList("", null, 1);
        $(this).text("显示所有");
    }, function() {
        loadMetroList("", null, 0);
        $(this).text("显示已刪除");
    });
});
var metroMap = [];

function loadMetroList(type, hidden, deleted) {
    var $aList = $("#aList"), $bList = $("#bList"), $cList = $("#cList"), html = "", ahtml = "", bhtml = "", chtml = "", src = "", sum = 0, list = [];
    url = urlWithParam + "/admin/metroapp/search.d";
    if(type == null || type == "" || type == undefined) {
        type = "";
    }
    if(!isNull(hidden) && !isNull(deleted)) {
        url = urlWithParam + "/admin/metroapp/search.d?hidden=" + hidden + "&deleted=" + deleted;
    } else {
        if(!isNull(deleted)) {
            url = urlWithParam + "/admin/metroapp/search.d?deleted=" + deleted;
        } else if(!isNull(hidden)) {
            url = urlWithParam + "/admin/metroapp/search.d?hidden=" + hidden;
        }
    }
    $.ajax({
        type : "POST",
        url : url,
        dataType : 'json',
        data : {
            "type" : type
        },
        success : function(data) {
            metroMap = data;
            var len = data.data.length;

            for(var i = 0; i < len; i++) {
                if(data.data[i].type == "1") {
                    ahtml += htmlEve(i)
                } else if(data.data[i].type == "2") {
                    bhtml += htmlEve(i)
                } else {
                    chtml += htmlEve(i)
                }
            }

            function htmlEve(i) {
                list = [];
                src = data.data[i].pics;
                src = src.split(',');
                sum = src.length;

                for(var b = 0; b < sum; b++) {
                    if(src[b] != null && src[b] != "") {
                        list.push("<img class='wh_40 ml_0 mr_10' src='" + src[b] + "' />");
                    }
                }
                ;
                html = "";
                html += '<tr height="50" ids=' + data.data[i].id + '>';
                html += '    <td>' + getTimeStr(data.data[i].opTime) + '</td>';
                if(data.data[i].type == "1") {
                    html += '    <td>A</td>';
                } else if(data.data[i].type == "2") {
                    html += '    <td>B</td>';
                } else {
                    html += '    <td>C</td>';
                }
                if(data.data[i].name) {
                    html += '    <td>' + data.data[i].name + '</td>';
                } else {
                    html += '    <td></td>';
                }
                html += '    <td>' + list.join('') + '</td>';
                if(data.data[i].tabname == "soft") {
                    html += '    <td>找应用</td>';
                } else {
                    html += '    <td>找游戏</td>';
                }
                if(data.data[i].hidden == true) {
                    html += '    <td><span class="co_dg f_l">隐藏</span></td>';
                } else {
                    html += '    <td><span class="co_green f_l">显示</span></td>';
                }
                if(data.data[i].hidden == true) {
                    html += '<td>' + '  <a onclick="ShowEve(' + data.data[i].id + ',' + data.data[i].type + ')" class="co_dg f_l mr_15">显示</a>' + '<a onclick="editMetro(' + data.data[i].id + ')" class="co_bl f_l mr_15">编辑</a>';
                    if(data.data[i].deleted == true) {
                        html += '<a onclick="DelBack(' + data.data[i].id + ',' + data.data[i].type + ',' + data.data[i].hidden + ')" class="co_bl f_l">还原删除</a>';
                    } else {
                        html += '<a onclick="DelEve(' + data.data[i].id + ',' + data.data[i].type + ',' + data.data[i].hidden + ')" class="co_bl f_l">删除</a>';
                    }
                    html += '</td>';
                } else {
                    html += '<td>' + '  <a onclick="HideEve(' + data.data[i].id + ',' + data.data[i].type + ')" class="co_green f_l mr_15">隐藏</a>' + '<a onclick="editMetro(' + data.data[i].id + ')" class="co_bl f_l mr_15">编辑</a>';
                    if(data.data[i].deleted == true) {
                        html += '<a onclick="DelBack(' + data.data[i].id + ',' + data.data[i].type + ',' + data.data[i].deleted + ')" class="co_bl f_l">还原删除</a>';
                    } else {
                        html += '<a onclick="DelEve(' + data.data[i].id + ',' + data.data[i].type + ',' + data.data[i].hidden + ')" class="co_bl f_l">删除</a>';
                    }
                    html += '</td>';
                }
                html += '</tr>';

                return html
            }


            $aList.html(ahtml);
            $bList.html(bhtml);
            $cList.html(chtml);
            $aList.find("tr:odd").addClass("trhover");
            $bList.find("tr:odd").addClass("trhover");
            $cList.find("tr:odd").addClass("trhover")
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
        top : 200,
        width : 600,
        modal : true,
        shadow : false,
        closed : false,
        height : 400
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
            }, 1200)
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
}

// 修改推荐
function editMetro(id) {
    var $editMetro = $("#editMetro"), $formEditData = $("#formEditData"), $editPost = $("#editPost"), $editClose = $("#editClose"), pic = "", plen = 0, len = metroMap.data.length;

    $editMetro.window({
        title : '修改推荐',
        top : 200,
        width : 600,
        modal : true,
        shadow : false,
        closed : false,
        height : 400
    });
    $editMetro.show();

    for(var i = 0; i < len; i++) {
        if(metroMap.data[i].id == id) {
            $editMetro.find("input[name=id]").val(id);
            $editMetro.find("input[name=strImageUrls]").val(metroMap.data[i].pics);
            $editMetro.find("input[name=shortDesc]").val(metroMap.data[i].shortDesc);
            $editMetro.find("input[name=name]").val(metroMap.data[i].name);
            $editMetro.find("input[name=url]").val(metroMap.data[i].url);
            $editMetro.find("input[name=type][value=" + metroMap.data[i].type + "]").attr('checked', 'checked');
            $editMetro.find("input[name=tabname][value=" + metroMap.data[i].tabname + "]").attr('checked', 'checked');
            $editMetro.find("input[name=hidden][value=" + metroMap.data[i].hidden + "]").attr('checked', 'checked');
            pic = metroMap.data[i].pics;
            pic = pic.split(',');
            plen = pic.length;

            for(var b = 0; b < plen; b++) {
                $editMetro.find("input[name=pics]").eq(b).val(pic[b]);
                $editMetro.find("img").eq(b).attr('src', pic[b]);
                $editMetro.find("img").eq(b).show()
            }
        }
    }

    $editPost.bind({
        click : function() {
            var len = $editMetro.find("input[name=picsFile]").length
            
            for(var i=0;i<len;i++){
                if($editMetro.find("input[name=picsFile]").eq(i).val()){
                    $editMetro.find("input[name=picsFile]").eq(i).prev("input[name=pics]").attr("name","")
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
}

// 显示隐藏
function ShowEve(id, type) {
    var msg = "您确定要显示吗？";

    if(confirm(msg) == true) {
        $.ajax({
            type : "POST",
            url : urlWithParam + "/admin/metroapp/show.d",
            traditional : true,
            data : {
                "id" : id,
                "type" : String(type)
            },
            dataType : 'json',
            success : function(data) {
                if(data.result.msg == "OK!") {
                    showMessager("显示操作成功");
                    loadMetroList("", null, "0");
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
    if(!checkData(type)) {
        var error = "";
        if(type == 1 || type == "1")
            error = "很抱歉，显示记录不能小于1个";
        if(type == 2 || type == "2")
            error = "很抱歉，显示记录不能小于4个";
        if(type == 3 || type == "3")
            error = "很抱歉，显示记录不能小于6个";
        alert(error);
        return;
    }
    if(confirm(msg) == true) {
        $.ajax({
            type : "POST",
            url : urlWithParam + "/admin/metroapp/hide.d",
            traditional : true,
            data : {
                "id" : id,
                "type" : String(type)
            },
            dataType : 'json',
            success : function(data) {
                if(data.result.msg == "OK!") {
                    showMessager("隐藏操作成功");
                    loadMetroList("", null, "0");
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
    // alert(hidden)
    // if(!checkData(type)) {
    // var error = "";
    // if(type == 1 || type == "1")
    // error = "很抱歉，显示记录不能小于1个";
    // if(type == 2 || type == "2")
    // error = "很抱歉，显示记录不能小于4个";
    // if((type == 3 || type == "3")&hidden){}
    // error = "很抱歉，显示记录不能小于6个";
    // alert(error);
    // return;
    // }

    $.messager.confirm('确认', '您确认要删除这条记录吗?', function(row) {
        if(row) {
            $.ajax({
                type : "POST",
                // url : urlWithParam + "/admin/metroapp/" + id + ".del.d",
                url : urlWithParam + "/admin/metroapp/deleted.d",
                traditional : true,
                data : {
                    "id" : id,
                    "type" : String(type),
                    "deleted" : true
                },
                dataType : 'json',
                success : function(data) {
                    if(data.result.msg == "OK!") {
                        loadMetroList("", null, "0");
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
                // url : urlWithParam + "/admin/metroapp/" + id + ".del.d",
                url : urlWithParam + "/admin/metroapp/deleted.d",
                traditional : true,
                data : {
                    "id" : id,
                    "type" : String(type),
                    "deleted" : false
                },
                dataType : 'json',
                success : function(data) {
                    if(data.result.msg == "OK!") {
                        loadMetroList("", null, "0");
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

/* 验证显示记录数 */
function checkData(type) {
    var num = 0;
    if(type == 1 || type == "1") {
        $("#aList tr").each(function() {
            var text = $(this).find("td:eq(5)").text();
            if(text == "显示") {
                num = num + 1;
            }
        });
        if(num <= 1) {
            return false;
        }
    }
    if(type == 2 || type == "2") {
        $("#bList tr").each(function() {
            var text = $(this).find("td:eq(5)").text();
            if(text == "显示") {
                num = num + 1;
            }
        });
        if(num <= 4) {
            return false;
        }
    }
    if(type == 3 || type == "3") {
        $("#cList tr").each(function() {
            var text = $(this).find("td:eq(5)").text();
            if(text == "显示") {
                num = num + 1;
            }
        });
        if(num <= 6) {
            return false;
        }
    }
    return true;
}