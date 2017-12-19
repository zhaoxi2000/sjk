var urlWithParam = "/sjk-market-admin";

$(document).ready(function() {
    SetType();
    Synch();
    LoadTag();
    AddEve();
    DelEve();
    EditImg();
    
    $("#catalog").bind({
        change : function() {
            SetType()
        }
    })
    
    $("#editTagBtn").bind({
        click:function(){
            editTagWin()
        }
    })
});

var liHTML = "<input name='strImageUrls' type='text' class='f_l w_250 mr_10' /><span class='btn w_70 f_l'><button type='button' class='w_30 f_l mr_5' name='add'>+</button><button type='button' class='w_30 f_l mr_5' name='del'>x</button></span>";
// 添加行
function AddEve() {
    var add = $("#addImg button[name='add']");
    add.on("click", function() {
        var ty_html = $(this).parent().parent("li").html();
        $(this).parent().parent().after(["<li>", liHTML, "</li>"].join(''));
        add.unbind();
        AddEve();
        DelEve();
    });
}

// 删除行
function DelEve() {
    var del = $("#addImg button[name='del']");
    del.on("click", function() {
        var sum = $("#addImg li").length;
        if(sum > 1) {
            var $this = $(this).parent().parent("li");
            $this.remove();
        }
    });
};

//设置分类
function SetType(){
    var $catalog = $("#catalog"),
    $subCatalog = $("#subCatalog"),
    $lastUpdateTime = $("#lastUpdateTime"),
    $applUTime = $("#applUTime"),
    $marketlUTime = $("#marketlUTime"),
    catalog = parseInt($catalog.find("option:selected").attr("value")),
    subCatalog = parseInt($subCatalog.attr("subCatalog"));
    
    $.ajax({
        type : "GET",
        url : urlWithParam + "/admin/catalog/list.json?pid="+catalog,
        dataType : 'json',
        success : function(data) {
            var num = data.rows.length;
            var html = "";
            html += "<option>请选择分类</option>";
            for(var i = 0; i < num; i++) {
                html += "<option value=" + data.rows[i].id + ">" + data.rows[i].name + "</option>"
            }
            $subCatalog.html(html);
            $subCatalog.find("option[value="+subCatalog+"]").attr("selected","selected");
        }
    })
}

//同步
function Synch(){
    var $synch = $("#synch"),
    $newlogoUrl = $("#newlogoUrl"),
    $newdownloadUrl = $("#newdownloadUrl"),
    $newdescription = $("#newdescription"),
    $newupdateInfo = $("#newupdateInfo"),
    $oldlogoUrl = $("#oldlogoUrl"),
    $olddownloadUrl = $("#olddownloadUrl"),
    $olddescription = $("#olddescription"),
    $oldupdateInfo = $("#oldupdateInfo"),
    $oldstrImage = $("#oldstrImage"),
    $addImg = $("#addImg"),
    $ImageUrlsname = $("#ImageUrlsname"),
    strImageUrls = "",
    html = "";
    
    $synch.bind({
        click:function(){
            html = "";
            $("#remoteLogoUrl").val($newlogoUrl.val());
            $newlogoUrl.val($oldlogoUrl.val()).attr("name","logoUrl");
            $newlogoUrl.next("img").attr("src",$oldlogoUrl.val());
            
            $newdownloadUrl.val($olddownloadUrl.val()).attr("name","downloadUrl");
            
            $newdescription.html($olddescription.html().replace(/\r\n/ig, "<br />").replace(/\n/ig, "<br />")).attr("name","description");
            
            $newupdateInfo.html($oldupdateInfo.html().replace(/\r\n/ig, "<br />").replace(/\n/ig, "<br />")).attr("name","updateInfo");
            
            strImageUrls = $oldstrImage.val();
            strImageUrls = strImageUrls.split(',');
            var sum = strImageUrls.length-1;
            
            for(var i=0;i<sum;i++){
                html +='<li>';
                //html +='  	  <input name="oldImageUrls" type="hidden" value="" />';
                html +='    <input name="strImageUrls" value="'+strImageUrls[i]+'" type="text" class="f_l w_250 mr_10" />';
                html +='    <span class="btn w_70 f_l">';
                html +='        <button type="button" class="w_30 f_l mr_5" name="add">+</button>';
                html +='        <button type="button" class="w_30 f_l mr_5" name="del">x</button>';
                html +='    </span>';
                html +='    <img class="wh_20" src="'+strImageUrls[i]+'"/>';
                html +='</li>';
            };
            $addImg.html(html);
            AddEve();
            DelEve();
        }
    })
}

var tagsMap = [];

//加载标签
function LoadTag() {
    var $catalog = $("#catalog"),
    catalog = parseInt($catalog.find("option:selected").attr("value"));
    
    $.ajax({
        type : "POST",
        url : urlWithParam+"/admin/tag/normaltag-list.json",
        traditional : true,
        dataType : 'json',
        success : function(data) {
            var len = data.rows.length

            for(var i = 0; i < len; i++) {
                if(data.rows[i].catalog == catalog) {
                    tagsMap.push(data.rows[i])
                }
            }
        }
    });
}

//弹层
function editTagWin(){
    var $editTag = $("#editTag");
    
    $editTag.show();
    initTagMenu()
}

// 加载标签信息
function initTagMenu() {
    var tagwareArr = [], 
    len = tagsMap.length, 
    $TagMenu = $("#TagMenu"),
    $id = $("#id"),
    appId = parseInt($id.val());
    
    for(var i = 0; i < len; i++) {
        tagwareArr.push(["<li><a tagid='", tagsMap[i].id, "' tagname='", tagsMap[i].name, "' tagType='", tagsMap[i].tagType, "'>", tagsMap[i].name, "</a></li>"].join(''));
    }
    var html = [];
    html.push(tagwareArr.join(""));

    $TagMenu.html(html.join(""));

    $.ajax({
        type : "POST",
        url : urlWithParam + "/admin/tagapp/tags/" + appId + ".d",
        traditional : true,
        dataType : 'json',
        success : function(data) {
            var lentag = data.data.length;

            for(var t = 0; t < lentag; t++) {
                $TagMenu.find("a[tagid='" + data.data[t].tagId + "']").attr("value", 1).addClass("cur")
            }
            UpTag(appId)
        }
    })
}

function EditImg(){
    var $oldLogoUrl = $("#oldLogoUrl"),
    $newlogoUrl = $("#newlogoUrl"),
    $remoteLogoUrl = $("#remoteLogoUrl"),
    $logofile = $("#logofile"),
    oldLogoUrl = "",
    logoUrl = "",
    logofile = "";
    
    $("body").bind({
        mouseover:function(){
            oldLogoUrl = $oldLogoUrl.val();
            logoUrl = $newlogoUrl.val();
            logofile = $logofile.val();
            
            if(oldLogoUrl !== logoUrl){
                $remoteLogoUrl.val(logoUrl);
                $remoteLogoUrl.attr("name","remoteLogoUrl")
            }else{
                $remoteLogoUrl.removeAttr("name");
            }
            if(logofile!==""){
                $logofile.attr("name","logoFile")
            }else{
                $logofile.removeAttr("name");
            }
        }
    })
}

//更新标签
function UpTag(appId) {
    var $TagMenu_li_a = $("#TagMenu li a"), 
    $TagMenu = $("#TagMenu"), 
    $editTag = $("#editTag"), 
    $tagPost = $("#tagPost"),
    $tagClose = $("#tagClose");

    $TagMenu_li_a.bind({
        click : function() {
            if(parseInt($(this).attr("value")) !== 1) {
                $(this).attr("value", 1).addClass("cur");
            } else {
                $(this).attr("value", 0).removeClass("cur");
            }
        }
    });
    
    $tagClose.bind({
        click:function(){
            $editTag.hide()
        }
    })

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
                    "appId" : appId,
                    "tagId" : tagsids,
                    "tagName" : tagnames,
                    "tagType" : tagTypes,
                },
                dataType : 'json',
                success : function(data) {
                    if(data.result.msg == "OK!") {
                        $tagPost.unbind()
                    }
                }
            })
            $editTag.hide()
        }
    })
}