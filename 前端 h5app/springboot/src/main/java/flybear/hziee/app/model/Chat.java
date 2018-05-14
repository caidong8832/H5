package flybear.hziee.app.model;

import java.util.List;

public class Chat {

	private Integer id;
	private Integer userId;
	private String title;
	private Integer chatTime;
	private Integer sponId;

    public void setId(Integer id){
        this.id = id;
    }
    public Integer getId(){
        return this.id;
    }
    public void setUserId(Integer userId){
        this.userId = userId;
    }
    public Integer getUserId(){
        return this.userId;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setChatTime(Integer chatTime){
        this.chatTime = chatTime;
    }
    public Integer getChatTime(){
        return this.chatTime;
    }
    public void setSponId(Integer sponId){
        this.sponId = sponId;
    }
    public Integer getSponId(){
        return this.sponId;
    }
}