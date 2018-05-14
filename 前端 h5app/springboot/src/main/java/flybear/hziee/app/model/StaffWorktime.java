package flybear.hziee.app.model;

import java.util.List;

public class StaffWorktime {

	private Integer id;
	private Integer staffId;
	private Integer onworkTime;
	private Integer outworkTime;
	private Integer workingTime;

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
    public void setOnworkTime(Integer onworkTime){
        this.onworkTime = onworkTime;
    }
    public Integer getOnworkTime(){
        return this.onworkTime;
    }
    public void setOutworkTime(Integer outworkTime){
        this.outworkTime = outworkTime;
    }
    public Integer getOutworkTime(){
        return this.outworkTime;
    }
    public void setWorkingTime(Integer workingTime){
        this.workingTime = workingTime;
    }
    public Integer getWorkingTime(){
        return this.workingTime;
    }
}