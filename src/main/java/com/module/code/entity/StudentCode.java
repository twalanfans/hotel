package com.module.code.entity;

import com.common.persistence.DataEntity;
/**
 * 
 * @ClassName : StudentCode
 * @Description : TODO(类的作用)
 * @author : aprwu
 * @date : 2017年11月27日 上午9:45:29
 */
public class StudentCode extends DataEntity<StudentCode>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String studentCode;	//学籍号	
	private String studentName;	//学生姓名
	private String appleId;		//苹果设备ID
	private String andriodId;	//安卓设备ID
	private String pc;			//PC端ID
	private String schoolId;	//学校ID
	private String schoolName; 	//学校名称
	
	
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	public String getStudentCode() {
		return studentCode;
	}
	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getAppleId() {
		return appleId;
	}
	public void setAppleId(String appleId) {
		this.appleId = appleId;
	}
	public String getAndriodId() {
		return andriodId;
	}
	public void setAndriodId(String andriodId) {
		this.andriodId = andriodId;
	}
	public String getPc() {
		return pc;
	}
	public void setPc(String pc) {
		this.pc = pc;
	}
	public StudentCode(String studentCode, String studentName, String appleId,
			String andriodId, String pc, String schoolId) {
		super();
		this.studentCode = studentCode;
		this.studentName = studentName;
		this.appleId = appleId;
		this.andriodId = andriodId;
		this.pc = pc;
		this.schoolId = schoolId;
	}
	public StudentCode() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "StudentCode [studentCode=" + studentCode + ", studentName="
				+ studentName + ", appleId=" + appleId + ", andriodId="
				+ andriodId + ", pc=" + pc + ", schoolId=" + schoolId + "]";
	}
	
	
	
}
