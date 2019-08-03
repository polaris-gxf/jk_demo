package cn.itcast.web.struts2.cargo.contract;

import java.math.BigDecimal;
import java.util.List;

import cn.itcast.entity.ExtCproduct;
import cn.itcast.entity.Factory;
import cn.itcast.entity.dao.ExtCproductDAO;
import cn.itcast.entity.dao.FactoryDAO;
import cn.itcast.web.common.util.Arith;
import cn.itcast.web.common.util.UtilFuns;
import cn.itcast.web.struts2._BaseStruts2Action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Description: 货物下的附件
 * @Author: nutony
 * @CreateTime: 2013-11-30
 */
public class ExtCproductAction extends _BaseStruts2Action implements ModelDriven<ExtCproduct>{
	private ExtCproduct model = new ExtCproduct();
	public ExtCproduct getModel() {
		return model;
	}
	
	//转向新增页面
	public String tocreate(){
		//获得它所属的合同。
		//初始化数据	生产厂家的下拉列表
		FactoryDAO fDao = (FactoryDAO) this.getDao("daoFactory");
		List<Factory> factoryList = fDao.find("from Factory o where o.state=1 order by o.orderNo");		//0停用1启用
		ActionContext.getContext().put("factoryList", factoryList);
		
		//附件列表信息
		ExtCproductDAO oDao = (ExtCproductDAO) this.getDao("daoExtCproduct");
		//查询某个货物下的附件信息
		List<ExtCproduct> dataList = oDao.find("from ExtCproduct o where o.contractProduct.id='"+model.getContractProduct().getId()+"'");			
		ActionContext.getContext().put("dataList", dataList);
		
		return "pcreate";
	}
	
	//转向修改页面
	public String toupdate(){
		ExtCproductDAO oDao = (ExtCproductDAO) this.getDao("daoExtCproduct");
		ExtCproduct obj = (ExtCproduct) oDao.get(ExtCproduct.class, model.getId());
		ActionContext.getContext().getValueStack().push(obj);
		
		//初始化数据	生产厂家的下拉列表
		FactoryDAO fDao = (FactoryDAO) this.getDao("daoFactory");
		List<Factory> factoryList = fDao.find("from Factory o where o.state=1 order by o.orderNo");		//0停用1启用
		ActionContext.getContext().put("factoryList", factoryList);
		
		return "pupdate";
	}
	
	//保存
	public String save(){
		ExtCproductDAO oDao = (ExtCproductDAO) this.getDao("daoExtCproduct");
		if(UtilFuns.isNotEmpty(model.getCnumber()) && UtilFuns.isNotEmpty(model.getPrice())){		//简写，封装到工具类
			//计算金额  = 数量*单价
			Arith arith = new Arith();
			model.setAmount(new BigDecimal(arith.mul(model.getCnumber(), model.getPrice().doubleValue())));
		}

		
		oDao.saveOrUpdate(model);
		
		return tocreate();		//返回新增页面，方便用户继续添加
	}
	
	//删除
	public String delete(){
		String[] ids = model.getId().split(", ");
		ExtCproductDAO oDao = (ExtCproductDAO) this.getDao("daoExtCproduct");
		oDao.deleteAllById(ids, ExtCproduct.class);
		
		return tocreate();
	}
}
