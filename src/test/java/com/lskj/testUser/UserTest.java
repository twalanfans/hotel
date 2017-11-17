/*package com.lskj.testUser;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.module.version.entity.Version;
import com.module.version.service.VersionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="classpath:spring-context.xml")

public class UserTest {
	@Autowired
	private VersionService vs;
	@Test
	public void test1(){
		List<Version> list = vs.selectVersion();
		String i = "1"; //传入的平台
		String j = "1.1.1"; //传入的版本号
		//System.out.println(list);
		for (Version version : list) {
			if(version.getId().equals(i)){//判断平台
				System.out.println("平台："+version.getId());
				if(j.equals(version.getVersion())){//判断版本
					System.out.println("当前版本号："+version.getVersion());
					System.out.println("版本："+version.getVersion());
				}else{
					System.out.println("当前版本号："+version.getVersion()+"  需要更新");
					System.out.println(version.getPath());//当版本号不对时返回路径
				}
			}
		}
		
	}
}
*/