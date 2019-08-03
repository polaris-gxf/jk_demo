<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title></title>
	<link rel="stylesheet" rev="stylesheet" type="text/css" href="../../skin/default/css/default.css" media="all" />
	<script type="text/javascript" src="../../js/datepicker/WdatePicker.js"></script>
	<script type="text/javascript" src="../../js/common.js"></script>
	
	<script type="text/javascript">
		//防止重复提交 by tony 20131201
		function frameSubmit(){
			var repeatFlag = document.getElementById("repeatFlag");
			if(repeatFlag.value=="1"){
				alert("正在打印请稍后... 5秒中后解冻, 您可以继续尝试!");
			}else{
				setTimeout("clearRepeatFlag();",5000);			//5秒中取消设置
				repeatFlag.value = "1";		//设置已经点击 打印 
				formSubmit('/contract/outProductAction_print','_self');
			}
		}
		
	    function clearRepeatFlag(){
	       	var repeatFlag = document.getElementById("repeatFlag");
	       	repeatFlag.value = "";			//清楚标识
	    }

	</script>
</head>
<body>
<form name="icform" method="post">
	<input type="text" id="repeatFlag" name="repeatFlag" value=""/>
	
<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
    <div id="navMenubar">
<ul>
<li id="print"><a href="#" onclick="frameSubmit();this.blur();">打印</a></li>
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
出货表月统计
    </div> 
    </div>
    </div>
 
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">船期：</td>
	            <td class="tableContent">
						<input type="text" style="width:90px;" name="inputDate" value="2011-12"
						 	   onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM'});"/> 	            
	            </td>
	        </tr>
		</table>
	</div>
</div>

 
</form>
</body>
</html>

