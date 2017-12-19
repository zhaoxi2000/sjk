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
                $("#lastUpdateTime").datepicker();
            });
        </script>
    </head>
    <body>
        <form action="admin/biggame/save.d" method="post" enctype="multipart/form-data" accept-charset="UTF-8" id="formData">
            <div class="con">
                <div class="title">
                    <span class="tag">新增应用</span>
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
                            <td class="ta_r w_80">
                                名称：
                            </td>
                            <td class="w_220">
                                <input class="w_220" name="name" id="name" type="text" value=""/>
                            </td>
                            <td class="ta_r w_80">
                                大类：
                            </td>
                            <td class="w_120">
                                <input class="w_120" name="catalog" value="100"/>
                            </td>
                            <td class="ta_r w_80">
                                小类：
                            </td>
                            <td class="w_120">
                                <select class="w_120" name="subCatalog">
                                    <option value="101" selected="selected">赛车游戏</option>
                                    <option value="102">RPG角色扮演</option>
                                    <option value="103">FPS第一人称射击游戏</option>
                                    <option value="104">体育竞技</option>
                                    <option value="105">其他游戏</option>
                                </select> 
                            </td>
                            <td class="ta_r w_80">pkname：</td>
                            <td class="w_120"><input name="pkname" id="pkname" class="w_120" type="text" value=""/></td>
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
                                <input class="w_120" name="version" type="text" value=""/>
                            </td>
                            <td class="ta_r">
                                更新时间：
                            </td>
                            <td>
                                <input class="w_120" name="lastUpdateTime" id="lastUpdateTime" type="text" value=""/>
                            </td>
                            <td class="ta_r">
                                支持系统：
                            </td>
                            <td>
                                <input class="w_120" name="osversion" id="osversion" type="text" value=""/>
                            </td>
                        </tr>
                        <tr>
                            <td class="ta_r">下载修正增减：</td>
                            <td colspan="7"><input class="w_500" name="deltaDownload" type="text" value=""/></td>
                        </tr>
                        <tr>
                            <td class="ta_r">大小：</td>
                            <td colspan="7"><input class="w_500" name="size" type="text" value=""/></td>
                        </tr>
                        <tr>
                            <td class="ta_r">解压后大小：</td>
                            <td colspan="7"><input class="w_500" name="freeSize" type="text" value=""/></td>
                        </tr>
                        <tr>
                            <td class="ta_r">一句说：</td>
                            <td colspan="7"><input class="w_500" name="shortDesc" type="text" value=""/></td>
                        </tr>
                        <tr>
                            <td class="ta_r">下载地址：</td>
                            <td colspan="7"><input class="w_500" name="downloadUrl" type="text" value=""/></td>
                        </tr>
                        <tr>
                            <td class="ta_r">语言：</td>
                            <td colspan="7"><input class="w_500" name="language" id="language" type="text" value=""/></td>
                        </tr>
                        <tr>
                            <td class="ta_r">关键词：</td>
                            <td colspan="7"><input class="w_500" name="Keywords" type="text" value=""/></td>
                        </tr>
                        <tr>
                            <td class="ta_r">ICON：</td>
                            <td colspan="7"><input class="w_500" name="logoUrl" type="text" value=""/></td>
                        </tr>
                        <tr>
                            <td class="ta_r">首页图：</td>
                            <td colspan="7"><input class="w_500" name="indexImgUrl" type="text" value=""/></td>
                        </tr>
                        <tr>
                            <td class="ta_r" valign="top">简介：</td>
                            <td colspan="7" class="pt_10 pb_10"><textarea class="w_800 h_200 f_l" name="description" cols="" rows=""></textarea></td>
                        </tr>
                        <tr>
                            <td class="ta_r" valign="top">更新：</td>
                            <td colspan="7" class="pt_10 pb_10"><textarea class="w_800 h_200 f_l" name="updateInfo" cols="" rows=""></textarea></td>
                        </tr>
                        <tr>
                            <td class="ta_r" valign="top">截图：</td>
                            <td colspan="7" class="pt_10">
                                <ul id="addImg" class="addImg">
                                    <li>
                                        <input name="strImageUrls" type="text" class="f_l w_250 mr_10" />
                                        <span class="btn w_80 f_l">
                                            <button type="button" class="w_30 f_l mr_5" name="add">+</button>
                                            <button type="button" class="w_30 f_l mr_5" name="del">x</button>
                                        </span>
                                    </li>
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
            
            <input name="marketAppId" type="hidden" value="0"/>
            <input name="marketName" type="hidden" value="shoujikong"/>
           
            <input name="publisherShortName" type="hidden" value="0"/>
            <input name="versionCode" type="hidden" value="0"/>
            <input name="price" type="hidden" value="0"/>
            <input name="starRating" type="hidden" value="0"/>
            <input name="viewCount" type="hidden" value="0"/>
            <input name="supportpad" type="hidden" value="0"/>
            <input name="enumStatus" type="hidden" value="add"/>
            <input name="auditCatalog" type="hidden" value="0"/>
            <input name="hidden" type="hidden" value="0"/>
            <input name="realDownload" type="hidden" value="0"/>
            <input name="keywords" type="hidden" value=""/>

        </form>
    </body>
</html>
