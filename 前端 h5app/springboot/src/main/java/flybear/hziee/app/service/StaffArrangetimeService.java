package flybear.hziee.app.service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import flybear.hziee.app.mapper.StaffArrangetimeMapper;
import flybear.hziee.app.model.StaffArrangetime;
import flybear.hziee.app.util.UIUtils;
import flybear.hziee.core.mybatis.SqlRunner;
import flybear.hziee.core.sql.Row;
import flybear.hziee.core.sql.SQLBuilder;
import flybear.hziee.core.util.Fn;


@Service
public class StaffArrangetimeService {

    @Autowired
    private StaffArrangetimeMapper mapper;
    
    @Autowired
	private SqlRunner sqlRunner;

    public List<StaffArrangetime> findAll() {
        return mapper.selectAll();
    }

    public Integer delete(Integer id){
        return mapper.deleteByPrimaryKey(id);
    }
    
    public StaffArrangetime findById(Integer id){
		return mapper.selectByPrimaryKey(id);
	}
	
	public Integer update(StaffArrangetime entity) throws Exception{
		return mapper.updateByPrimaryKey(entity);
	}

    public Integer save(StaffArrangetime entity) {
        return mapper.insert(entity);
    }
    
    private Row getStaffName(int id) {
    	String sql = "select id,staff_name as text from x_staff_info where id = "+id+"";
		List<Row> list = sqlRunner.select(sql);
		Row name = list.get(0);
		return name;
	}

    public Map<String, Object> getUIGridData(Map<String, Object> where, Map<String, String> pageMap) {
        SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(StaffArrangetime.class);
        String sql = sqlBuilder
                .fields("*")   //这里约定前端grid需要显示多少个具体列，也可以全部*
                .where(where)
                .parseUIPageAndOrder(pageMap)
                .order("id", "asc")
                .selectSql();
        List<Row> list = sqlRunner.select(sql);
        
        for(Row row:list){
        	row.put("begintime", Fn.date(row.getInt("begintime"), "yyyy-MM-dd"));
        	row.put("staffId", getStaffName(row.getInt("staffId")).getString("text"));
        }

        String countSql = sqlBuilder.fields("count(*)").where(where).selectSql();
        Integer count = sqlRunner.count(countSql);
        return UIUtils.getGridData(count, list);
    }
    
	public boolean staffArrangetimeIsExist(String key){
			SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(StaffArrangetime.class);
			String sql = sqlBuilder.fields("*").where("key='"+key+"'").selectSql();
			List<Row> list = sqlRunner.select(sql);
			if(list.size()==0){
				return false;
			}else{
				return true;
			}
		}
	
	public int[] getWorknone(int[][] worktime){    //得到每日休假人数的数组
		int worknone[] =  {0,0,0,0,0,0,0};
		for(int i = 0;i < worknone.length;i++){
			int noSum = 0;
			for(int j = 0; j < worktime.length;j++){
				if(worktime[j][i] == 0){
					noSum++;
				}
			}
			worknone[i] = noSum;
		}
		return worknone;
	}
	
	public int getRandomnum(int min,int[] worknone){     //通过每日休假客服人员与人数的最小值从而得到剩余天数的随机值
		/*Random rand = new Random();
		int num=1;
		int a;
		int[] lastnum = new int[worknone.length];
		for(a = 0;a < worknone.length; a++){
			if(worknone[a] == min){
				lastnum[num-1] = a;
				num++;
			}
		}
		int bianhao = rand.nextInt(num);
		return lastnum[bianhao];*/
		Random rand = new Random();
		int num=0;
		int a;
		for(a = 0;a < worknone.length; a++){
			if(worknone[a] == min){
				num++;
			}
		}
		int[] lastnum = new int[num];
		int b = 0;
		for(a = 0;a < worknone.length; a++){
			if(worknone[a] == min){
				lastnum[b] = a;
				b++;
			}
		}
		int bianhao = rand.nextInt(b);
		return lastnum[bianhao];
		
	}
	
	public int getMinworknone(int[] worknone){      //得到最少人员休假日的休假人数
		int min;
		min = worknone[0];
		for(int i = 0;i < worknone.length;i++){
			if(worknone[i]<min){
				min = worknone[i];
			}
		}
		return min;
	}

	public  List<Row> getList() {
		String sql = "select id,staff_name as text from x_staff_info";
		List<Row> list = sqlRunner.select(sql);
		return list;
	}
	
	public int DateToLong(String date) throws ParseException{//转化日期String到Integer
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
		Date date1 = sdf.parse(date);
		//System.out.println(date1);
		int timechuo = Integer.parseInt(String.valueOf(date1.getTime()).toString().substring(0,10));
		return timechuo;
	}
	
	public boolean saveIn(int staff_id, String weektime, int isaddtime ){
		//String sqlOfchongfu = "select * from x_staff_arrangtime where staff_id = '"+ staff_id +"' AND isAddtime = '"+ isaddtime +"'";
		String sqlOfsaveIn = "insert into x_staff_arrangetime(staff_id,begintime,weektime,isAddtime) values("+staff_id+","+0+","+weektime+","+isaddtime+")";
		/*if(sqlRunner.select(sqlOfchongfu) != null){
			return false;
		}else{*/
			sqlRunner.insert(sqlOfsaveIn);
			return true;
		//}
	}
	
	public void saveInbegintime(int begintime,int isaddtime,int staff_id){
		String sqlOfsaveInbegintime = "UPDATE x_staff_arrangetime SET begintime = '"+begintime+"',isAddtime = '"+isaddtime+"' WHERE begintime = 0 AND staff_id = '"+staff_id+"'";
		sqlRunner.update(sqlOfsaveInbegintime);
	}
	
	public void saveInweektime(int begintime, String othernews){
		int releaseTime = (int) (System.currentTimeMillis()/1000);
		String sqlOfsaveInweektime = "insert into x_staff_weektime(begintime,setweektime,othernews) values("+begintime+","+releaseTime+",'"+othernews+"')";
		sqlRunner.insert(sqlOfsaveInweektime);
	}
}
