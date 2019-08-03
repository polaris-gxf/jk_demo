<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title></title>
	<link rel="stylesheet" rev="stylesheet" type="text/css" href="../../skin/default/css/default.css" media="all" />
	<script type="text/javascript" src="../../js/common.js"></script>
</head>
<body>
<form name="icform" method="post">

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
    <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="formSubmit('/basicinfo/factoryAction_save','_self');this.blur();">保存</a></li>
<li id="back"><a href="#" onclick="formSubmit('/basicinfo/factoryAction_list','_self');this.blur();">返回</a></li>
</ul>
    </div>
</div>
</div>
</div>
     
<div>
    <div class="textbox-header">
    <div class="textbox-inner-header">
    <div class="textbox-title">
新增生产厂家
    </div> 
    </div>
    </div>
 

    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">分类：</td>
	            <td class="tableContent"><input type="text" name="ctype"/></td>
	            <td class="columnTitle">排序号：</td>
	            <td class="tableContent"><input type="text" name="orderNo"/></td>
	        </tr>	        
	        <tr>
	            <td class="columnTitle">全称：</td>
	            <td class="tableContent"><input type="text" name="fullName"/></td>
	            <td class="columnTitle">简称：</td>
	            <td class="tableContent"><input type="text" name="factoryName"/></td>
	        </tr>
	        <tr>
	            <td class="columnTitle">联系人：</td>
	            <td class="tableContent"><input type="text" name="contractor"/></td>
	            <td class="columnTitle">电话：</td>
	            <td class="tableContent"><input type="text" name="phone"/></td>
	        </tr>
	        <tr>
	            <td class="columnTitle">手机：</td>
	            <td class="tableContent"><input type="text" name="mobile"/></td>
	            <td class="columnTitle">传真：</td>
	            <td class="tableContent"><input type="text" name="fax"/></td>
	        </tr>
	        <tr>
	            <td class="columnTitle">验货员：</td>
	            <td class="tableContent"><input type="text" name="inspector"/></td>
	            <td class="columnTitle">说明：</td>
	            <td class="tableContent"><input type="text" name="cnote"/></td>
	        </tr>

		</table>
	</div>
</div>

 
</form>
</body>
</html>

