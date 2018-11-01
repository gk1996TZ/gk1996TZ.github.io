package com.muck.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muck.annotation.Logger;
import com.muck.domain.CompanyMember;
import com.muck.domain.Manager;
import com.muck.excelquerytemplate.CompanyMemberExcelQueryTemplate;
import com.muck.exception.base.BizException;
import com.muck.handler.IdTypeHandler;
import com.muck.request.CompanyMemberForm;
import com.muck.response.ResponseResult;
import com.muck.response.StatusCode;
import com.muck.service.CompanyMemberService;
import com.muck.service.PropertiesService;
import com.muck.utils.UploadUtils;

/**
 * @Description: 企业人员导入Controller
 * @version: v1.0.0
 * @author: 甘坤
 */
@RestController("adminCompanyMemberController")
@RequestMapping("/admin/companyMember/")
public class CompanyMemberController {

	@Autowired
	private CompanyMemberService companyMemberService;
	@Autowired
	private PropertiesService propertiesService;
	
	@RequestMapping(value = "save.action", method = RequestMethod.POST)
	@Logger(operatorDesc = "添加企业相关人员", operatorModel = "添加人员")
	public ResponseResult save(CompanyMemberForm companyMemberForm) {

			//开始封装数据
			CompanyMember companyMember=new CompanyMember();
			companyMember.setAtWhereAndJob(companyMemberForm.getAtWhereAndJob());
			companyMember.setCompanyManageYear(companyMemberForm.getCompanyManageYear());
			companyMember.setCultureDegree(companyMemberForm.getCultureDegree());
			companyMember.setFromWhenToWhen(companyMemberForm.getFromWhenToWhen());
			companyMember.setGraduateInfo(companyMemberForm.getGraduateInfo());
			companyMember.setImageUrl(companyMemberForm.getImageUrl());
			companyMember.setJobName(companyMemberForm.getJobName());
			companyMember.setMemberBirth(companyMemberForm.getMemberBirth());
			companyMember.setMemberName(companyMemberForm.getMemberName());
			companyMember.setMemberSex(companyMemberForm.getMemberSex());
			companyMember.setMemberType(companyMemberForm.getType());
				companyMemberService.save(companyMember);
				return ResponseResult.ok();
	}


	@RequestMapping(value="importExcelData.action",method=RequestMethod.POST)
	public ResponseResult importExcelData(HttpServletRequest request){
		Manager manager = (Manager) request.getSession().getAttribute("manager");
		if(manager == null){
			throw new BizException(StatusCode.MANAGER_LOGIN_RESTART);
		}
		int type=0;
		String strType=request.getParameter("type");
		if(strType!=null&&!strType.equals("")){
			 type=Integer.parseInt(strType);
		}
		// 第一步：将表格数据上传到服务器上，返回存放到本地的路径
		String path = UploadUtils.uploadFile(propertiesService.getWindowsRootPath(),"companyMember", request);
		CompanyMember companyMember = companyMemberService.readExcelData(new CompanyMemberExcelQueryTemplate(), path,type);

		// 封装企业负责人的操作人id
		if (companyMember != null) {
			companyMember.setOperatorId(IdTypeHandler.decode(manager.getId()));
			companyMember.setOperatorName(manager.getManagerName());
			companyMember.setOperatorPhone(manager.getManagerPhone());
		}
		companyMemberService.save(companyMember);
		return ResponseResult.ok();
	}

}
