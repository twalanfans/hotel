package com.module.version.controler;

import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.web.BaseController;
import com.module.version.entity.Version;
import com.module.version.service.VersionService;
/**
 * 版本更新
 * @author AprWu
 * @version 2017-9-27
 */
@Controller
public class VersioinControler extends BaseController{
	@Autowired
	private VersionService versionService;
	
	@RequestMapping(value="/version/queryVersion")
	public void queryVersionById(HttpServletRequest request,HttpServletResponse response)throws Exception{
		Writer out = response.getWriter();
		String id = request.getParameter("id");
		String v = request.getParameter("version");
		
		//通过当前传过来的id获取到当前version对象
		Version selectVersionById = versionService.selectVersionById(id);
		//通过当前平台获取到当前的版本号
		String version = selectVersionById.getVersion();
		//通过当前平台获取当前地址
		String path = selectVersionById.getPath();
		//更新的内容
		String content = selectVersionById.getContent();
		
		if(v.equals(version)){
			System.out.println("不要更新");
			out.write("");
		}else{
			System.out.println("需要更新");
			out.write(path+","+content);
		}
	}
	//苹果版本绕过检测所用
	@RequestMapping(value="/version/checkVersion")
	public void checkVersion(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Writer out = response.getWriter();
		int a = 0;
		//传入的版本号
		String v = request.getParameter("version");
		//查询出所有历代版本
		List<Version> allLostVersion = versionService.queryAllLostVersion();
		//传入进来的版本与历代版本进行比较，一个都没有的则是审核版，返回0
		for (Version version : allLostVersion) {
			if(v.equals(version.getVersion())){
				a++;
			}
		}
		if(a>0){
			out.write("1");
		}else{
			out.write("0");
		}
	}
}
