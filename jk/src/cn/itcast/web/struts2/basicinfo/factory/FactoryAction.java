package cn.itcast.web.struts2.basicinfo.factory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.struts2.ServletActionContext;

import cn.itcast.entity.Factory;
import cn.itcast.entity.dao.FactoryDAO;
import cn.itcast.web.common.util.DownloadBaseAction;
import cn.itcast.web.common.util.file.FileUtil;
import cn.itcast.web.struts2._BaseStruts2Action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Description: 
 * @Author: nutony
 * @CreateTime: 2013-11-25
 */
public class FactoryAction extends _BaseStruts2Action implements ModelDriven<Factory> {
	private Factory model = new Factory();
	public Factory getModel() {
		return model;
	}

	//列表
	public String list(){
		//如何调用DAO
		FactoryDAO oDao = (FactoryDAO) this.getDao("daoFactory");
		List<Factory> dataList = oDao.find("from Factory o order by o.orderNo");	//拿到返回的集合，声明一个别名为o
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
		FactoryDAO oDao = (FactoryDAO) this.getDao("daoFactory");
		Factory obj = (Factory) oDao.get(Factory.class, model.getId());		//拿到单个记录
		ActionContext.getContext().getValueStack().push(obj);
		
		return "pupdate";
	}
	
	//转向查看页面
	public String toview(){
		//准备数据
		FactoryDAO oDao = (FactoryDAO) this.getDao("daoFactory");
		Factory obj = (Factory) oDao.get(Factory.class, model.getId());		//拿到单个记录
		ActionContext.getContext().getValueStack().push(obj);
		
		return "pview";
	}
	
	//保存(新增修改)
	public String save(){
		FactoryDAO oDao = (FactoryDAO) this.getDao("daoFactory");
		Factory obj = null;
		//初始化
		if(model.getId()==null){
			obj = new Factory();
			obj.setState("1");			//状态：1启用0停用
		}else{
			obj = (Factory) oDao.get(Factory.class, model.getId());
		}
		obj.setCtype(model.getCtype());
		obj.setFullName(model.getFullName());
		obj.setFactoryName(model.getFactoryName());
		obj.setContractor(model.getContractor());
		obj.setPhone(model.getPhone());
		obj.setMobile(model.getMobile());
		obj.setFax(model.getFax());
		obj.setCnote(model.getCnote());
		obj.setInspector(model.getInspector());
		
		obj.setOrderNo(model.getOrderNo());
		
		oDao.saveOrUpdate(obj);				//保存实体
		
		return list();
	}
	
	//删除单个
	public String deleteone(){
		FactoryDAO oDao = (FactoryDAO) this.getDao("daoFactory");
		oDao.delete(model.getId(), Factory.class);				//按id删除单个对象
		
		return list();
	}
	
	//批量删除
	public String delete(){
		String[] ids = model.getId().split(", ");			//逗号空格分隔
		FactoryDAO oDao = (FactoryDAO) this.getDao("daoFactory");
		oDao.deleteAllById(ids, Factory.class);

		return "actlist";
	}
	
	//启用
	public String start(){
		this.changeState("1");
		
		return list();
	}
	
	//停用
	public String stop(){
		this.changeState("0");
		
		return list();
	}
	
	//改变状态     1启用0停用
	private void changeState(String curValue){
		String[] ids = model.getId().split(", ");			//逗号空格分隔
		FactoryDAO oDao = (FactoryDAO) this.getDao("daoFactory");
		Set oSet = new HashSet();
		for(String id : ids){
			Factory f = (Factory) oDao.get(Factory.class, id);
			f.setState(curValue);		
			oSet.add(f);
		}
		oDao.saveOrUpdateAll(oSet);			//批量处理
	}
	
	//批量导出
	public void exportex() throws IOException{
		/*
		 * POI操作步骤：
		 * 1、创建或者打开一个工作簿workbook
		 * 2、创建或者打开一个工作表sheet
		 * 3、创建行对象 row
		 * 4、创建单元格对象 cell，指定其所在列，设置内容
		 * 5、设置样式（不是必须）
		 * 6、保存、关闭
		 * 7、下载
		 */
		
		String rootPath = ServletActionContext.getRequest().getRealPath("/");
		String exportFile = rootPath + "/web/tmp/" + "factory.xls";
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		HSSFRow nRow = sheet.createRow(4);			//下标从0开始
		HSSFCell nCell = nRow.createCell((short)3);
		nCell.setCellValue("传智播客");
		
		FileOutputStream fOut = new FileOutputStream(exportFile);		//创建xls文件，无内容 0字节
		wb.write(fOut);							//写内容，xls文件已经可以打开
		fOut.flush();							//刷新缓冲区
		fOut.close();							//关闭
		
		DownloadBaseAction down = new DownloadBaseAction();
		HttpServletResponse response = ServletActionContext.getResponse();
		down.prototypeDownload(new File(exportFile), "factory.xls", response, true);
		
	}
	
	//批量导出
	public void export() throws IOException, InterruptedException{
		/*
		 * POI操作步骤：
		 * 1、创建或者打开一个工作簿workbook
		 * 2、创建或者打开一个工作表sheet
		 * 3、创建行对象 row
		 * 4、创建单元格对象 cell，指定其所在列，设置内容
		 * 5、设置样式（不是必须）
		 * 6、保存、关闭
		 * 7、下载
		 */
		
		FileUtil fu = new FileUtil();
		
		String rootPath = ServletActionContext.getRequest().getRealPath("/");
		String elsFile = "factory.xls";										//写到目录中的文件
		
		String exportFile = rootPath + "/web/tmp/" + fu.newFile(rootPath + "/web/tmp/", elsFile);				//产生一个新的文件名
		
		
		int rowNo = 0;						//行号
		short colNo = 0;					//列号
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		
		//sheet.setColumnWidth((short)0, (short)(30*256));						//设置列宽	底层API精度不够
		
		
		HSSFCellStyle curStyle = wb.createCellStyle();					//创建样式对象
		HSSFFont curFont = wb.createFont();
		
		
		HSSFRow nRow = sheet.createRow(rowNo++);						//下标从0开始
		nRow.setHeightInPoints(40);										//设置行高
		
		HSSFCell nCell = null;
		

		Region region = null;
		region = new Region(0, (short)(0), 0, (short)6);	//横向合并单元格 
		sheet.addMergedRegion(region);						//绑定
		nCell = nRow.createCell((short)0);
		nCell.setCellValue("生产厂家通讯录");
		nCell.setCellStyle(this.bigTilte(wb, curStyle, curFont));
		
		//重新初始化样式
		curStyle = wb.createCellStyle();								//创建样式对象
		curFont = wb.createFont();
		
		nRow = sheet.createRow(rowNo++);
		nRow.setHeightInPoints(28);										//设置行高

		//输出标题栏
		String[] titles = new String[]{"全称","缩写","联系人","电话","手机","传真","备注"};
		for(int i=0;i<titles.length;i++){
			nCell = nRow.createCell((short)i);
			nCell.setCellValue(titles[i]);
			nCell.setCellStyle(this.tilte(wb, curStyle, curFont));			//绑定样式
		}
		
		
		//重新初始化样式
		curStyle = wb.createCellStyle();					//创建样式对象
		curFont = wb.createFont();
		
		//写内容
		FactoryDAO oDao = (FactoryDAO) this.getDao("daoFactory");
		List<Factory> oList = oDao.find("from Factory o where o.state='1'");			//只获取启动的数据
		
		for(int n=0;n<2000;n++){			//测试用
		for(Factory f : oList){
			//怎么截断
//			if(rowNo>5){
//				break;		//跳出循环
//			}
			
			colNo = 0;		//初始化，清零	
			
			nRow = sheet.createRow(rowNo++);			//行对象
			nRow.setHeightInPoints(20);
			
			nCell = nRow.createCell((short)colNo++);
			nCell.setCellValue(f.getFullName());
			nCell.setCellStyle(this.text(wb, curStyle, curFont));
			
			nCell = nRow.createCell((short)colNo++);
			nCell.setCellValue(f.getFactoryName());
			nCell.setCellStyle(this.text(wb, curStyle, curFont));
			
			nCell = nRow.createCell((short)colNo++);
			nCell.setCellValue(f.getContractor());
			nCell.setCellStyle(this.text(wb, curStyle, curFont));
			
			nCell = nRow.createCell((short)colNo++);
			nCell.setCellValue(f.getPhone());
			nCell.setCellStyle(this.text(wb, curStyle, curFont));
			
			nCell = nRow.createCell((short)colNo++);
			nCell.setCellValue(f.getMobile());
			nCell.setCellStyle(this.text(wb, curStyle, curFont));
			
			nCell = nRow.createCell((short)colNo++);
			nCell.setCellValue(f.getFax());
			nCell.setCellStyle(this.text(wb, curStyle, curFont));
			
			nCell = nRow.createCell((short)colNo++);
			nCell.setCellValue(f.getCnote());
			nCell.setCellStyle(this.text(wb, curStyle, curFont));
			
			Thread.sleep(5);       //休眠一会
		}
		}
		
		for(int i=0;i<titles.length;i++){
			sheet.autoSizeColumn((short)i);									//自动适应列宽		BUG
		}
		
		
		FileOutputStream fOut = new FileOutputStream(exportFile);		//创建xls文件，无内容 0字节
		wb.write(fOut);							//写内容，xls文件已经可以打开
		fOut.flush();							//刷新缓冲区
		fOut.close();							//关闭
		
		DownloadBaseAction down = new DownloadBaseAction();
		HttpServletResponse response = ServletActionContext.getResponse();
		down.prototypeDownload(new File(exportFile), "factory.xls", response, false);
		
	}	
	
	//大标题样式
	private HSSFCellStyle bigTilte(HSSFWorkbook wb, HSSFCellStyle curStyle, HSSFFont curFont ){
		curStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);				//横向居中
		curStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);	//纵向居中
		
		curStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);				//单元格上细线
		curStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);			//单元格下细线
		curStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);				//单元格左细线
		curStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);				//单元格右细线
		
		curFont.setFontHeightInPoints((short)20);						//设置字体大小
		curFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);				//字体加粗
		
		curStyle.setFont(curFont);		//绑定字体
		
		return curStyle;
	}
	
	//标题样式   excel中不能创建太多的样式对象和字体对象
	private HSSFCellStyle tilte(HSSFWorkbook wb, HSSFCellStyle curStyle, HSSFFont curFont ){
		curStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);				//横向居中
		curStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);	//纵向居中
		
		curStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);				//单元格上细线
		curStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);			//单元格下细线
		curStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);				//单元格左细线
		curStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);				//单元格右细线
		
		curFont.setFontName("微软雅黑");									//设置字体
		curFont.setFontHeightInPoints((short)11);						//设置字体大小
		curFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);				//字体加粗
		
		curStyle.setFont(curFont);		//绑定字体
		
		return curStyle;
	}
	
	//标题样式   excel中不能创建太多的样式对象和字体对象
	private HSSFCellStyle text(HSSFWorkbook wb, HSSFCellStyle curStyle, HSSFFont curFont ){
		curStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);				//横向居左
		curStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);	//纵向居中
		
		curStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);				//单元格上细线
		curStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);			//单元格下细线
		curStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);				//单元格左细线
		curStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);				//单元格右细线
		
		curStyle.setFont(curFont);		//绑定字体
		
		return curStyle;
	}
}
