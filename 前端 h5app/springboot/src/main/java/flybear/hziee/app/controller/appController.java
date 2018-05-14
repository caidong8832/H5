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
import flybear.hziee.app.service.ArticleService;
import flybear.hziee.app.service.NewsService;
import flybear.hziee.app.util.UIUtils;
import flybear.hziee.app.model.Article;
import flybear.hziee.core.base.BaseController;
import flybear.hziee.core.sql.Row;
import flybear.hziee.core.util.UploadUtils;

@Controller
@RequestMapping("app")
public class appController extends BaseController{
	@Autowired
	private ArticleService ArticleService;
	@Autowired
	private ArticleCommentService ArticleCommentService;
	@Autowired
	private NewsService NewsService;

	@RequestMapping(value={"article"})
	public void getArticle(HttpServletRequest request,HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
        String callbackFunName  = request.getParameter("callback"); 
        response.setContentType("text/html;charset=UTF-8");	
		PrintWriter out  = response.getWriter();
		/*String city = request.getParameter("city");
		city = new String(city.getBytes("ISO-8859-1"),"UTF-8");*/
		for(int i = 0;i<ArticleService.getList().size();i++){
			Row coordinate = ArticleService.getArticle().get(i);
			String article = (String) coordinate.getString("text");
			/*String reg="[^\u4e00-\u9fa5]";
			coordinate = coordinate.replaceAll(reg,"");*/
			System.out.println(article);
			json.put("coordinate"+i, article);
		}
		out.write(callbackFunName+"("+json.toString()+")");
	}
	
	@RequestMapping(value={"articleComment"})
	public void getArticleComment(HttpServletRequest request,HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
        String callbackFunName  = request.getParameter("callback"); 
        response.setContentType("text/html;charset=UTF-8");	
		PrintWriter out  = response.getWriter();
		/*String city = request.getParameter("city");
		city = new String(city.getBytes("ISO-8859-1"),"UTF-8");*/
		System.out.println(ArticleCommentService.getArticleComment());
		for(int i = 0;i<ArticleCommentService.getArticleComment().size();i++){
			Row coordinate = ArticleCommentService.getArticleComment().get(i);
			String articleComment = (String) coordinate.getString("text");
			json.put("coordinate"+i, articleComment);
			
		}
		out.write(callbackFunName+"("+json.toString()+")");
	}
	
	@RequestMapping(value={"news"})
	public void getNews(HttpServletRequest request,HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
        String callbackFunName  = request.getParameter("callback"); 
        response.setContentType("text/html;charset=UTF-8");	
		PrintWriter out  = response.getWriter();
		for(int i = 0;i<NewsService.getNews().size();i++){
			Row coordinate = NewsService.getNews().get(i);
			String news = (String) coordinate.getString("text");
			System.out.println(coordinate);
			json.put("coordinate"+i, news);
		}
		out.write(callbackFunName+"("+json.toString()+")");
	}
}
