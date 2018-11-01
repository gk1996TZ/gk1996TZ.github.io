package com.muck.admin.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muck.domain.CompanyManager;
import com.muck.domain.Manager;
import com.muck.excelquerytemplate.CompanyManagerExcelQueryTemplate;
import com.muck.exception.base.BizException;
import com.muck.handler.IdTypeHandler;
import com.muck.request.CompanyManagerForm;
import com.muck.response.ResponseResult;
import com.muck.response.StatusCode;
import com.muck.service.CompanyManagerService;
import com.muck.service.PropertiesService;
import com.muck.utils.UploadUtils;

/**
 * @Description: 企业管理人员Controller
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年6月20日 下午10:35:28
 */
@RestController("AdminCompanyManagerController")
@RequestMapping("/admin/companyManager")
public class CompanyManagerController extends BaseController{

	@Autowired
	CompanyManagerService companyManagerService;
	@Autowired
	private PropertiesService propertiesService;
	/**
	 * @Description: 添加企业管理人员
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年6月21日  上午9:58:17
	 * @param: companyManagerForm 传入一个需要被添加的企业管理人员数据对象
	 * @return: ResponseResult 返回添加的操作信息
	 */
	@RequestMapping("save.action")
	public ResponseResult save (CompanyManagerForm companyManagerForm){
		// 第一步：封装添加的数据
		CompanyManager companyManager = new CompanyManager();
		
		//管理人员编号
		companyManager.setCompanyManagerId(companyManagerForm.getCompanyManagerId());
		//管理人员姓名
		companyManager.setCompanyManagerName(companyManagerForm.getCompanyManagerName());
		//管理人员性别
		companyManager.setCompanyManagerSex(companyManagerForm.getCompanyManagerSex());
		//管理人员岗位
		companyManager.setCompanyManagerPost(companyManagerForm.getCompanyManagerPost());
		//管理人员联系电话
		companyManager.setCompanyManagerPhone(companyManagerForm.getCompanyManagerPhone());
		//管理人员身份证号
		companyManager.setCompanyManagerIdNumber(companyManagerForm.getCompanyManagerIdNumber());
		
		companyManagerService.save(companyManager);
		return ResponseResult.ok();
	}
	
	/**
	 * @Description: 通过id删除企业管理人员（逻辑删除）
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年6月21日  上午10:14:01
	 * @Param:id 传入一个企业管理人员id
	 * @return:ResponseResult 返回操作信息
	 */
	@RequestMapping("deleteById.action")
	public ResponseResult deleteById(String id){
		CompanyManager companyManager = companyManagerService.queryById(id);
		if(companyManager == null){
			throw new BizException(StatusCode.NOT_FOUND);
		}
		companyManagerService.deleteById(id);
		return ResponseResult.ok();
	}
	
	/**
	 * @Description: 根据id删除管理人员（真实删除）
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年6月21日  上午10:16:42
	 * @Param: id 传入一个管理人员id
	 * @return:ResponseResult 返回操作信息
	 */
	@RequestMapping("deleteByIdReal.action")
	public ResponseResult deleteByIdReal(String id){
		CompanyManager companyManager = companyManagerService.queryById(id);
		if(companyManager == null){
			throw new BizException(StatusCode.NOT_FOUND);
		}
		companyManagerService.deleteByIdReal(id);
		return ResponseResult.ok();
	}
	
	/**
	 * @Description: 根据id修改企业管理人员信息
	 * @Version:v1.0.0
	 * @Author:朱俊亮
	 * @Date:2018年6月21日  上午10:20:45
	 * @Param:companyManagerForm 传入一个企业管理人员信息
	 * @Return:ResponseResult 返回修改的操作信息
	 */
	@RequestMapping("editById.action")
	public ResponseResult editById(CompanyManagerForm companyManagerForm){
		// 第一步：封装修改的数据
		CompanyManager companyManager = new CompanyManager();
		
		//管理人员id
		companyManager.setId(companyManagerForm.getId());
		//管理人员编号
		companyManager.setCompanyManagerId(companyManagerForm.getCompanyManagerId());
		//管理人员姓名
		companyManager.setCompanyManagerName(companyManagerForm.getCompanyManagerName());
		//管理人员性别
		companyManager.setCompanyManagerSex(companyManagerForm.getCompanyManagerSex());
		//管理人员岗位
		companyManager.setCompanyManagerPost(companyManagerForm.getCompanyManagerPost());
		//管理人员联系电话
		companyManager.setCompanyManagerPhone(companyManagerForm.getCompanyManagerPhone());
		//管理人员身份证号
		companyManager.setCompanyManagerIdNumber(companyManagerForm.getCompanyManagerIdNumber());

		companyManagerService.editById(companyManager);
		return ResponseResult.ok();
	}
	
	/**
	 * @Description: 导入企业管理人员汇总
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年6月20日  下午11:13:47
	 * @Param: request 传入一个HttpServlet请求
	 * @param:Exception 抛出所有可能发生的异常
	 * @return:ResponseResult 返回操作信息
	 */
	@RequestMapping(name="importExcelSummary.action")
	public ResponseResult importExcelSummary(HttpServletRequest request)throws Exception{
		Manager manager = (Manager)request.getAttribute("user");
		if(manager == null){
			throw new BizException(StatusCode.NOT_FOUND);
		}
		//第一步：将表格数据上传到服务器上，返回存放到本地的路径
		String path = UploadUtils.uploadFile(propertiesService.getWindowsRootPath(),"companyManager", request);
		
		//第二步：读取Excel表格中的数据 
		List<Map<String, String>> exportData = companyManagerService.readExcelData(path);
		
		//第三步：将Excel表格数据转化为数据库对象数据
		List<CompanyManager> list = companyManagerService.selectDataFromExcelMapData(new CompanyManagerExcelQueryTemplate(), exportData);
		
		//第四步：封装企业负责人的操作人id
		if(list != null){
			for(CompanyManager companyManager : list){
				companyManager.setOperatorId(IdTypeHandler.decode(manager.getId()));
				companyManager.setOperatorName(manager.getManagerName());
				companyManager.setOperatorPhone(manager.getManagerPhone());
			}
		}
		//第四步：将Excel表格中的数据添加到数据库中
		companyManagerService.saveAll(list);
		return ResponseResult.ok();
	}
}
