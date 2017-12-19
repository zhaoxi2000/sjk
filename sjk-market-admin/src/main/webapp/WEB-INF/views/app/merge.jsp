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
        <title>应用信息--新增/修改</title>
        <link rel="stylesheet" type="text/css" href="css/admin_dig.css" />
        <link rel="stylesheet" type="text/css" href="css/common.css" />
        <script type="text/javascript" src="js/lib/jquery-1.7.1.min.js"></script>
        <script type="text/javascript" src="js/soft/public.js"></script>
    </head>
    <body>
        <form action="admin/app/save.d" method="post"
        enctype="multipart/form-data" accept-charset="UTF-8" id="formData">
            <div class="con">
                <div class="title">
                    <span class="tag">编辑应用</span>
                </div>
                <div class="f_l pb_15 pl_10 w_1100">
                    <span class="w_72 f_l pt_20">
                        <img class="f_l w_72 h_72" src="${app.logoUrl}" />
                    </span>
                    <table class="tab2" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td class="ta_r w_80">
                                名称：
                            </td>
                            <td class="w_230">
                                <input class="w_220" name="name" type="text" value="${app.name}" />
                            </td>
                            <td class="ta_r w_80">
                                大类：
                            </td>
                            <td class="w_150">
                                <select class="w_150" name="catalog" id="catalog">
                                    <option value="1" ${app.catalog==1?"selected='selected'":""}>软件</option>
                                    <option value="2" ${app.catalog==2?"selected='selected'":""}>游戏</option>
                                    <option value="100" ${app.catalog==100?"selected='selected'":""}>大型游戏</option>
                                </select>
                            </td>
                            <td class="ta_r w_80">
                                小类：
                            </td>
                            <td class="w_150">
                                <select class="w_150" name="subCatalog" id="subCatalog" subCatalog="${app.subCatalog}"></select>
                            </td>
                            <td class="ta_r w_80">
                                pkname：
                            </td>
                            <td class="w_150"><input name="pkname" class="w_150"
                                type="text" value="${app.pkname}" />
                            </td>
                        </tr>
                        <tr>
                            <td class="ta_r">
                                标签：
                            </td>
                            <td>
                                <a class="co_bl" id="editTagBtn">修改标签</a>
                            </td>
                            <td class="ta_r">
                                版本：
                            </td>
                            <td><input class="w_150" name="version" type="text"
                                value="${app.version}" />
                            </td>
                            <td class="ta_r">
                                更新时间：
                            </td>
                            <td>
                                <fmt:formatDate value="${app.lastUpdateTime}"
                                var="_lastUpdateTime" type="both" />
                                <input class="w_150"
                                id="lastUpdateTime" name="lastUpdateTime" type="text"
                                value="${_lastUpdateTime}" />
                            </td>
                            <td class="ta_r">
                                支持系统：
                            </td>
                            <td><input class="w_150" name="osversion" type="text"
                                value="${app.osversion}" />
                            </td>
                        </tr>
                        <tr>
                            <td class="ta_r">
                                一句简介：
                            </td>
                            <td colspan="4"><input class="w_500" name="shortDesc"
                                type="text" value="${app.shortDesc}" />
                            </td>
                            <td colspan="2" class="ta_r">
                                下载修正增减：
                            </td>
                            <td><input class="w_150" name="deltaDownload" type="text"
                                value="${app.deltaDownload}" />
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="f_l">
                    <div class="than">
                        <h3><span class="f_l">手动修改 最后修改时间：（<span id="applUTime">${app.lastUpdateTime}</span>） </span><span class="btn w_60 f_r"><button id="synch" type="button">同步</button></span></h3>
                        <table class="tab2 w_600" border="0" cellspacing="0"
                        cellpadding="0">
                            <tr>
                                <td class="w_80 ta_r">
                                    ICON：
                                </td>
                                <td class="w_520"><input name="oldLogoUrl" type="hidden"
                                    id="oldLogoUrl" value="${app.logoUrl}" /><input
                                    class="w_250 f_l mr_10" name="logoUrl" id="newlogoUrl"
                                    type="text" value="${app.logoUrl}" /><input name="logoFile"
                                    id="logofile" class="f_l" type="file" />
                                    <img class="wh_20"
                                    src="${app.logoUrl}" />
                                    <input name="remoteLogoUrl"
                                    id="remoteLogoUrl" type="hidden" />
                                </td>
                            </tr>
                            <tr>
                                <td class="ta_r">
                                    apk：
                                </td>
                                <td><input class="w_400" id="newdownloadUrl"
                                    name="downloadUrl" type="text" value="${app.downloadUrl}" />
                                </td>
                            </tr>
                            <tr>
                                <td class="ta_r" valign="top">
                                    简介：
                                </td>
                                <td class="pt_10">
                                    <textarea id="newdescription" name="description" class="w_500 h_200 f_l" cols="" rows="">${app.description}</textarea>
                                </td>
                            </tr>
                            <tr>
                                <td class="ta_r pt_5" valign="top">
                                    更新信息：
                                </td>
                                <td class="pt_15">
                                    <textarea class="w_500 h_200 f_l" name="updateInfo" id="newupdateInfo" cols="" rows="">${app.updateInfo}</textarea>
                                </td>
                            </tr>
                        </table>
                        <ul id="addImg" class="addImg ml_80 pt_15">
                            <c:forEach var="items" begin="0"
                            items="${app.strImageUrls.split(',')}">
                                <li><input name="strImageUrls" type="text" value="${items}"
                                    class="f_l w_250 mr_10" /><span class="btn w_80 f_l"> <button type="button" class="w_30 f_l mr_5" name="add">+</button> <button type="button" class="w_30 f_l mr_5" name="del">x</button> </span>
                                    <img class="wh_20" src="${items}" />
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                    <div class="than">
                        <h3> 内容源内容 最后更新时间：（<span id="marketlUTime">${marketApp.lastUpdateTime}</span>） </h3>
                        <table class="tab2 w_600" border="0" cellspacing="0"
                        cellpadding="0">
                            <tr>
                                <td class="w_80 ta_r">
                                    ICON：
                                </td>
                                <td class="w_520"><input class="w_400 f_l" type="text"
                                    id="oldlogoUrl" value="${marketApp.logoUrl}" />
                                    <img
                                    class="wh_20" src="${marketApp.logoUrl}" />
                                </td>
                            </tr>
                            <tr>
                                <td class="ta_r">
                                    apk：
                                </td>
                                <td><input class="w_400 mr_10" type="text"
                                    id="olddownloadUrl" value="${marketApp.downloadUrl}" /><a
                                    class="co_bl" target="_blank" href="${marketApp.detailUrl}">详情链接</a>
                                    <input name="detailUrl" type="hidden"
                                    value="${marketApp.detailUrl}" />
                                </td>
                            </tr>
                            <tr>
                                <td class="ta_r" valign="top">
                                    简介：
                                </td>
                                <td class="pt_10">
                                    <textarea id="olddescription" class="w_500 h_200 f_l" cols="" rows="">${marketApp.description}</textarea>
                                </td>
                            </tr>
                            <tr>
                                <td class="ta_r pt_5" valign="top">
                                    更新信息：
                                </td>
                                <td class="pt_15">
                                    <textarea class="w_500 h_200 f_l" id="oldupdateInfo" cols="" rows="">${marketApp.updateInfo}</textarea>
                                </td>
                            </tr>
                            <tr>
                                <td class="ta_r pt_10" valign="top">
                                    截图：
                                </td>
                                <td id="ImageUrlsname"><input id="oldstrImage" type="hidden"
                                    value="${marketApp.strImageUrls}" />
                                    <c:forEach var="items"
                                    begin="0" items="${marketApp.strImageUrls.split(',')}">
                                        <img class="w_72 h_72 f_l pr_15 pt_20" src="${items}" />
                                    </c:forEach>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
                <br />
                <div class="btn">
                    <span title="是否容许自动覆盖该应用"> 是否自动覆盖： 
                        <input type="radio" value="0" name="autoCover" ${app.autoCover==false?"checked='checked'":""}>否
                        <input type="radio" name="autoCover" value="1" ${app.autoCover==true?"checked='checked'":""}>是<br />
                    </span>
                    <button id="postdata" type="submit" class="w_100 mt_10 mb_15 f_l">保存</button>
                </div>
            </div>
            <input name="id" type="hidden" id="id" value="${app.id}" /><input
            name="appId" type="hidden" id="appId" value="${app.appId}" /><input
            name="apkId" type="hidden" id="apkId" value="${app.apkId}" /><input
            name="marketAppId" type="hidden" value="${app.marketAppId}" /><input
            name="marketName" type="hidden" value="${app.marketName}" /><input
            name="size" type="hidden" value="${app.size}" /><input
            name="publisherShortName" type="hidden"
            value="${app.publisherShortName}" /><input name="versionCode"
            type="hidden" value="${app.versionCode}" /><input name="price"
            type="hidden" value="${app.price}" /><input name="starRating"
            type="hidden" value="${app.starRating}" /><input name="viewCount"
            type="hidden" value="${app.viewCount}" /><input name="downloadRank"
            type="hidden" value="${app.downloadRank}" /><input
            name="supportpad" type="hidden" value="${app.supportpad}" /><input
            name="enumStatus" type="hidden" value="update" /><input
            name="auditCatalog" type="hidden" value="${app.auditCatalog}" /><input
            name="hidden" type="hidden" value="${app.hidden}" /><input
            name="indexImgUrl" type="hidden" value="${app.indexImgUrl}" /><input
            name="minsdkversion" type="hidden" value="${app.minsdkversion}" /><input
            name="screens" type="hidden" value="${app.screens}" /><input
            name="models" type="hidden" value="${app.models}" /><input
            name="keywords" type="hidden" value="${app.keywords}" /><input
            name="downloadRank" type="hidden" value="${app.downloadRank}" /><input
            name="pageUrl" type="hidden" value="${app.pageUrl}" /><input
            name="notice" type="hidden" value="${app.notice}" /><input
            name="officeHomepage" type="hidden" value="${app.officeHomepage}" />
            <input name="language" type="hidden" value="${app.language}" /><input
            name="audit" type="hidden" value="${app.audit}" /><input
            name="pathStatus" type="hidden" value="${app.pathStatus}" /><input
            name="status" type="hidden" value="${app.status}" /><input
            name="permissions" type="hidden" value="${app.permissions}" /><input
            name="signatureSha1" type="hidden" value="${app.signatureSha1}" /><input
            name="advertises" type="hidden" value="${app.advertises}" /><input
            name="adActionTypes" type="hidden" value="${app.adActionTypes}" /><input
            name="adPopupTypes" type="hidden" value="${app.adPopupTypes}" /><input
            name="supportpad" type="hidden" value="${app.supportpad}" /><input
            name="auditCatalog" type="hidden" value="${app.auditCatalog}" /><input
            name="hidden" type="hidden" value="${app.hidden}" /><input
            name="Keywords" type="hidden" value="" /><input name="realDownload"
            type="hidden" value="${app.realDownload}" /><input
            name="deltaDownload" type="hidden" value="${app.deltaDownload}" /><input
            name="lastDayDownload" type="hidden" value="${app.lastDayDownload}" />
            <input name="lastDayDelta" type="hidden" value="${app.lastDayDelta}" />
            <input name="officialSigSha1" type="hidden"
            value="${app.officialSigSha1}" /><input name="lastWeekDownload"
            type="hidden" value="${app.lastWeekDownload}" /><input
            name="lastWeekDelta" type="hidden" value="${app.lastWeekDelta}" /><input
            name="virusKind" type="hidden" value="${app.virusKind}" /><input
            name="virusBehaviors" type="hidden" value="${app.virusBehaviors}" />
            <input name="virusName" type="hidden" value="${app.virusName}" /><input
            name="md5" type="hidden" value="${app.md5}" />
            <fmt:formatDate value="${app.lastFetchTime}" var="_lastFetchTime"
            type="both" />
            <input name="lastFetchTime" type="hidden" value="${_lastFetchTime}" />
        </form>
        <div class="tagWin" id="editTag">
            <div class="editType w_700 h_280">
                <ul id="TagMenu"></ul>
            </div>
            <span class="btn"> <button type="button" class="ml_20" id="tagPost">提交</button> <button type="button" class="ml_10" id="tagClose">关闭</button> </span>
        </div>
    </body>
</html>
