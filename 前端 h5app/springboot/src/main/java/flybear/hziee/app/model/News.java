package flybear.hziee.app.model;

import java.util.List;

public class News {

	private Integer id;
	private String title;
	private String content;
	private String filename;
	private String time;
	private Byte isHot;

    public void setId(Integer id){
        this.id = id;
    }
    public Integer getId(){
        return this.id;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setContent(String content){
        this.content = content;
    }
    public String getContent(){
        return this.content;
    }
    public void setFilename(String filename){
        this.filename = filename;
    }
    public String getFilename(){
        return this.filename;
    }
    public void setTime(String time){
        this.time = time;
    }
    public String getTime(){
        return this.time;
    }
    public void setIsHot(Byte isHot){
        this.isHot = isHot;
    }
    public Byte getIsHot(){
        return this.isHot;
    }
}