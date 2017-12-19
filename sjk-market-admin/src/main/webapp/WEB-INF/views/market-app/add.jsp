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
        <title>大型游戏--新增--MarketApp</title>
        <link rel="stylesheet" type="text/css" href="css/admin_dig.css" />
        <link href="css/blue2.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" type="text/css" href="css/common.css" />
        <script type="text/javascript" src="js/lib/jquery-1.7.1.min.js"></script> 
        <script type="text/javascript" src="js/lib/jquery-ui-1.7.custom.min.js"></script>
        <script type="text/javascript" src="js/market-app/public.js"></script>
         
    </head>
    <body>
        <form action="admin/marketapp/save.d" method="post" enctype="multipart/form-data" accept-charset="UTF-8" id="formData">
            <div class="con">
                <div class="title">
                    <span class="tag">新增应用</span>
                </div>
                
                <div class="f_l pb_15 pl_10 w_1300">
                    <table class="tab2 w_1300" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td class="ta_r w_120">
                                名称：
                            </td>
                            <td class="w_240">
                                <input class="w_200" name="name" id="name" type="text" value=""/> <span class="co_red">*</span>
                            </td>
                            <td class="ta_r w_100">
                                大类：
                            </td>
                            <td class="w_230">
                                <input class="w_130" name="catalog" value="100"  readonly="readonly"/>
                            </td>
                            <td class="ta_r w_100">
                                小类：
                            </td>
                            <td class="w_230">
                                <select class="w_130" name="subCatalog" id="subCatalog">
                                    <option value="101" selected="selected">赛车游戏</option>
                                    <option value="102">RPG角色扮演</option>
                                    <option value="103">FPS第一人称射击游戏</option>
                                    <option value="104">体育竞技</option>
                                    <option value="105">其他游戏</option>
                                </select> 
                                <input name="subCatalogName" id="subCatalogName" type="hidden" value="赛车游戏"/> 
                                <span class="co_red">*</span>
                            </td>
                        </tr>
                        <tr>
                            <td class="ta_r">
                                版本：
                            </td>
                            <td>
                                <input class="w_130" name="version" type="text" value=""/> <span class="co_red">*</span>
                            </td> 
                            <td class="ta_r">
                                支持系统：
                            </td>
                            <td>
                                <input class="w_130" name="osversion" id="osversion" type="text" value=""/>
                            </td>
                            <td class="ta_r">pkname：</td>
                            <td><input name="pkname" id="pkname" class="w_130" type="text" value=""/> <span class="co_red">*</span></td>
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
                                        <span class="btn w_70 f_l">
                                            <button type="button" class="w_30 f_l mr_5" name="add">+</button>
                                            <button type="button" class="w_30 f_l" name="del">x</button>
                                        </span>
                                    </li>
                                </ul>
                            </td>
                        </tr>
                        <!--
                        <tr>
                            <td class="ta_r">下载修正增减：</td>
                            <td colspan="5"><input class="w_500" name="deltaDownload" type="text" value=""/></td>
                        </tr>
                         -->
                        <tr>
                            <td class="ta_r">一句说：</td>
                            <td colspan="5"><input class="w_500" name="shortDesc" type="text" value=""/></td>
                        </tr>
                        <tr>
                            <td class="ta_r">
                                下载地址：
                            </td>
                            <td colspan="5">
                                <input class="w_500" name="downloadUrl" type="text" value="" />
                            </td>
                        </tr>
                        <tr>
                            <td class="ta_r">语言：</td>
                            <td colspan="5">
                                <select class="f_l w_160 mr_10" name="language">
                                    <option value="中文" selected="selected">中文</option>
                                    <option value="英文">英文</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td class="ta_r">关键词：</td>
                            <td colspan="5"><input class="w_500" name="Keywords" type="text" value=""/></td>
                        </tr>
                        <tr>
                            <td class="ta_r">ICON：</td>
                            <td colspan="5">
                                <input name="oldLogoUrl" type="hidden" id="oldLogoUrl" value=""/>
                                <input class="w_250 mr_10 f_l mt_10" name="logoUrl" id="logoUrl" type="text" value=""/>
                                <input name="logoFile" class="f_l mt_10" id="logofile" type="file" />
                                <input name="remoteLogoUrl" id="remoteLogoUrl" type="hidden"/>
                                <span class="ml_10 f_l"><span class="co_red">*</span> ICON文件大小不能超过100KB</span>
                            </td>
                        </tr>
                        <tr>
                            <td class="ta_r">首页图：</td>
                            <td colspan="5">
                                <input name="oldIndexImgUrl" id="oldIndexImgUrl" type="hidden" value=""/>
                                <input class="w_250 mr_10 f_l mt_10" name="indexImgUrl" id="indexImgUrl" type="text" value=""/>
                                <input name="indexImgFile" class="f_l mt_10" id="indexImgfile" type="file" />
                                <input name="remoteIndexImgUrl" id="remoteIndexImgUrl" type="hidden"/>
                                <span class="ml_10 f_l"><span class="co_red">*</span> 首页图文件大小不能超过100KB</span>
                            </td>
                        </tr>
                        <tr>
                            <td class="ta_r" valign="top">简介：</td>
                            <td colspan="5" class="pt_10 pb_10"><textarea class="w_800 h_200 f_l" name="description" cols="" rows=""></textarea></td>
                        </tr>
                        <tr>
                            <td class="ta_r" valign="top">更新：</td> 
                            <td colspan="5" class="pt_10 pb_10">
                            <textarea class="w_800 h_200 f_l" name="updateInfo" cols="" rows=""></textarea>
                            </td>
                        </tr>
                        <tr>
                            <td class="ta_r" valign="top">截图：</td>
                            <td colspan="5" class="pt_10">
                                <ul id="addImg" class="addImg w_800 addImg_800">
                                    <li>
                                        <input name="strImageUrls" type="text" class="f_l w_250 mr_10" />
                                        <input name="imageFile" type="file" class="f_l mr_10" />
                                        <span class="btn w_80 f_l">
                                            <button type="button" class="w_30 f_l mr_5" name="add">+</button>
                                            <button type="button" class="w_30 f_l mr_5" name="del">x</button>
                                        </span>
                                        <span class="f_l lh_20"><span class="co_red">*</span> 图片大小不超过100KB</span>
                                    </li>
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

            <input name="marketName" type="hidden" value="shoujikong"/> 
            <input name="id" type="hidden" value="0"/> 
            <input name="appId" type="hidden" value="0"/> 
            <input name="apkId" type="hidden" value="0"/>
            <input name="publisherShortName" type="hidden" value="0"/>
            <input name="versionCode" type="hidden" value="0"/>
            <input name="price" type="hidden" value="0"/>
            <input name="starRating" type="hidden" value="0"/>
            <input name="viewCount" type="hidden" value="0"/>
            <input name="adRisk" type="hidden" value="0"/>
            <input name="pathStatus" type="hidden" value="0"/> 
            <input name="status" type="hidden"  value="0"/>
            
            <input name="permissions" type="hidden" value=""/>
            <input name="signatureSha1" type="hidden" value=""/>
            <input name="advertises" type="hidden" value=""/>
            <input name="adActionTypes" type="hidden" value=""/>
            <input name="adPopupTypes" type="hidden" value=""/>
            
            <input name="downloads" type="hidden" value="0"/>
            <input name="supportpad" type="hidden" value="0"/>
            <input name="enumStatus" type="hidden" value="add"/>
            <input name="hidden" type="hidden" value="0"/>
            <input name="realDownload" type="hidden" value="0"/>
            <input name="virusKind" type="hidden" value="0"/>
            <input name="virusBehaviors" type="hidden" value=""/>
            <input name="virusName" type="hidden" value=""/>
            <input name="md5" type="hidden" value=""/>
        </form>
    </body>
</html>
