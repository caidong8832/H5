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

import flybear.hziee.app.service.LabelService;
import flybear.hziee.app.util.UIUtils;
import flybear.hziee.app.model.Label;
import flybear.hziee.core.base.BaseController;
import flybear.hziee.core.sql.Row;
import flybear.hziee.core.util.UploadUtils;

@Controller
@RequestMapping("label")
public class LabelController extends BaseController{

	@Autowired
	private LabelService LabelService;

		@RequestMapping(value={"add"})
		public String add(Model model,HttpServletRequest request,HttpServletResponse response,Label Label) throws Exception {
			if (request.getMethod().equals("POST")) {
				int flag = LabelService.save(Label);
				if (flag == 1) {
					return ajaxReturn(response, null, "添加成功", 1);
				} else {
					return ajaxReturn(response, null, "添加失败", 0);
				}
			} else {
				return "label/add";
			}
		}	

		@RequestMapping(value={"edit"})
		public String edit(Model model,HttpServletRequest request,HttpServletResponse response,Label Label) throws Exception {
			if (request.getMethod().equals("POST")) {
				if(Label != null){
					LabelService.update(Label);
					return ajaxReturn(response, null, "修改成功", 1);
				}else{
					return ajaxReturn(response, null, "修改失败", 0);
				}
			}else{
				String id = request.getParameter("id");
				Label entity = LabelService.findById(Integer.valueOf(id));
				model.addAttribute("list", entity);
				return "label/edit";
			}
			
		}
	

	@RequestMapping(value={"list"})
	public String list(Model model,HttpServletRequest request,HttpServletResponse response) {
		if(request.getMethod().equals("POST")){			
			Map<String, Object>	list = LabelService.getUIGridData(null,UIUtils.getPageParams(request));
			return ajaxReturn(response,list);
		}
		else{
			return "label/list";
		}
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String delete(HttpServletRequest request,HttpServletResponse response,Integer id) throws Exception {
		int flag = LabelService.delete(id);
		if (flag == 1) {
			return ajaxReturn(response, null, "删除成功", 1);
		} else {
			return ajaxReturn(response, null, "删除失败", 0);
		}
		
	}

}

