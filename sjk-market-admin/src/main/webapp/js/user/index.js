//当前左侧分类对象
var curTreeObj = {};
/**
 * 初始化用户信息
 */
function initUserInfo() {
    // 初始化用户信息
    $.ajax({
        url : "./user/getCurUserInfo.d",
        dataType : 'json',
        type : "POST",
        success : function(data) {
            var user = data._result[0];
            if(user) {
                $("#username").text(user.name);
                $("#username").attr("userId", user.id);
                $("#username").attr("userType", user.userType);
            }
        },
        error : function(XmlHttpRequest, textStatus, errorThrown) {
            if(XmlHttpRequest.status == 403) {
                alert("请登录!");
                window.parent.location.href = "./login.html";
            } else {
                alert(textStatus + "," + errorThrown);
            }
        }
    });
}

// 绑定左侧导航树点击事件
function clickTree() {
    $("#app_menu a[class*=link]").live("click", function() {
        $("#app_menu a[class*=link]").removeClass("a_cur");
        $(this).addClass("a_cur");

        var treeId = $(this).attr("typeId");
        var treeName = $(this).attr("name");
        var treeDesc = $(this).text();
        var kindId = $(this).attr("kind");
        curTreeObj = new NavTree(kindId, treeId, treeName, treeDesc);
        $(this).attr("target", "softiframe");
        $("#treeNode").attr("kindId", kindId);
        $("#treeNode").attr("treeId", treeId);
        $("#treeNode").attr("treeName", treeName);
        $("#treeNode").attr("treeDesc", treeDesc);
    });
}

function NavTree(kindId, treeId, treeName, treeDesc) {
    this.kindId = kindId;
    this.treeId = treeId;
    this.treeName = treeName;
    this.treeDesc = treeDesc;
}

// 导航菜单
function NavEve() {
    var $nav_li_a = $("#nav li a");
    var $menu_ul = $("#menu ul");

    $menu_ul.eq(0).show();

    $nav_li_a.each(function(i, n) {
        $(n).unbind("click").click(function() {
            $nav_li_a.removeClass("n_cur");
            $(n).addClass("n_cur");
            $menu_ul.hide();
            $menu_ul.eq(i).show();
        })
    })
}

// 折叠菜单
function MenEve(id) {
    var $menu_a = $("#" + id + " a[class='link']");
    var $menu_up = $("#" + id + " a[class*='up']");

    $menu_up.toggle(function() {
        $menu_a.removeClass("a_cur");
        $('#softiframe').attr('src', $(this).attr('href'));
        $(this).addClass("la_cur");
        $(this).parent("li").css({
            "height" : "100%"
        });
        var treeId = $(this).attr("typeId");
        var treeName = $(this).attr("name");
        var treeDesc = $(this).text();
        var kindId = $(this).attr("kind");

        curTreeObj = new NavTree(kindId, treeId, treeName, treeDesc);
        $(this).attr("target", "softiframe");
        $("#treeNode").attr("kindId", kindId);
        $("#treeNode").attr("treeId", treeId);
        $("#treeNode").attr("treeName", treeName);
        $("#treeNode").attr("treeDesc", treeDesc);

    }, function() {
        $menu_a.removeClass("a_cur");
        $('#softiframe').attr('src', $(this).attr('href'));
        $(this).removeClass("la_cur");
        $(this).parent("li").css({
            "height" : "32px"
        });
        var treeId = $(this).attr("typeId");
        var treeName = $(this).attr("name");
        var treeDesc = $(this).text();
        var kindId = $(this).attr("kind");
        curTreeObj = new NavTree(kindId, treeId, treeName, treeDesc);
        $(this).attr("target", "softiframe");
        $("#treeNode").attr("kindId", kindId);
        $("#treeNode").attr("treeId", treeId);
        $("#treeNode").attr("treeName", treeName);
        $("#treeNode").attr("treeDesc", treeDesc);
    });
    if(id != "app_menu") {
        $menu_a.click(function() {
            $menu_a.removeClass("a_cur");
            $(this).addClass("a_cur");
        })
    }
}