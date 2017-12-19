<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../base.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <!--IE doesnot support relative path.-->
        <base href="<%=basePath%>" />
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>大型游戏--新增/修改</title>
        <link rel="stylesheet" type="text/css" href="css/admin_dig.css" />
        <link href="css/blue2.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" type="text/css" href="css/common.css" />
        <script type="text/javascript" src="js/lib/jquery-1.7.1.min.js"></script>
        <script type="text/javascript" src="js/big-game/public.js"></script>
        <script type="text/javascript" src="js/lib/jquery-ui-1.7.custom.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function() {
                EditEve();
                $("#lastUpdateTime").datepicker();
            });
        </script>
    </head>
    <body>
        <form action="" method="post" enctype="multipart/form-data" accept-charset="UTF-8" id="formData">
            <div class="con">
                <div class="title">
                    <span class="tag">修改应用</span>
                </div>
                
                <div class="f_l pb_15 pl_10 w_1100">
                    <!--<span class="w_72 f_l pr_15">
                        <span class="w_72 f_l ta_c lh_24">ICON</span>
                        <img src="" class="f_l w_72 h_72"/>
                        <a class="w_72 f_l ta_c lh_24 co_bl">修改</a>
                    </span>
                    <span class="w_72 f_l pr_15">
                        <span class="w_72 f_l ta_c lh_24">首页图</span>
                        <img src="" class="f_l w_72 h_72"/>
                        <a class="w_72 f_l ta_c lh_24 co_bl">修改</a>
                    </span>-->
                
                    <table class="tab2" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td class="ta_r w_110">
                                名称：
                            </td>
                            <td class="w_220">
                                <input class="w_220" name="name" id="name" type="text" value="${app.name}"/>
                            </td>
                            <td class="ta_r w_90">
                                大类：
                            </td>
                            <td class="w_140">
                                <input class="w_140" name="catalog" value="100"/>
                            </td>
                            <td class="ta_r w_90">
                                小类：
                            </td>
                            <td class="w_140">
                                <select class="w_140" name="subCatalog">
                                    <option value="101" ${app.subCatalog == 101?"selected='selected'":""}>赛车游戏</option>
                                    <option value="102" ${app.subCatalog == 102?"selected='selected'":""}>RPG角色扮演</option>
                                    <option value="103" ${app.subCatalog == 103?"selected='selected'":""}>FPS第一人称射击游戏</option>
                                    <option value="104" ${app.subCatalog == 104?"selected='selected'":""}>体育竞技</option>
                                    <option value="105" ${app.subCatalog == 105?"selected='selected'":""}>其他游戏</option>
                                </select> 
                            </td>
                            <td class="ta_r w_90">pkname：</td>
                            <td class="w_140"><input name="pkname" id="pkname" class="w_140" type="text" value="${app.pkname}"/></td>
                        </tr>
                        <tr>
                            <td class="ta_r">
                                标签：
                            </td>
                            <td>
                                <input class="w_220" type="text" value=""/>
                            </td>
                            <td class="ta_r">
                                版本：
                            </td>
                            <td>
                                <input class="w_140" name="version" type="text" value="${app.version}"/>
                            </td>
                            <td class="ta_r">
                                更新时间：
                            </td>
                            <td>
                                <fmt:formatDate value="${app.lastUpdateTime}" var="_lastUpdateTime" type="both" />
                                <input class="w_140" id="lastUpdateTime" name="lastUpdateTime" type="text" value="${_lastUpdateTime}"/>
                            </td>
                            <td class="ta_r">
                                支持系统：
                            </td>
                            <td>
                                <input class="w_140" name="osversion" id="osversion" type="text" value="${app.osversion}"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="ta_r">
                                下载修正增减：
                            </td>
                            <td colspan="7">
                                <input class="w_500" name="deltaDownload" type="text" value="${app.deltaDownload}"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="ta_r">大小：</td>
                            <td colspan="7"><input class="w_500" name="size" type="text" value="${app.size}"/></td>
                        </tr>
                        <tr>
                            <td class="ta_r">解压后大小：</td>
                            <td colspan="7"><input class="w_500" name="freeSize" type="text" value="${app.freeSize}"/></td>
                        </tr>
                        <tr>
                            <td class="ta_r">一句说：</td>
                            <td colspan="7"><input class="w_500" name="shortDesc" type="text" value="${app.shortDesc}"/></td>
                        </tr>
                        <tr>
                            <td class="ta_r">下载地址：</td>
                            <td colspan="7"><input class="w_500" name="downloadUrl" type="text" value="${app.downloadUrl}"/></td>
                        </tr>
                        <tr>
                            <td class="ta_r">语言：</td>
                            <td colspan="7"><input class="w_500" name="language" id="language" type="text" value="${app.language}"/></td>
                        </tr>
                         <tr>
                            <td class="ta_r">关键词：</td>
                            <td colspan="7"><input class="w_500" name="keywords" type="text" value="${app.keywords}"/></td>
                        </tr>
                        
                        <tr>
                            <td class="ta_r">ICON：</td>
                            <td colspan="7">
                                <input name="oldLogoUrl" type="hidden" id="oldLogoUrl" value="${app.logoUrl}"/>
                                <input class="w_250 f_l mr_10" name="logoUrl" id="logoUrl" type="text" value="${app.logoUrl}"/>
                                <input name="logoFile" id="logofile" class="f_l" type="file" />
                                <img class="wh_20" src="${app.logoUrl}"/>
                                <input name="remoteLogoUrl" id="remoteLogoUrl" type="hidden"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="ta_r">首页图：</td>
                            <td colspan="7">
                                <input name="oldIndexImgUrl" id="oldIndexImgUrl" type="hidden" value="${app.indexImgUrl}"/>
                                <input class="w_250 f_l mr_10" name="indexImgUrl" id="indexImgUrl" type="text" value="${app.indexImgUrl}"/>
                                <input name="indexImgFile" class="f_l" id="indexImgfile" type="file" />
                                <img class="wh_20" src="${app.indexImgUrl}"/>
                                <input name="remoteIndexImgUrl" id="remoteIndexImgUrl" type="hidden"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="ta_r" valign="top">简介：</td>
                            <td colspan="7" class="pt_10 pb_10"><textarea class="w_800 h_200 f_l" name="description" cols="" rows="">${app.description}</textarea></td>
                        </tr>
                        <tr>
                            <td class="ta_r" valign="top">更新：</td>
                            <td colspan="7" class="pt_10 pb_10"><textarea class="w_800 h_200 f_l" name="updateInfo" cols="" rows="">${app.updateInfo}</textarea></td>
                        </tr>
                        <tr>
                            <td class="ta_r" valign="top">截图：</td>
                            <td colspan="7" class="pt_10">
                               <input name="strImageUrls" type="hidden" value="${app.strImageUrls}" />
                                <ul id="addImg" class="addImg w_800 addImg_800">
                                    <c:forEach var="items" begin="0" items="${app.strImageUrls.split(',')}">
                                        <li id="li-addImg-${app.id}">
                                            <input name="imageUrl" value="${items}" type="text" class="f_l w_250 mr_10" />
                                            <%-- 如果用户在这一行上传文件,那么imageUrl需要干掉. --%>
                                            <input name="imageFile" type="file" class="f_l mr_10" />
                                            <span class="btn w_70 f_l">
                                                <button type="button" class="w_30 f_l mr_5" name="add">+</button>
                                                <button type="button" class="w_30 f_l mr_5" name="del">x</button>
                                            </span>
                                            <img class="wh_20" src="${items}"/>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td colspan="7">
                                <div class="btn">
                                    <button id="saveBtn" type="button" class="w_100 mt_10 mb_15 f_l">保存</button>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
                
            </div>
            <input name="id" type="hidden" value="${app.id}"/>
            <input name="status" type="hidden" value="${app.status}"/>
            <fmt:formatDate value="${app.lastFetchTime}" var="_lastFetchTime" type="both" />
            <input id="lastFetchTime" name="lastFetchTime" type="hidden" value="${_lastFetchTime}"/>
            <input name="marketAppId" type="hidden" value="${app.marketAppId}"/>
            <input name="marketName" type="hidden" value="${app.marketName}"/>

            <input name="publisherShortName" type="hidden" value="${app.publisherShortName}"/>
            <input name="versionCode" type="hidden" value="${app.versionCode}"/>
            <input name="price" type="hidden" value="${app.price}"/>
            <input name="starRating" type="hidden" value="${app.starRating}"/>
            <input name="viewCount" type="hidden" value="${app.viewCount}"/>
            
            
            <input name="permissions" type="hidden" value="${app.permissions}"/>
            <input name="signatureSha1" type="hidden" value="${app.signatureSha1}"/>
            <input name="advertises" type="hidden" value="${app.advertises}"/>
            <input name="adActionTypes" type="hidden" value="${app.adActionTypes}"/>
            <input name="adPopupTypes" type="hidden" value="${app.adPopupTypes}"/>
            <input name="downloadRank" type="hidden" value="${app.downloadRank}"/>
            <input name="supportpad" type="hidden" value="${app.supportpad}"/>
            <input name="enumStatus" type="hidden" value="update"/>
            <input name="auditCatalog" type="hidden" value="${app.auditCatalog}"/>
            <input name="hidden" type="hidden" value="${app.hidden}"/>
            <input name="pageUrl" type="hidden" value="${app.pageUrl}"/>
            <input name="Keywords" type="hidden" value=""/>
            <input name="realDownload" type="hidden" value="${app.realDownload}"/>
            <input name="deltaDownload" type="hidden" value="${app.deltaDownload}"/>
            <input name="lastDayDownload" type="hidden" value="${app.lastDayDownload}"/>
            <input name="lastDayDelta" type="hidden" value="${app.lastDayDelta}"/>
            <input name="lastWeekDownload" type="hidden" value="${app.lastWeekDownload}"/>
            <input name="lastWeekDelta" type="hidden" value="${app.lastWeekDelta}"/>
            <input name="virusKind" type="hidden" value="${app.virusKind}"/>
            <input name="virusBehaviors" type="hidden" value="${app.virusBehaviors}"/>
            <input name="virusName" type="hidden" value="${app.virusName}"/>
        </form>
    </body>
</html>
