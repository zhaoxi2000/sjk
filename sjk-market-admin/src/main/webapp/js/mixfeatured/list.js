var urlWithParam = "/sjk-market-admin";
var mixfeatured = {
		init:function(){
			this
			.loadGridTable()
			.bindEve();
			
		},
		// 加载DataGrid控件
		loadGridTable:function(){
			var that = this;
			$('#dataList')
					.datagrid(
							{
								loadMsg : '正在加载...',
								iconCls : 'icon-save',
								width : '1580',
								nowrap : false,
								striped : true,
								collapsible : false,
								url : urlWithParam + "/admin/mixfeatured/search.json",
								pageSize : 50,
								sortOrder : 'desc',
								idField : "id",
								columns : [ [
										{
											field : 'opTime',
											title : '操作时间',
											sortable : true,
											width : 120,
											formatter : function(value) {
												return getTimeStr(value);
											}
										},
										{
											field : 'id',
											title : 'id',
											sortable : true,
											width : 50
										},
										{
											field : 'appOrTagId',
											title : 'AppId',
											sortable : true,
											width : 80
										},
										{

											field : 'name',
											title : '名称',
											sortable : true,
											width : 150,
											formatter : function(value, rec, index) {
												/*
												var url = urlWithParam
														+ "/tagapp/index.html?tagId="
														+ rec.id;
												var html = '<a class="mr_10 co_bl f_l" href="'
														+ url + '">' + value + '</a>';
													*/	
												return value;
											}
										},
										{
											field : 'type',
											title : '类型',
											sortable : true,
											width : 50,
											formatter : function(value, rec, index) {
												if (value == 1) {
													return "应用";
												} else if (value == 2) {
													return "专题";
												}
											}
										},
										{
											field : 'picType',
											title : '图片类型',
											sortable : true,
											width : 50,
											formatter : function(value, rec, index) {
												switch (value) {
												case 1:
													return "A";
													break;
												case 2:
													return "B";
													break;
												case 3:
													return "C";
													break;
												case 4:
													return "D";
													break;
												default:
													return "未定义";

												}
											}
										},
										{
											field : 'rank',
											title : '排序',
											sortable : true,
											width : 80
										}
										/*
										 * , { field : 'shortDesc', title : '简短说明',
										 * sortable : true, width : 80 }
										 */
										,
										{
											field : 'bigPics',
											title : '大图片',
											sortable : true,
											width : 50,
											formatter : function(value, rec, index) {
												var html = "";
												if (value != "" && value != null) {
													html = '<img src="'
															+ value
															+ '" class="w_20 h_20 f_l mt_4 mr_10" />';
												} else {
													html = "无图";
												}
												return html;
											}
										},
										{
											field : 'mediumPics',
											title : '中图片',
											sortable : true,
											width : 50,
											formatter : function(value, rec, index) {
												var html = "";
												if (value != "" && value != null) {
													html = '<img src="'
															+ value
															+ '" class="w_20 h_20 f_l mt_4 mr_10" />';
												} else {
													html = "无图";
												}
												return html;
											}
										},
										{
											field : 'smallPics',
											title : '小图片',
											sortable : true,
											width : 50,
											formatter : function(value, rec, index) {
												var html = "";
												if (value != "" && value != null) {
													html = '<img src="'
															+ value
															+ '" class="w_20 h_20 f_l mt_4 mr_10" />';
												} else {
													html = "无图";
												}
												return html;
											}
										},
										{
											field : 'hidden',
											title : '状态',
											sortable : true,
											width : 80,
											formatter : function(value, rec, index) {
												if (value == 1 || value == 'true'
														|| value == true) {
													return '<a onclick="mixfeatured.showEve('
															+ rec.id + ');">隐藏</a>';
												} else {
													return '<a onclick="mixfeatured.hiddenEve('
															+ rec.id + ');">显示</a>';
												}
											}
										},
										{
											field : 'operate',
											title : '操作',
											width : 120,
											formatter : function(value, rec, index) {
												var del = '<a class="mr_15 co_bl f_l" onclick="mixfeatured.del('
														+ rec.id + ')">删除</a>';
												var edit = '<a class="mr_10 co_bl f_l" onclick="mixfeatured.edit('
														+ rec.id
														+ ',\''
														+ rec.appOrTagId
														+ '\',\''
														+ rec.name
														+ '\',\''
														+ rec.type
														+ '\',\''
														+ rec.picType
														+ '\',\''
														+ rec.rank
														+ '\',\''
														+ rec.shortDesc
														+ '\',\''
														+ rec.hidden
														+ '\',\''
														+ rec.bigPics
														+ '\',\''
														+ rec.mediumPics
														+ '\',\''
														+ rec.smallPics
														+ '\');">修改</a>';
												return edit + del;
											}
										} ] ],
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
													// setBtnDisabled("search", false);
												}
											});
			return that;

		},
		searchEve:function(){


			var queryParams = $('#dataList').datagrid('options').queryParams;
			var keyPicType = $.trim($("#keyPicType option:selected").val());
			var keyType = $.trim($("#keyType option:selected").val());
			var keyHidden = $.trim($("#keyHidden option:selected").val());

			queryParams.picType = (keyPicType !== "" ? keyPicType : '');
			queryParams.type = (keyType !== "" ? keyType : '');
			queryParams.hidden = (keyHidden !== "" ? keyHidden : '');

			queryParams.page = 1;

			// 主显示使用
			$('#dataList').datagrid('options').pageNumber = 1;
			// page对象 中的pageNumber对象
			var pager = $('#dataList').datagrid('getPager');
			$(pager).pagination('options').pageNumber = 1;
			$('#dataList').datagrid('reload');

		},
		edit:function(id, appOrTagId, name, type, picType, rank, shortDesc, hidden,	bigPics, mediumPics, smallPics){
			var that = this;
			var box = $('#editMetro'), editPost = $('#editPost'), editClose = $('#editClose'), formEditData = $('#formEditData');
			var $edittype = $('#edittype');

			box.find("input[name='type']").removeAttr("checked");
			box.find("input[name='type'][value='" + type + "']").attr('checked','checked');

			box.find("input[name='picType']").removeAttr("checked");
			box.find("input[name='picType'][value='" + picType + "']").attr('checked','checked');

			box.find("input[name='hidden']").removeAttr("checked");
			var hiddenValue = 0;
			if (hidden == true || hidden == 'true') {
				hiddenValue = 1;
			}
			box.find("input[name='hidden'][value='" + hiddenValue + "']").attr('checked', 'checked');

			box.find('input[name="id"]').val(id);
			box.find('input[name="appOrTagId"]').val(appOrTagId);
			box.find('input[name="name"]').val(name);
			box.find('input[name="rank"]').val(rank);
			box.find('input[name="shortDesc"]').val(shortDesc);
			box.find('input[name="oldBigPicsUrl"]').val(bigPics);
			box.find('input[name="oldMediumPicsUrl"]').val(mediumPics);
			box.find('input[name="oldSmallPicsUrl"]').val(smallPics);

			$('#editBigPicsUrl').attr('src', bigPics);
			$('#editMediumPicsUrl').attr('src', mediumPics);
			$('#editSmallPicsUrl').attr('src', smallPics);

			box.window({
				title : '修改推荐',
				top : '100px',
				width : 600,
				modal : true,
				shadow : false,
				closed : false,
				height : 500
			});
			box.show();
			editPost.bind({
				click : function() {
					
					if(!that.isNull(box.find('input[name="appOrTagId"]'),'ID')) return false; 
					if(!that.isNull(box.find('input[name="name"]'),'名称')) return false; 
					if(!that.isNull(box.find('input[name="rank"]'),'排序')) return false; 

					formEditData.submit();
					box.window('close');
				}
			})

			editClose.bind({
				click : function() {
					box.window('close');

				}
			})

			box.find('input[name="appOrTagId"]').blur(function() {
				var val = this.value;
				if (val.length < 1)
					return false;

				box.find('input[name="type"]').each(function() {
					if (this.value == 2 && this.checked) {
						that.getTagPic(val, box, 'edit');

					}

				})

			})

		},
		del:function(id){

			var msg = "您确定要删除吗？";

			$.messager.confirm('确认', '您确认要删除这条记录吗?', function(row) {
				if (row) {
					$.ajax({
						type : "POST",
						url : urlWithParam + "/admin/mixfeatured/" + id + ".del.d",
						traditional : true,
						data : {
							"id" : id,
							"deleted" : true
						},
						dataType : 'json',
						success : function(data) {
							if (data.result.msg == "OK!") {
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

		},
		add:function(){
			var that = this;
			var $addMetro = $("#addMetro"), $addPost = $("#addPost"), $closePost = $("#closePost"), $formData = $("#formData"), $addtype = $('#addtype');

			$addMetro.window({
				title : '新增推荐',
				top : '100px',
				width : 600,
				modal : true,
				shadow : false,
				closed : false,
				height : 500
			});
			$addMetro.show();
			
			$addPost.unbind('click').bind({
				click : function() {
					if(!that.isNull($formData.find('input[name="appOrTagId"]'),'ID')) return false; 
					if(!that.isNull($formData.find('input[name="name"]'),'名称')) return false; 
					if(!that.isNull($formData.find('input[name="rank"]'),'排序')) return false; 
					$formData.submit();
					$addMetro.window('close');

				}
			})

			$closePost.unbind('click').bind({
				click : function() {
					$addMetro.window('close');
				}
			})

			$addMetro.find('input[name="appOrTagId"]').blur(function() {
				var val = this.value;
				if (val.length < 1)
					return false;

				$addMetro.find('input[name=type]').each(function() {
					if (this.value == 2 && this.checked) {
						that.getTagPic(val, $addMetro, 'add');

					}

				})

			})

		},
		bindEve:function(){		
			var that = this;
			$('#addBtn').bind({
				click : function() {
					that.add();
				}
			});
			$('#keyPicType,#keyType,#keyHidden').bind({
				change : function() {
					that.searchEve();
				}
			});
			return that;
		},
		// 取专题图片
		getTagPic:function(id, parent, parm) {

			var getTagUrl = '/admin/motag/get.' + id + '.d';

			$.ajax({
				type : "GET",
				url : urlWithParam + getTagUrl,
				dataType : 'json',
				success : function(data) {

					if (data == '' || data == null || data == 'undefined') return false;
					var data = data.data;
					parent.find('input[name=oldBigPicsUrl]').val(data.bigPics);
					parent.find('input[name=oldMediumPicsUrl]').val(data.mediumPics);
					parent.find('input[name=oldSmallPicsUrl]').val(data.smallPics);

					if (parm == 'edit') {
						$('#editBigPicsUrl').attr('src', data.bigPics);
						$('#editMediumPicsUrl').attr('src', data.mediumPics);
						$('#editSmallPicsUrl').attr('src', data.smallPics);
					} else {
						$('#showBigPicsUrl').attr('src', data.bigPics);
						$('#showMediumPicsUrl').attr('src', data.mediumPics);
						$('#showSmallPicsUrl').attr('src', data.smallPics);

					}

				}
			});

		},

		// 显示
		showEve:function(id, type) {
			var that = this;
			var msg = "您确定要显示吗？";

			if (confirm(msg) == true) {
				$.ajax({
					type : "POST",
					url : urlWithParam + "/admin/mixfeatured/show.d",
					traditional : true,
					data : {
						"id" : id,
						"type" : String(type)
					},
					dataType : 'json',
					success : function(data) {
						if (data.result.msg == "OK!") {
							that.showMessager("显示操作成功");
							setTimeout(function() {
								that.searchEve();
								// window.location.href = './list.html'
							}, 200)
						} else {
							that.showMessager(data.result.msg);
						}
					}
				});
			} else {
				return false;
			}
		},
		//隐藏
		hiddenEve:function(id, type) {
			var that = this;
			var msg = "您确定要隐藏吗？";
			if (confirm(msg) == true) {
				$.ajax({
					type : "POST",
					url : urlWithParam + "/admin/mixfeatured/hide.d",
					traditional : true,
					data : {
						"id" : id,
						"type" : String(type)
					},
					dataType : 'json',
					success : function(data) {
						if (data.result.msg == "OK!") {
							that.showMessager("隐藏操作成功");
							setTimeout(function() {
								that.searchEve();
								// window.location.href = './list.html'
							}, 200)
						} else {
							that.showMessager(data.result.msg);
						}
					}
				});
			} else {
				return false;
			}
		},
		showMessager:function(msg) {
			if (msg == null || msg == "" || msg == undefined)
				msg = "操作成功";
			$.messager.show({
				msg : msg,
				title : '提示',
				showType : 'slide',
				timeout : 3000
			});
		},
		// 删除图片
		delPic:function(id, btn) {

			var img = $('#' + id);
			var hidden = img.parent().parent().find('input[type="hidden"]');
			var btn = $(btn);
			if (img.attr('src') != '') {
				img.data('src', img.attr('src')).attr('src', '');
				hidden.data('val', hidden.val()).val('');
				btn.text('+');

			} else {
				if (img.data('src') == '')
					return false;
				img.attr('src', img.data('src') || '').removeData('src');
				hidden.val(hidden.data('val') || '').removeData('val');
				btn.text('x');

			}

		},
		//是否为空值
		isNull:function(obj,tit){
			var that = this;
			if(obj.val().length<1){
				that.showMessager('<span class="co_red">'+tit+'不能为空</span>');	
				return false;
			}
			return true
			
		}
}

$(document).ready(function() {
	mixfeatured.init();
	

});



function getTimeStr(value) {
	var time = new Date();
	var mm = time.getMonth() + 1;
	if (mm < 9) {
		mm = "0" + mm
	}
	;
	var dd = time.getDate();
	if (dd < 10) {
		dd = "0" + dd
	}
	;
	var mmdd = mm + "-" + dd;
	var showValmmdd = value.substring(5, 10);
	var showVal = value.substring(2, 16);
	if (mmdd == showValmmdd) {
		return showVal + "<span class='co_red'>新</span>"
	} else {
		if (showVal != "") {
			return showVal;
		} else {
			return value.substring(2, 16)
		}
	}
}