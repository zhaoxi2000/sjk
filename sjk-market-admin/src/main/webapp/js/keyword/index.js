var urlWithParam = "/sjk-market-admin";

$(document).ready(function() {
    $('#keyword').bind('keydown', function(e) {
        if(e.which == 13) {
            SearchEve();
        }
    });
    $('#search').click(function() {
        SearchEve();
    });
    $('#add').click(function() {
        AddKeyword()
    });
    loadGridTable();
    var p = $('#dataList').datagrid('getPager');
    $(p).pagination({
        onBeforeRefresh : function() {
        }
    });
	
	var $inputs = $('ul.inputsUl'),$inputVal = $inputs.find("input[name=targetKeyword]");

	bindEvent($inputs,$inputVal);
	
});

//事件绑定
function bindEvent(obj,vobj){
	obj.delegate('button','click',function(){
		var $this = $(this);
		(this.className.indexOf('plus')!=-1) ? addInput($this) : delInput($this,obj);	
		return false;
	});
/*	obj.delegate('input','blur',function(){
		var val = getVal(obj);
			vobj.val(val);
		
	});*/
		
	}
//添加行
function addInput(obj,vals){
	if(!vals){
		obj.parent('li').after('<li class="clear"><input type="text" class="f_l" value="" /> <button type="button" class="plus w_30 f_l mr_5" name="add">+</button> <button type="button" class="minus w_30 f_l" name="del">x</button></li>');
	}
	else{
		
		var html ='';
		for(var i=0,j=vals.length;i<j;i++){
			
			html += '<li class="clear"><input type="text" class="f_l" value="'+vals[i]+'" /> <button type="button" class="plus w_30 f_l mr_5" name="add">+</button> <button type="button" class="minus w_30 f_l" name="del">x</button></li>';
			}
		obj.html(html+'<input type="hidden" name="targetKeyword" />');
		}
	}
//删除行
function delInput(obj,pobj){
	if(pobj.find('li').length>1) obj.parent('li').remove();
	
	}
//取input值
function getVal(obj){
	var val = [],reData = {};
	$('input',obj).not('[name=targetKeyword]').each(function(){
		if(this.value!=''){
			var tval = $.trim(this.value.replace(/\,/g,''));
			if(!reData.hasOwnProperty(tval)){
				val.push(tval);
				reData[tval]=1;
				}
			}
		
		});
	return val;
	}
	
// 加载DataGrid控件
function loadGridTable() {
    $('#dataList').datagrid({
        loadMsg : '正在加载...',
        iconCls : 'icon-save',
        width : 950,
        nowrap : false,
        striped : true,
        collapsible : false,
        url : urlWithParam + "/admin/keyword/search.json",
        pageSize : 50,
        sortOrder : 'desc',
        idField : "id",
        onLoadError : function() {
            var params = arguments;
            if(params[0].status == 403) {
                alert("请登录.");
                window.parent.location.href = "../login.html";
            }
        },
        columns : [[{
            field : 'id',
            title : 'id',
            sortable : true,
            width : 50
        }, {
            field : 'name',
            title : '搜索词',
            sortable : true,
            width : 220,
            formatter : function(value, rec, index) {
                var html = '<a class="mr_10 co_bl f_l" onclick="EditKeyword(' + rec.id + ',\'' + rec.name + '\',\'' + rec.rankType + '\',\'' + rec.targetKeyword + '\')">' + value + '</a>';
                return html;
            }
        }, {
            field : 'rankType',
            title : '搜索内容和排序',
            sortable : true,
            width : 200,
            formatter : function(value, rec, index) {
				switch(value)
				{
					case 'DOWNLOAD':
						return '不仅名称搜索,按下载量排序';
					break;
					case 'DOCUMENT':
						return '不仅名称搜索,按分词排序';
					break;
					case 'ONLY_NAME_DOWNLOAD':
						return '仅名称搜索,按下载量排序';
					break;
					case 'ONLY_NAME_DOCUMENT':
						return '仅名称搜索,按分词排序';
					break;
					default:
						return '未定义';
					
				}
				
            }
        }, {
            field : 'targetKeyword',
            title : '目标搜索词',
            sortable : true,
            width : 400
        }, {
            field : 'operate',
            title : '操作',
            width : 80,
            formatter : function(value, rec, index) {
                var del = '<a class="mr_15 co_bl f_l" onclick="Delete(' + rec.id + ',' + index + ')">删除</a>';
                var edit = '<a class="mr_10 co_bl f_l" onclick="EditKeyword(' + rec.id + ',\'' + rec.name + '\',\'' + rec.rankType + '\',\'' + rec.targetKeyword + '\')">修改</a>';
                return edit + del;
            }
        }]],
        pagination : true,
        pagePosition:'both',//上下显示分页效果
		pagerFixedTop:true,//滚动固定上栏分页
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
	 setBtnDisabled("search",true);
    var q = $.trim($('#keyword').val());
    var queryParams = $('#dataList').datagrid('options').queryParams;
    if(q !== "") {
        queryParams.q = q;
    } else {
        queryParams.q = '';
    }
    queryParams.page = 1;

    $('#dataList').datagrid('options').pageNumber = 1;
    var pager = $('#dataList').datagrid('getPager');
    $(pager).pagination('options').pageNumber = 1;
    $('#dataList').datagrid('reload');
}

// 新增搜索词
function AddKeyword() {
    var $addkeyword = $("#addkeyword"), 
    $addPost = $("#addPost"),
    $addclose = $("#addclose"), 
    name = "",
    rankType = "",
    targetKeyword = "";

    $addkeyword.window({
        title : '新增搜索词',
        top : 200,
        width : 600,
        modal : true,
        shadow : false,
        closed : false,
        height : 500
    });
    $addkeyword.show();
    $addPost.unbind();

    $addPost.bind({
        click : function() {
            name = $addkeyword.find("input[name=name]").val();
            rankType = $addkeyword.find("input[name=rankType]:checked").val();
            targetKeyword = getVal($addkeyword.find('.inputsUl')) || $addkeyword.find("input[name=targetKeyword]").val();
			
            
            $.ajax({
                type : "POST",
                url : urlWithParam + "/admin/keyword/save.d",
                traditional : true,
                data : {
                    "name" : name,
                    "rankType" : rankType,
                    "targetKeyword" : targetKeyword,
                    "totalHits" : 0
                },
                dataType : 'json',
                success : function(data) {}
            });
            
            $(this).parent().parent().parent().parent().parent().parent().parent().parent().hide();
            $addPost.hide();
            $(".window-mask").hide();
            setTimeout(function() {
                window.location.href = './index.html'
            }, 200)
        }
    })

    $addclose.bind({
        click : function() {
            $(this).parent().parent().parent().parent().parent().parent().parent().parent().hide();
            $addkeyword.hide();
            $(".window-mask").hide()
        }
    })
	
	
}

// 修改搜索词
function EditKeyword() {
    var $editkeyword = $("#editkeyword"), 
    $editPost = $("#editPost"),
    $editclose = $("#editclose"),
    id = "",
    name = "",
    rankType = "",
    targetKeyword = "";
    
    $editkeyword.find("input[name=id]").val(arguments[0]);
    $editkeyword.find("input[name=name]").val(arguments[1]);
    $editkeyword.find("input[name=rankType][value="+arguments[2]+"]").attr("checked","checked");
	var val = arguments[3].split(',');
	addInput($editkeyword.find('.inputsUl'),val);
	$editkeyword.find("input[name=targetKeyword]").val(arguments[3]);
	
    $editkeyword.window({
        title : '修改搜索词',
        top : 200,
        width : 600,
        modal : true,
        shadow : false,
        closed : false,
        height : 500
    });
    $editkeyword.show();
    $editPost.unbind();

    $editPost.bind({
        click : function() {
            id = parseInt($editkeyword.find("input[name=id]").val());
            name = $editkeyword.find("input[name=name]").val();
            rankType = $editkeyword.find("input[name=rankType]:checked").val();
            targetKeyword = getVal($editkeyword.find('.inputsUl')) || $editkeyword.find("input[name=targetKeyword]").val();
            
            $.ajax({
                type : "POST",
                url : urlWithParam + "/admin/keyword/save.d",
                traditional : true,
                data : {
                    "id" : id,
                    "name" : name,
                    "rankType" : rankType,
                    "targetKeyword" : targetKeyword,
                    "totalHits" : 0
                },
                dataType : 'json',
                success : function(data) {}
            });
            
            $(this).parent().parent().parent().parent().parent().parent().parent().parent().hide();
            $editPost.hide();
            $(".window-mask").hide();
            setTimeout(function() {
                window.location.href = './index.html'
            }, 200)
        }
    })

    $editclose.bind({
        click : function() {
            $(this).parent().parent().parent().parent().parent().parent().parent().parent().hide();
            $editkeyword.hide();
            $(".window-mask").hide()
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
                url : urlWithParam + "/admin/keyword/"+id+".del.d",
                traditional : true,
                dataType : 'json',
                success : function(data) {}
            });
            $('#dataList').datagrid('unselectAll');
            $('#dataList').datagrid('reload');
            $(".window-mask").hide();
        }
    });
}