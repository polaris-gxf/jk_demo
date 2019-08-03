package cn.itcast.web.struts2.cargo.packinglist;

import java.util.List;

import cn.itcast.entity.Contract;
import cn.itcast.entity.Export;
import cn.itcast.entity.PackingList;
import cn.itcast.entity.dao.ContractDAO;
import cn.itcast.entity.dao.ExportDAO;
import cn.itcast.entity.dao.PackingListDAO;
import cn.itcast.web.common.util.UtilFuns;
import cn.itcast.web.struts2._BaseStruts2Action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Description: 
 * @Author: nutony
 * @CreateTime: 2013-12-6
 */
public class PackingListAction extends _BaseStruts2Action implements ModelDriven<PackingList>{
	private PackingList model = new PackingList();
	public PackingList getModel() {
		return model;
	}

	//列表
	public String list(){
		PackingListDAO oDao = (PackingListDAO) this.getDao("daoPackingList");
		List<PackingList> dataList = oDao.find("from PackingList o");
		ActionContext.getContext().put("dataList", dataList);
		
		return "plist";
	}
	
	//转向新增页面
	public String tocreate(){
		/*
		 * 操作步骤：
		 * 1、获得选中报运
		 * 2、得到报运信息，并在新建页面显示（显示报运号）
		 */
		
		String[] ids = model.getId().split(", ");				//export id
		ExportDAO eDao = (ExportDAO) this.getDao("daoExport");
		
		
		//拼接HTML片段  <div><input type="checkbox" name="exportId" value="exportId"> exportNo </div>
		StringBuffer sBuf = new StringBuffer();
		for(String id : ids){
			Export export = (Export) eDao.get(Export.class, id);
			
			//小技巧：通过一个框来存放多个值，多个值用竖杠隔开
			sBuf.append("<input type=\"checkbox\" name=\"exportIds\" value=\"").append(id).append("|").append(export.getCustomerContract()).append("\" checked class=\"input\"/>")
				.append(export.getCustomerContract())
				.append("&nbsp;&nbsp;");
			
		}
		ActionContext.getContext().put("mrecordData", sBuf.toString());
		
		return "pcreate";
	}
	
	//转向修改页面
	public String toupdate(){
		PackingListDAO oDao = (PackingListDAO) this.getDao("daoPackingList");
		PackingList obj = (PackingList) oDao.get(PackingList.class, model.getId());
		ActionContext.getContext().getValueStack().push(obj);
		
		String[] _exportId = obj.getExportIds().split(",");
		String[] _exportNo = obj.getExportNos().split("\\|");
		
		//拼接HTML片段
		StringBuffer sBuf = new StringBuffer();
		for(int i=0;i<_exportId.length;i++){
			
			//小技巧：通过一个框来存放多个值，多个值用竖杠隔开
			sBuf.append("<input type=\"checkbox\" name=\"exportIds\" value=\"").append(_exportId[i]).append("|").append(_exportNo[i]).append("\" checked class=\"input\"/>")
				.append(_exportNo[i])
				.append("&nbsp;&nbsp;");
			
		}
		ActionContext.getContext().put("mrecordData", sBuf.toString());
		
		return "pupdate";
	}
	
	//转向查看方法
	public String toview(){
		PackingListDAO oDao = (PackingListDAO) this.getDao("daoPackingList");
		PackingList obj = (PackingList) oDao.get(PackingList.class, model.getId());
		ActionContext.getContext().getValueStack().push(obj);
		
		String[] _exportId = obj.getExportIds().split(",");
		String[] _exportNo = obj.getExportNos().split("\\|");
		
		//拼接HTML片段
		StringBuffer sBuf = new StringBuffer();
		for(int i=0;i<_exportId.length;i++){
			
			//小技巧：通过一个框来存放多个值，多个值用竖杠隔开
			sBuf.append("<a href=\"/export/exportAction_toviewinfo?id=").append(_exportId[i]).append("\">");
			sBuf.append(_exportNo[i]).append("</a>&nbsp;&nbsp;");
			
		}
		ActionContext.getContext().put("mrecordData", sBuf.toString());
		
		return "pview";
	}
	
	//保存
	public String save(){
		PackingListDAO oDao = (PackingListDAO) this.getDao("daoPackingList");
		//手工拆串
		String[] _tmpIds = model.getExportIds().split(", ");
		
		String _exportId = "";			//报运ID
		String _exportNo = "";			//报运号
		
		for(String _tmpId : _tmpIds){
			//分隔报运ID和报运号
			String[] _tmp = _tmpId.split("\\|");			//竖杠跟正则表达式冲突，要进行转义   id|no
			_exportId += _tmp[0] + ",";	
			_exportNo += _tmp[1] + "|";
		}
		_exportId = UtilFuns.delLastChar(_exportId);
		_exportNo = UtilFuns.delLastChar(_exportNo);
		
		model.setExportIds(_exportId);
		model.setExportNos(_exportNo);
		
		oDao.saveOrUpdate(model);
		
		return list();
	}
	
	
}

