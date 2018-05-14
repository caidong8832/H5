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

import flybear.hziee.app.service.StaffArrangetimeService;
import flybear.hziee.app.util.UIUtils;
import flybear.hziee.app.model.StaffArrangetime;
import flybear.hziee.core.base.BaseController;
import flybear.hziee.core.sql.Row;
import flybear.hziee.core.util.UploadUtils;

@Controller
@RequestMapping("staff_arrangetime")
public class StaffArrangetimeController extends BaseController{

	@Autowired
	private StaffArrangetimeService StaffArrangetimeService;

		@RequestMapping(value={"add"})
		public String add(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
			if (request.getMethod().equals("POST")) {
				/*int flag = StaffArrangetimeService.save(StaffArrangetime);
				if (flag == 1) {
					return ajaxReturn(response, null, "添加成功", 1);
				} else {
					return ajaxReturn(response, null, "添加失败", 0);
				}*/
				String othernews = request.getParameter("othernews");
				String begintimeC = request.getParameter("begintime");
				int begintimeA = StaffArrangetimeService.DateToLong(begintimeC);
				List<Row> list = StaffArrangetimeService.getList();
				int staffNum = 0;
				for(Row tmp:list){
					staffNum++;
				}
				int[] staffId = new int[staffNum];
				staffNum = 0;
				for(Row tmp:list){
					int i = tmp.getInt("id");
					//System.out.println(i);
					staffId[staffNum] = i;
					staffNum++;
				}
				for(int i = 0; i < staffId.length;i++){
					StaffArrangetimeService.saveInbegintime(begintimeA, 1, staffId[i]);
				}
				StaffArrangetimeService.saveInweektime(begintimeA, othernews);
				System.out.println(begintimeA);
				return ajaxReturn(response, null, "添加成功", 1);
			} else {
				return "staff_arrangetime/add";
			}
		}	

		@RequestMapping(value={"edit"})
		public String edit(Model model,HttpServletRequest request,HttpServletResponse response,StaffArrangetime StaffArrangetime) throws Exception {
			if (request.getMethod().equals("POST")) {
				if(StaffArrangetime != null){
					StaffArrangetimeService.update(StaffArrangetime);
					return ajaxReturn(response, null, "修改成功", 1);
				}else{
					return ajaxReturn(response, null, "修改失败", 0);
				}
			}else{
				String id = request.getParameter("id");
				StaffArrangetime entity = StaffArrangetimeService.findById(Integer.valueOf(id));
				model.addAttribute("list", entity);
				return "staff_arrangetime/edit";
			}
			
		}
	

	@RequestMapping(value={"list"})
	public String list(Model model,HttpServletRequest request,HttpServletResponse response) {
		if(request.getMethod().equals("POST")){			
			Map<String, Object>	list = StaffArrangetimeService.getUIGridData(null,UIUtils.getPageParams(request));
			return ajaxReturn(response,list);
		}
		else{
			return "staff_arrangetime/list";
		}
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String delete(HttpServletRequest request,HttpServletResponse response,Integer id) throws Exception {
		int flag = StaffArrangetimeService.delete(id);
		if (flag == 1) {
			return ajaxReturn(response, null, "删除成功", 1);
		} else {
			return ajaxReturn(response, null, "删除失败", 0);
		}
		
	}
	
	@RequestMapping(value={"spetime"})
	public String specialtime(Model model,HttpServletRequest request,HttpServletResponse response){
		return "staff_arrangetime/specialtime";
	}
	
	@RequestMapping(value = { "staff_name" })
	public String getstaffname(Model model, HttpServletRequest request,HttpServletResponse response) {
		/*List<Row> list = StaffInfoService.getList();
		for(Row row:list){
			System.out.println(row);
		}*/
		return ajaxReturn(response,StaffArrangetimeService.getList() );
	}
	
	@RequestMapping(value={"buildsche"})
	public void buildsche(Model model, HttpServletRequest request,HttpServletResponse response,StaffArrangetime StaffArrangetime) throws Exception{
		String str = request.getParameter("str");
		//String begintime = request.getParameter("begintime");
		char strnum[] = str.toCharArray();
		int a = strnum.length/7;
		int[][] worktime = new int[a][7];
		int num = 0;
		for(int i = 0;i < a;i++){
			for(int j = 0;j < 7;j++){
				String c = strnum[num]+"";
				worktime[i][j] = Integer.parseInt(c);
				num++;
			}
		}
		int[] worknone = {0,0,0,0,0,0,0};
		worknone = StaffArrangetimeService.getWorknone(worktime);
		int b,c;
		for(b = 0;b < worktime.length;b++){
			int zeronum = 0;
			int min = StaffArrangetimeService.getMinworknone(worknone);
			for(c = 0;c < worktime[b].length;c++){
				if(worktime[b][c] == 0){
					zeronum++;
				}
			}
			if(zeronum < 1){
				int rand = StaffArrangetimeService.getRandomnum(min,worknone);
				if(worknone[rand] < min+1){
					worktime[b][rand] = 0;
					worknone[rand] += 1;
				}
			}
		}
		/*for(b = 0; b < worktime.length;b++){
			for(c = 0;c <worktime[b].length;c++){
				System.out.print(worktime[b][c]+" ");
			}
			System.out.println();
		}*/
		List<Row> list = StaffArrangetimeService.getList();
		/*int d = 0;
		for(Row tmp:list){
			int e = 0;
			tmp.put("tim1", worktime[d][e]);
			tmp.put("tim2", worktime[d][e+1]);
			tmp.put("tim3", worktime[d][e+2]);
			tmp.put("tim4", worktime[d][e+3]);
			tmp.put("tim5", worktime[d][e+4]);
			tmp.put("tim6", worktime[d][e+5]);
			tmp.put("tim7", worktime[d][e+6]);
			d++;
		}*/
		int[] staffId = new int[worktime.length];
		int staffNum = 0;
		for(Row tmp:list){
			int i = tmp.getInt("id");
			System.out.println(i);
			staffId[staffNum] = i;
			staffNum++;
			
		}
		String[] weektime = new String[a];
		for(b = 0; b < weektime.length;b++){
			String weektimetoString = "1";
			for(c = 0;c <worktime[b].length;c++){
				weektimetoString += worktime[b][c];
			}
			weektime[b] = weektimetoString;
		}
		for(b = 0;b < weektime.length;b++){
			if(StaffArrangetimeService.saveIn(staffId[b], weektime[b], 0)){
				System.out.println(1);
			}
		}
		for(b = 0;b < weektime.length;b++){
			System.out.println(weektime[b]);
		}
		//return "staff_arrangetime/specialtime";
	}
	
	@RequestMapping(value={"begintime"})
	public String workonetime(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String begintimeC = request.getParameter("begintime");
		int begintime = StaffArrangetimeService.DateToLong(begintimeC);
		List<Row> list = StaffArrangetimeService.getList();
		int staffNum = 0;
		for(Row tmp:list){
			staffNum++;
		}
		int[] staffId = new int[staffNum];
		staffNum = 0;
		for(Row tmp:list){
			int i = tmp.getInt("id");
			//System.out.println(i);
			staffId[staffNum] = i;
			staffNum++;
		}
		for(int i = 0; i < staffId.length;i++){
			StaffArrangetimeService.saveInbegintime(begintime, 1, staffId[i]);
		}
		return "staff_arrangetime/specialtime";
		//return ajaxReturn(response,null);
	}


}

