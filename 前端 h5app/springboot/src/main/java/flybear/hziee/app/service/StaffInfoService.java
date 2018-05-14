package flybear.hziee.app.service;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import flybear.hziee.app.mapper.StaffInfoMapper;
import flybear.hziee.app.model.StaffInfo;
import flybear.hziee.app.util.UIUtils;
import flybear.hziee.core.mybatis.SqlRunner;
import flybear.hziee.core.sql.Row;
import flybear.hziee.core.sql.SQLBuilder;
import flybear.hziee.core.util.Fn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class StaffInfoService {

    @Autowired
    private StaffInfoMapper mapper;
    
    @Autowired
	private SqlRunner sqlRunner;

    public List<StaffInfo> findAll() {
        return mapper.selectAll();
    }

    public Integer delete(Integer id){
        return mapper.deleteByPrimaryKey(id);
    }
    
    public StaffInfo findById(Integer id){
		return mapper.selectByPrimaryKey(id);
	}
	
	public Integer update(StaffInfo entity) throws Exception{
		return mapper.updateByPrimaryKey(entity);
	}

    public Integer save(StaffInfo entity) throws ParseException {
        return mapper.insert(entity);
    }

    public Map<String, Object> getUIGridData(Map<String, Object> where, Map<String, String> pageMap) {
        SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(StaffInfo.class);
        String sql = sqlBuilder
                .fields("*")   //这里约定前端grid需要显示多少个具体列，也可以全部*
                .where(where)
                .parseUIPageAndOrder(pageMap)
                .order("id", "asc")
                .selectSql();
        List<Row> list = sqlRunner.select(sql);
        for(Row row:list){
        	if(row.getInt("staffLevel")==1){
        		row.put("staffLevel", "客服");
        	}else if(row.getInt("staffLevel")==2){
        		row.put("staffLevel", "医生");
        	}else if(row.getInt("staffLevel")==3){
        		row.put("staffLevel", "主管");
        	}
    		if(row.getInt("staffSex")==1){
    			row.put("staffSex", "男");
    		}else{
    			row.put("staffSex", "女");	
    		}
    		row.put("intime", Fn.date(row.getInt("intime"), "yyyy-MM-dd"));	
    		row.put("outtime", Fn.date(row.getInt("outtime"), "yyyy-MM-dd"));	
 		}     
        String countSql = sqlBuilder.fields("count(*)").where(where).selectSql();
        Integer count = sqlRunner.count(countSql);
        return UIUtils.getGridData(count, list);
    }
    
	public boolean staffInfoIsExist(String key){
			SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(StaffInfo.class);
			String sql = sqlBuilder.fields("*").where("key='"+key+"'").selectSql();
			List<Row> list = sqlRunner.select(sql);
			if(list.size()==0){
				return false;
			}else{
				return true;
			}
		}

	public  List<Row> getList() {
		String sql = "select id,staff_name as text from x_staff_info";
		List<Row> list = sqlRunner.select(sql);
		return list;
	}

	public int DateToLong(String date) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
		Date date1 = sdf.parse(date);
		System.out.println(date1);
		int timechuo = Integer.parseInt(String.valueOf(date1.getTime()).toString().substring(0,10));
		return timechuo;
	}
}
