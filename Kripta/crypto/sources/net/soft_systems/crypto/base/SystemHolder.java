/* Generated by Together */

package net.soft_systems.crypto.base;
import java.util.Vector;
import net.soft_systems.integrator.*;
public class SystemHolder {
	protected Bean rootBean;
	protected SystemHolder() { }
	static public SystemHolder getNewSystemHolder(Bean rootBean) {
		SystemHolder sh = new SystemHolder();
		sh.init(rootBean);
		return sh;
	}
	public void init(Bean rootBean) {
		if (rootBean != null) {
			this.rootBean = rootBean;
			try {
				subjects = BeanUtil.getBeanById(rootBean, "subjects").getChildBeans();
				resources = BeanUtil.getBeanById(rootBean, "resources").getChildBeans();
				threats = BeanUtil.getBeanById(rootBean, "threats").getChildBeans();
				vulnerabilities = BeanUtil.getBeanById(rootBean, "vulnerabilities").getChildBeans();
				protections = BeanUtil.getBeanById(rootBean, "protections").getChildBeans();
				boundaries = BeanUtil.getBeanById(rootBean, "boundaries").getChildBeans();
				relations = BeanUtil.getBeanById(rootBean, "relations").getChildBeans();
			}
			catch (NullPointerException ex) {
				Debug.critical("?????? ? ????????. ?? ??????? ????????? ?????????");
				ex.printStackTrace();
			}
		}
		else { Debug.critical("?????? ? ???????? ?????????"); }
	}
	private Vector subjects;
	private Vector resources;
	private Vector threats;
	private Vector vulnerabilities;
	private Vector protections;
	private Vector boundaries;
	private Vector relations;
	public Vector getSubjects() { return subjects; }
	public Vector getResources() { return resources; }
	public Vector getThreats() { return threats; }
	public Vector getVulnerabilities() { return vulnerabilities; }
	public Vector getProtections() { return protections; }
	public Vector getBoundaries() { return boundaries; }
	public Vector getRelations() { return relations; }
	public Bean getVulnerabilityTopic() { return BeanUtil.getBeanById(rootBean, "vulnerabilities"); }
	public Bean getRelationsTopic() { return BeanUtil.getBeanById(rootBean, "relations"); }
	public Bean getBoundaryTopic() { return BeanUtil.getBeanById(rootBean, "boundaries"); }
}

