package com.module.course.web;

import org.springframework.stereotype.Controller;

import com.common.web.BaseFileController;

/**
 * 
 * 创建人：yanxb <br>
 * 创建时间：2013-12-6 <br>
 * 功能描述： 用户文件上传下载和文件列表<br>
 */
@Controller
public class UserFileController extends BaseFileController{
	
/*	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value = "userFile/indexPage")
	public String indexPage(HttpServletRequest request) throws Exception {
		//文件列表
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", 1);
		String uploadType = request.getParameter("uploadType");
		String fileName = request.getParameter("fileName");
		if(StringUtils.isNotEmpty(uploadType)){
			map.put("upload_type", Integer.parseInt(uploadType));
			request.setAttribute("uploadType", uploadType);
		}
		if(StringUtils.isNotEmpty(fileName)){
			map.put("file_name", fileName);
			request.setAttribute("fileName", fileName);
		}
		Page page = userFileService.findPage(map);
		request.setAttribute("lst_file", page);
		return "main/index-userFile";
	}
	
	public String getFolder(MultipartHttpServletRequest request){
	    String uploadFilePath=Global.USERFILES_BASE_URL;
	    if(StringUtils.isEmpty(uploadFilePath)){
	        return request.getSession().getServletContext().getRealPath("/");
	    }
	    return uploadFilePath;
	}
	
	*//**
	 * 上传文件
	 *//*
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(MultipartHttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String, Object> result = new HashMap<String, Object>();
		String folder = "";
		
		try {
			folder = getFolder(request);
		} catch (Exception ex) {
			result.put("message", "获取folder失败");
			return ajaxHtml(JsonUtil.getJsonString4JavaPOJO(result), response);
		}
		if (StringUtils.isEmpty(folder)) {// step-1 获得文件夹
			response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
			if (!result.containsKey("message")) {
				result.put("message", "处理失败");
			}
			return ajaxJson(JsonUtil.getJsonString4JavaPOJO(result), response);
		}
		if (handler(request, response, result, folder)) {
			result.put("status", "success");
			result.put("message", "上传成功");
		}
		System.out.println(result.toString());
		return ajaxHtml(JsonUtil.getJsonString4JavaPOJO(result), response);
	}
	
	*//**
	 * 处理文件上传
	 *//*
	public boolean handler(MultipartHttpServletRequest request, HttpServletResponse response, Map<String, Object> result, String folder) throws IOException{
		Date baseDate = new Date();
		String fileName = DateTime.toDate("yyyyMMddHH", baseDate);
		MultipartFile file = request.getFile("file");
		if (file == null) {// step-2 判断file
			return getError("文件内容为空", HttpStatus.UNPROCESSABLE_ENTITY, result, response, null);
		}
		String orgFileName = file.getOriginalFilename();
		orgFileName = (orgFileName == null) ? "" : orgFileName;
		
		Pattern p = Pattern.compile("\\s|\t|\r|\n");
        Matcher m = p.matcher(orgFileName);
        orgFileName = m.replaceAll("_");
		
		
		String newFilePath= UserFileService.getNewFilePath();
		String realFilePath = folder  + "/"+ "admin" + "/" + newFilePath;
		if(!(new File(realFilePath).exists())){
			new File(realFilePath).mkdirs();
		}
		String bigRealFilePath = realFilePath  +  fileName.concat(".").concat(FilenameUtils.getExtension(orgFileName).toLowerCase());
		
		if (file.getSize() > 0) {
			File targetFile = new File(bigRealFilePath);
			file.transferTo(targetFile);//写入目标文件
		}
		//保存user file
		UserFileDTO fileDTO = new UserFileDTO("1", 
																		new Date(), 
																		IpTool.getClientAddress(request), 
																		orgFileName, 
																		bigRealFilePath, 
																		1);
		
		fileDTO.setFileSize(String.valueOf(NumUtil.divideNumber(file.getSize(), 1024*1024)));
		
		userFileService.save(fileDTO);
		return true;
	}
	
	boolean getError(String message, HttpStatus status, Map<String, Object> result, HttpServletResponse response, Exception ex) {
		response.setStatus(status.value());
		result.put("message", message);
		LOG.warn(message, ex);
		return false;
	}
	
	*//**文件下载**//*
    @RequestMapping("download")
    public String download(HttpServletRequest request, HttpServletResponse response) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	try {
    		String fileId = request.getParameter("fileId");
    		if(StringUtils.isEmpty(fileId)){
    			map.put("status", "error");
    			map.put("message", "下载错误");
    			return ajaxJson(JsonUtil.getJsonString4JavaPOJO(map), response);
    		}
        	map.put("file_id", fileId);
        	List<UserFileDTO> list = userFileService.find(map);
        	UserFileDTO file = list.get(0);
			FileOperateUtil.download(request, response, "application/octet-stream; charset=utf-8", file.getFilePath(), file.getFileName());
			return null;
		} catch (IOException e) {
			logger.error("文件下载出错");
			map.put("status", "error");
			map.put("message", "下载错误");
		}
        return ajaxJson(JsonUtil.getJsonString4JavaPOJO(map), response);
    }

    *//**获取文件大小**//*
    @RequestMapping(value = "/getfilesize")
	@ResponseBody
	public String getFileSize(HttpServletRequest request) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	String fileId = request.getParameter("fileId");
    	map.put("file_id", fileId);
    	List<UserFileDTO> list = userFileService.find(map);
    	if(list.size() != 0){
    		UserFileDTO file = list.get(0);
        	Long fileLength = new File(file.getFilePath()).length();
        	return fileLength.toString();
    	}
    	return (new Long(0L)).toString();
	}*/
 
}