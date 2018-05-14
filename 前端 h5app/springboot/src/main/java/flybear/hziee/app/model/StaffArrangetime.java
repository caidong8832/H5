package flybear.hziee.app.model;

import java.util.List;

public class StaffArrangetime {

	private Integer id;
	private Integer staffId;
	private String weektime;
	private Integer begintime;
	private Integer isaddtime;

    public void setId(Integer id){
        this.id = id;
    }
    public Integer getId(){
        return this.id;
    }
    public void setStaffId(Integer staffId){
        this.staffId = staffId;
    }
    public Integer getStaffId(){
        return this.staffId;
    }
    public void setWeektime(String weektime){
        this.weektime = weektime;
    }
    public String getWeektime(){
        return this.weektime;
    }
    public void setBegintime(Integer begintime){
        this.begintime = begintime;
    }
    public Integer getBegintime(){
        return this.begintime;
    }
    public void setIsaddtime(Integer isaddtime){
        this.isaddtime = isaddtime;
    }
    public Integer getIsaddtime(){
        return this.isaddtime;
    }
}