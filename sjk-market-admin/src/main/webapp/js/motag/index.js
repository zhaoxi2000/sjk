var urlWithParam = "/sjk-market-admin";

$(document).ready(function() {
	loadPid('keyPid', null, false);
	changeEve()
	$('#keyword').bind('keydown', function(e) {
		if (e.which == 13) {
			SearchEve();
		}
	});
	$('#search').click(function() {
		SearchEve();
	});
	loadGridTable();
	var p = $('#dataList').datagrid('getPager');
	$(p).pagination({
		onBeforeRefresh : function() {
		}
	});
});
function changeEve() {
	var $sltkeyTagType = $("#keyTagType");
	$sltkeyTagType.bind({
		change : function() {
			if (parseInt($sltkeyTagType.val()) == 1) {
				loadsltPId('keyPid', 1, null, false);
			} else {
				loadsltPId('keyPid', 2, null, false);
			}
		}
	});
	var $sltTagType = $("#sltTagType");
	$sltTagType.bind({
		change : function() {
			if (parseInt($sltTagType.val()) == 1) {
				loadsltPId('sltPId', 1, null, true)
			} else {
				loadsltPId('sltPId', 2, null, true)
			}
		}
	});
	// var $sltkeyCatalog = $("#keyCatalog");
	// $sltkeyCatalog.bind({
	// change : function() {
	// if (parseInt($sltkeyCatalog.val()) == 1) {
	// loadsltPId('keyPid', 1, null, false)
	// } else {
	// loadsltPId('keyPid', 2, null, false)
	// }
	// }
	// });
}

function loadsltPId(sltId, value, pid, isAddOrEdit) {
	var $sltPId = $("#" + sltId), url = "", len = 0, list = [];

	if (value == 1) {
		url = urlWithParam + "/admin/motag/topic-list.json"
	} else {
		url = urlWithParam + "/admin/motag/normaltag-list.json"
	}

	$.ajax({
		type : "GET",
		url : url,
		dataType : 'json',
		success : function(data) {
			if (data != null) {
				len = data.rows.length;
				if (isAddOrEdit) {
					list.push("<option value='0'>--选择上级类别--</option>");
				} else {
					list.push("<option value='0'>--选择上级类别--</option>");
				}
				for ( var i = 0; i < len; i++) {
					if (isAddOrEdit) {
						if (pid == data.rows[i].id) {
							list.push("<option value='" + data.rows[i].id
									+ "' selected='selected'>"
									+ data.rows[i].name + "</option>")
						} else {
							list.push("<option value='" + data.rows[i].id
									+ "'>" + data.rows[i].name + "</option>")
						}
					} else {
						if (parseInt(data.rows[i].pid) == 0) {
							list.push("<option value='" + data.rows[i].id
									+ "'>" + data.rows[i].name + "</option>");
						}
					}

				}
				$sltPId.html(list.join(''))
			}
		}
	});
}

function loadPid(objId, value, isAddOrEdit) {
	$obj = $("#" + objId);
	$.ajax({
		type : "GET",
		url : urlWithParam + "/admin/motag/topic-list.json",
		dataType : 'json',
		success : function(data) {
			if (data != null) {
				var data = data.rows;
				$obj.empty();
				if (isAddOrEdit) {
					$obj.append("<option value=\"0\">--选择上级类别--</option>");
				} else {
					$obj.append("<option value=\"\">--选择上级类别--</option>");
				}
				$.each(data, function(i, row) {
					if (row.pid == 0) {
						if (value == row.id) {
							$obj.append("<option value=\"" + row.id
									+ "\"  selected=\"selected\">" + row.name
									+ "</option>");
						} else {
							$obj.append("<option value=\"" + row.id + "\">"
									+ row.name + "</option>");
						}
					}
				});
				$obj.bind({
					change : function() {
						SearchEve()
					}
				})
			}
		}
	});

}

// 加载DataGrid控件
function loadGridTable() {
	$('#dataList')
			.datagrid(
					{
						loadMsg : '正在加载...',
						iconCls : 'icon-save',
						width : '1050',
						nowrap : false,
						striped : true,
						collapsible : false,
						url : urlWithParam + "/admin/motag/search.json",
						pageSize : 50,
						sortOrder : 'desc',
						idField : "id",
						columns : [ [
								{
									field : 'id',
									title : 'id',
									sortable : true,
									width : 50
								},
								{
									field : 'name',
									title : '专题标签名称',
									sortable : true,
									width : 150,
									formatter : function(value, rec, index) {
										var url = urlWithParam
												+ "/mo-tagapp/index.html?tagType="+
												+ rec.tagType 
												+"&tagId="
												+ rec.id;
										var html = '<a class="mr_10 co_bl f_l" href="'
												+ url + '">' + value + '</a>';
										return html;
									}
								},
								{
									field : 'pname',
									title : '上级专题',
									sortable : true,
									width : 150,
									formatter : function(value, rec, index) {
										if (value == null || value == "") {
											return "【无】";
										} else {
											return "[" + rec.pid + "]" + value;
										}
									}
								},
								{
									field : 'catalog',
									title : '类型',
									sortable : true,
									width : 50,
									formatter : function(value, rec, index) {
										if (value == 1) {
											return "应用";
										} else if (value == 2) {
											return "游戏";
										} else if (value == 100) {
											return "大型游戏";
										}
									}
								},
								{
									field : 'tagType',
									title : '类型',
									sortable : true,
									width : 50,
									formatter : function(value, rec, index) {
										if (value == 1) {
											return "专题";
										} else if (value == 2) {
											return "标签";
										}
									}
								},
								{
									field : 'imgUrl',
									title : '专题/标签图片',
									sortable : true,
									width : 150,
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
									field : 'bigPics',
									title : '大图',
									sortable : true,
									width : 100,
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
									title : '中图',
									sortable : true,
									width : 100,
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
									title : '小图',
									sortable : true,
									width : 100,
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
									field : 'rank',
									title : '排序',
									sortable : true,
									width : 50
								},
								{
									field : 'operate',
									title : '操作',
									width : 120,
									formatter : function(value, rec, index) {
										var del = '<a class="mr_15 co_bl f_l" onclick="Delete('
												+ rec.id
												+ ','
												+ index
												+ ')">删除</a>';
										var edit = '<a class="mr_10 co_bl f_l" onclick="Edit('
												+ rec.id
												+ ',\''
												+ rec.name
												+ '\',\''
												+ rec.tagDesc
												+ '\','
												+ rec.catalog
												+ ','
												+ rec.tagType
												+ ',\''
												+ rec.imgUrl
												+ '\','
												+ rec.pid
												+ ','
												+ rec.rank
												+ ',\''
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
							setBtnDisabled("search", false);
						}
					});

}
function setBtnDisabled(btnId, disabled) {
	if (disabled) {
		$("#" + btnId).attr("style", "color:#999").attr("disabled", "disabled");
	} else {
		$("#" + btnId).removeAttr("style").removeAttr("disabled");
	}
}
// 搜索
function SearchEve() {
	setBtnDisabled("search", true);
	var keywords = $.trim($('#keyword').val());
	var tagType = $.trim($("#keyTagType option:selected").val());
	var queryParams = $('#dataList').datagrid('options').queryParams;
	var pid = $.trim($("#keyPid option:selected").val());
	if (keywords !== "") {
		queryParams.keywords = keywords;
	} else {
		queryParams.keywords = '';
	}
	if (pid !== "") {
		queryParams.pid = pid;
	} else {
		queryParams.pid = '';
	}
	if (tagType !== "" && tagType !== null && tagType !== undefined
			&& tagType !== 'undefined') {
		queryParams.tagType = tagType;
	} else {
		queryParams.tagType = '';
	}
	queryParams.page = 1;

	// 主显示使用
	$('#dataList').datagrid('options').pageNumber = 1;
	// page对象 中的pageNumber对象
	var pager = $('#dataList').datagrid('getPager');
	$(pager).pagination('options').pageNumber = 1;
	$('#dataList').datagrid('reload');
}

// 添加
function Add() {
	Edit(0, "", "", 1, 1, "", null, 999999, "", "", "");
}

// 添加
function Edit() {
	var id = arguments[0];
	var name = arguments[1];
	var tagDesc = arguments[2];
	var catalog = arguments[3];
	var tagType = arguments[4];
	var imgUrl = arguments[5];
	var pid = arguments[6];
	var rank = arguments[7];
	var bigPics = arguments[8];
	var mediumPics = arguments[9];
	var smallPics = arguments[10];

	$editPanl = $("#editPanl");
	var title = "添加专题标签";
	if (id > 0) {
		var title = "修改专题标签";
	}
	$editPanl.window({
		title : title,
		top : 200,
		width : 650,
		modal : true,
		shadow : true,
		closed : false,
		height : 490
	});
	$editPanl.show();
	initHtml(id, name, tagDesc, catalog, tagType, imgUrl, pid, rank, bigPics,
			mediumPics, smallPics);
}

function initHtml() {
	var id = arguments[0];
	var name = arguments[1];
	var tagDesc = arguments[2];
	var catalog = arguments[3];
	var tagType = arguments[4];
	var imgUrl = arguments[5];
	var pid = arguments[6];
	var rank = arguments[7];
	var bigPics = arguments[8];
	var mediumPics = arguments[9];
	var smallPics = arguments[10];

	$id = $("#id");
	// $catalog = $("#catalog");
	$name = $("#txtTagName");
	// $oldImgUrl = $("#txtOldImgUrl");
	// $showImgUrl = $("#showImgUrl");
	$tagDesc = $("#txtTagDesc");
	$rank = $("#txtRank");

	$oldBigPicsUrl = $("#txtOldBigPicsUrl");
	$showBigPicsUrl = $("#showBigPicsUrl");

	$oldMediumPicsUrl = $("#txtOldMediumPicsUrl");
	$showMediumPicsUrl = $("#showMediumPicsUrl");

	$oldSmallPicsUrl = $("#txtOldSmallPicsUrl");
	$showSmallPicsUrl = $("#showSmallPicsUrl");

	loadsltPId('sltPId', tagType, pid, true);
	// loadsltPId(tagType, pid)
	$id.val(id);
	$name.val(name);
	$tagDesc.val(tagDesc);
	$rank.val(rank);
	$("#sltTagType option").removeAttr("selected");
	$("#sltTagType option[value='" + tagType + "']").attr("selected",
			"selected");
	$("#sltCatalog option").removeAttr("selected");
	$("#sltCatalog option[value='" + catalog + "']").attr("selected",
			"selected");
	// if (imgUrl != null && imgUrl != "") {
	// $showImgUrl.attr("src", imgUrl);
	// $oldImgUrl.val(imgUrl);
	// } else {
	// $oldImgUrl.val("");
	// $showImgUrl.attr("src", "");
	// }
	// BigPics
	if (bigPics != null && bigPics != "") {
		$showBigPicsUrl.attr("src", bigPics);
		$oldBigPicsUrl.val(bigPics);
	} else {
		$showBigPicsUrl.attr("src", "");
		$oldBigPicsUrl.val("");
	}
	// MediumPics
	if (mediumPics != null && mediumPics != "") {
		$showMediumPicsUrl.attr("src", mediumPics);
		$oldMediumPicsUrl.val(mediumPics);
	} else {
		$showMediumPicsUrl.attr("src", "");
		$oldMediumPicsUrl.val("");
	}
	// SmallPics
	if (smallPics != null && smallPics != "") {
		$showSmallPicsUrl.attr("src", smallPics);
		$oldSmallPicsUrl.val(smallPics);
	} else {
		$showSmallPicsUrl.attr("src", "");
		$oldSmallPicsUrl.val("");
	}

	$formData = $("#formData");
	$tagsPost = $("#tagsPost");
	var actionUrl = "";
	if (id == 0) {
		actionUrl = urlWithParam + "/admin/motag/save.d";
	} else {
		actionUrl = urlWithParam + "/admin/motag/edit.d";
	}
	$formData.attr("action", actionUrl);
	/* POST请求 */
	$tagsPost.click(function() {
		var catalog = $("#sltCatalog option:selected").val();
		if ($name.val() == "" || $name.val() == null) {
			$("#errorName").html("很抱歉，您还没有输入专题标签名称。");
			return false;
		} else {
			$("#errorName").empty();
		}
		if (isNaN($rank.val())) {
			$("#errorRank").html("很抱歉，排序值必须是整数。");
			return false;
		} else {
			$("#errorRank").empty();
		}
		if (catalog == "" || catalog == null) {
			alert("很抱歉，数据填写错误！");
			return false;
		}
		$formData.submit();
	});
}

// 删除
function Delete() {
	var id = arguments[0];
	$.messager.confirm('确认', '您确认要删除这条记录吗?', function(row) {
		if (row) {
			$.ajax({
				type : "POST",
				url : urlWithParam + "/admin/motag/del.d",
				traditional : true,
				data : {
					"id" : id
				},
				dataType : 'json',
				success : function(data) {
					if (data.result.msg == "OK!") {
						$.messager.show({
							msg : '删除成功',
							title : '提示',
							showType : 'slide',
							timeout : 3000
						});
						$('#dataList').datagrid('unselectAll');
						$('#dataList').datagrid('reload');
						$(".window-mask").hide();
					}
				}
			});
		}
	});
}


//删除图片
function delPic(id, btn) {

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

}