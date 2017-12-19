<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
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
        <form action="admin/app/save.d" method="post" enctype="multipart/form-data" accept-charset="UTF-8" id="formData">
            <div class="con">
                <div class="title">
                    <span class="tag">编辑应用</span>
                </div>
                
                <div class="f_l pb_15 pl_10 w_1100">
                    <span class="w_72 f_l pr_15">
                        <span class="w_72 f_l ta_c lh_24">ICON</span>
                        <img src="" class="f_l w_72 h_72"/>
                        <a class="w_72 f_l ta_c lh_24 co_bl">修改</a>
                    </span>
                
                    <table class="tab2" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td class="ta_r w_80">
                                名称：
                            </td>
                            <td class="w_220">
                                <input class="w_220" name="name" type="text" value=""/>
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
                                    <option value="1">赛车游戏</option>
                                    <option value="2">RPG角色扮演</option>
                                    <option value="3">FPS第一人称射击游戏</option>
                                    <option value="4">体育竞技</option>
                                    <option value="5">其他游戏</option>
                                </select> 
                            </td>
                            <td class="w_80"></td>
                            <td class="w_120"></td>
                        </tr>
                        <tr>
                            <td class="ta_r">
                                标签：
                            </td>
                            <td>
                                <input class="w_220" name="" type="text" value=""/>
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
                                <input class="w_120" name="lastUpdateTime" type="text" value=""/>
                            </td>
                            <td class="ta_r">
                                支持系统：
                            </td>
                            <td>
                                <input class="w_120" name="osversion" type="text" value=""/>
                            </td>
                        </tr>
                        <tr>
                            <td class="ta_r">一句说：</td>
                            <td colspan="7"><input class="w_500" name="shortDesc" type="text" value=""/></td>
                        </tr>
                    </table>
                </div>
                
                <div class="than">
                    <h3><span class="f_l">手动修改   最后修改时间：（）</span><span id="synch" class="btn w_60 f_r"><button>同步</button></span></h3>
                    <table class="tab2" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td rowspan="2" class="w_80"><img src="" class="w_48"/></td>
                            <td class="w_100 ta_c">apk：</td>
                            <td class="w_320"><input class="w_300" name="" type="text" value=""/></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td colspan="3"><textarea id="des" class="w_500 h_200 f_l" name="description" cols="" rows=""></textarea></td>
                        </tr>
                        <tr>
                            <td colspan="3"><textarea class="w_500 h_200 f_l" name="updateInfo" cols="" rows=""></textarea></td>
                        </tr>
                    </table>
                    
                    <ul id="addImg" class="addImg">
                        <li>
                            <input name="strImageUrls" type="text" class="f_l w_200 mr_10" />
                            <span class="btn w_80 f_l">
                                <button type="button" class="w_30 f_l mr_5" name="add">+</button>
                                <button type="button" class="w_30 f_l mr_5" name="del">x</button>
                            </span>
                        </li>
                    </ul>
                </div>
                
                <div class="than">
                    <h3>内容源内容   最后更新时间：（）</h3>
                    <table class="tab2" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td rowspan="2" class="w_80"></td>
                            <td class="w_100 ta_c">apk：</td>
                            <td class="w_320"><input class="w_300" name="" type="text" value=""/></td>
                        </tr>
                        <tr>
                            <td class="ta_c">详情链接：</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td colspan="3"><textarea class="w_500 h_200 f_l" name="description" cols="" rows=""></textarea></td>
                        </tr>
                        <tr>
                            <td colspan="3"><textarea class="w_500 h_200 f_l" name="updateInfo" cols="" rows=""></textarea></td>
                        </tr>
                    </table>
                </div>
                
                <div class="btn">
                    <button id="postdata" type="submit" class="w_100 mt_10 mb_15 f_l">保存</button>
                </div>
            </div>
        </form>
    </body>
</html>
