package com.lskj.testschool;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.module.owncenter.entity.School;
import com.module.owncenter.service.SchoolService;

/**
 * 
 * @ClassName : SchoolTest
 * @Description : TODO(类的作用)
 * @author : aprwu
 * @date : 2017年11月23日 上午9:41:57
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="classpath:spring-context.xml")
public class SchoolTest {
	@Autowired
	private SchoolService vs;
	@Test
	public void test1(){
		School s = new School();
		s.setSchoolId("0");
		List<School> school = SchoolService.querySchoolList(s);
		for (School ss : school) {
			System.out.println(ss);
		}
	}
}
