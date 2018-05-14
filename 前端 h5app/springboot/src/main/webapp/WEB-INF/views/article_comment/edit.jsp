<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://flybear.hziee/tags" prefix="z"%>


<form id="form" action="article_comment/edit" method="post"  >
    <table align="center" class="form-table">
			<tr>
				<td>评论者用户名：</td>
				<td>
						<input type="text" name="contentName" id="contentName" value="${list.contentName}" />
				</td>
			</tr>
			<tr>
				<td>评论内容：</td>
				<td>
						<input type="text" name="content" id="content" value="${list.content}" />
				</td>
			</tr>
			<tr>
				<td>评论时间：</td>
				<td>
						<input type="text" name="contentTime" id="contentTime" value="${list.contentTime}" readOnly="true"/>
				</td>
			</tr>
			<tr>
				<td>赞：</td>
				<td>
						<input type="text" name="up" id="up" value="${list.up}" readOnly="true"/>
				</td>
			</tr>
			<tr>
				<td>踩：</td>
				<td>
						<input type="text" name="down" id="down" value="${list.down}" readOnly="true"/>
				</td>
			</tr>
			<tr>
				<td>所属文章：</td>
				<td>
						<input type="text" name="articleId" id="articleId" value="${list.articleId}" readOnly="true"/>
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