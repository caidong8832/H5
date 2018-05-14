package flybear.hziee.app.controller;

import java.util.*;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import flybear.hziee.app.service.NewsService;
import flybear.hziee.app.util.UIUtils;
import flybear.hziee.app.model.News;
import flybear.hziee.core.base.BaseController;
import flybear.hziee.core.sql.Row;
import flybear.hziee.core.util.UploadUtils;

@Controller
@RequestMapping("news")
public class NewsController extends BaseController{

	@Autowired
	private NewsService NewsService;

		@RequestMapping(value={"edit"})
		public String edit(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
			String id = request.getParameter("id");
			News entity1 = NewsService.findById(Integer.valueOf(id));
			model.addAttribute("list", entity1);
			return "news/edit";
		}
	
		@RequestMapping(value = "edit",method = RequestMethod.POST)
		public String edit (Model model,HttpServletResponse response,HttpServletRequest request,
							MultipartHttpServletRequest mhsr,News entity) throws Exception{
				String filename = "";//保存名字
				MultipartFile file_filename = mhsr.getFile("data_filename");
				if(file_filename != null&&file_filename.getSize()>0){
					Map<String,Object> info = UploadUtils.saveMultipartFile(file_filename);
					int status = Integer.valueOf(info.get("status").toString());
					if(status > 0){
						filename = info.get("saveName").toString();
						entity.setFilename(filename);
					}else{
						return ajaxReturn(response,null,info.get("errorMsg").toString(),0);
					}
				}
				int flag = NewsService.update(entity);
				if (flag > 0) {
					return ajaxReturn(response,null,"修改成功",1);
				}else {
					return ajaxReturn(response,null,"修改失败",0);
				}
		}


		@RequestMapping(value={"add"})
		public String add(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
			return "news/add";
		}	

		@RequestMapping(value = "add",method = RequestMethod.POST)
		public String save (Model model,HttpServletResponse response,HttpServletRequest request,
							MultipartHttpServletRequest mhsr,News entity)throws Exception{
			String filename = "";//保存名字
				MultipartFile file_filename = mhsr.getFile("data_filename");
				if(file_filename != null&&file_filename.getSize()>0){
					Map<String,Object> info = UploadUtils.saveMultipartFile(file_filename);
					int status = Integer.valueOf(info.get("status").toString());
					if(status > 0){
						filename = info.get("saveName").toString();
						entity.setFilename(filename);
					}else{
						return ajaxReturn(response,null,info.get("errorMsg").toString(),0);
					}
				}
			int flag = NewsService.save(entity);
			if (flag > 0) {
				return ajaxReturn(response,null,"添加成功",1);
			}else {
				return ajaxReturn(response,null,"添加失败",0);
			}
		}
	
	@RequestMapping(value={"isHotlist"})
	public String isHotlist(Model model,HttpServletRequest request,HttpServletResponse response) {
		List<Row> list = new ArrayList<Row>();
		Row row = new Row();
		row.put("id", "1");
		row.put("text", "是");
		list.add(row);
		Row row1 = new Row();
		row1.put("id", "0");
		row1.put("text", "否");
		list.add(row1);
		return ajaxReturn(response,list);
	}

	@RequestMapping(value={"list"})
	public String list(Model model,HttpServletRequest request,HttpServletResponse response) {
		if(request.getMethod().equals("POST")){			
			Map<String, Object>	list = NewsService.getUIGridData(null,UIUtils.getPageParams(request));
			return ajaxReturn(response,list);
		}
		else{
			return "news/list";
		}
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String delete(HttpServletRequest request,HttpServletResponse response,Integer id) throws Exception {
		int flag = NewsService.delete(id);
		if (flag == 1) {
			return ajaxReturn(response, null, "删除成功", 1);
		} else {
			return ajaxReturn(response, null, "删除失败", 0);
		}
		
	}

}

