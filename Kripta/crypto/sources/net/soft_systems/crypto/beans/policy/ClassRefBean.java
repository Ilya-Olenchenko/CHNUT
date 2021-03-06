/* Generated by Together */

package net.soft_systems.crypto.beans.policy;
import net.soft_systems.integrator.*;
import org.w3c.dom.Element;
public class ClassRefBean extends LeafBean {
	public ClassRefBean() { }
	public ClassRefBean(String className, String id) {
		this.id = id;
		this.className = className;
	}
	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
	public String getName() { return getId(); }
	public void setName(String name) { this.id = name; }
	public String getClassName() { return className; }
	public void setClassName(String className) { this.className = className; }
	public Class getClassRef() throws ClassNotFoundException { return Class.forName(className); }
	public void store(Element beanElement, BeanConfig config) {
		super.store(beanElement, config);
		config.setAttribute(beanElement, "class-ref", className);
	}
	public void load(Element beanElement, BeanConfig config) {
		super.load(beanElement, config);
		className = config.getAttribute(beanElement, "class-ref");
	}
	protected String id;
	private String className;
}

