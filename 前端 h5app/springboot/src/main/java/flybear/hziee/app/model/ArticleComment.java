package flybear.hziee.app.model;

import java.util.List;

public class ArticleComment {

	private Integer id;
	private Integer contentName;
	private String content;
	private Integer contentTime;
	private Integer up;
	private Integer down;
	private Integer articleId;

    public void setId(Integer id){
        this.id = id;
    }
    public Integer getId(){
        return this.id;
    }
    public void setContentName(Integer contentName){
        this.contentName = contentName;
    }
    public Integer getContentName(){
        return this.contentName;
    }
    public void setContent(String content){
        this.content = content;
    }
    public String getContent(){
        return this.content;
    }
    public void setContentTime(Integer contentTime){
        this.contentTime = contentTime;
    }
    public Integer getContentTime(){
        return this.contentTime;
    }
    public void setUp(Integer up){
        this.up = up;
    }
    public Integer getUp(){
        return this.up;
    }
    public void setDown(Integer down){
        this.down = down;
    }
    public Integer getDown(){
        return this.down;
    }
    public void setArticleId(Integer articleId){
        this.articleId = articleId;
    }
    public Integer getArticleId(){
        return this.articleId;
    }
}