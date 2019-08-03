<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title></title>
	<link rel="stylesheet" rev="stylesheet" type="text/css" href="../../skin/default/css/default.css" media="all" />
	
 	<link rel="stylesheet" rev="stylesheet" type="text/css" href="../../css/extreme/extremecomponents.css" />
    <link rel="stylesheet" rev="stylesheet" type="text/css" href="../../css/extreme/extremesite.css" />
    	
	<script type="text/javascript" src="../../js/common.js"></script>
	
</head>
<body>
<form name="icform" method="post">
	<input type="text" name="contract.id" value="${contract.id}"/><!-- 传递合同的ID -->

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
    <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="formSubmit('/contract/contractProductAction_save','_self');this.blur();">保存</a></li>
<li id="back"><a href="#" onclick="formSubmit('/contract/contractAction_list','_self');this.blur();">返回</a></li>
</ul>
    </div>
</div>
</div>
</div>
     
<div>
    <div class="textbox-header">
    <div class="textbox-inner-header">
    <div class="textbox-title">
新增货物
    </div> 
    </div>
    </div>
 
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle_mustbe">货号：</td>
	            <td class="tableContent"><input type="text" name="productNo" value=""/></td>	        
	            <td class="columnTitle">货物描述：</td>
	            <td class="tableContent">
	            	<input type="text" name="productDesc" value=""/>
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
	            <td class="tableContent"><input type="text" name="loadingRate"/></td>
	        </tr>	        
	        <tr>
	            <td class="columnTitle">数量：</td>
	           <td class="tableContent"><input type="text" name="cnumber"/></td>
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
	            <td class="tableContent"><input type="text" name="boxNum"/></td>
	            <td class="columnTitle">单价：</td>
	            <td class="tableContent"><input type="text" name="price"/></td>
	        </tr>
		</table>
	</div>
</div>

    <div class="textbox-header">
    <div class="textbox-inner-header">
    <div class="textbox-title">
货物信息列表
    </div> 
    </div>
    </div>
    
	<div class="eXtremeTable" >
	<table id="ec_table"  border="0"  cellspacing="0"  cellpadding="0"  class="tableRegion"  width="98%" >
		<thead>
		<tr>
			<td class="tableHeader"><input type="checkbox" name="selid" onclick="checkAll('id',this)"/></td>
			<td class="tableHeader">序号</td>
			<td class="tableHeader">货号</td>
			<td class="tableHeader">生产厂家</td>
			<td class="tableHeader">数量</td>
			<td class="tableHeader">包装单位</td>
			<td class="tableHeader">箱数</td>
			<td class="tableHeader">单价</td>
			<td class="tableHeader">总金额</td>
			<td class="tableHeader">操作</td>
		</tr>
		</thead>
		<tbody class="tableBody" >
		
		<s:iterator value="#dataList" var="dl" status="line">
		<tr class="odd"  onmouseover="this.className='highlight'"  onmouseout="this.className='odd'" >
			<td><input type="checkbox" name="id" value="${id}"/></td>
			<td><s:property value="#line.index+1"/></td>
			<td>${productNo}</td>
			<td>${factory.fullName}</td>
			<td>${cnumber}</td>
			<td>${packingUnit}</td>
			<td>${boxNum}</td>
			<td>${price}</td>
			<td>${amount}</td>
			<td>
				[<a href="contractProductAction_toupdate?id=${id}">修改</a>]
				[<a href="contractProductAction_delete?id=${id}&contract.id=${contract.id}">删除</a>]
				[<a href="extCproductAction_tocreate?contractProduct.contract.id=${contract.id}&contractProduct.id=${id}">附件</a>]
			</td>
		</tr>
		</s:iterator>
		</tbody>
	</table>
	</div>

 
</form>
</body>
</html>

