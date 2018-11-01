package com.muck.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.muck.domain.DisposalMuckTurnover;

/**
 * @Description: 处置场进出渣土Mapper
 * @version: v1.0.0
 * @author 朱俊亮
 * @date 2018年5月7日 下午3:19:11
 */
@Repository
public interface DisposalMuckMapper extends BaseMapper<DisposalMuckTurnover>{

	/**
	 * @Description: 查询指定条数的进出渣土数据
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月7日 下午4:26:09
	 * @param: disposalId 传入处置场id
	 * @param: count 传入查询的数据的条数
	 * @return: List<DisposalMuckTurnover> 返回含有指定处置场指定数量的进出渣土数据的列表
	 */
	public List<DisposalMuckTurnover> queryDisposalMuckTurnover(@Param("disposalId")String disposalId,@Param("count")Integer count);
}
