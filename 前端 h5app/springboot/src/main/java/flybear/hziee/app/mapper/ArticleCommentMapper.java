package flybear.hziee.app.mapper;


import flybear.hziee.app.model.ArticleComment;
import java.util.List;

public interface ArticleCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ArticleComment record);

    ArticleComment selectByPrimaryKey(Integer id);

    List<ArticleComment> selectAll();

    int updateByPrimaryKey(ArticleComment record);
    
}