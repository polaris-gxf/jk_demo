package cn.itcast.web.struts2.cargo.contract;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.struts2.ServletActionContext;

import cn.itcast.entity.ContractProduct;
import cn.itcast.entity.ExtCproduct;
import cn.itcast.entity.dao.ContractProductDAO;
import cn.itcast.web.common.util.DownloadBaseAction;
import cn.itcast.web.common.util.UtilFuns;
import cn.itcast.web.common.util.file.PoiUtil;
import cn.itcast.web.struts2._BaseStruts2Action;

/**
 * @Description: 
 * @Author: nutony
 * @CreateTime: 2013-12-1
 */
public class OutProductAction extends _BaseStruts2Action {
	private String inputDate;		//属性驱动，格式 yyyy-MM
	public String getInputDate() {
		return inputDate;
	}
	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}

	//打印
	public void print1() throws IOException, ParseException{
		String outFile = "outproduct.xls";
		
		int rowNo = 0;				//行号
		short colNo = 1;			//列号
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		
		HSSFRow nRow = null;
		HSSFCell nCell = null;
		
		HSSFCellStyle curStyle = wb.createCellStyle();
		HSSFFont curFont = wb.createFont();
		
		//设置标题：		将第一行作为标题，即每页都打印此行 sheetN,startCol,stopCol,startRow,stopRow
		wb.setRepeatingRowsAndColumns(0,1,9,0,1);

		//页脚
		HSSFFooter footer = sheet.getFooter();
		footer.setRight("第"+HSSFFooter.page()+"页 共"+HSSFFooter.numPages()+"页     ");	//页数

		
		//合并单元格
		Region region = null;
		region = new Region(0, (short)(1), 0, (short)9);
		sheet.addMergedRegion(region);
		
		//大标题
		nRow = sheet.createRow(rowNo++);
		nCell = nRow.createCell((short)1);
		nCell.setCellValue(inputDate.replaceFirst("-0", "-").replaceFirst("-", "年") + "月份出货表");
		nCell.setCellStyle(this.titleStyle(wb, curStyle, curFont));
		
		//重新设置
		curStyle = wb.createCellStyle();
		curFont = wb.createFont();
		
		//输出标题栏
		nRow = sheet.createRow(rowNo++);
		String[] titles = new String[]{"客人","订单号","货号","数量","工厂","附件","工厂交期","船期","贸易条款"};
		for(int i=0;i<titles.length;i++){
			nCell = nRow.createCell((short)(i+1));
			nCell.setCellValue(titles[i]);
			nCell.setCellStyle(this.subtitleStyle(wb, curStyle, curFont));
		}
		
		//重新设置
		curStyle = wb.createCellStyle();
		curFont = wb.createFont();		

		//获取出货表数据
		ContractProductDAO cpDao = (ContractProductDAO) this.getDao("daoContractProduct");
		String hql = "from ContractProduct o where o.contract.shipTime like '"+ inputDate +"%'";		//只适合mysql
		List<ContractProduct> cpList = cpDao.find(hql);
		
		//处理出货表内容
		for(ContractProduct cp : cpList){
			colNo = 1;				//初始化列号
			
			nRow = sheet.createRow(rowNo++);
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(cp.getContract().getCustomName());
			nCell.setCellStyle(this.contentStyle(wb, curStyle, curFont));
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(cp.getContract().getContractNo());
			nCell.setCellStyle(this.contentStyle(wb, curStyle, curFont));
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(cp.getProductNo());
			nCell.setCellStyle(this.contentStyle(wb, curStyle, curFont));
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(cp.getCnumber() + cp.getPackingUnit());
			nCell.setCellStyle(this.contentStyle(wb, curStyle, curFont));
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(cp.getFactory().getFactoryName());
			nCell.setCellStyle(this.contentStyle(wb, curStyle, curFont));
			
			//附件
			nCell = nRow.createCell(colNo++);
			String _extString = "";
			for(ExtCproduct ep : cp.getExtCproducts()){
				_extString += ep.getTypeName() + "\n";
			}
			if(UtilFuns.isEmpty(_extString)){
				nCell.setCellValue("无");
			}else{
				nCell.setCellValue(_extString);
			}
			nCell.setCellStyle(this.extProductStyle(wb, curStyle, curFont));
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(UtilFuns.dateTimeFormat(cp.getContract().getDeliveryPeriod()));		//将日期类型转换为yyyy-MM格式的字符串
			nCell.setCellStyle(this.contentStyle(wb, curStyle, curFont));
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(UtilFuns.dateTimeFormat(cp.getContract().getShipTime()));
			nCell.setCellStyle(this.contentStyle(wb, curStyle, curFont));
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(cp.getContract().getTradeTerms());
			nCell.setCellStyle(this.contentStyle(wb, curStyle, curFont));
		}
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();			//生成流对象
		wb.write(byteArrayOutputStream);													//将excel写入流
		
		HttpServletResponse response = ServletActionContext.getResponse();

		//工具类，封装弹出下载框：		
		DownloadBaseAction down = new DownloadBaseAction();
		down.download(byteArrayOutputStream, response, outFile);

	}
	
	//基于模板打印打印
	public void print() throws IOException, ParseException{
		String outFile = "outproduct.xls";
		String tempFile = ServletActionContext.getRequest().getRealPath("/") + "/make/xlsprint/tOUTPRODUCT.xls";		//模板文件，必须手工提前准备好
		
		int rowNo = 0;			//行号
		short colNo = 1;		//列号
		
		PoiUtil poiUtil = new PoiUtil();
		
		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(tempFile));		//打开模板文件
		HSSFSheet sheet = wb.getSheetAt(0);										//打开第一个sheet
		
		HSSFRow nRow = null;
		HSSFCell nCell = null;
		
		HSSFCellStyle curStyle = wb.createCellStyle();
		HSSFFont curFont = wb.createFont();
		
		//大标题
		nRow = sheet.createRow(rowNo++);
		nRow.setHeightInPoints(36);
		
		nCell = nRow.createCell((short)1);
		nCell.setCellValue(inputDate.replaceFirst("-0", "-").replaceFirst("-", "年") + "月份出货表");
		nCell.setCellStyle(this.titleStyle(wb, curStyle, curFont));
		
		//重新设置
		curStyle = wb.createCellStyle();
		curFont = wb.createFont();
		
		rowNo++;		//跳过标题行
		

		//获取出货表数据
		ContractProductDAO cpDao = (ContractProductDAO) this.getDao("daoContractProduct");
		String hql = "from ContractProduct o where o.contract.shipTime like '"+ inputDate +"%'";		//只适合mysql
		List<ContractProduct> cpList = cpDao.find(hql);
		
		//处理出货表内容
		for(ContractProduct cp : cpList){
			colNo = 1;				//初始化列号
			
			nRow = sheet.createRow(rowNo++);
			nRow.setHeightInPoints(24);
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(cp.getContract().getCustomName());
			nCell.setCellStyle(this.contentStyle(wb, curStyle, curFont));
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(cp.getContract().getContractNo());
			nCell.setCellStyle(this.contentStyle(wb, curStyle, curFont));
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(cp.getProductNo());
			nCell.setCellStyle(this.contentStyle(wb, curStyle, curFont));
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(cp.getCnumber() + cp.getPackingUnit());
			nCell.setCellStyle(this.contentStyle(wb, curStyle, curFont));
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(cp.getFactory().getFactoryName());
			nCell.setCellStyle(this.contentStyle(wb, curStyle, curFont));
			
			//附件
			nCell = nRow.createCell(colNo++);
			String _extString = "";
			for(ExtCproduct ep : cp.getExtCproducts()){
				_extString += ep.getTypeName() + "\n";
			}
			if(UtilFuns.isEmpty(_extString)){
				nCell.setCellValue("无");
			}else{
				_extString = UtilFuns.delLastChar(_extString);		//工具类，实现删除最后一个字符
				nCell.setCellValue(_extString);
				
				float height = poiUtil.getCellAutoHeight(_extString, 12f);
				nRow.setHeightInPoints(height);							//(一行字+行之间的间隙)*行数
			}
			nCell.setCellStyle(this.extProductStyle(wb, curStyle, curFont));
			

			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(UtilFuns.dateTimeFormat(cp.getContract().getDeliveryPeriod()));		//将日期类型转换为yyyy-MM格式的字符串
			nCell.setCellStyle(this.contentStyle(wb, curStyle, curFont));
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(UtilFuns.dateTimeFormat(cp.getContract().getShipTime()));
			nCell.setCellStyle(this.contentStyle(wb, curStyle, curFont));
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(cp.getContract().getTradeTerms());
			nCell.setCellStyle(this.contentStyle(wb, curStyle, curFont));
		}
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();			//生成流对象
		wb.write(byteArrayOutputStream);													//将excel写入流
		
		HttpServletResponse response = ServletActionContext.getResponse();

		//工具类，封装弹出下载框：		
		DownloadBaseAction down = new DownloadBaseAction();
		down.download(byteArrayOutputStream, response, outFile);

	}
	
	//标题样式
	private HSSFCellStyle titleStyle(HSSFWorkbook wb, HSSFCellStyle curStyle, HSSFFont curFont){
		curStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);					//横向居中
		curStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);		//纵向居中
		
		//设置字体
		
		curFont.setFontName("宋体");											//设置字体
		curFont.setFontHeightInPoints((short)16);							//字体大小	
		curFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);					//加粗
		curStyle.setFont(curFont);											//绑定
		
		return curStyle;
	}	
	
	
	//子标题样式
	private HSSFCellStyle subtitleStyle(HSSFWorkbook wb, HSSFCellStyle curStyle, HSSFFont curFont){
		curStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);					//横向居中
		curStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);		//纵向居中
		
		curStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);					//表格细线
		curStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		curStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		curStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		
		//设置字体
		curFont.setFontName("黑体");										//设置字体
		curFont.setFontHeightInPoints((short)12);							//字体大小	
		curStyle.setFont(curFont);											//绑定
		
		return curStyle;
	}	
	
	
	//内容样式
	private HSSFCellStyle contentStyle(HSSFWorkbook wb, HSSFCellStyle curStyle, HSSFFont curFont){
		curStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);					//横向居左
		curStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);		//纵向居中
		
		curStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);					//表格细线
		curStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		curStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		curStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		
		//设置字体
		curFont.setFontName("Times New Roman");								//设置字体
		curFont.setFontHeightInPoints((short)10);							//字体大小	
		curStyle.setFont(curFont);											//绑定
		
		return curStyle;
	}	
	
	//附件样式
	private HSSFCellStyle extProductStyle(HSSFWorkbook wb, HSSFCellStyle curStyle, HSSFFont curFont){
		curStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);					//横向居左
		curStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);		//纵向居中
		curStyle.setWrapText(true);											//换行
		
		curStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);					//表格细线
		curStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		curStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		curStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		
		//设置字体
		curFont.setFontName("Times New Roman");								//设置字体
		curFont.setFontHeightInPoints((short)10);							//字体大小	
		curStyle.setFont(curFont);											//绑定
		
		return curStyle;
	}	
}
