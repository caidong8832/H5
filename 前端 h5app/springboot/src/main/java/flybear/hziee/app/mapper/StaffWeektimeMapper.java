package flybear.hziee.app.mapper;


import flybear.hziee.app.model.StaffWeektime;
import java.util.List;

public interface StaffWeektimeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StaffWeektime record);

    StaffWeektime selectByPrimaryKey(Integer id);

    List<StaffWeektime> selectAll();

    int updateByPrimaryKey(StaffWeektime record);
    
}