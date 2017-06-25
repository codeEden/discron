/**
 * 
 */
package com.nt.open.spring.custom;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author fulianqiu
 *
 */
public class DiscronNamespaceHandler extends NamespaceHandlerSupport {

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.xml.NamespaceHandler#init()
	 */
	@Override
	public void init() {
		registerBeanDefinitionParser("init", new DiscronBeanDefinitionParser());
	}

}
