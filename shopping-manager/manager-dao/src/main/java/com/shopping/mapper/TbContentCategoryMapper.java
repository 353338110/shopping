package com.shopping.mapper;

import java.util.List;

import com.shopping.pojo.TbContentCategory;
import com.shopping.pojo.TbContentCategoryExample;
import org.apache.ibatis.annotations.Param;

public interface TbContentCategoryMapper {
    int countByExample(TbContentCategoryExample example);

    int deleteByExample(TbContentCategoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbContentCategory record);

    /**
     * 插入数据并且返回最后一个插入的id
     * @param record
     * @return
     */
    long insertReturnId(TbContentCategory record);

    /**
     * 插入数据并且返回最后一个插入的id
     * @param record
     * @return
     */
    long insertSelectiveReturnId(TbContentCategory record);

    int insertSelective(TbContentCategory record);

    List<TbContentCategory> selectByExample(TbContentCategoryExample example);

    TbContentCategory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbContentCategory record, @Param("example") TbContentCategoryExample example);

    int updateByExample(@Param("record") TbContentCategory record, @Param("example") TbContentCategoryExample example);

    int updateByPrimaryKeySelective(TbContentCategory record);

    int updateByPrimaryKey(TbContentCategory record);
}