package flybear.hziee.app.mapper;


import flybear.hziee.app.model.StaffInfo;
import java.util.List;

public interface StaffInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StaffInfo record);

    StaffInfo selectByPrimaryKey(Integer id);

    List<StaffInfo> selectAll();

    int updateByPrimaryKey(StaffInfo record);
    
}