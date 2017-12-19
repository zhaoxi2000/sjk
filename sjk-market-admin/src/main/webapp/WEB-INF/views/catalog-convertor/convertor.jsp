<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../base.jsp"%>
<!DOCTYPE>
<html>
<head>
<!--IE doesnot support relative path.-->
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>市场类别和平台类别对于关系--新增/修改</title>
<link rel="stylesheet" type="text/css" href="css/admin_dig.css" />
<link rel="stylesheet" type="text/css" href="css/common.css" />
<script type="text/javascript" src="js/lib/jquery-1.7.1.min.js"></script> 
<script type="text/javascript" src="js/common/common.js"></script> 
<script type="text/javascript" src="js/catalog-convertor/convertor.js"></script> 
</head>
<body>
	<form action="admin/catalogconvertor/edit.list.d" method="post" accept-charset="UTF-8"  id="formData">
		<div class="con">
			<div class="title">
				<span class="tag">修改【<label class="co_red">${marketName }</label>】市场与【<label class="co_green">平台</label>】类别对于关系</span>
			</div>

			<div class="f_l pb_15 pl_10 w_1100">
				<table id="dataList" class="tab2 w_700" border="0" cellspacing="0" cellpadding="0">
					<thead> 
						<tr><th class="w_150"  align='center'>市场父类别</th> 
							<th class="w_200"  align='center'>市场类别</th> 
							<th class="w_100" align='center'>关联状态</th>
							<th colspan="2"  class="w_200"  align='center'>平台类别</th> 
						</tr> 
					</thead>
					<tbody>
						<c:forEach var="catalog" begin="0" items="${marketCatalogs}">
							<tr>
								<td class="w_30" name="OldCatalog"   align='center' tag="${catalog.catalog}"></td >  
								 <td  class="w_110"  align='center'>[${catalog.subCatalog}]${catalog.subCatalogName} 
								 <input type="hidden" name="marketName" value="${marketName}" />
								 <input type="hidden" name="id" value="${catalog.id}" /> 
								 <input type="hidden" name="catalog" value="${catalog.catalog}" /> 
								 <input type="hidden" name="subCatalog" value="${catalog.subCatalog}" />
								 <input type="hidden" name="subCatalogName" value="${catalog.subCatalogName}" />  
								</td>
								
								<td  align='center'>${ catalog.isConvertor==true?"<label  class=\"co_green\">已关联--></label>":"<label  class=\"co_red\">未关联--x</label>" } </td>
								<td  align='center'> 
										<select class="w_80" name="targetCatalog" tag="${catalog.targetCatalog}"> 
										  <option value="0"   ${catalog.targetCatalog <1 ?" selected=\"selected\"":""} >--选择--</option>
										  <option value="1"   ${catalog.targetCatalog==1?" selected=\"selected\"":""} >软件</option>
										  <option  value="2"  ${catalog.targetCatalog==2?" selected=\"selected\"":""}>游戏</option>
										</select>
								</td>
								<td>
										<select class="w_100" name="targetSubCatalog" tag="${catalog.targetSubCatalog}">  
										  <option value="0"  >--选择--</option>
										</select>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="btn">
				<button id="postdata" type="submit" class="w_100 mt_10 mb_15 f_l">保存</button>
			</div>
		</div>
	</form>
</body>
</html>
