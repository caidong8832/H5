package flybear.hziee.app.model;

import java.util.List;

public class StaffInfo {

	private Integer id;
	private String staffName;
	private Integer staffSex;
	private Integer staffLevel;
	private Integer staffPhone;
	private String staffArea;
	private Integer intime;
	private Integer outtime;

    public void setId(Integer id){
        this.id = id;
    }
    public Integer getId(){
        return this.id;
    }
    public void setStaffName(String staffName){
        this.staffName = staffName;
    }
    public String getStaffName(){
        return this.staffName;
    }
    public void setStaffSex(Integer staffSex){
        this.staffSex = staffSex;
    }
    public Integer getStaffSex(){
        return this.staffSex;
    }
    public void setStaffLevel(Integer staffLevel){
        this.staffLevel = staffLevel;
    }
    public Integer getStaffLevel(){
        return this.staffLevel;
    }
    public void setStaffPhone(Integer staffPhone){
        this.staffPhone = staffPhone;
    }
    public Integer getStaffPhone(){
        return this.staffPhone;
    }
    public void setStaffArea(String staffArea){
        this.staffArea = staffArea;
    }
    public String getStaffArea(){
        return this.staffArea;
    }
    public void setIntime(Integer intime){
        this.intime = intime;
    }
    public Integer getIntime(){
        return this.intime;
    }
    public void setOuttime(Integer outtime){
        this.outtime = outtime;
    }
    public Integer getOuttime(){
        return this.outtime;
    }
}