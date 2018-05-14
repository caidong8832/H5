package flybear.hziee.app.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.minidev.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import flybear.hziee.app.service.ArticleCommentService;
import flybear.hziee.app.util.UIUtils;
import flybear.hziee.app.model.ArticleComment;
import flybear.hziee.core.base.BaseController;
import flybear.hziee.core.sql.Row;
import flybear.hziee.core.util.UploadUtils;

@Controller
@RequestMapping("article_comment")
public class ArticleCommentController extends BaseController{

	@Autowired
	private ArticleCommentService ArticleCommentService;

		@RequestMapping(value={"add"})
		public String add(Model model,HttpServletRequest request,HttpServletResponse response,ArticleComment ArticleComment) throws Exception {
			if (request.getMethod().equals("POST")) {
				int flag = ArticleCommentService.save(ArticleComment);
				if (flag == 1) {
					return ajaxReturn(response, null, "添加成功", 1);
				} else {
					return ajaxReturn(response, null, "添加失败", 0);
				}
			} else {
				return "article_comment/add";
			}
		}	

		@RequestMapping(value={"edit"})
		public String edit(Model model,HttpServletRequest request,HttpServletResponse response,ArticleComment ArticleComment) throws Exception {
			if (request.getMethod().equals("POST")) {
				if(ArticleComment != null){
					ArticleCommentService.update(ArticleComment);
					return ajaxReturn(response, null, "修改成功", 1);
				}else{
					return ajaxReturn(response, null, "修改失败", 0);
				}
			}else{
				String id = request.getParameter("id");
				ArticleComment entity = ArticleCommentService.findById(Integer.valueOf(id));
				model.addAttribute("list", entity);
				return "article_comment/edit";
			}
			
		}
	

	@RequestMapping(value={"list"})
	public String list(Model model,HttpServletRequest request,HttpServletResponse response) {
		if(request.getMethod().equals("POST")){			
			Map<String, Object>	list = ArticleCommentService.getUIGridData(null,UIUtils.getPageParams(request));
			return ajaxReturn(response,list);
		}
		else{
			return "article_comment/list";
		}
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String delete(HttpServletRequest request,HttpServletResponse response,Integer id) throws Exception {
		int flag = ArticleCommentService.delete(id);
		if (flag == 1) {
			return ajaxReturn(response, null, "删除成功", 1);
		} else {
			return ajaxReturn(response, null, "删除失败", 0);
		}
		
	}
}

