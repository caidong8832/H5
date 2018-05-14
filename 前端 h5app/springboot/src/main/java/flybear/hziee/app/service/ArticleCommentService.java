package flybear.hziee.app.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import flybear.hziee.app.mapper.ArticleCommentMapper;
import flybear.hziee.app.model.ArticleComment;
import flybear.hziee.app.util.UIUtils;
import flybear.hziee.core.mybatis.SqlRunner;
import flybear.hziee.core.sql.Row;
import flybear.hziee.core.sql.SQLBuilder;
import flybear.hziee.core.util.Fn;


@Service
public class ArticleCommentService {

    @Autowired
    private ArticleCommentMapper mapper;
    
    @Autowired
	private SqlRunner sqlRunner;

    public List<ArticleComment> findAll() {
        return mapper.selectAll();
    }

    public Integer delete(Integer id){
        return mapper.deleteByPrimaryKey(id);
    }
    
    public ArticleComment findById(Integer id){
		return mapper.selectByPrimaryKey(id);
	}
	
	public Integer update(ArticleComment entity) throws Exception{
		return mapper.updateByPrimaryKey(entity);
	}

    public Integer save(ArticleComment entity) {
    	int contentTime = (int) (System.currentTimeMillis()/1000); 
    	entity.setContentTime(contentTime);
        return mapper.insert(entity);
    }

    private Row getArticleName(int id) {
    	String sql = "select id,article_title as text from x_article where id = "+id+"";
		List<Row> list = sqlRunner.select(sql);
		Row name = list.get(0);
		return name;
	}

	public Map<String, Object> getUIGridData(Map<String, Object> where, Map<String, String> pageMap) {
        SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(ArticleComment.class);
        String sql = sqlBuilder
                .fields("*")   //这里约定前端grid需要显示多少个具体列，也可以全部*
                .where(where)
                .parseUIPageAndOrder(pageMap)
                .order("id", "asc")
                .selectSql();
        List<Row> list = sqlRunner.select(sql);
        for(Row row:list){
			row.put("contentTime", Fn.date(row.getInt("contentTime"), "yyyy-MM-dd"));
			row.put("articleId", getArticleName(row.getInt("articleId")).getString("text"));
		}
        String countSql = sqlBuilder.fields("count(*)").where(where).selectSql();
        Integer count = sqlRunner.count(countSql);
        return UIUtils.getGridData(count, list);
    }
    
	public boolean articleCommentIsExist(String key){
			SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(ArticleComment.class);
			String sql = sqlBuilder.fields("*").where("key='"+key+"'").selectSql();
			List<Row> list = sqlRunner.select(sql);
			if(list.size()==0){
				return false;
			}else{
				return true;
			}
		}

		public List<Row> getArticleComment() {
		String sql = "select content as text from x_article_comment";
		List<Row> list = sqlRunner.select(sql);
		System.out.println(list);
		return list;
		}

}
