package flybear.hziee.app.mapper;


import flybear.hziee.app.model.StaffWorktime;
import java.util.List;

public interface StaffWorktimeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StaffWorktime record);

    StaffWorktime selectByPrimaryKey(Integer id);

    List<StaffWorktime> selectAll();

    int updateByPrimaryKey(StaffWorktime record);
    
}