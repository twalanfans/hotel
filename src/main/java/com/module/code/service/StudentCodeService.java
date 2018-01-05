package com.module.code.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.noo.pagination.page.PageContext;
import org.noo.pagination.page.Pagination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.common.config.Global;
import com.common.utils.DateUtils;
import com.common.utils.QrcodeMade;
import com.common.utils.SpringContextHolder;
import com.common.utils.UserAgentUtils;
import com.github.pagehelper.PageHelper;
import com.module.code.dao.KeyCodeDao;
import com.module.code.dao.StudentCodeDao;
import com.module.code.entity.KeyCode;
import com.module.code.entity.StudentCode;

/**
 * 
 * @ClassName : StudentCodeService
 * @Description : TODO(对学籍号的操作的业务)
 * @author : aprwu
 * @date : 2017年11月27日 上午10:31:40
 */
@Service
@Transactional(readOnly = true)
public class StudentCodeService {

	@Transactional(readOnly = true)
	public static List<StudentCode> codeList() {
		Pagination pager = PageContext.getPageContext(); // 分页
		int pageNo = pager.getCurrentPage();
		int pageSize = pager.getPageSize();
		PageHelper.startPage(pageNo, pageSize); // 拦截器分页开始
		List<StudentCode> list = queryCodeList();
		return list;
	}

	public static List<StudentCode> queryCodeList() {
		StudentCodeDao StudentCodeDao = SpringContextHolder.getBean(StudentCodeDao.class);
		List<StudentCode> list = StudentCodeDao.queryCodeList();
		return list;
	}

	public static void updateCodeInfo(StudentCode studentCode) {
		StudentCodeDao StudentCodeDao = SpringContextHolder.getBean(StudentCodeDao.class);
		StudentCodeDao.updateCodeInfo(studentCode);
	}
	/*
	 * 准备使用POI导入
	 */
	public static void createKeyCode(StudentCode studentCode) {
		StudentCodeDao studentCodeDao = SpringContextHolder.getBean(StudentCodeDao.class);
		
		studentCodeDao.insertStudentCode(studentCode);
	}

	public static StudentCode getCodeById(String code,String studentName) {
		StudentCodeDao studentCodeDao = SpringContextHolder.getBean(StudentCodeDao.class);
		StudentCode student = studentCodeDao.queryStudentCodeById(code,studentName);
		
		return student;
	}

	public static String madeKeyCode() {
		String base = "0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < 16; j++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
			if (j == 3 || j == 7 || j == 11) {
				sb.append("-");
			}
		}
		return sb.toString();
	}

	public static int deleteKeyCode(String keyId) {
		StudentCodeDao studentCodeDao = SpringContextHolder.getBean(StudentCodeDao.class);
		int ret = 0;
		try {
			ret = studentCodeDao.deleteById(keyId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	/*
	 * 新版POI导出，理论是一张表的数据，没测
	 * 再多数据则需要再新建表，存放数据
	 */
	public static void queryExcel(HttpServletRequest request,
			HttpServletResponse response, Integer num,String name) throws Exception {
		KeyCodeDao keyCodeDao = SpringContextHolder.getBean(KeyCodeDao.class);
		//查询出对应的激活码集合
		List<KeyCode> list = keyCodeDao.queryExcel(num);
		
		// 1 创建工作簿
		Workbook workbook = new SXSSFWorkbook();
		// 2 创建sheet
		Sheet sheet = workbook.createSheet("激活码");
		CellStyle cellStyle = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setColor(Font.COLOR_RED);
		font.setFontName("楷体");
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setFont(font);

		/**
		 * 准备数据,取出所有数据，再将对应的数据放入第二个集合(待导出的数据)中
		 */

		/*for (int i = 0; i < num; i++) {
			list.add(list.get(i));
		}*/
		/**
		 * 使用user对象的属性名作为标题 1 如何获得user的全部属性名 --- 反射 1 创建user的 类对象 Class 2
		 * 根据类对象获得所有属性
		 */
		// 3 创建row 参数 行的下表 从0开始
		Row firstRow = sheet.createRow(0);
		// 创建类对象的3中方式

		KeyCode keycode = new KeyCode();
		Class<? extends KeyCode> keyClass = keycode.getClass();
		Field[] fields = keyClass.getDeclaredFields();

		// 遍历
		/*
		 * for (int i = 0; i < fields.length; i++) {
		 * 
		 * Comment comment = fields[i].getAnnotation(Comment.class);
		 * 
		 * // 把名字赋值单元格 Cell createCell = firstRow.createCell(i);
		 * createCell.setCellValue(comment.value());//null
		 * createCell.setCellStyle(cellStyle);
		 * 
		 * }
		 */
		/**
		 * 创建数据行
		 */
		// 根据集合元素个数创建行,跳过标题行

		for (int i = 0; i < list.size(); i++) {
			Row row = sheet.createRow(i);
			// 根据属性个数创建单元格
			// 4 创建cell并赋值
			for (int j = 1; j < fields.length; j++) {
				// 创建单元格
				Cell cell = row.createCell(j);
				/**
				 * 赋值 获得集合中元素对象user 的各个属性
				 */
				// 反射打破封装
				fields[j].setAccessible(true);
				// 1 通过反射拿到属性对应的值 obj: 代表集合中正在遍历元素对象user
				Object val = fields[j].get(list.get(i));
				// 2 赋值 日期类特殊处理
				if (val instanceof Date) {
					String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(val);
					cell.setCellValue(format);
				} else {
					cell.setCellValue(val.toString());
				}
			}
		}
		// 6 通过流写出
		/*
		 * File file = new File("d://激活码.xls"); //服務器这个路径需要改 OutputStream os =
		 * new FileOutputStream(file); workbook.write(os);
		 * System.out.println("导出成功！"); //1)、设置响应的头文件，会自动识别文件内容
		 * response.setContentType("multipart/form-data");
		 * //2)、设置Content-Disposition response.setHeader("Content-Disposition",
		 * "attachment;filename=KeyCode.xls"); //3)、输出流 OutputStream out =
		 * response.getOutputStream(); //4)、获取服务端生成的excel文件，这里的path等于4.8中的path
		 * InputStream in = new FileInputStream(file); //5)、输出文件 int b;
		 * while((b=in.read())!=-1){ out.write(b); } in.close(); out.close();
		 */
		// 6 通过流写出--缓存的路径
		File file = new File("D://KeyCode.xls");
		//File file = new File("/home/KeyCode.xls");
		OutputStream os = new FileOutputStream(file);
		workbook.write(os);
		System.out.println("导出成功！");
		// 1)、设置响应的头文件，会自动识别文件内容
		response.setContentType("multipart/form-data");
		// 2)、设置Content-Disposition response.setHeader("Content-Disposition",
		// "attachment;filename=KeyCode.xls");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream; charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;"
				+ UserAgentUtils.encodeFileName(request, "KeyCode.xls"));
		// 3)、输出流
		OutputStream out = response.getOutputStream();
		// 4)、获取服务端生成的excel文件，这里的path等于4.8中的path
		InputStream in = new FileInputStream(file);
		// 5)、输出文件
		int b;
		while ((b = in.read()) != -1) {
			out.write(b);
		}
		in.close();
		out.close();
	}

	public static void packageToZip(HttpServletRequest request,
			HttpServletResponse response, String path[], String name)
			throws IOException {
		String endpoint = Global.getConfig("endpoint");
		String accessKeyId = Global.getConfig("accessKeyId");
		String accessKeySecret = Global.getConfig("accessKeySecret");
		String bucketName = Global.getConfig("bucketName");
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId,
				accessKeySecret);

		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream; charset=utf-8");
		response.setHeader("Content-disposition", "attachment;"
				+ UserAgentUtils.encodeFileName(request, name + "_二维码打包.rar"));
		ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
		response.setHeader("Content-Length", "未知");
		byte[] buffer = new byte[1024];
		for (int i = 0; i < path.length; i++) {
			// 创建OSSClient实例
			OSSObject ossObject = ossClient.getObject(bucketName, path[i]);
			// 读Object内容
			BufferedInputStream bis = new BufferedInputStream(
					ossObject.getObjectContent());

			out.putNextEntry(new ZipEntry((i + 1) + ".jpg")); // 对文件命名
			int len;
			// 读入需要下载的文件的内容，打包到zip文件
			while ((len = bis.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			out.closeEntry();
			bis.close();
		}
		out.close();
		ossClient.shutdown();
	}
}
