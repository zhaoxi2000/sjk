//condition条件判断(起开关作用).closure
function setIntervalWrapper(callback, paramForCallback, time) {
	if (typeof callback === "function") {
		var callWrapper = function() {
			callback(paramForCallback);
		};
		return setInterval(callWrapper, time);
	} else {
		throw Exception;
	}
}

/* 外网市场一级类别编号 对应名称 */
function GetCatalogNames(catalogId) {
	var result = "";
	var CatalogArr = {
		"rows" : [ {
			"id" : 1,
			"name" : "软件"
		}, {
			"id" : 2,
			"name" : "游戏"
		}, {
			"id" : 17,
			"name" : "游戏"
		}, {
			"id" : 34,
			"name" : "软件"
		}, {
            "id" : 100,
            "name" : "大型游戏"
        } ]
	}
	var data = CatalogArr.rows;
	var len = data.length;
	for ( var i = 0; i < len; i++) {
		var rs = data[i];
		if (catalogId == rs.id) {
			result = rs.name;
			break;
		}
	}
	return result;
}

function getSelectDatas(data, sltTags, value) {
	sltTags.empty();
	sltTags.append("<option value=\"6\">-Metro专题列表</option>");
}

function getSelectData(data, sltTags, value) {

	sltTags.empty();
	sltTags.append("<option value=\"\">--请选择--</option>");
	$.each(data, function(i, row) {
		if (parseInt(row.pid) == 0) {
			if (IsChilds(data, row.id)) {
				sltTags.append("<optgroup label=\"+" + row.name + "\"   >"
						+ row.name + "</optgroup>");
				getChildTags(data, sltTags, value, row.id);
			} else {
				if (value == row.id) {
					sltTags.append("<option value=\"" + row.id
							+ "\"  selected=\"selected\">-" + row.name
							+ "</option>");
				} else {
					sltTags.append("<option value=\"" + row.id + "\">-"
							+ row.name + "</option>");
				}
			}
		}
	});
}

function loadSelet(sltId, data, defultValue) {
	$objSlt = $("#" + sltId);
	$objSlt.empty();
	$.each(data, function(i, row) {
		if (defultValue == row.value) {
			$objSlt.append("<option value=\"" + row.value
					+ "\"  selected=\"selected\">" + row.text + "</option>");
		} else {
			$objSlt.append("<option value=\"" + row.value + "\">" + row.text
					+ "</option>");
		}
	});
}

function IsChilds(data, pid) {
	var count = 0;
	$.each(data, function(i, row) {
		if (parseInt(row.pid) == parseInt(pid)) {
			count++;
		}
	});
	return count > 0;
}

function getChildTags(data, sltTags, value, pid) {
	$.each(data, function(i, row) {
		if (parseInt(row.pid) == parseInt(pid)) {
			if (value == row.id) {
				sltTags.append("<option value=\"" + row.id
						+ "\"  selected=\"selected\">+--" + row.name
						+ "</option>");
			} else {
				sltTags.append("<option value=\"" + row.id + "\">+--"
						+ row.name + "</option>");
			}
		}
	});
}

String.format = function() {
	if (arguments.length == 0)
		return null;

	var str = arguments[0];
	for ( var i = 1; i < arguments.length; i++) {
		var re = new RegExp('\\{' + (i - 1) + '\\}', 'gm');
		str = str.replace(re, arguments[i]);
	}
	return str;
}

// 正则表达式
function name_va(na) {
	// var patten = new RegExp("^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]+$");
	if (na == null || na == "" || na == undefined) {
		return false;
	}
	return true;
}

function isNull(value) {
	if (value == null || value == "" || value == undefined) {
		return true;
	} else {
		return false;
	}
}

function user_va(va) {
	var patten = new RegExp("^[a-zA-Z][a-zA-Z0-9_]{5,19}$");
	return patten.test(va);
}

function pwd_va(pa) {
	var patten = new RegExp("[a-zA-Z0-9_@!#]{5,19}");
	return patten.test(pa);
}

function mail_va(ma) {
	var patten = new RegExp(
			"^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$");
	return patten.test(ma);
}

function tel_va(ta) {
	var patten = new RegExp(
			"^$|(^[0-9]{3,4}\-[0-9]{3,8}$)|(^[0-9]{3,8}$)|(^\([0-9]{3,4}\)[0-9]{3,8}$)|(^0{0,1}13[0-9]{9}$)");
	return patten.test(ta);
}

function pwd2_va(n_pwd, n_pwd2) {
	if (n_pwd == n_pwd2) {
		return true
	}
}