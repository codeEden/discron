/**
 * 
 */
package com.nt.open.discron.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nt.open.discron.common.util.CommonStatusCode;
import com.nt.open.discron.common.util.Result;
import com.nt.open.discron.entity.po.JobPO;
import com.nt.open.discron.service.JobManagerService;
import com.nt.open.discron.vo.JobPageResult;
import com.nt.open.discron.vo.RequestVO;

/**
 * @author bjfulianqiu
 *
 */
@Controller
@RequestMapping("/jobmanager")
public class JobMangerController {

	Logger logger = LoggerFactory.getLogger(JobMangerController.class);
	
	@Resource
	JobManagerService jobManagerService;
	 /**
     * 
     * (跳转到微信消息列表页) 
     *
     * @param req
     * @param resp
     * @throws Exception
     */
    @RequestMapping(value="/toIndex", method=RequestMethod.GET)
    public String toList(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
    	logger.info("toIndex  JobManager ");
    	return "/templates/layout/JobManager";
    }
    
    /**
     * 
     * (job列表) 
     *
     * @param req
     * @param resp
     * @throws Exception
     */
    @RequestMapping(value="/list", method=RequestMethod.POST)
    @ResponseBody
    public Result getList(@RequestBody RequestVO requestVO,HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
    	Result result=new Result();
    	
    	try{
    		logger.info("pageSize=={},,pageIndex=={}",requestVO.getPageSize(),requestVO.getPageIndex());
    		JobPageResult jobPageResult=jobManagerService.list(requestVO);
    		result.setData(jobPageResult);
    		result.setResultcode(CommonStatusCode.StatusCode.OK);
    	}catch(Exception e){
    		result.setResultcode(CommonStatusCode.StatusCode.SERVER_ERROR);
    		e.printStackTrace();
    	}
    	
    	return result;
    	
    }
}
