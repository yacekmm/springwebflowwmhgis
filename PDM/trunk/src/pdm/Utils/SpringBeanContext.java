package pdm.Utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringBeanContext implements ApplicationContextAware {

	private ApplicationContext applicationContext;
	
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		applicationContext = arg0;
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List getSpringBeans(String match, ApplicationContext applicationContext) {
		List l = new ArrayList();
		String[] beanArray = applicationContext.getBeanDefinitionNames();
		for (int j=0; j<beanArray.length; j++) {
			if (isMatch(beanArray[j], match)) {
				l.add(beanArray[j]);
			}
		}
		return l;
	}

	public static boolean isMatch(String beanName, String mappedName) {
		return (mappedName.equals(beanName)) ||
			(mappedName.endsWith("*") && beanName.startsWith(mappedName.substring(0, mappedName.length() - 1))) ||
			(mappedName.startsWith("*") && beanName.endsWith(mappedName.substring(1, mappedName.length())));
	}

}
