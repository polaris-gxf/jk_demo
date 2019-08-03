package cn.itcast.web.struts2.cargo.contract;

import java.math.BigDecimal;
import java.util.List;

import cn.itcast.entity.ContractProduct;
import cn.itcast.entity.Factory;
import cn.itcast.entity.dao.ContractProductDAO;
import cn.itcast.entity.dao.FactoryDAO;
import cn.itcast.web.common.util.Arith;
import cn.itcast.web.common.util.UtilFuns;
import cn.itcast.web.struts2._BaseStruts2Action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Description: 合同下的货物
 * @Author: nutony
 * @CreateTime: 2013-11-30
 */
public class ContractProductAction extends _BaseStruts2Action implements ModelDriven<ContractProduct>{
	private ContractProduct model = new ContractProduct();
	public ContractProduct getModel() {
		return model;
	}
	
	//转向新增页面
	public String tocreate(){
		//获得它所属的合同。
		//初始化数据	生产厂家的下拉列表
		FactoryDAO fDao = (FactoryDAO) this.getDao("daoFactory");
		List<Factory> factoryList = fDao.find("from Factory o where o.state=1 order by o.orderNo");		//0停用1启用
		ActionContext.getContext().put("factoryList", factoryList);
		
		//货物列表信息		?应该用ContractProductDAO
		ContractProductDAO oDao = (ContractProductDAO) this.getDao("daoContractProduct");
		List<ContractProduct> dataList = oDao.find("from ContractProduct o where o.contract.id='"+model.getContract().getId()+"'");			//查询某个合同下的货物信息
		ActionContext.getContext().put("dataList", dataList);
		
		return "pcreate";
	}
	
	//转向修改页面
	public String toupdate(){
		ContractProductDAO oDao = (ContractProductDAO) this.getDao("daoContractProduct");
		ContractProduct obj = (ContractProduct) oDao.get(ContractProduct.class, model.getId());
		ActionContext.getContext().getValueStack().push(obj);
		
		//初始化数据	生产厂家的下拉列表
		FactoryDAO fDao = (FactoryDAO) this.getDao("daoFactory");
		List<Factory> factoryList = fDao.find("from Factory o where o.state=1 order by o.orderNo");		//0停用1启用
		ActionContext.getContext().put("factoryList", factoryList);
		
		return "pupdate";
	}
	
	//保存
	public String save(){
		ContractProductDAO oDao = (ContractProductDAO) this.getDao("daoContractProduct");
		if(UtilFuns.isNotEmpty(model.getCnumber()) && UtilFuns.isNotEmpty(model.getPrice())){		//简写，封装到工具类
			//计算金额  = 数量*单价
			Arith arith = new Arith();
			model.setAmount(new BigDecimal(arith.mul(model.getCnumber(), model.getPrice().doubleValue())));
		}
		
		//计算合同的总金额
		
		oDao.saveOrUpdate(model);
		
		return tocreate();		//返回新增页面，方便用户继续添加
	}
	
	//删除
	public String delete(){
		String[] ids = model.getId().split(", ");
		ContractProductDAO oDao = (ContractProductDAO) this.getDao("daoContractProduct");
		oDao.deleteAllById(ids, ContractProduct.class);
		
		//重新计算合同的总金额
		
		return tocreate();
	}
}
