$(function(){
	
	var siteForm = {};
	siteForm.siteName = '滨湖印象2';
	siteForm.siteAddress = "东阳街与海棠路交叉口";
	siteForm.areaId = '1k705';
	siteForm.companyIds = ['b8r5'];
	siteForm.memo = '这个是喜洋洋的工地，是负责修地铁的';
	siteForm.siteCleanerName = '阿羊';
	siteForm.siteCleanerPhone = '120';
	
	var siteManagers = [];
	siteManagers.push({siteManager:'狗爸爸',siteManagerPhone:'182119001'});
	siteManagers.push({siteManager:'狗妈妈',siteManagerPhone:'182119001'});
	siteForm.siteManagers = siteManagers;
	
	siteForm.deviceCodes = ['1000051','1000052'];
	
	
	console.info(JSON.stringify(siteForm));
	
	
	$.ajax({
		 url:'http://localhost:9999/admin/site/save.action',
	        type:'POST',
	        data : JSON.stringify(siteForm),
	        dataType:"json",
	        contentType:"application/json;charset=utf-8",
	        success:function(result){
	        	console.info(result);
	        }
	});
});
