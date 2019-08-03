package cn.itcast.web.struts2.cargo.export;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import cn.itcast.entity.Contract;
import cn.itcast.entity.ContractProduct;
import cn.itcast.entity.Export;
import cn.itcast.entity.ExportProduct;
import cn.itcast.entity.ExtCproduct;
import cn.itcast.entity.ExtEproduct;
import cn.itcast.entity.dao.ContractDAO;
import cn.itcast.entity.dao.ExportDAO;
import cn.itcast.entity.dao.ExportProductDAO;
import cn.itcast.web.common.util.UtilFuns;
import cn.itcast.web.struts2._BaseStruts2Action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Description: 
 * @Author: nutony
 * @CreateTime: 2013-12-4
 */
public class ExportAction extends _BaseStruts2Action implements ModelDriven<Export>{
	private Export model = new Export();
	public Export getModel() {
		return model;
	}
	
	//列表
	public String list(){
		ExportDAO oDao = (ExportDAO) this.getDao("daoExport");
		List<Export> dataList = oDao.find("from Export o");
		ActionContext.getContext().put("dataList", dataList);
		
		return "plist";
	}
	
	//从合同中新增报运
	public String contractsave(){
		/*
		 * 操作步骤：
		 * 1、获取多个合同信息，记录到报运的字符串中 CONTRACT_IDS、CUSTOMER_CONTRACT
		 * 2、将合同的货物信息写入到报运的货物信息中
		 * 3、将合同的附件信息写入到报运的附件信息中
		 */
		
		ContractDAO cDao = (ContractDAO) this.getDao("daoContract");
		//strtus底层如何封装数据，原理，它从页面找表单元素id，在po对象中找setId方法，有责设置，无则抛弃。结论，strus2封装参数时，不关心你是哪个对象。
		String[] tmpIds = model.getId().split(", ");			//contract id  
		
		String _contractId = model.getId().replaceAll(" ", "");
		String _contractNo = "";
		
		ExportDAO oDao = (ExportDAO) this.getDao("daoExport");
		Export export = new Export();		
		Set epSet = new HashSet();
		
		for(String id : tmpIds){
			//拼接字符串
			Contract contract = (Contract) cDao.get(Contract.class, id);
			_contractNo += contract.getContractNo() + " ";
			
			//处理货物
			
			for(ContractProduct cp : contract.getContractProducts()){
				ExportProduct ep = new ExportProduct();
				
				//数据的搬家   get/set
				ep.setProductNo(cp.getProductNo());
				ep.setProductDesc(cp.getProductDesc());
				ep.setFactory(cp.getFactory());
				ep.setPackingUnit(cp.getPackingUnit());
				ep.setCnumber(cp.getCnumber());
				ep.setBoxNum(cp.getBoxNum());
				ep.setPrice(cp.getPrice());
				ep.setAmount(cp.getAmount());
				
				
				Set extepSet = new HashSet();
				for(ExtCproduct extcp : cp.getExtCproducts()){
					ExtEproduct extep = new ExtEproduct();
					
					extep.setProductNo(extcp.getProductNo());
					extep.setProductDesc(extcp.getProductDesc());
					extep.setFactory(extcp.getFactory());
					extep.setPackingUnit(extcp.getPackingUnit());
					extep.setCnumber(extcp.getCnumber());
					extep.setBoxNum(extcp.getBoxNum());
					extep.setPrice(extcp.getPrice());
					extep.setAmount(extcp.getAmount());
					
					extep.setExportProduct(ep);		//绑定关系
					extepSet.add(extep);
				}
				
				ep.setExport(export);
				ep.setExtEproduct(extepSet);		//绑定关系
				epSet.add(ep);
			}
		}


		export.setContractIds(_contractId);			//contract ids
		export.setCustomerContract(_contractNo);	//合同号
		
		export.setExportProduct(epSet);				//绑定关系
		
		//初始化
		export.setState(0);							//0-草稿 1-已上报 2-装箱 3-委托 4-发票 5-财务
		
		oDao.saveOrUpdate(export);					//暗箱操作，直接保存了报运的数据，报运的其他主信息要在修改时进行补录。
		
		return list();
	}

	//转向修改页面
	public String toupdate(){
		ExportDAO oDao = (ExportDAO) this.getDao("daoExport");
		Export obj = (Export) oDao.get(Export.class, model.getId());
		ActionContext.getContext().getValueStack().push(obj);
		
		//准备数据：拼接JS串		addTRRecord(objId, id, productNo, cnumber, grossWeight, netWeight);
		StringBuffer sBuf = new StringBuffer();
		//for(int j=0;j<20;j++){				//test
		for(ExportProduct ep : obj.getExportProduct()){
			sBuf.append("addTRRecord('resultTable', '").append(ep.getId()).append("', '")
				.append(ep.getProductNo()).append("', '")
				.append(ep.getCnumber()).append("', '")
				.append(UtilFuns.convertNull(ep.getGrossWeight())).append("', '")
				.append(UtilFuns.convertNull(ep.getNetWeight())).append("');");
		}
		//}
		ActionContext.getContext().put("mrecordData", sBuf.toString());
		
		
		return "pupdate";
	}
	
	//修改保存
	public String save(){
		/*
		 * 处理批量货物信息：
		 * 1、获得页面提交的数据
		 * 2、生产报运货物对象，将页面的值写入
		 */
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String[] orderNo = request.getParameterValues("orderNo");
		String[] mr_id = request.getParameterValues("mr_id");
		
		String[] cnumber = request.getParameterValues("ep_cnumber");
		String[] grossWeight = request.getParameterValues("ep_grossWeight");
		String[] netWeight = request.getParameterValues("ep_netWeight");
		
		ExportProductDAO epDao = (ExportProductDAO) this.getDao("daoExportProduct");
		Set epSet = new HashSet();
		
		
		for(int i=0;i<orderNo.length;i++){
			ExportProduct ep = (ExportProduct) epDao.get(ExportProduct.class, mr_id[i]);
			if(UtilFuns.isNotEmpty(cnumber[i])){
				ep.setCnumber(Integer.parseInt(cnumber[i]));
			}
			if(UtilFuns.isNotEmpty(grossWeight[i])){
				ep.setGrossWeight(new BigDecimal(grossWeight[i]));
			}
			if(UtilFuns.isNotEmpty(netWeight[i])){
				ep.setNetWeight(new BigDecimal(netWeight[i]));
			}
			
			epSet.add(ep);
		}
		
		model.setExportProduct(epSet);			//绑定关系
		
		ExportDAO oDao = (ExportDAO) this.getDao("daoExport");
		oDao.saveOrUpdate(model);
		
		return list();
	}
	
	
	//批量删除
	public String delete(){
		String[] ids = model.getId().split(", ");			//逗号空格分隔
		ExportDAO oDao = (ExportDAO) this.getDao("daoExport");
		oDao.deleteAllById(ids, Export.class);

		return list();
	}
	
	
	//转向查看页面
	public String toview(){
		//准备数据
		ExportDAO oDao = (ExportDAO) this.getDao("daoExport");
		Export obj = (Export) oDao.get(Export.class, model.getId());		//拿到单个记录
		ActionContext.getContext().getValueStack().push(obj);
		
		return "pview";
	}	
	
	//转向查看页面	装箱用的 by tony 2013-12-06
	public String toviewinfo(){
		//准备数据
		ExportDAO oDao = (ExportDAO) this.getDao("daoExport");
		Export obj = (Export) oDao.get(Export.class, model.getId());		//拿到单个记录
		ActionContext.getContext().getValueStack().push(obj);
		
		return "pviewinfo";
	}	
	
}
