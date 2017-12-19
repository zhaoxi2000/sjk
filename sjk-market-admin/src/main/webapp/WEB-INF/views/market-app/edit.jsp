<%@ page language="java" contentType="text/html;charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../base.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <base href="<%=basePath%>" />
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>大型游戏--修改--MarketApp</title>
        <link rel="stylesheet" type="text/css" href="css/admin_dig.css" />
        <link href="css/blue2.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" type="text/css" href="css/common.css" />
        <script type="text/javascript" src="js/lib/jquery-1.7.1.min.js"></script>
        <script type="text/javascript" src="js/lib/jquery-ui-1.7.custom.min.js"></script>
        <script type="text/javascript" src="js/market-app/public.js"></script>
    </head>
    <body>
        <form action="admin/marketapp/edit.d" method="post" enctype="multipart/form-data" accept-charset="UTF-8" id="formData">
            <div class="con">
                <div class="title">
                    <span class="tag">修改应用</span>
                </div>
                <div class="f_l pb_15 pl_10 w_1300">
                    <table class="tab2 w_1300" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td class="ta_r w_120">
                                名称：
                            </td>
                            <td class="w_240"><input class="w_200" name="name" id="name"
                                type="text" value="${marketApp.name}" /> <span class="co_red">*</span>
                            </td>
                            <td class="ta_r w_100">
                                大类：
                            </td>
                            <td class="w_230">
                                <input class="w_130" name="catalog" value="100" readonly="readonly" /> <span class="co_red">*</span>
                            </td>
                            <td class="ta_r w_100">
                                小类：
                            </td>
                            <td class="w_230">
                                <select class="w_130" name="subCatalog">
                                    <option value="101" ${marketApp.subCatalog == 101?"selected='selected'":""}>赛车游戏</option>
                                    <option value="102" ${marketApp.subCatalog == 102?"selected='selected'":""}>RPG角色扮演</option>
                                    <option value="103" ${marketApp.subCatalog == 103?"selected='selected'":""}>FPS第一人称射击游戏</option>
                                    <option value="104" ${marketApp.subCatalog == 104?"selected='selected'":""}>体育竞技</option>
                                    <option value="105" ${marketApp.subCatalog == 105?"selected='selected'":""}>其他游戏</option>
                                </select>
                                <input name="subCatalogName" type="hidden" value="${marketApp.subCatalogName}" /><span class="co_red">*</span>
                            </td>
                        </tr>
                        <tr>
                            <td class="ta_r">
                                版本：
                            </td>
                            <td>
                                <input name="version" type="text" value="${marketApp.version}" /> 
                                <span class="co_red">*</span>
                            </td>
                            <td class="ta_r">
                                支持系统：
                            </td>
                            <td>
                                <input name="osversion" id="osversion" class="w_130" type="text" value="${marketApp.osversion}" />
                                <span class="co_red">*</span>
                            </td>
                            <td class="ta_r">
                                pkname：
                            </td>
                            <td>
                                <input name="pkname" id="pkname" class="w_130" type="text" value="${marketApp.pkname}" />
                                <span class="co_red">*</span>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td colspan="5" class="w_1100">
                                <span class="w_1100 f_l">
                                    <span class="w_170 pr_5 f_l ta_c">cpu</span>
                                    <span class="w_240 pr_5 f_l ta_c">下载地址</span>
                                    <span class="w_150 f_l ta_c">大小</span>
                                    <span class="w_150 f_l ta_c">解压后大小</span>
                                    <span class="w_150 f_l ta_c">不支持的手机类型</span>
                                </span>
                                <ul id="addCpu" class="addImg w_1100 addImg_1100">
                                    <c:choose>
                                        <c:when test="${bigGamePack.size()>0 }">
                                            <c:forEach var="items" begin="0"  items="${bigGamePack}">
                                                <li>
                                                    <select class="f_l w_160 mr_10" name="bigGamePacks[0].cputype">
                                                        <option value="0" ${items.cputype == 0?"selected='selected'":""}>请选择型号</option>
                                                        <option value="1" ${items.cputype == 1?"selected='selected'":""}>高通</option>
                                                        <option value="2" ${items.cputype == 2?"selected='selected'":""}>三星</option>
                                                        <option value="3" ${items.cputype == 3?"selected='selected'":""}>联发科</option>
                                                        <option value="4" ${items.cputype == 4?"selected='selected'":""}>德州仪器</option>
                                                        <option value="5" ${items.cputype == 5?"selected='selected'":""}>Intel</option>
                                                        <option value="6" ${items.cputype == 6?"selected='selected'":""}>Nvidia</option>
                                                        <option value="7" ${items.cputype == 7?"selected='selected'":""}>Marvell</option>
                                                        <option value="8" ${items.cputype == 8?"selected='selected'":""}>海思</option>
                                                        <option value="100" ${items.cputype == 100?"selected='selected'":""}>通用</option>
                                                    </select>
                                                    <input name="bigGamePacks[0].url" value="${items.url}" type="text" class="f_l w_240 mr_10"/>
                                                    <input name="bigGamePacks[0].size" value="${items.size}" type="text" class="f_l w_130 mr_10"/>
                                                    <input name="bigGamePacks[0].freeSize" value="${items.freeSize}" type="text" class="f_l w_130 mr_10"/>
                                                    <input name="bigGamePacks[0].unsupportPhoneType" value="${items.unsupportPhoneType}" type="hidden"/>
                                                    <input name="bigGamePacks[0].unsupportPhoneName" type="text" class="f_l w_150 mr_10"/>
                                                    <span class="btn w_70 f_l"> <button type="button" class="w_30 f_l mr_5" name="add">+</button> <button type="button" class="w_30 f_l" name="del">x</button> </span>
                                                </li> 
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <li>
                                                <select class="f_l w_160 mr_10" name="bigGamePacks[0].cputype">
                                                    <option value="0" selected="selected">请选择型号</option>
                                                    <option value="1">高通</option>
                                                    <option value="2">三星</option>
                                                    <option value="3">联发科</option>
                                                    <option value="4">德州仪器</option>
                                                    <option value="5">Intel</option>
                                                    <option value="6">Nvidia</option>
                                                    <option value="7">Marvell</option>
                                                    <option value="8">海思</option>
                                                    <option value="100">通用</option>
                                                </select>
                                                <input name="bigGamePacks[0].url" type="text" class="f_l w_240 mr_10"/>
                                                <input name="bigGamePacks[0].size" type="text" class="f_l w_130 mr_10"/>
                                                <input name="bigGamePacks[0].freeSize" type="text" class="f_l w_130 mr_10"/>
                                                <input name="bigGamePacks[0].unsupportPhoneType" type="hidden"/>
                                                <input name="bigGamePacks[0].unsupportPhoneName" type="text" class="f_l w_150 mr_10"/>
                                                <span class="btn w_70 f_l"> <button type="button" class="w_30 f_l mr_5" name="add">+</button> <button type="button" class="w_30 f_l" name="del">x</button> </span>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>
                                </ul>
                            </td>
                        </tr>
                        <tr>
                            <td class="ta_r">
                                一句说：
                            </td>
                            <td colspan="5">
                                <input class="w_500" name="shortDesc" type="text" value="${marketApp.shortDesc}" />
                            </td>
                        </tr>
                        <tr>
                            <td class="ta_r">
                                下载地址：
                            </td>
                            <td colspan="5">
                                <input class="w_500" name="downloadUrl" type="text" value="${marketApp.downloadUrl}" />
                            </td>
                        </tr>
                        <tr>
                            <td class="ta_r">
                                语言：
                            </td>
                            <td colspan="5">
                                <select class="f_l w_160 mr_10" name="language">
                                    <option value="中文" ${marketApp.language == 中文?"selected='selected'":""}>中文</option>
                                    <option value="英文" ${marketApp.language == 英文?"selected='selected'":""}>英文</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td class="ta_r">
                                关键词：
                            </td>
                            <td colspan="5">
                                <input class="w_500" name="keywords" type="text" value="${marketApp.keywords}" />
                            </td>
                        </tr>
                        <tr>
                            <td class="ta_r">
                                ICON：
                            </td>
                            <td colspan="5">
                                <input name="oldLogoUrl" type="hidden" id="oldLogoUrl" value="${marketApp.logoUrl}" />
                                <input class="w_250 f_l mr_10 mt_10" name="logoUrl" id="logoUrl" type="text" value="${marketApp.logoUrl}" />
                                <input name="logoFile" class="f_l mt_10" id="logofile" type="file" />
                                <img class="wh_20 mt_10" src="${marketApp.logoUrl}" />
                                <input name="remoteLogoUrl" id="remoteLogoUrl" type="hidden" />
                                <span class="ml_10 f_l"><span class="co_red">*</span>ICON文件大小不能超过100KB</span>
                            </td>
                        </tr>
                        <tr>
                            <td class="ta_r">
                                首页图：
                            </td>
                            <td colspan="5">
                                <input name="oldIndexImgUrl" id="oldIndexImgUrl" type="hidden" value="${marketApp.indexImgUrl}" />
                                <input class="w_250 f_l mr_10 mt_10" name="indexImgUrl" id="indexImgUrl" type="text" value="${marketApp.indexImgUrl}" />
                                <input name="indexImgFile" class="f_l mt_10" id="indexImgfile" type="file" />
                                <img class="wh_20 mt_10" src="${marketApp.indexImgUrl}" />
                                <input name="remoteIndexImgUrl" id="remoteIndexImgUrl" type="hidden" />
                                <span class="ml_10 f_l"><span class="co_red">*</span>首页图文件大小不能超过100KB</span>
                            </td>
                        </tr>
                        <tr>
                            <td class="ta_r" valign="top">
                                简介：
                            </td>
                            <td colspan="5" class="pt_10 pb_10">
                                <textarea class="w_800 h_200 f_l" name="description" cols="" rows="">${marketApp.description}</textarea>
                            </td>
                        </tr>
                        <tr>
                            <td class="ta_r" valign="top">
                                更新：
                            </td>
                            <td colspan="5" class="pt_10 pb_10">
                                <textarea class="w_800 h_200 f_l" name="updateInfo" cols="" rows="">${marketApp.updateInfo}</textarea>
                            </td>
                        </tr>
                        <tr>
                            <td class="ta_r" valign="top">
                                截图：
                            </td>
                            <td colspan="5" class="pt_10">
                                <input name="strImageUrls" type="hidden" value="${marketApp.strImageUrls}" />
                                <ul id="addImg" class="addImg w_800 addImg_800">
                                    <c:forEach var="items" begin="0" items="${marketApp.strImageUrls.split(',')}">
                                        <li id="li-addImg-${marketApp.id}"><input name="imageUrl"
                                            value="${items}" type="text" class="f_l w_250 mr_10" /> <%-- 如果用户在这一行上传文件,那么imageUrl需要干掉. --%> <input name="imageFile" type="file" class="f_l mr_10" /><span
                                            class="btn w_70 f_l"> <button type="button" class="w_30 f_l mr_5" name="add">+</button> <button type="button" class="w_30 f_l mr_5" name="del">x</button> </span>
                                            <img class="wh_20" src="${items}" />
                                            <span class="ml_10 f_l lh_20"><span class="co_red">*</span>图片大小不超过100KB</span>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td colspan="5">
                                <div class="btn">
                                    <button id="saveBtn" type="button" class="w_100 mt_10 mb_15 f_l">保存</button>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="list" id="unPhonelist" name="0">
                <span id="unPhonecon" class="editcon"></span>
                <span class="addinfo dp_n">
                    <input name="" id="unPhonepid" type="text" />
                    <span class="btn w_160 mt_8">
                        <button id="unPhonebtn" type="button" class="mr_10">增加</button>
                    </span>
                </span>
                
                <span class="listNav">
                    <span class="f_l pl_10" id="brandTit">手机品牌</span>
                    <span class="f_l pl_50" id="searchBTit">搜索品牌：</span>
                    <input class="f_l w_120 mt_5" id="searchBrand" type="text"/>
                    <a class="f_r mr_10" id="returnList">返回</a>
                </span>
                
                <div class="fixedBox">
                    <div class="mobileBox" id="mobileBox">
                        <span class="brandList" id="brandList"></span>
                        <span class="brandList" id="productList"></span>
                    </div>
                </div>
            </div>
            
            <input name="id" type="hidden" value="${marketApp.id}" />
            <input id="lastFetchTime" name="lastFetchTime" type="hidden" value="${_lastFetchTime}" />
            <input name="marketName" type="hidden" value="${marketApp.marketName}" />
            <input name="publisherShortName" type="hidden" value="${marketApp.publisherShortName}" />
            <input name="versionCode" type="hidden" value="${marketApp.versionCode}" />
            <input name="price" type="hidden" value="${marketApp.price}" />
            <input name="starRating" type="hidden" value="${marketApp.starRating}" />
            <input name="viewCount" type="hidden" value="${marketApp.viewCount}" />
            <input name="status" type="hidden" value="${marketApp.status}" />
            <input name="permissions" type="hidden" value="${marketApp.permissions}" />
            <input name="signatureSha1" type="hidden" value="${marketApp.signatureSha1}" />
            <input name="advertises" type="hidden" value="${marketApp.advertises}" />
            <input name="adActionTypes" type="hidden" value="${marketApp.adActionTypes}" />
            <input name="adPopupTypes" type="hidden" value="${marketApp.adPopupTypes}" />
            <input name="downloads" type="hidden" value="${marketApp.downloads}" />
            <input name="supportpad" type="hidden" value="${marketApp.supportpad}" />
            <input name="enumStatus" type="hidden" value="update" />
            <input name="virusKind" type="hidden" value="${marketApp.virusKind}" />
            <input name="virusBehaviors" type="hidden" value="${marketApp.virusBehaviors}" />
            <input name="virusName" type="hidden" value="${marketApp.virusName}" />
            <input name="md5" type="hidden" value="${marketApp.md5}" />
            <input name="size" type="hidden" value="${marketApp.size}" />
            <input name="adRisk" type="hidden" value="${marketApp.adRisk}" />
        </form>
    </body>
</html>
