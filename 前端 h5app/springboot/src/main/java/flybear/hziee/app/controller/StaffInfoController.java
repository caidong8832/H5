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

import flybear.hziee.app.service.StaffInfoService;
import flybear.hziee.app.util.UIUtils;
import flybear.hziee.app.model.StaffInfo;
import flybear.hziee.app.model.Staff;
import flybear.hziee.core.base.BaseController;
import flybear.hziee.core.sql.Row;
import flybear.hziee.core.util.UploadUtils;

@Controller
@RequestMapping("staff_info")
public class StaffInfoController extends BaseController{

	@Autowired
	private StaffInfoService StaffInfoService;

		@RequestMapping(value={"add"})
		public String add(Model model,HttpServletRequest request,HttpServletResponse response,StaffInfo StaffInfo) throws Exception {
			if (request.getMethod().equals("POST")) {
				String intimeC = request.getParameter("intime1");
				/*String outtimeC = request.getParameter("outtime1");*/
				int intime = StaffInfoService.DateToLong(intimeC);
				/*int outtime = StaffInfoService.DateToLong(outtimeC);*/
				StaffInfo.setIntime(intime);
				/*StaffInfo.setOuttime(outtime);*/
				StaffInfo.setOuttime(0);
				int flag = StaffInfoService.save(StaffInfo);
				if (flag == 1) {
					return ajaxReturn(response, null, "添加成功", 1);
				} else {
					return ajaxReturn(response, null, "添加失败", 0);
				}
			} else {
				return "staff_info/add";
			}
		}

		@RequestMapping(value={"edit"})
		public String edit(Model model,HttpServletRequest request,HttpServletResponse response,StaffInfo StaffInfo) throws Exception {
			if (request.getMethod().equals("POST")) {
				if(StaffInfo != null){
					String intimeC = request.getParameter("intime2");
					String outtimeC = request.getParameter("outtime2");
					int intime = StaffInfoService.DateToLong(intimeC);
					int outtime = StaffInfoService.DateToLong(outtimeC);
					StaffInfo.setIntime(intime);
					StaffInfo.setOuttime(outtime);
					StaffInfoService.update(StaffInfo);
					return ajaxReturn(response, null, "修改成功", 1);
				}else{
					return ajaxReturn(response, null, "修改失败", 0);
				}
			}else{
				String id = request.getParameter("id");
				StaffInfo entity = StaffInfoService.findById(Integer.valueOf(id));
				model.addAttribute("list", entity);
				return "staff_info/edit";
			}
			
		}

	@RequestMapping(value={"list"})
	public String list(Model model,HttpServletRequest request,HttpServletResponse response) {
		if(request.getMethod().equals("POST")){
			Map<String, Object>	list = StaffInfoService.getUIGridData(null,UIUtils.getPageParams(request));
			return ajaxReturn(response,list);
		}
		else{
			return "staff_info/list";
		}
	}
	
	@RequestMapping(value = { "name_list" })
	public String roleList(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		return ajaxReturn(response,StaffInfoService.getList() );
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String delete(HttpServletRequest request,HttpServletResponse response,Integer id) throws Exception {
		int flag = StaffInfoService.delete(id);
		if (flag == 1) {
			return ajaxReturn(response, null, "删除成功", 1);
		} else {
			return ajaxReturn(response, null, "删除失败", 0);
		}
	}
}

