package flybear.hziee.app.service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import flybear.hziee.app.mapper.StaffWeektimeMapper;
import flybear.hziee.app.model.StaffWeektime;
import flybear.hziee.app.util.UIUtils;
import flybear.hziee.core.mybatis.SqlRunner;
import flybear.hziee.core.sql.Row;
import flybear.hziee.core.sql.SQLBuilder;
import flybear.hziee.core.util.Fn;


@Service
public class StaffWeektimeService {

    @Autowired
    private StaffWeektimeMapper mapper;
    
    @Autowired
	private SqlRunner sqlRunner;

    public List<StaffWeektime> findAll() {
        return mapper.selectAll();
    }

    public Integer delete(Integer id){
        return mapper.deleteByPrimaryKey(id);
    }
    
    public StaffWeektime findById(Integer id){
		return mapper.selectByPrimaryKey(id);
	}
	
	public Integer update(StaffWeektime entity) throws Exception{
		return mapper.updateByPrimaryKey(entity);
	}

    public Integer save(StaffWeektime entity) {
        return mapper.insert(entity);
    }

    public Map<String, Object> getUIGridData(Map<String, Object> where, Map<String, String> pageMap) {
        SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(StaffWeektime.class);
        String sql = sqlBuilder
                .fields("*")   //这里约定前端grid需要显示多少个具体列，也可以全部*
                .where(where)
                .parseUIPageAndOrder(pageMap)
                .order("id", "asc")
                .selectSql();
        List<Row> list = sqlRunner.select(sql);
        
        for(Row row:list){
        	row.put("begintime", Fn.date(row.getInt("begintime"), "yyyy-MM-dd"));
        	row.put("setweektime", Fn.date(row.getInt("setweektime"),"yyyy-MM-dd"));
        }

        String countSql = sqlBuilder.fields("count(*)").where(where).selectSql();
        Integer count = sqlRunner.count(countSql);
        return UIUtils.getGridData(count, list);
    }
    
	public boolean staffWeektimeIsExist(String key){
			SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(StaffWeektime.class);
			String sql = sqlBuilder.fields("*").where("key='"+key+"'").selectSql();
			List<Row> list = sqlRunner.select(sql);
			if(list.size()==0){
				return false;
			}else{
				return true;
			}
		}
	
	public int DateToLong(String date) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
		Date date1 = sdf.parse(date);
		System.out.println(date1);
		int timechuo = Integer.parseInt(String.valueOf(date1.getTime()).toString().substring(0,10));
		return timechuo;
	}
	
	public Row getBegintime(int id){  //得到排班所开始的时间
		String sqlTogetbegintime = "select begintime from x_staff_weektime where id = '"+id+"'";
		List<Row> list = sqlRunner.select(sqlTogetbegintime);
		Row begintime = list.get(0);
		return begintime;
	}
	
	public List<Row> getNews(int begintime){   //通过排班开始时间去得到排班字段
		String sqlTogetnews = "select staff_id,weektime as text from x_staff_arrangetime where begintime = '"+begintime+"'";
		List<Row> list = sqlRunner.select(sqlTogetnews);
		return list;
	}
	
	public List<Row> getList() {  //去员工表循环读取出员工的名字
		String sql = "select id,staff_name as text from x_staff_info";
		List<Row> list = sqlRunner.select(sql);
		return list;
	}
	
	public void updateshoudong(int begintime,String week,int id){
		String sqlToupdatenews = "Update x_staff_arrangetime set weektime = '"+ week +"' where begintime = '"+begintime+"' AND staff_id ='"+id+"'";
		sqlRunner.update(sqlToupdatenews);
	}
}
