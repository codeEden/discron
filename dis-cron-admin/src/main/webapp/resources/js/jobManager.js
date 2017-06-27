initTab(1);
function initTab(page){
	var reqeustParameters= new Object();
	reqeustParameters.pageIndex=page;
	var parameter = JSON.stringify(reqeustParameters);
	
	$.ajax({
		url : '/jobmanager/list',
		type : 'post',
		data : parameter,
		dataType : 'json',
		contentType : "application/json",
		dataType : 'json',
		success : function(results) {
			var resultcode = results.resultcode;
			if (resultcode == false || resultcode == "false") {
				alert("加载失败！！");
			} else {
				showTableData(results);
				showPage(results);
			}
		},
		error : function() {
			alert("请求失败");
		}
	});
}

function showTableData(results){
	var list = results.data.resultList;
	var trs = "";
	var startIndex = (results.data.pageInfo.pageIndex - 1)
				* results.data.pageInfo.pageSize;
	for (var i = 0; i < list.length; i++) {
		var index = startIndex + i + 1;
		var item = list[i];
		var status=item.status;
		var statusStr=getStatusStr(status);
		trs = trs + "<tr><td class='text-center'>" + index + "</td>";
		
//		<td>序号</td>
//        <td style='width:300px;'>job名称</td>
//        <td>cron</td>
//        <td>超时时间</td>
//        <td>任务类型</td>
//        <td>url</td>
//        <td>最后执行时间</td>
//        <td>当前处理机器</td>
//        <td>状态</td>
//        <td>创建时间</td>
		
		trs = trs
				+ "<td class='text-center'>"
				+ item.jobName
				+ "</td><td class='text-left'>"
				+ item.cron
				+ "</td>"
				+ "<td class='text-center'>"
				+ item.timeout
				+ "</td>"
				+ "<td class='text-center'>"
				+ item.type
				+ "</td>"
				+ "<td class='text-center'>"
				+ item.url
				+ "</td><td class='text-center'>"
				+ item.lastExeTime
				+ "</td><td class='text-center'>"
				+item.handleHost
				+ "</td><td class='text-center'>"
				+statusStr
				+ "</td><td class='text-center'>"
				+item.createTime
				+ "</td></tr>";
	}
	$("#tbody").html(trs);
}

function getStatusStr(status){
	var statusStr="";
	if(status==1){
		statusStr="禁用";
	}else if(status==0){
		statusStr="正常";
	}
	return statusStr;
}

function showPage(results){
	if (results.data.pageInfo.rowCount == 0) {
		results.data.pageInfo.pageCount = 1;
		$('#totalPage').html(0);
	} else {
		$('#totalPage').html(results.data.pageInfo.pageCount);
	}
	$('#totalRows').html(results.data.pageInfo.rowCount);

	$('#paginationReal').twbsPagination({
		totalPages : results.data.pageInfo.pageCount,
		visiblePages : 10,
		startPage : results.data.pageInfo.pageIndex,
		//	        first : "第一页",
		//	        prev :"上一页",
		//	        next :"下一页",
		//	        last :"最后一页",
		onPageClick : function(event, page) {
			initTab(page);
		}
	});
}
