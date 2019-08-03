package cn.itcast.web.struts2.cargo.contract;

import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeanUtils;

import cn.itcast.entity.Contract;
import cn.itcast.entity.ContractProduct;
import cn.itcast.entity.ExtCproduct;
import cn.itcast.entity.dao.ContractDAO;
import cn.itcast.web.common.util.UtilFuns;
import cn.itcast.web.print.ContractPrint;
import cn.itcast.web.struts2._BaseStruts2Action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Description: 
 * @Author: nutony
 * @CreateTime: 2013-11-25
 */
public class ContractAction extends _BaseStruts2Action implements ModelDriven<Contract> {
	private Contract model = new Contract();
	public Contract getModel() {
		return model;
	}

	//列表
	public String list(){
		ContractDAO oDao = (ContractDAO) this.getDao("daoContract");
		List<Contract> dataList = oDao.find("from Contract o");
		ActionContext.getContext().put("dataList", dataList);
		
		return "plist";
	}
	
	//转向新增页面
	public String tocreate(){
		return "pcreate";
	}
	
	//转向修改页面
	public String toupdate(){
		//准备数据
		ContractDAO oDao = (ContractDAO) this.getDao("daoContract");
		Contract obj = (Contract) oDao.get(Contract.class, model.getId());		//拿到单个记录
		ActionContext.getContext().getValueStack().push(obj);
		
		return "pupdate";
	}
	
	//转向查看页面
	public String toview(){
		//准备数据
		ContractDAO oDao = (ContractDAO) this.getDao("daoContract");
		Contract obj = (Contract) oDao.get(Contract.class, model.getId());		//拿到单个记录
		ActionContext.getContext().getValueStack().push(obj);
		
		return "pview";
	}
	
	//保存(新增修改)
	public String save() throws ParseException{
		String _tmpDate = UtilFuns.formatDateTimeCN(UtilFuns.dateTimeFormat(model.getDeliveryPeriod()));	//yyyy年MM月dd日
		
		ContractDAO oDao = (ContractDAO) this.getDao("daoContract");
		//初始化
		if(model.getId()==null){
			model.setState(0);			//状态：0草稿 1已上报待报运
			model.setCrequest(model.getCrequest().replaceFirst("deliveryPeriod",_tmpDate));		//替换要求中的交货日期
		}
		
		oDao.saveOrUpdate(model);				//保存实体
		return list();
	}
	
	//删除单个
	public String deleteone(){
		ContractDAO oDao = (ContractDAO) this.getDao("daoContract");
		oDao.delete(model.getId(), Contract.class);				//按id删除单个对象
		
		return list();
	}
	
	//批量删除
	public String delete(){
		String[] ids = model.getId().split(", ");			//逗号空格分隔
		ContractDAO oDao = (ContractDAO) this.getDao("daoContract");
		oDao.deleteAllById(ids, Contract.class);

		return list();
	}
	
	//上报
	public String submit(){
		this.changeState(1);
		
		return list();
	}
	
	//取消
	public String cancel(){
		this.changeState(0);
		
		return list();
	}
	
	//改变状态     0草稿1已上报
	private void changeState(Integer curValue){
		String[] ids = model.getId().split(", ");			//逗号空格分隔
		ContractDAO oDao = (ContractDAO) this.getDao("daoContract");
		Set oSet = new HashSet();
		for(String id : ids){
			Contract f = (Contract) oDao.get(Contract.class, id);
			f.setState(curValue);		
			oSet.add(f);
		}
		oDao.saveOrUpdateAll(oSet);			//批量处理
	}

	//信息复制
	public String copy1(){
		/*
		 * 操作步骤：
		 * 1、获取旧的合同
		 * 2、创建新合同，get旧合同，set新合同
		 * 3、合同信息的初始化
		 * 4、货物
		 * 5、附件
		 * 6、保存
		 * 
		 */
		
		ContractDAO oDao = (ContractDAO) this.getDao("daoContract");
		Contract oldContract = (Contract) oDao.get(Contract.class, model.getId());		//拿到旧合同
		Contract newContract = new Contract();			//创建新合同
		
		newContract.setCustomName(oldContract.getCustomName());
		newContract.setContractNo("复制" + oldContract.getContractNo());
		newContract.setTotalAmount(oldContract.getTotalAmount());
		
		//拿到旧合同下的所有货物
		Set cpSet = new HashSet();			//只要想到子记录多条时，都需要它。
		
		//处理货物信息
		for(ContractProduct cp : oldContract.getContractProducts()){
			ContractProduct newcp = new ContractProduct();
			newcp.setFactory(cp.getFactory());
			newcp.setProductNo(cp.getProductNo());
			newcp.setProductDesc(cp.getProductDesc());
			newcp.setCnumber(cp.getCnumber());
			newcp.setPrice(cp.getPrice());
			newcp.setBoxNum(cp.getBoxNum());
			
			//初始化
			newcp.setFinished(false);
			newcp.setOutNumber(0);					//分次报运
			
			//处理附件信息
			Set epSet = new HashSet();
			for(ExtCproduct ep : cp.getExtCproducts()){
				ExtCproduct newep = new ExtCproduct();
				newep.setFactory(ep.getFactory());
				newep.setProductNo(ep.getProductNo());
				newep.setProductDesc(ep.getProductDesc());
				newep.setCnumber(ep.getCnumber());
				newep.setPrice(ep.getPrice());
				newep.setBoxNum(ep.getBoxNum());
				
				//初始化
				newep.setFinished(false);
				newep.setOutNumber(0);					//分次报运
				
				newep.setContractProduct(newcp);		//绑定关系
				
				epSet.add(newep);
			}
			
			newcp.setExtCproducts(epSet);				//绑定关系
			newcp.setContract(newContract);				//绑定关系
			
			cpSet.add(newcp);
		}
		
		//补全其他字段
		
		//初始化
		newContract.setState(0);			//0草稿1已上报待报运
		
		newContract.setContractProducts(cpSet);			//绑定关系
		
		oDao.saveOrUpdate(newContract);		//保存
		
		return list();
	}
	
	//信息复制优化
	public String copy(){
		ContractDAO oDao = (ContractDAO) this.getDao("daoContract");
		Contract contract = (Contract) oDao.get(Contract.class, model.getId());		//拿到旧合同
		
		contract.setId(null);	//强制设置为null
		contract.setContractNo("复制" + contract.getContractNo());
		
		//处理货物信息
		for(ContractProduct cp : contract.getContractProducts()){
			cp.setId(null);
			
			//初始化
			cp.setFinished(false);
			cp.setOutNumber(0);					//分次报运
			
			//处理附件信息
			for(ExtCproduct ep : cp.getExtCproducts()){
				ep.setId(null);
				
				//初始化
				ep.setFinished(false);
				ep.setOutNumber(0);					//分次报运
			}
		}
		
		
		//初始化
		contract.setState(0);			//0草稿1已上报待报运
		
		oDao.saveOrUpdate(contract);		//保存
		
		return list();
	}	
	
	
	//信息复制优化
	public String copy3(){
		ContractDAO oDao = (ContractDAO) this.getDao("daoContract");
		Contract contract = (Contract) oDao.get(Contract.class, model.getId());		//拿到旧合同
		Contract newContract = new Contract();
		
		BeanUtils.copyProperties(contract, newContract);			//复制对象
		
		newContract.setId(null);	//强制设置为null
		newContract.setContractNo("复制" + contract.getContractNo());
		
		//处理货物信息
		for(ContractProduct cp : newContract.getContractProducts()){
			cp.setId(null);
			
			//初始化
			cp.setFinished(false);
			cp.setOutNumber(0);					//分次报运
			
			//处理附件信息
			for(ExtCproduct ep : cp.getExtCproducts()){
				ep.setId(null);
				
				//初始化
				ep.setFinished(false);
				ep.setOutNumber(0);					//分次报运
			}
		}		
		
		oDao.saveOrUpdate(newContract);		//保存
		
		return list();
	}
	
	//打印
	public void print() throws Exception{
		String contractId = model.getId();			//选中的合同ID
		ContractDAO cDao = (ContractDAO) this.getDao("daoContract");
		HttpServletResponse response = ServletActionContext.getResponse();
		
		ContractPrint cp = new ContractPrint();
		cp.print(contractId, cDao, response);
	}
}
