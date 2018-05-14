package flybear.hziee.app.service;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import flybear.hziee.app.mapper.ArticleMapper;
import flybear.hziee.app.model.Article;
import flybear.hziee.app.util.UIUtils;
import flybear.hziee.core.mybatis.SqlRunner;
import flybear.hziee.core.sql.Row;
import flybear.hziee.core.sql.SQLBuilder;
import flybear.hziee.core.util.Fn;


@Service
public class ArticleService {

    @Autowired
    private ArticleMapper mapper;
    
    @Autowired
	private SqlRunner sqlRunner;

    public List<Article> findAll() {
        return mapper.selectAll();
    }

    public Integer delete(Integer id){
        return mapper.deleteByPrimaryKey(id);
    }
    
    public Article findById(Integer id){
		return mapper.selectByPrimaryKey(id);
	}
	
	public Integer update(Article entity) throws Exception{
		int editTime = (int) (System.currentTimeMillis()/1000); 
    	entity.setEditTime(editTime);
		return mapper.updateByPrimaryKey(entity);
	}

    public Integer save(Article entity) {
    	int releaseTime = (int) (System.currentTimeMillis()/1000); 
    	entity.setReleaseTime(releaseTime);
    	entity.setEditTime(0);
    	plateAdd(entity.getPlate());
        return mapper.insert(entity);
    }

    public Map<String, Object> getUIGridData(Map<String, Object> where, Map<String, String> pageMap) {
        SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(Article.class);
        String sql = sqlBuilder
                .fields("*")   //这里约定前端grid需要显示多少个具体列，也可以全部*
                .where(where)
                .parseUIPageAndOrder(pageMap)
                .order("id", "asc")
                .selectSql();
        List<Row> list = sqlRunner.select(sql);

        for(Row row:list){
			row.put("releaseTime", Fn.date(row.getInt("releaseTime"), "yyyy-MM-dd"));	
			row.put("editTime", Fn.date(row.getInt("editTime"), "yyyy-MM-dd"));
			row.put("plate", getPlateName(row.getInt("plate")).getString("text"));
		}       

        String countSql = sqlBuilder.fields("count(*)").where(where).selectSql();
        Integer count = sqlRunner.count(countSql);
 
        return UIUtils.getGridData(count, list);
    }
    
	public boolean articleIsExist(String key){
			SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(Article.class);
			String sql = sqlBuilder.fields("*").where("key='"+key+"'").selectSql();
			List<Row> list = sqlRunner.select(sql);
			if(list.size()==0){
				return false;
			}else{
				return true;
			}
		}
	public Row getPlateName(int value) {
		/*SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(Role.class);
		String sql = sqlBuilder.fields("id,name as text") // 这里约定前端grid需要显示多少个具体列，也可以全部*
				.where(where)
				.order("id", "asc").selectSql();*/
		String sql = "select staff_name as text from x_staff_info where id = "+value+"";
		List<Row> list = sqlRunner.select(sql);
		Row name = list.get(0);
		return name;
	}

	public List<Row> getList() {
		String sql = "select id,article_title as text from x_article";
		List<Row> list = sqlRunner.select(sql);
		return list;
	}
	
	public List<Row> getArticle() {
		String sql = "select article_content as text from x_article";
		List<Row> list = sqlRunner.select(sql);
		return list;
	}
	
	public void plateAdd(int value) {
		String sql = "update x_plate set x_plate.project_num=x_plate.project_num+1 where id = "+value+"";
		List<Row> list = sqlRunner.select(sql);
		System.out.println(list);
	}
}
