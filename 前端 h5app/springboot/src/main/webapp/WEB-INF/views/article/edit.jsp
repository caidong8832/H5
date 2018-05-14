<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://flybear.hziee/tags" prefix="z"%>


<form id="form" action="article/edit" method="post"  >
    <table align="center" class="form-table">
			<tr>
				<td>患者姓名：</td>
				<td>
						<input type="text" name="articleTitle" id="articleTitle" value="${list.articleTitle}" />
				</td>
			</tr>
			
			
			<%-- <tr>
				<td>板块：</td>
				<td>
						<input class="jq-combobox" name="plate"  type="text" value="${list.plate}" data-options="{
								method:'post',
								editable:false,
								url: '${z:u('plate/name_list')}',
								
								}"/>
				</td>
			</tr> --%>
			<tr>
				<td>责任医生：</td>
				<td>
						<input class="jq-combobox" name="plate"  type="text" value="${list.plate}" data-options="{
								method:'post',
								editable:false,
								url: '${z:u('plate/name_list')}',
								
								}"/>
				</td>
			</tr>
				
				<tr>
				<td>学号：</td>
				<td>
				
						 <input type="text" name="author" id="author" value="${list.author}" /> 
				</td>
			</tr>
				
			<tr>
				<td>就诊次数：</td>
				<td>
						<input type="text" name="level" id="level" value="${list.level}" />
				</td>
			</tr>
			<tr>
				<td>病况简述：</td>
				<td>
						<input type="text" name="articleContent" id="articleContent" value="${list.articleContent}" />
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
</script>