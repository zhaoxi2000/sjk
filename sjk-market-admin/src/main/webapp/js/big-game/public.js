
$(document).ready(function() {
    AddEve();
    DelEve();
    EditIndexImg();
    EditImg();
    PostEve()
});

var liHTML = "<input name='imageUrl' type='text' class='f_l w_250 mr_10' /><input name='imageFile' type='file' class='f_l mr_10' /><span class='btn w_80 f_l'><button type='button' class='w_30 f_l mr_5' name='add'>+</button><button type='button' class='w_30 f_l mr_5' name='del'>x</button></span>";
// 添加行
function AddEve() {
    var add = $("#addImg button[name='add']");
    add.on("click", function() {
        var ty_html = $(this).parent().parent("li").html();
        $(this).parent().parent().after(["<li>", liHTML, "</li>"].join(''));
        add.unbind();
        AddEve();
        DelEve();
        EditImg()
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

function EditEve(){
    var $formData = $("#formData"),
    $lastUpdateTime = $("#lastUpdateTime"),
    id=0,
    lastUpdateTime="";
    
    if(request.QueryString("id") !== "" && request.QueryString("id") !== null){
        var id = parseInt(request.QueryString("id"));
    
        $formData.attr("action","admin/biggame/"+id+".d");
        setTimeout(function(){
            $formData.attr("action","admin/biggame/save.d");
            lastUpdateTime = $lastUpdateTime.val().substring(0, 19)
            $lastUpdateTime.val(lastUpdateTime);
            $("#lastFetchTime").val(lastUpdateTime);
        },500)
    }
}


//判断图片
function EditImg(){
    var $addImg = $("#addImg"),
    imageFile ="",
    len=0;
    
    $("body").bind({
        mouseover:function(){
            len = $addImg.find("li").length
            
            for(var i=0;i<len;i++){
                var $this = $addImg.find("li").eq(i);
                imageFile = $this.find("input[name=imageFile]").val();
                if(imageFile){
                    $this.find("input[name=imageUrl]").removeAttr("name")
                }
            }
        }
    })
}

function EditIndexImg(){
    var $oldIndexImgUrl = $("#oldIndexImgUrl"),
    $indexImgUrl = $("#indexImgUrl"),
    $remoteIndexImgUrl = $("#remoteIndexImgUrl"),
    $indexImgfile = $("#indexImgfile"),
    oldIndexImgUrl = "",
    indexImgUrl = "",
    indexImgfile = "",
    $oldLogoUrl = $("#oldLogoUrl"),
    $logoUrl = $("#logoUrl"),
    $remoteLogoUrl = $("#remoteLogoUrl"),
    $logofile = $("#logofile"),
    oldLogoUrl = "",
    logoUrl = "",
    logofile = "";
    
    $("body").bind({
        mouseover:function(){
            oldIndexImgUrl = $oldIndexImgUrl.val();
            indexImgUrl = $indexImgUrl.val();
            indexImgfile = $indexImgfile.val();
            
            if(oldIndexImgUrl !== indexImgUrl){
                $remoteIndexImgUrl.val(indexImgUrl)
                $remoteIndexImgUrl.attr("name","remoteIndexImgUrl")
            }else{
                $remoteIndexImgUrl.removeAttr("name");
            }
            if(indexImgfile!==""){
                $indexImgfile.attr("name","indexImgFile")
            }else{
                $indexImgfile.removeAttr("name");
            }
            
            oldLogoUrl = $oldLogoUrl.val();
            logoUrl = $logoUrl.val();
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

//提交验证
function PostEve() {
    var $saveBtn = $("#saveBtn"),
    $formData = $("#formData"),
    $name = $("#name"),
    $pkname = $("#pkname"),
    $osversion = $("#osversion"),
    $language = $("#language"),
    $lastUpdateTime = $("#lastUpdateTime"),
    name = "",
    pkname = "",
    osversion = "",
    language = "",
    lastUpdateTime = "";

    $saveBtn.bind({
        click:function(){
            name = $name.val();
            pkname = $pkname.val();
            osversion = $osversion.val();
            language = $language.val();
            lastUpdateTime = $lastUpdateTime.val();
            
            if(con_va(name) && pkname !== "" && osversion !== "" && language !== "" && lastUpdateTime !== "") {
                $formData.submit();
            } else {
                if(!con_va(name)) {
                    $name.addClass("bo_red")
                }else{
                    $name.removeClass("bo_red")
                }
                if(pkname =="") {
                    $pkname.addClass("bo_red")
                }else{
                    $pkname.removeClass("bo_red")
                }
                if(osversion =="") {
                    $osversion.addClass("bo_red")
                }else{
                    $osversion.removeClass("bo_red")
                }
                if(language =="") {
                    $language.addClass("bo_red")
                }else{
                    $language.removeClass("bo_red")
                }
                if(lastUpdateTime =="") {
                    $lastUpdateTime.addClass("bo_red")
                }else{
                    $lastUpdateTime.removeClass("bo_red")
                }
            }
        }
    })
}

//接收参数验证
var request = {
    QueryString : function(val) {
        var uri = window.location.search;
        var re = new RegExp("" + val + "\=([^\&\?]*)", "ig");
        return ((uri.match(re)) ? (uri.match(re)[0].substr(val.length + 1))
                : null);
    }
}

function con_va(str){
    var chineseRegex = /[^\x00-\xff]/g;
    var str_sum = str.replace(chineseRegex,"**").length;
    if(str_sum > 0 && str_sum < 80){
        return true
    }
}