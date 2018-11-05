package com.shopping.mapper;

import com.shopping.pojo.TbItem;
import com.shopping.pojo.TbItemExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TbItemMapper {
    int countByExample(TbItemExample example);

    int deleteByExample(TbItemExample example);

    int deleteByPrimaryKey(Long id);

    /**
     * 根据主键批量删除
     * @param ids
     * @return
     */
    int deleteByMultiPrimaryKey(@Param("list") List ids);

    /**
     * 根据主键批量下架
     * @param ids
     * @return
     */
    int instockMultiByPrimaryKey(@Param("list") List ids);

    /**
     * 根据主键批量上架
     * @param ids
     * @return
     */
    int reshelfMultiByPrimaryKey(@Param("list") List ids);

    int insert(TbItem record);

    int insertSelective(TbItem record);

    List<TbItem> selectByExample(TbItemExample example);

    TbItem selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbItem record, @Param("example") TbItemExample example);

    int updateByExample(@Param("record") TbItem record, @Param("example") TbItemExample example);

    int updateByPrimaryKeySelective(TbItem record);

    int updateByPrimaryKey(TbItem record);
}