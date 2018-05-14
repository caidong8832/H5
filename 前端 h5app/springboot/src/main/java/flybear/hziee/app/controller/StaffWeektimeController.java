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

import flybear.hziee.app.service.StaffWeektimeService;
import flybear.hziee.app.util.UIUtils;
import flybear.hziee.app.model.Plate;
import flybear.hziee.app.model.StaffWeektime;
import flybear.hziee.core.base.BaseController;
import flybear.hziee.core.sql.Row;
import flybear.hziee.core.util.UploadUtils;

@Controller
@RequestMapping("staff_weektime")
public class StaffWeektimeController extends BaseController{

	@Autowired
	private StaffWeektimeService StaffWeektimeService;
	int begintime;
	
		@RequestMapping(value={"add"})
		public String add(Model model,HttpServletRequest request,HttpServletResponse response,StaffWeektime StaffWeektime) throws Exception {
			if (request.getMethod().equals("POST")) {
				int flag = StaffWeektimeService.save(StaffWeektime);
				if (flag == 1) {
					return ajaxReturn(response, null, "添加成功", 1);
				} else {
					return ajaxReturn(response, null, "添加失败", 0);
				}
			} else {
				return "staff_weektime/add";
			}
		}	

		@RequestMapping(value={"edit"})
		public String edit(Model model,HttpServletRequest request,HttpServletResponse response,StaffWeektime StaffWeektime) throws Exception {
			if (request.getMethod().equals("POST")) {
				/*if(StaffWeektime != null){
					StaffWeektimeService.update(StaffWeektime);
					return ajaxReturn(response, null, "修改成功", 1);
				}else{
					return ajaxReturn(response, null, "修改失败", 0);
				}*/
				return ajaxReturn(response, null, "修改成功", 1);
			}else{
				/*String id = request.getParameter("id");
				StaffWeektime entity = StaffWeektimeService.findById(Integer.valueOf(id));
				model.addAttribute("list", entity);*/
				String id = request.getParameter("id");
				Row row = StaffWeektimeService.getBegintime(Integer.valueOf(id));
				begintime = row.getInt("begintime");
				List<Row> listNews = StaffWeektimeService.getNews(begintime);
				List<Row> listStaffName = StaffWeektimeService.getList();
				int nameOfnum = 0;
				for(Row name:listStaffName){
					//System.out.println(name);
					nameOfnum++;
				}
				String staffname[] = new String[nameOfnum];
				String weektime[] = new String[nameOfnum];
				int weeknum = 0;
				int weekname = 0;
				for(Row name:listStaffName){
					String str = name.getString("text");
					staffname[weekname] = str;
					weekname++;
				}
				for(Row news:listNews){
					//System.out.println(news);
					String str = news.getString("text");
					weektime[weeknum] = str;
					weeknum++;
				}
				int[][] week = new int[nameOfnum][7];
				for(int i = 0;i < weektime.length;i++){
					//System.out.println(weektime[i]);
					char strnum[] = weektime[i].toCharArray();
					int x = 1;
					for(int j = 0;j < 7;j++){
						String str = strnum[x]+"";
						week[i][j] = Integer.parseInt(str);
						x++;
					}
				}
				String weekC[][] = new String[week.length][7];
				for(int i = 0 ;i < week.length;i++){
					for(int j = 0;j < week[i].length;j++){
						if(week[i][j] == 1){
							weekC[i][j] = "排班";
						}else{
							weekC[i][j] = "休息";
						}
					}
					System.out.println();
				}
				int m = 0;
				for(Row weeknews :listNews){
					weeknews.put("staffname", staffname[m]);
					weeknews.put("mon", weekC[m][0]);
					weeknews.put("tue", weekC[m][1]);
					weeknews.put("wed", weekC[m][2]);
					weeknews.put("thu", weekC[m][3]);
					weeknews.put("fri", weekC[m][4]);
					weeknews.put("sat", weekC[m][5]);
					weeknews.put("sun", weekC[m][6]);
					weeknews.put("begintime", begintime);
					m++;
				}
				for(Row weeknews:listNews){
					System.out.println(weeknews);
				}
				request.setAttribute("listNews", listNews);
				return "staff_weektime/edit";
			}
			
		}
	

	@RequestMapping(value={"list"})
	public String list(Model model,HttpServletRequest request,HttpServletResponse response) {
		if(request.getMethod().equals("POST")){			
			Map<String, Object>	list = StaffWeektimeService.getUIGridData(null,UIUtils.getPageParams(request));
			return ajaxReturn(response,list);
		}
		else{
			return "staff_weektime/list";
		}
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String delete(HttpServletRequest request,HttpServletResponse response,Integer id) throws Exception {
		int flag = StaffWeektimeService.delete(id);
		if (flag == 1) {
			return ajaxReturn(response, null, "删除成功", 1);
		} else {
			return ajaxReturn(response, null, "删除失败", 0);
		}
		
	}
	
	@RequestMapping(value = "changepaiban")
	public void delete(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String allin = request.getParameter("allin");
		List<Row> listStaffName = StaffWeektimeService.getList();
		char strnum[] = allin.toCharArray();
		int a = strnum.length/7;
		int[] staffId = new int[a];
		int weekname = 0;
		for(Row name:listStaffName){
			int str = name.getInt("id");
			staffId[weekname] = str;
			weekname++;
		}
		String week[] = new String[a];
		int num = 0;
		for(int i = 0;i < week.length;i++){
			week[i]="1";
			for(int j = 0;j < 7; j++){
				week[i] += strnum[num];
				num++;
			}
		}
		for(int i = 0;i < week.length;i++){
			System.out.println(week[i]);
		}
		for(int i = 0;i < week.length;i++){
			StaffWeektimeService.updateshoudong(begintime, week[i],staffId[i]);
		}
	}
	
	@RequestMapping(value={"look"})
	public String look(Model model,HttpServletRequest request,HttpServletResponse response) {
		if(request.getMethod().equals("POST")){			
			Map<String, Object>	list = StaffWeektimeService.getUIGridData(null,UIUtils.getPageParams(request));
			return ajaxReturn(response,list);
		}
		else{
			return "staff_weektime/staffweek";
		}
	}
	
	@RequestMapping(value={"stafflook"})
	public String stafflook(Model model,HttpServletRequest request,HttpServletResponse response,StaffWeektime StaffWeektime) throws Exception {
		if (request.getMethod().equals("POST")) {
			/*if(StaffWeektime != null){
				StaffWeektimeService.update(StaffWeektime);
				return ajaxReturn(response, null, "修改成功", 1);
			}else{
				return ajaxReturn(response, null, "修改失败", 0);
			}*/
			return ajaxReturn(response, null, "修改成功", 1);
		}else{
			/*String id = request.getParameter("id");
			StaffWeektime entity = StaffWeektimeService.findById(Integer.valueOf(id));
			model.addAttribute("list", entity);*/
			String id = request.getParameter("id");
			Row row = StaffWeektimeService.getBegintime(Integer.valueOf(id));
			begintime = row.getInt("begintime");
			List<Row> listNews = StaffWeektimeService.getNews(begintime);
			List<Row> listStaffName = StaffWeektimeService.getList();
			int nameOfnum = 0;
			for(Row name:listStaffName){
				//System.out.println(name);
				nameOfnum++;
			}
			String staffname[] = new String[nameOfnum];
			String weektime[] = new String[nameOfnum];
			int weeknum = 0;
			int weekname = 0;
			for(Row name:listStaffName){
				String str = name.getString("text");
				staffname[weekname] = str;
				weekname++;
			}
			for(Row news:listNews){
				//System.out.println(news);
				String str = news.getString("text");
				weektime[weeknum] = str;
				weeknum++;
			}
			int[][] week = new int[nameOfnum][7];
			for(int i = 0;i < weektime.length;i++){
				//System.out.println(weektime[i]);
				char strnum[] = weektime[i].toCharArray();
				int x = 1;
				for(int j = 0;j < 7;j++){
					String str = strnum[x]+"";
					week[i][j] = Integer.parseInt(str);
					x++;
				}
			}
			String weekC[][] = new String[week.length][7];
			for(int i = 0 ;i < week.length;i++){
				for(int j = 0;j < week[i].length;j++){
					if(week[i][j] == 1){
						weekC[i][j] = "排班";
					}else{
						weekC[i][j] = "休息";
					}
				}
				System.out.println();
			}
			int m = 0;
			for(Row weeknews :listNews){
				weeknews.put("staffname", staffname[m]);
				weeknews.put("mon", weekC[m][0]);
				weeknews.put("tue", weekC[m][1]);
				weeknews.put("wed", weekC[m][2]);
				weeknews.put("thu", weekC[m][3]);
				weeknews.put("fri", weekC[m][4]);
				weeknews.put("sat", weekC[m][5]);
				weeknews.put("sun", weekC[m][6]);
				weeknews.put("begintime", begintime);
				m++;
			}
			for(Row weeknews:listNews){
				System.out.println(weeknews);
			}
			request.setAttribute("listNews", listNews);
			return "staff_weektime/stafflook";
		}
		
	}
	
	/*@RequestMapping(value = "weeknews")
	public String getweeknews(HttpServletRequest request,HttpServletResponse response){
		System.out.println(begintime);
		List<Row> listNews = StaffWeektimeService.getNews(begintime);
		List<Row> listStaffName = StaffWeektimeService.getList();
		int nameOfnum = 0;
		for(Row name:listStaffName){
			//System.out.println(name);
			nameOfnum++;
		}
		String staffname[] = new String[nameOfnum];
		String weektime[] = new String[nameOfnum];
		int weeknum = 0;
		int weekname = 0;
		for(Row name:listStaffName){
			String str = name.getString("text");
			staffname[weekname] = str;
			weekname++;
		}
		for(Row news:listNews){
			//System.out.println(news);
			String str = news.getString("text");
			weektime[weeknum] = str;
			weeknum++;
		}
		int[][] week = new int[nameOfnum][7];
		for(int i = 0;i < weektime.length;i++){
			//System.out.println(weektime[i]);
			char strnum[] = weektime[i].toCharArray();
			int x = 1;
			for(int j = 0;j < 7;j++){
				String str = strnum[x]+"";
				week[i][j] = Integer.parseInt(str);
				x++;
			}
		}
		for(int i = 0 ;i < week.length;i++){
			for(int j = 0;j < week[i].length;j++){
				System.out.print(week[i][j]);
			}
			System.out.println();
		}
		int m = 0;
		for(Row weeknews :listNews){
			weeknews.put("staffname", staffname[m]);
			weeknews.put("mon", week[m][0]);
			weeknews.put("tue", week[m][1]);
			weeknews.put("wed", week[m][2]);
			weeknews.put("thu", week[m][3]);
			weeknews.put("fri", week[m][4]);
			weeknews.put("sat", week[m][5]);
			weeknews.put("sun", week[m][6]);
			m++;
		}
		for(Row weeknews:listNews){
			System.out.println(weeknews);
		}
		return ajaxReturn(response,listNews);
	}*/

}

