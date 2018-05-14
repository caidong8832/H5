package flybear.hziee.app.model;

import java.util.List;

public class StaffWeektime {

	private Integer id;
	private Integer begintime;
	private Integer setweektime;
	private String othernews;

    public void setId(Integer id){
        this.id = id;
    }
    public Integer getId(){
        return this.id;
    }
    public void setBegintime(Integer begintime){
        this.begintime = begintime;
    }
    public Integer getBegintime(){
        return this.begintime;
    }
    public void setSetweektime(Integer setweektime){
        this.setweektime = setweektime;
    }
    public Integer getSetweektime(){
        return this.setweektime;
    }
    public void setOthernews(String othernews){
        this.othernews = othernews;
    }
    public String getOthernews(){
        return this.othernews;
    }
}