/**
 * 
 */
package com.nt.open.spring.custom;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

import com.nt.open.discron.run.JobMain;
import com.nt.open.spring.vo.Discron;

/**
 * @author fulianqiu
 *
 */
public class DiscronBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

	// Element 对应的类  
	 protected Class<Discron> getBeanClass(Element element) {  
		 return Discron.class;  
	 } 
	 
	 @Override
	protected void doParse(Element element, BeanDefinitionBuilder builder) {
		String id=element.getAttribute("id");
		if(id!=null){
			builder.addPropertyValue("id", id);
		}
		//开始执行任务
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				JobMain.start();
			}
		}).start();
		
	}
}
