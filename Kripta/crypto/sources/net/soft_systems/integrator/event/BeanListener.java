/* Generated by Together */

package net.soft_systems.integrator.event;
import net.soft_systems.integrator.Bean;
public interface BeanListener {
	public void addBean(Bean bean, Bean parent);
	public void delBean(Bean bean, Bean parent);
	public void beforeAddBean(Bean bean, Bean parent);
	public void afterDelBean(String beanId, Bean parentBean, Class beanClass);
}

