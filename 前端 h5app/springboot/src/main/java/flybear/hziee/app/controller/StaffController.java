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

import flybear.hziee.app.service.StaffService;
import flybear.hziee.app.util.UIUtils;
import flybear.hziee.app.model.Staff;
import flybear.hziee.core.base.BaseController;
import flybear.hziee.core.sql.Row;
import flybear.hziee.core.util.UploadUtils;

@Controller
@RequestMapping("staff")
public class StaffController extends BaseController{

	@Autowired
	private StaffService StaffService;

		@RequestMapping(value={"add"})
		public String add(Model model,HttpServletRequest request,HttpServletResponse response,Staff Staff) throws Exception {
			if (request.getMethod().equals("POST")) {
				int flag = StaffService.save(Staff);
				if (flag == 1) {
					return ajaxReturn(response, null, "添加成功", 1);
				} else {
					return ajaxReturn(response, null, "添加失败", 0);
				}
			} else {
				return "staff/add";
			}
		}	

		@RequestMapping(value={"edit"})
		public String edit(Model model,HttpServletRequest request,HttpServletResponse response,Staff Staff) throws Exception {
			if (request.getMethod().equals("POST")) {
				if(Staff != null){
					StaffService.update(Staff);
					return ajaxReturn(response, null, "修改成功", 1);
				}else{
					return ajaxReturn(response, null, "修改失败", 0);
				}
			}else{
				String id = request.getParameter("id");
				Staff entity = StaffService.findById(Integer.valueOf(id));
				model.addAttribute("list", entity);
				return "staff/edit";
			}
			
		}
	

	@RequestMapping(value={"list"})
	public String list(Model model,HttpServletRequest request,HttpServletResponse response) {
		if(request.getMethod().equals("POST")){			
			Map<String, Object>	list = StaffService.getUIGridData(null,UIUtils.getPageParams(request));
			return ajaxReturn(response,list);
		}
		else{
			return "staff/list";
		}
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String delete(HttpServletRequest request,HttpServletResponse response,Integer id) throws Exception {
		int flag = StaffService.delete(id);
		if (flag == 1) {
			return ajaxReturn(response, null, "删除成功", 1);
		} else {
			return ajaxReturn(response, null, "删除失败", 0);
		}
	}
	
	@RequestMapping(value={"test"})
	public String test(HttpServletRequest request,HttpServletResponse response) throws Exception{
		List<Object> temp = new ArrayList<Object>();
		List<Object> temp2 = new ArrayList<Object>();
		List<Object> temp3 = new ArrayList<Object>();
	    temp.add("国际汇率消息");
	    temp.add(56.33);
	    temp2.add("萨德反导弹系统");
	    temp2.add(24.03);
	    temp3.add("美国投降");
	    temp3.add(19.64);
	    List<List<Object>> resultList = new ArrayList<List<Object>>();
	    resultList.add(temp);
	    resultList.add(temp2);
	    resultList.add(temp3);
	    System.out.println(resultList);
	    return ajaxReturn(response, resultList);
		/*return "staff/add";*/
	}
	
	@RequestMapping(value={"selftime"})
	public String selftime(Model model,HttpServletRequest request,HttpServletResponse response) {
		String allin = request.getParameter("allin");
		System.out.println(allin);
		return "staff/selftime";
	}
	
	@RequestMapping(value={"line"})
	public String line(Model model,HttpServletRequest request,HttpServletResponse response) {
		return "staff/line";
	}
	
	@RequestMapping(value={"bar"})
	public String bar(Model model,HttpServletRequest request,HttpServletResponse response) {
		return "staff/bar";
	}
	
	 
	@RequestMapping(value={"web"})
	public String websocket(Model model,HttpServletRequest request,HttpServletResponse response) {
		return "staff/websocket";
	}
    
}

