<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title></title>
	<link rel="stylesheet" rev="stylesheet" type="text/css" href="../../skin/default/css/default.css" media="all" />
	<script type="text/javascript" src="../../js/datepicker/WdatePicker.js"></script>
	<script type="text/javascript" src="../../js/common.js"></script>
	
</head>
<body>
<form name="icform" method="post">

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
    <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="formSubmit('/contract/contractAction_save','_self');this.blur();">保存</a></li>
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
新增购销合同
    </div> 
    </div>
    </div>
 
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle_mustbe">客户名称：</td>
	            <td class="tableContent"><input type="text" name="customName" value="杰信商贸有限公司"/></td>	        
	            <td class="columnTitle">打印版式：</td>
	            <td class="tableContentAuto">
	            	<input type="radio" name="printStyle" value="1"  class="input">一个货物
					<input type="radio" name="printStyle" value="2" checked class="input">两个货物
	        	</td>
	        </tr>	        
	        <tr>
	            <td class="columnTitle">收&nbsp;购&nbsp;方：</td>
	            <td class="tableContent"><input type="text" name="offeror"/></td>
	            <td class="columnTitle">合&nbsp;同&nbsp;号：</td>
	            <td class="tableContent"><input type="text" name="contractNo"/></td>
	        </tr>	        
	        <tr>
	            <td class="columnTitle">签单日期：</td>
	            <td class="tableContent">
						<input type="text" style="width:90px;" name="signingDate" value=""
						 					onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/> 	            
	            </td>
	            <td class="columnTitle">制&nbsp;单&nbsp;人：</td>
	            <td class="tableContent"><input type="text" name="inputBy"/></td>
	        </tr>
	        <tr>
	            <td class="columnTitle">审&nbsp;单&nbsp;人：</td>
	            <td class="tableContent"><input type="text" name="checkBy"/></td>
	            <td class="columnTitle">验&nbsp;货&nbsp;员：</td>
	            <td class="tableContent"><input type="text" name="inspector"/></td>
	        </tr>
	        <tr>
	            <td class="columnTitle">船&nbsp;&nbsp;&nbsp;&nbsp;期：</td>
	            <td class="tableContent">
						<input type="text" style="width:90px;" name="shipTime" value=""
						 					onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/> 	            
	            </td>	            
	            <td class="columnTitle">交货期限：</td>
	            <td class="tableContent">
						<input type="text" style="width:90px;" name="deliveryPeriod" value=""
						 					onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/> 	            
	            </td>		            
	        </tr>
	        <tr>
	            <td class="columnTitle">重要程度：</td>
	            <td class="tableContentAuto" colspan="3">
	            	<input type="radio" name="importNum" value="1" class="input">★
					<input type="radio" name="importNum" value="2" class="input">★★
					<input type="radio" name="importNum" value="3" checked class="input">★★★
	        	</td>	            
	        </tr>
	        <tr>
	            <td class="columnTitle">要&nbsp;&nbsp;&nbsp;&nbsp;求：</td>
	            <td colspan="3">
	            <textarea name="crequest" style="height:100px;">
  ★   产品与封样无明显差异，唛头、标签及包装质量务必符合公司要求。 
 ★★  产品生产前期、中期、后期抽验率不得少于10%，质量和封样一致，并将验货照片传回公司。 
★★★ 重点客人的质量标准检验，产品抽验率不得少于50%，务必做到入箱前检查。 
 交期：deliveryPeriod/工厂。 
 没有经过我司同意无故延期交货造成严重后果的，按照客人的相关要求处理。 
 开票：出货后请将增值税发票、验货报告、合同复印件及出库单一并寄至我司，以便我司安排付款。	            
	            </textarea>
	            </td>
	        </tr>
	        <tr>
	            <td class="columnTitle">说&nbsp;&nbsp;&nbsp;&nbsp;明：</td>
	            <td colspan="3"><textarea name="remark" style="height:100px;"></textarea></td>
	        </tr>

		</table>
	</div>
</div>

 
</form>
</body>
</html>

