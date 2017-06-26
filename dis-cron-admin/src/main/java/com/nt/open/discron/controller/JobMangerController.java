/**
 * 
 */
package com.nt.open.discron.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author bjfulianqiu
 *
 */
@Controller
@RequestMapping("/jobmanager")
public class JobMangerController {

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
    	return "/templates/layout/showAll";
    }
}
