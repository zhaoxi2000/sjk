
$(document).ready(function() {
    AddTag("gameBtn","gameTag","gameBox","gameMsg");
    AddTag("softBtn","softTag","softBox","softMsg");
    AddTag("bigGameBtn","bigGameTag","bigGameBox","bigGameMsg");
    DelTag("gameBox","gameMsg");
    DelTag("softBox","softMsg");
    DelTag("bigGameBox","bigGameMsg");
    editTag("box");
    LoadTag()
});


function LoadTag(){
    var $gameBox = $("#gameBox"),
    $softBox = $("#softBox"),
    $bigGameBox = $("#bigGameBox"),
    len = 0,
    gamehtml ="",
    softhtml = "",
    bigGamehtml = "";
    
    $.ajax({
        type : "POST",
        url : "../admin/tag/normaltag-list.json",
        traditional : true,
        dataType : 'json',
        success : function(data) {
            len = data.rows.length;
            
            for(var i =0; i<len; i++){
                if(data.rows[i].catalog==2){
                    gamehtml += "<span><a data-id='"+data.rows[i].id+"' data-name='"+data.rows[i].name+"' data-rank='"+data.rows[i].rank+"' class='con'>"+data.rows[i].name+"</a><a data-id='"+data.rows[i].id+"' class='close' title='点击删除'></a></span>"
                }else if(data.rows[i].catalog==1){
                    softhtml += "<span><a data-id='"+data.rows[i].id+"' data-name='"+data.rows[i].name+"' data-rank='"+data.rows[i].rank+"' class='con'>"+data.rows[i].name+"</a><a data-id='"+data.rows[i].id+"' class='close' title='点击删除'></a></span>"
                }else if(data.rows[i].catalog==100){
                    bigGamehtml += "<span><a data-id='"+data.rows[i].id+"' data-name='"+data.rows[i].name+"' data-rank='"+data.rows[i].rank+"' class='con'>"+data.rows[i].name+"</a><a data-id='"+data.rows[i].id+"' class='close' title='点击删除'></a></span>"
                }
            };
            $gameBox.html(gamehtml);
            $softBox.html(softhtml);
            $bigGameBox.html(bigGamehtml);
        }
    });
}

function AddTag(btn,tag,box,msg){
    var $btn = $("#"+btn+""),
    $tag = $("#"+tag+""),
    $box = $("#"+box+""),
    $msg = $("#"+msg+""),
    tags = "",
    catalog = 0,
    keyVal = "多个标签用英文逗号“,”隔开";
    
    $btn.bind({
        click:function(){
            catalog = parseInt($tag.attr("name"));
            tags = $tag.val();
            
            if(tags !== "" && tags !== keyVal){
                $.ajax({
                    type : "POST",
                    url : "../admin/tag/add-names.d",
                    traditional : true,
                    data : {
                        "catalog" : catalog,
                        "names" : tags,
                        "tagType":2
                    },
                    dataType : 'json',
                    success : function(data) {
                        $msg.text("添加成功！");
                        setTimeout(function(){
                            $msg.text("");
                        },1000);
                        $tag.val(keyVal);
                        
                        LoadTag()
                    }
                });
            }
        }
    });
    
    $tag.bind({
        focus:function(){
            if($(this).val() == keyVal){
                $(this).val("")
            }
        },
        focusout:function(){
            if($(this).val() == ""){
                $(this).val(keyVal)
            }
        }
    });
    
}

// 修改标签名称
function editTag(obj) {
    var $obj = $("#"+obj+""),
    $editTag = $("#editTag"),
    $editPost = $("#editPost"),
    $editClose = $("#editClose"),
    id = 0,
    names = "",
    rank = 0;
    
    $obj.delegate("a[class=con]","dblclick",function(){
        $editTag.window({
            title : '修改标签名称',
            top : 100,
            width : 550,
            modal : true,
            shadow : false,
            closed : false,
            height : 250
        });
        $editTag.show();
        
        $editTag.find("input[name=id]").val($(this).data("id"));
        $editTag.find("input[name=names]").val($(this).data("name"));
        $editTag.find("input[name=rank]").val($(this).data("rank"));
    })
    
    $editPost.bind({
        click:function(){
            id = parseInt($editTag.find("input[name=id]").val());
            names = $editTag.find("input[name=names]").val();
            rank = parseInt($editTag.find("input[name=rank]").val());
            
            if(!rank)rank = ""
            
            $.ajax({
                type : "POST",
                url : "../admin/tag/update-names.d",
                data:{
                    "id" : id,
                    "names" : names,
                    "rank" : rank
                },
                traditional : true,
                dataType : 'json',
                success : function(data) {
                    showMessager("修改成功");
                    $editPost.parent().parent().parent().parent().parent().parent().parent().hide();
                    $editTag.hide();
                    $(".window-mask").hide();
                    LoadTag()
                }
            });
        }
    })
    
    $editClose.bind({
        click:function(){
            $(this).parent().parent().parent().parent().parent().parent().parent().hide();
            $editTag.hide();
            $(".window-mask").hide();
        }
    })
}

function DelTag(obj,msg){
    var $obj = $("#"+obj+""),
    $msg = $("#"+msg+""),
    tagid = 0;
    
    $obj.delegate("a[class=close]","click",function(){
        if(confirm("确定要删除该信息吗？删除将不能恢复！")){
            tagid = parseInt($(this).data("id"));
        
            $.ajax({
                type : "POST",
                url : "../admin/tag/"+tagid+".del.d",
                traditional : true,
                dataType : 'json',
                success : function(data) {
                    $msg.text("删除成功！");
                    setTimeout(function(){
                        $msg.text("");
                    },1000);
                        
                    LoadTag()
                }
            });
        }else{
            return false
        }
    })
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
