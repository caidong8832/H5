package flybear.hziee.app.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import flybear.hziee.app.service.ArticleService;
import flybear.hziee.app.util.UIUtils;
import flybear.hziee.app.model.Article;
import flybear.hziee.core.base.BaseController;
import flybear.hziee.core.sql.Row;
import flybear.hziee.core.util.UploadUtils;

@Controller
@RequestMapping("article")
public class ArticleController extends BaseController{

	@Autowired
	private ArticleService ArticleService;

		@RequestMapping(value={"add"})
		public String add(Model model,HttpServletRequest request,HttpServletResponse response,Article Article) throws Exception {
			if (request.getMethod().equals("POST")) {
				int flag = ArticleService.save(Article);
				if (flag == 1) {
					
					return ajaxReturn(response, null, "添加成功", 1);
				} else {
					return ajaxReturn(response, null, "添加失败", 0);
				}
			} else {
				return "article/add";
			}
		}	

		@RequestMapping(value={"edit"})
		public String edit(Model model,HttpServletRequest request,HttpServletResponse response,Article Article) throws Exception {
			if (request.getMethod().equals("POST")) {
				if(Article != null){
					ArticleService.update(Article);
					return ajaxReturn(response, null, "修改成功", 1);
				}else{
					return ajaxReturn(response, null, "修改失败", 0);
				}
			}else{
				String id = request.getParameter("id");
				Article entity = ArticleService.findById(Integer.valueOf(id));
				model.addAttribute("list", entity);
				return "article/edit";
			}
		}
	

	@RequestMapping(value={"list"})
	public String list(Model model,HttpServletRequest request,HttpServletResponse response) {
		if(request.getMethod().equals("POST")){
			Map<String, Object>	list = ArticleService.getUIGridData(null,UIUtils.getPageParams(request));
			return ajaxReturn(response,list);
		}
		else{
			return "article/list";
		}
	}
	
	@RequestMapping(value = { "name_list" })
	public String roleList(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		return ajaxReturn(response,ArticleService.getList() );
	}
	


	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String delete(HttpServletRequest request,HttpServletResponse response,Integer id) throws Exception {
		int flag = ArticleService.delete(id);
		if (flag == 1) {
			return ajaxReturn(response, null, "删除成功", 1);
		} else {
			return ajaxReturn(response, null, "删除失败", 0);
		}
	}
/*	
	@RequestMapping(value = "authCode")
	public String getMobileAuthCode( HttpServletRequest request,HttpServletResponse response, String callback)
	        throws Exception {
		 String result =  "{'ret':'true'}";
		    //加上返回参数
		    result=callback+"("+result+")";
		 return ajaxReturn(response,result);
	}*/

}

