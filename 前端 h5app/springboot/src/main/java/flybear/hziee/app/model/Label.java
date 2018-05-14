package flybear.hziee.app.model;

import java.util.List;

public class Label {

	private Integer id;
	private String label;

    public void setId(Integer id){
        this.id = id;
    }
    public Integer getId(){
        return this.id;
    }
    public void setLabel(String label){
        this.label = label;
    }
    public String getLabel(){
        return this.label;
    }
}