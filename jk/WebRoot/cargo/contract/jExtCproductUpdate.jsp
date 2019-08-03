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
	<input type="hidden" name="id" value="${id}"/>
	<input type="text" name="contract.id" value="${contractProduct.contract.id}"/><!-- 传递合同的ID -->
	<input type="hidden" name="contractProduct.id" value="${contractProduct.id}"/><!-- 传递货物的ID -->

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
    <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="formSubmit('/contract/extCproductAction_save','_self');this.blur();">保存</a></li>
<li id="back"><a href="#" onclick="formSubmit('/contract/contractProductAction_tocreate','_self');this.blur();">返回</a></li>
</ul>
    </div>
</div>
</div>
</div>
     
<div>
    <div class="textbox-header">
    <div class="textbox-inner-header">
    <div class="textbox-title">
修改附件
    </div> 
    </div>
    </div>
 
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle_mustbe">货号：</td>
	            <td class="tableContent"><input type="text" name="productNo" value="${productNo}"/></td>	        
	            <td class="columnTitle">货物描述：</td>
	            <td class="tableContent">
	            	<input type="text" name="productDesc" value="${productDesc}"/>
	        	</td>
	        </tr>	        
	        <tr>
	            <td class="columnTitle">生产厂家：</td>
	            <td class="tableContent">
	            	<s:select name="factory.id" list="factoryList"
	            			 listValue="fullName" listKey="id"
	            			 headerValue="--请选择--" headerKey=""
	            	></s:select>
	            </td>
	            <td class="columnTitle">装率：</td>
	            <td class="tableContent"><input type="text" name="loadingRate" value="${loadingRate}"/></td>
	        </tr>	        
	        <tr>
	            <td class="columnTitle">数量：</td>
	           <td class="tableContent"><input type="text" name="cnumber" value="${cnumber}"/></td>
	            <td class="columnTitle">包装单位：</td>
	            <td class="tableContent">
		<s:select          
            list="#{'PCS':'PCS','SETS':'SETS'}" name="packingUnit"
            headerValue="--请选择--" headerKey=""
            emptyOption="false"
            />	            
	            </td>
	        </tr>
	        <tr>
	            <td class="columnTitle">箱数：</td>
	            <td class="tableContent"><input type="text" name="boxNum" value="${boxNum}"/></td>
	            <td class="columnTitle">单价：</td>
	            <td class="tableContent"><input type="text" name="price" value="${price}"/></td>
	        </tr>
		</table>
	</div>
</div>


 
</form>
</body>
</html>

