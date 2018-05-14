package flybear.hziee.app.model;

import java.util.List;

public class Article {

	private Integer id;
	private String articleTitle;
	private Integer releaseTime;
	private Integer editTime;
	private String author;
	private Integer plate;
	private Integer level;
	private String articleContent;

    public void setId(Integer id){
        this.id = id;
    }
    public Integer getId(){
        return this.id;
    }
    public void setArticleTitle(String articleTitle){
        this.articleTitle = articleTitle;
    }
    public String getArticleTitle(){
        return this.articleTitle;
    }
    public void setReleaseTime(Integer releaseTime){
        this.releaseTime = releaseTime;
    }
    public Integer getReleaseTime(){
        return this.releaseTime;
    }
    public void setEditTime(Integer editTime){
        this.editTime = editTime;
    }
    public Integer getEditTime(){
        return this.editTime;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public String getAuthor(){
        return this.author;
    }
    public void setPlate(int plate){
        this.plate = plate;
    }
    public int getPlate(){
        return this.plate;
    }
    public void setLevel(Integer level){
        this.level = level;
    }
    public Integer getLevel(){
        return this.level;
    }
    public void setArticleContent(String articleContent){
        this.articleContent = articleContent;
    }
    public String getArticleContent(){
        return this.articleContent;
    }
}