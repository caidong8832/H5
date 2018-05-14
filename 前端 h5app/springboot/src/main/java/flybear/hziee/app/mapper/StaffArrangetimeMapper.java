package flybear.hziee.app.mapper;


import flybear.hziee.app.model.StaffArrangetime;
import java.util.List;

public interface StaffArrangetimeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StaffArrangetime record);

    StaffArrangetime selectByPrimaryKey(Integer id);

    List<StaffArrangetime> selectAll();

    int updateByPrimaryKey(StaffArrangetime record);
    
}