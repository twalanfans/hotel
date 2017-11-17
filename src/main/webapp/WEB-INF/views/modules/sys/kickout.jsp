<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<script type="text/javascript">  
    function kickout(){  
    if(confirm("连接超时，您的账号在另一台设备上登录，您被挤下线，若不是您本人操作，请立即修改密码！")){
    		   window.location="${contextPath}/a/login" ;
			}
     }  
    window.onload=kickout();   
</script>  