<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://flybear.hziee/tags" prefix="z"%>


<form id="form" action="news/edit" method="post" enctype="multipart/form-data" >
    <table align="center" class="form-table">
			<tr>
				<td>标题：</td>
				<td>
						<input type="text" name="title" id="title" value="${list.title}" />
				</td>
			</tr>
			<tr>
				<td>内容：</td>
				<td>
						<input type="text" name="content" id="content" value="${list.content}" />
				</td>
			</tr>
			<tr>
				<td>附件：</td>
				<td>
						<input type="file" name="data_filename">
						<input type="text" name="filename" id="filename" value="${list.filename}" />
				</td>
			</tr>
			<tr>
				<td>日期：</td>
				<td>
						<input type="text" class="jq-datebox" id="time" name="time" value="">
				</td>
			</tr>
			<tr>
				<td>是否热点：</td>
				<td>
						<select class="jq-combobox" data-options="{
							method:'post',
							url: 'news/isHotlist',
							onSelect:function(data){setIsHot(data);}
							}">
							<option value="${list.isHot}" selected="selected"></option>
						</select>
						<input type="hidden"  name="isHot" id="isHot" value="${list.isHot}">
				</td>
			</tr>
		<tr>
			<td>&nbsp;</td>
			<td>
				<input type="hidden" name="id" id="id" value="${list.id}" />
				<button type="submit"  class="btn btn-small btn-success">确定</button>
				<button class="btn btn-primary btn-small J_close" type="button">返回</button>
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
	(function(){
		setTimeout(function(){
				$('#time').datebox('setValue', '${list.time}');
		},100);
	})();
	function setIsHot(data){
	    //console.log(data);
		$("#isHot").attr("value",data.id);
	}
</script>