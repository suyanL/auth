package com.ddup.auth.service.base;

import com.ddup.auth.service.constants.Constants;
import com.ddup.auth.service.constants.MeiteBeanUtils;
import lombok.Data;

/**
 * @author suancyg
 */
@Data
public class BaseApiService<T> {

	public BaseResponse<T> setResultError(Integer code, String msg) {
		return setResult(code, msg, null);
	}

	/**
	 * 返回错误，可以传msg
	 */
	public BaseResponse<T> setResultError(String msg) {
		return setResult(Constants.HTTP_RES_CODE_500, msg, null);
	}

	/***
	 * 返回成功，可以传data值
	 */
	public BaseResponse<T> setResultSuccess(T data) {
		return setResult(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_200_VALUE,
				data);
	}

	/**
	 * 返回成功，沒有data值
	 */
	public BaseResponse<T> setResultSuccess() {
		return setResult(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_200_VALUE,
				null);
	}

	/**
	 * 通用封装 通用封装
	 * @param code 响应码
	 * @param msg 相应信息
	 * @param data 相应数据
	 * @return 返回体
	 */

	public BaseResponse<T> setResult(Integer code, String msg, T data) {
		return new BaseResponse<T>(code, msg, data);
	}

	/**
	 * dto 转换do
	 * @param dtoEntity 转换对象
	 * @param doClass 目标对象
	 * @param <Do> 转换类型
	 * @return 响应
	 */
	public static <Do> Do dtoToDo(Object dtoEntity, Class<Do> doClass) {
		return MeiteBeanUtils.dtoToDo(dtoEntity, doClass);
	}

	/**
	 * do转换成dto
	 * @param doEntity 抓换对象
	 * @param dtoClass 目标对象class
	 * @param <Dto> DTO
	 * @return 返回值
	 */
	public static <Dto> Dto doToDto(Object doEntity, Class<Dto> dtoClass) {
		return MeiteBeanUtils.doToDto(doEntity, dtoClass);
	}

	public BaseResponse<T> setResultDb(int dbCount, T successMsg, String errorMsg) {
		return dbCount > 0 ? setResultSuccess(successMsg) : setResultError(errorMsg);
	}

}