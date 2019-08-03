<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title></title>

	<link rel="stylesheet" rev="stylesheet" type="text/css" href="../../skin/default/css/default.css" media="all" />
	
 	<link rel="stylesheet" rev="stylesheet" type="text/css" href="../../css/extreme/extremecomponents.css" />
    <link rel="stylesheet" rev="stylesheet" type="text/css" href="../../css/extreme/extremesite.css" />
</head>
<body>
<form name="icform" method="post">

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
    <div id="navMenubar">
<ul>
<li id="back"><a href="#" onclick="history.go(-1);this.blur();">返回</a></li>
</ul>
    </div>
</div>
</div>
</div>
     
<div>
    <div class="textbox-header">
    <div class="textbox-inner-header">
    <div class="textbox-title">
查看购销合同
    </div> 
    </div>
    </div>

    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle_mustbe">客户名称：</td>
	            <td class="tableContent">${customName}</td>	        
	            <td class="columnTitle">打印版式：</td>
	            <td class="tableContentAuto">
	            	<s:if test="printStyle==1">一个货物</s:if>
					<s:if test="printStyle==2">两个货物</s:if>
	        	</td>
	        </tr>	        
	        <tr>
	            <td class="columnTitle">收&nbsp;购&nbsp;方：</td>
	            <td class="tableContent">${offeror}</td>
	            <td class="columnTitle">合&nbsp;同&nbsp;号：</td>
	            <td class="tableContent">${contractNo}</td>
	        </tr>	        
	        <tr>
	            <td class="columnTitle">签单日期：</td>
	            <td class="tableContent"><s:date name="signingDate" format="yyyy-MM-dd"/></td>
	            <td class="columnTitle">制&nbsp;单&nbsp;人：</td>
	            <td class="tableContent">${inputBy}</td>
	        </tr>
	        <tr>
	            <td class="columnTitle">审&nbsp;单&nbsp;人：</td>
	            <td class="tableContent">${checkBy}</td>
	            <td class="columnTitle">验&nbsp;货&nbsp;员：</td>
	            <td class="tableContent">${inspector}</td>
	        </tr>
	        <tr>
	            <td class="columnTitle">船&nbsp;&nbsp;&nbsp;&nbsp;期：</td>
	            <td class="tableContent"><s:date name="shipTime" format="yyyy-MM-dd"/></td>	            
	            <td class="columnTitle">交货期限：</td>
	            <td class="tableContent"><s:date name="deliveryPeriod" format="yyyy-MM-dd"/></td>		            
	        </tr>
	        <tr>
	            <td class="columnTitle">重要程度：</td>
	            <td class="tableContentAuto" colspan="3">
	            	<s:if test="importNum==1">★</s:if>
					<s:if test="importNum==2">★★</s:if>
					<s:if test="importNum==3">★★★</s:if>
	        	</td>	            
	        </tr>
	        <tr>
	            <td class="columnTitle">要&nbsp;&nbsp;&nbsp;&nbsp;求：</td>
	            <td colspan="3" class="tableContent"><pre>&nbsp;&nbsp;${crequest}</pre></td>
	        </tr>
	        <tr>
	            <td class="columnTitle">说&nbsp;&nbsp;&nbsp;&nbsp;明：</td>
	            <td colspan="3" class="tableContent">${remark}</td>
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
	<table id="ec_table1"  border="0"  cellspacing="0"  cellpadding="0"  class="tableRegion"  width="98%" >
		<thead>
		<tr>
			<td class="tableHeader">序号</td>
			<td class="tableHeader">货号</td>
			<td class="tableHeader">生产厂家</td>
			<td class="tableHeader">数量</td>
			<td class="tableHeader">包装单位</td>
			<td class="tableHeader">箱数</td>
			<td class="tableHeader">单价</td>
			<td class="tableHeader">总金额</td>
		</tr>
		</thead>
		<tbody class="tableBody" >
		
		<s:iterator value="%{#root.contractProducts}" var="cp" status="line">
		<tr class="odd"  onmouseover="this.className='highlight'"  onmouseout="this.className='odd'" >
			<td>&nbsp;<s:property value="#line.index+1"/></td>
			<td>${productNo}</td>
			<td>${factory.fullName}</td>
			<td>${cnumber}</td>
			<td>${packingUnit}</td>
			<td>${boxNum}</td>
			<td>${price}</td>
			<td>${amount}</td>
		</tr>
				<s:iterator value="%{#cp.extCproducts}" var="dl" status="line">
				<tr class="odd"  onmouseover="this.className='highlight'"  onmouseout="this.className='odd'" >
					<td></td>
					<td><font color="blue">附件<s:property value="#line.index+1"/>：</font> ${productNo}</td>
					<td>${factory.fullName}</td>
					<td>${cnumber}</td>
					<td>${packingUnit}</td>
					<td>${boxNum}</td>
					<td>${price}</td>
					<td>${amount}</td>
				</tr>
				</s:iterator>		
		</s:iterator>
		</tbody>
	</table>
	</div>

<br/><br/><br/><br/>
 
</form>
</body>
</html>

