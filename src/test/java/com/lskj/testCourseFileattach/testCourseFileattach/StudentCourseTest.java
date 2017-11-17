package com.lskj.testCourseFileattach.testCourseFileattach;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.module.course.entity.Course;
import com.module.course.service.CourseManageService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="classpath:spring-context.xml")

public class StudentCourseTest {
	@Autowired
	private CourseManageService cs;
	@Test
	public void test1 (){
		Course course = new Course();
		
		course.setCourseName("语文课程");
		course.setIsCommon("1");
		course.setTeacherName("我是老师");
		List<Course> list = cs.queryStudentCourse(course);
		for (Course course2 : list) {
			System.out.println(course2);
		}
	}
	
}
