
$(document).ready(function() {
    AddEve();
    DelEve();
    AddCpuEve();
    DelCpuEve();
    EditIndexImg();
    EditImg();
    PostEve();
    SelectEve();
    PacksEve();
    LoadBrandList();
    loadPhoneId()
    AddClList("addCpu", "unPhonelist", "unPhonecon", "unPhonebtn", "unPhonepid", "unsupportPhoneName");
});

function SelectEve(){
    var $subCatalog = $("#subCatalog"),
    $subCatalogName = $("#subCatalogName"); 
    
    $subCatalog.bind({
        change : function() {
            $subCatalogName.val($(this).find("option:selected").html())
        }
    })
}

var liHTML = "<input name='imageUrl' type='text' class='f_l w_250 mr_10' /><input name='imageFile' type='file' class='f_l mr_10' /><span class='btn w_80 f_l'><button type='button' class='w_30 f_l mr_5' name='add'>+</button><button type='button' class='w_30 f_l mr_5' name='del'>x</button></span><span class='f_l lh_20'><span class='co_red'>*</span> 图片大小不超过100KB</span>";
// 添加图片行
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

// 删除图片行
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

var cpuHtml = '<select class="f_l w_160 mr_10" name="bigGamePacks[0].cputype"><option value="0" selected="selected">未知</option><option value="1">高通</option><option value="2">三星</option><option value="3">联发科</option><option value="4">德州仪器</option><option value="5">Intel</option><option value="6">Nvidia</option><option value="7">Marvell</option><option value="8">海思</option></select><input name="url" type="text" class="f_l w_240 mr_10"/><input name="size" type="text" class="f_l w_130 mr_10"/><input name="freeSize" type="text" class="f_l w_130 mr_10"/><input name="unsupportPhoneType" type="hidden"/><input name="unsupportPhoneName" type="text" class="f_l w_150 mr_10"/><span class="btn w_70 f_l"><button type="button" class="w_30 f_l mr_5" name="add">+</button><button type="button" class="w_30 f_l" name="del">x</button></span>'
// 添加CPU行
function AddCpuEve() {
    var add = $("#addCpu button[name='add']");
    
    add.on("click", function() {
        var ty_html = $(this).parent().parent("li").html();
        
        $(this).parent().parent().after(["<li>", cpuHtml, "</li>"].join(''));
        add.unbind();
        AddCpuEve();
        DelCpuEve();
        AddClList("addCpu", "unPhonelist", "unPhonecon", "unPhonebtn", "unPhonepid", "unsupportPhoneName");
        PacksEve()
    });
}

// 删除CPU行
function DelCpuEve() {
    var del = $("#addCpu button[name='del']");
    del.on("click", function() {
        var sum = $("#addCpu li").length;
        if(sum > 1) {
            var $this = $(this).parent().parent("li");
            $this.remove();
        }
        PacksEve()
    });
};

function PacksEve(){
    var $addCpu = $("#addCpu"),
    len = $addCpu.children("li").length;
    
    for(var i=0;i<len;i++){
        $addCpu.children("li").eq(i).children("select").attr("name","bigGamePacks["+i+"].cputype");
        $addCpu.children("li").eq(i).children("input").eq(0).attr("name","bigGamePacks["+i+"].url");
        $addCpu.children("li").eq(i).children("input").eq(1).attr("name","bigGamePacks["+i+"].size");
        $addCpu.children("li").eq(i).children("input").eq(2).attr("name","bigGamePacks["+i+"].freeSize");
        $addCpu.children("li").eq(i).children("input").eq(3).attr("name","bigGamePacks["+i+"].unsupportPhoneType");
        $addCpu.children("li").eq(i).children("input").eq(4).attr("name","bigGamePacks["+i+"].unsupportPhoneName");
    }
}

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

function AddClList(obj, obj1, obj2, obj3, obj4, name) {
    var $obj = $("#" + obj + ""),
    $list = $("#" + obj1 + ""),
    $con = $("#" + obj2 + ""),
    $btn = $("#" + obj3 + ""),
    $add = $("#" + obj4 + ""),
    $brandList = $("#brandList"),
    $productList = $("#productList"),
    $mobileBox = $("#mobileBox"),
    $brandTit = $("#brandTit"),
    $returnList = $("#returnList"),
    $searchBrand = $("#searchBrand"),
    $searchBTit = $("#searchBTit"),
    html = "",
    len = 0,
    phoneType = "",
    phoneId = "",
    newPhoneId = "",
    IdSum = "",
    phoneIdList = "",
    top = 0,
    left = 0;

    $list.bind({
        mouseover : function() {
            $(this).attr("name", "1")
        },
        mouseleave : function() {
            $(this).attr("name", "0")
        }
    });

    $obj.find("input[name*=" + name + "]").bind({
        click : function() {
            $mobileBox.css({"margin-left":0});
            $brandList.children("a").removeClass();
            $brandTit.text("手机品牌");
            
            $obj.find("input[name*=" + name + "]").attr("index", 0);
            $(this).attr("index", 1);
            top = $(this).offset().top;
            left = $(this).offset().left;
            $list.show();
            $list.css({
                "top" : (top + 22),
                "left" : left
            });

            phoneId = getS($(this).parent("li").children("input[name*='unsupportPhoneType']").val());
            phoneId = phoneId.split(';');
            IdSum = phoneId.length - 1;
            phoneIdList = "";
            
            if(IdSum>0){
                for(var b=1;b<IdSum;b++){
                    phoneIdList += phoneId[b] + "_";
                }
                
                $.ajax({
                    type: "post",
                    url: "/sjk-market-admin/admin/phone/get-phone.json?phoneIds="+phoneIdList,
                    dataType: 'json',
                    success: function(data){
                        len = data.data.length;
                        html = "";
                        phoneType = "";
                        newPhoneId = "";
                        for(var i=0;i<len;i++){
                            html += "<a name='0'><span class='con' phoneId='"+data.data[i].phoneId+"'>" + data.data[i].phoneType + "</span><span title='点击删除' class='close'></span></a>";
                            phoneType += data.data[i].phoneType + ";";
                            newPhoneId += data.data[i].phoneId + ";";
                        }
                        
                        $con.html(html);
                        $obj.find("input[name*='unsupportPhoneName'][index=1]").val(getS(phoneType));
                        $obj.find("input[name*='unsupportPhoneName'][index=1]").parent("li").children("input[name*='unsupportPhoneType']").val(getS(newPhoneId));
                        ClickDel();
                    }
                })
            }else{
                $con.html("");
            }
            
        }
    })
   
    $returnList.bind({
        click:function(){
            $mobileBox.stop().animate({"margin-left":0}, 300);
            $brandTit.text("手机品牌");
            $searchBTit.show();
            $searchBrand.show();
        }
    })

    $(document).mouseup(function(e) {
        if($list.attr("name") !== "1") {
            $list.hide();
        }
    })
}

function ProductList(brand){
    var $productList = $("#productList"),
    $unPhonecon = $("#unPhonecon"),
    $addCpu = $("#addCpu"),
    phoneType = "",
    oldPhoneType = "",
    len = 0,
    list = [],
    phoneId = "",
    oldPhoneId = "";
    
    $.ajax({
        type: "post",
        url: "/sjk-market-admin/admin/phone/phone-info.json?brand="+encodeURIComponent(brand),
        traditional : true,
        dataType: 'json',
        success: function(data){
            len = data.data.length;
            list = [];
            for(var i=0;i<len;i++){
                list.push("<a phoneId='"+data.data[i].phonelist[0].phoneId+"'>"+data.data[i].phonelist[0].phoneType+"</a>")
            }
            $productList.html(list.join(''));
            
            $productList.children("a").bind({
                click : function() {
                    phoneType = $(this).text();
                    phoneId = $(this).attr("phoneId");
                    
                    if(phoneType !== "") {
                        $unPhonecon.append("<a name='0'><span class='con' phoneId='"+phoneId+"'>" + phoneType + "</span><span title='点击删除' class='close'></span></a>");
                        oldPhoneType = $addCpu.find("input[name*='unsupportPhoneName'][index=1]").val();
                        oldPhoneId = $addCpu.find("input[name*='unsupportPhoneName'][index=1]").parent("li").children("input[name*='unsupportPhoneType']").val();
                        $unPhonecon.find(".close").unbind("click");
                        ClickDel();
                        $addCpu.find("input[name*='unsupportPhoneName'][index=1]").val(getS(oldPhoneType + phoneType));
                        $addCpu.find("input[name*='unsupportPhoneName'][index=1]").parent("li").children("input[name*='unsupportPhoneType']").val(getS(oldPhoneId + phoneId));
                        $unPhonecon.find("a").attr("name",0);
                        $unPhonecon.find("a").removeClass();
                    }
                }
            });
        }
    })
}

function LoadBrandList(){
    var $brandList = $("#brandList"),
    $mobileBox = $("#mobileBox"),
    $brandTit = $("#brandTit"),
    $searchBrand = $("#searchBrand"),
    $searchBTit = $("#searchBTit"),
    len = 0,
    brand = "",
    list = [];
    
    $.ajax({
        type: "post",
        url: "/sjk-market-admin/admin/phone/phone-info.json",
        dataType: 'json',
        success: function(data){
            len = data.data.length;
            
            for(var i=0;i<len;i++){
                list.push("<a>"+data.data[i]+"</a>")
            }
            $brandList.html(list.join(''));
            
            $brandList.children("a").bind({
                click:function(){
                    brand = $(this).text();
                    ProductList(brand);
                    $brandList.children("a").removeClass();
                    $(this).addClass("cur");
                    $mobileBox.stop().animate({"margin-left":-500}, 300);
                    $brandTit.text(brand+" - 手机型号");
                    $searchBTit.hide();
                    $searchBrand.hide();
                }
            });
            
            $searchBrand.bind({
                keyup:function(){
                    $brandList.children("a").hide();
                    $brandList.children("a:contains('"+ $(this).val() +"')").show()
                }
            })
        }
    })
}

function ClickDel() {
    var $unPhonecon = $("#unPhonecon"),
    $addCpu = $("#addCpu"),
    len = 0,
    PhoneType = "",
    PhoneId = "";
    
    $unPhonecon.find(".close").bind({
        click : function() {
            $(this).parent().remove();
            len = $unPhonecon.find(".close").length;
            PhoneType = "";
            PhoneId = "";
            for(var b = 0; b < len; b++) {
                PhoneType += $unPhonecon.find(".con").eq(b).text() + ";"
                PhoneId += $unPhonecon.find(".con").eq(b).attr("PhoneId") + ";"
            }
            $addCpu.find("input[name*='unsupportPhoneName'][index=1]").val(getS(PhoneType));
            $addCpu.find("input[name*='unsupportPhoneName'][index=1]").parent("li").children("input[name*='unsupportPhoneType']").val(getS(PhoneId));
        }
    })
}

function loadPhoneId(){
    var $addCpu = $("#addCpu"),
    phoneId = "",
    liLen = 0,
    len = 0,
    IdSum = 0,
    phoneIdList = "",
    newPhoneId = "",
    phoneType = "",
    c = 0;
    
    liLen = $addCpu.children("li").length;
    
    PhoneIdList()
    
    function PhoneIdList(){
        c++;
        if(c-1<liLen){
            phoneId = getS($addCpu.children("li").eq(c-1).children("input[name*='unsupportPhoneType']").val());
            phoneId = phoneId.split(';');
            IdSum = phoneId.length - 1;
            phoneIdList = "";
            
            if(IdSum>0){
                for(var b=1;b<IdSum;b++){
                    phoneIdList += phoneId[b] + "_";
                }
    
                $.ajax({
                    type: "post",
                    url: "/sjk-market-admin/admin/phone/get-phone.json?phoneIds="+phoneIdList,
                    dataType: 'json',
                    success: function(data){
                        len = data.data.length;
                        phoneType = "";
                        newPhoneId = "";
                        for(var i=0;i<len;i++){
                            phoneType += data.data[i].phoneType + ";";
                            newPhoneId += data.data[i].phoneId + ";";
                        }
                        $addCpu.children("li").eq(c-1).children("input[name*='unsupportPhoneName']").val(getS(phoneType));
                        $addCpu.children("li").eq(c-1).children("input[name*='unsupportPhoneType']").val(getS(newPhoneId));
                        
                        PhoneIdList()
                    }
                })
            }else{
                PhoneIdList()
            }
            
        }
        
    }
}

//提交验证
function PostEve() {
    var $saveBtn = $("#saveBtn"),
    $formData = $("#formData"),
    $name = $("#name"),
    $pkname = $("#pkname"),
    $osversion = $("#osversion"),
    $lastUpdateTime = $("#lastUpdateTime"),
    name = "",
    pkname = "",
    osversion = "",
    lastUpdateTime = "";
    
    $saveBtn.bind({
        click:function(){
            name = $name.val();
            pkname = $pkname.val();
            osversion = $osversion.val();
            lastUpdateTime = $lastUpdateTime.val();
            
            if(con_va(name,80) && pkname !== "" && osversion !== "" && lastUpdateTime !== "") {
                $formData.submit();
            } else {
                if(!con_va(name,80)) {
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
                if(lastUpdateTime =="") {
                    $lastUpdateTime.addClass("bo_red")
                }else{
                    $lastUpdateTime.removeClass("bo_red")
                }
            }
        }
    })
}

//****************************************************************
//* 名　　称：IsEmpty
//* 功    能：判断是否为空
//* 入口参数：fData：要检查的数据
//* 出口参数：True：空                              
//*           False：非空
//*****************************************************************
function IsEmpty(fData)
{
  return ((fData==null) || (fData.length==0) )
} 
//****************************************************************
//* 名　　称：IsInteger
//* 功    能：判断是否为正整数
//* 入口参数：fData：要检查的数据
//* 出口参数：True：是整数，或者数据是空的                            
//*           False：不是整数
//*****************************************************************
function IsInteger(obj)
{
	return !isNaN(obj);

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

function con_va(str,len){
    var chineseRegex = /[^\x00-\xff]/g;
    var str_sum = str.replace(chineseRegex,"**").length;
    if(str_sum > 0 && str_sum < len){
        return true
    }
}

function getS(str) {
    return str.replace(/(^[^;])/, ';$1').replace(/([^;]$)/, '$1;');
}