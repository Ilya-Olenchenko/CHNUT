/* Generated by Together */

package net.soft_systems.crypto.beans.policy;
import java.beans.PropertyVetoException;
import java.util.*;
import net.soft_systems.crypto.Run;
import net.soft_systems.crypto.base.SystemHolder;
import net.soft_systems.crypto.event.SystemChangeListener;
import net.soft_systems.crypto.frames.policy.RightMatrixFrame;
import net.soft_systems.crypto.util.Matrix;
import net.soft_systems.integrator.*;
import org.w3c.dom.*;
public class RightMatrixBean extends LeafBean implements EditableBean {
	/**
	 * Returns Rights while accessing from subject to object
	 * @param subjIndex Index of subject in vector <code>subjects</code>
	 * @param objIndex Index of object in vector <code>objects</code>
	 * @return Rights while accessing from subject to object
	 * @see RightMatrixBean.getSubjects()
	 * @see getObjects()
	 */
	public Vector getRights(int subjIndex, int objIndex) {
		return (Vector)rightMatrix.getValue(subjIndex, objIndex);
	}
	/**
	 * Returns Rights while accessing from subject to object
	 * @param subject Subject
	 * @param object Object
	 * @return Rights while accessing from subject to object
	 * @see getSubjects(), getObjects()
	 */
	public Vector getRights(Bean subject, Bean object) {
		int objIndex = objects.indexOf(object);
		int subjIndex = subjects.indexOf(subject);
		if (objIndex >= 0 && subjIndex >= 0) { return (Vector)rightMatrix.getValue(subjIndex, objIndex); }
		else { return null; }
	}
	static private SystemHolder systemInfo;
	static protected SystemHolder getSystemInfo(BeanConfig config) {
		if (systemInfo == null) { systemInfo = SystemHolder.getNewSystemHolder(config.getRootBean()); }
		return systemInfo;
	}
	/**
	 * Returns string representing access rights from Subject to Object
	 * @param subjIndex Index of subject in vector <code>subjects</code>
	 * @param objIndex Index of object in vector <code>objects</code>
	 */
	public String getRightsString(int subjIndex, int objIndex) {
		Vector rights = (Vector)rightMatrix.getValue(subjIndex, objIndex);
		String rightString = "{ ";
		if (rights != null) {
			Enumeration en = rights.elements();
			RightBean obj;
			while (en.hasMoreElements()) {
				obj = (RightBean)en.nextElement();
				rightString += obj.getId();
				if (en.hasMoreElements()) { rightString += " "; }
			}
			rightString += " }";
		}
		return rightString;
	}
	protected Vector objects;
	protected Vector subjects;
	/**
	 * @return Vector with objects. This vector contains resources and subjects
	 */
	public Vector getObjects() { return this.objects; }
	/**
	 * @return Vector with subjects -
	 */
	public Vector getSubjects() { return this.subjects; }
	/**
	 * Creates empty matrix with no rights
	 * @param subjects Vector of subjects
	 * @param resources Vector of shares (resources)
	 */
	protected Matrix initMatrix(Vector subjects, Vector resources) {
		Matrix rightMatrix = new Matrix(subjects.size(), resources.size() + subjects.size());
		for (int iSubject = 0; iSubject < subjects.size(); iSubject++) {
			for (int iResource = 0; iResource < resources.size(); iResource++) {
				rightMatrix.setValue(iSubject, iResource, new Vector());
			}
			for (int iSubjectCol = 0; iSubjectCol < subjects.size(); iSubjectCol++) {
				rightMatrix.setValue(iSubject, resources.size() + iSubjectCol, new Vector());
			}
		}
		return rightMatrix;
	}
	/**
	 * Checks validity of access rights using vector of available rights.
	 * Deletes incorrect (deleted) rights from vector <code>rights</code>
	 * @param rights Vector of access rights.
	 * @param availRights Vector of availiable rights
	 */
	public void validateRights(Vector rights, Vector availRights) {
		Enumeration en = rights.elements();
		RightBean obj;
		while (en.hasMoreElements()) {
			obj = (RightBean)en.nextElement();
			if (!availRights.contains(obj)) { rights.remove(obj); }
		}
	}
	/**
	 * Checks validity of access all rights in matrix using vector of available rights.
	 * Deletes incorrect (deleted) rights
	 * @param rights Vector of access rights.
	 * @param availRights Vector of availiable rights
	 */
	public void validateAllRights(Vector availRights) {
		for (int iSubject = 0; iSubject < subjects.size(); iSubject++) {
			for (int iObject = 0; iObject < objects.size(); iObject++) {
				Vector rights = getRights(iSubject, iObject);
				if (rights != null) { validateRights(rights, availRights); }
			}
		}
	}
	/**
	 * Updates subjects, objects vectors and rights matrix. Must be called after changing subjects or objects
	 * @param subjects Vector of subjects
	 * @param resources Vector of shares (resources)
	 */
	public void updateMatrix(Vector subjects, Vector resources) {
		Matrix newMatrix = initMatrix(subjects, resources);
		Bean object, subject;
		Vector rights;
		for (int iSubject = 0; iSubject < subjects.size(); iSubject++) {
			subject = (Bean)subjects.elementAt(iSubject);
			for (int iResource = 0; iResource < resources.size(); iResource++) {
				object = (Bean)resources.elementAt(iResource);
				rights = getRights(subject, object);
				if (rights != null) { newMatrix.setValue(iSubject, iResource, rights); }
			}
			for (int iSubjectCol = 0; iSubjectCol < subjects.size(); iSubjectCol++) {
				object = (Bean)subjects.elementAt(iSubjectCol);
				rights = getRights(subject, object);
				if (rights != null) { newMatrix.setValue(iSubject, resources.size() + iSubjectCol, rights); }
			}
		}
		rightMatrix = newMatrix;
		setElements(subjects, resources);
	}
	protected Matrix rightMatrix;
	SystemChangeListener systemChangeListener;
	public RightMatrixBean(Vector subjects, Vector resources) {
		id = "right-matrix";
		name = "������� ���� �������";
		rightMatrix = initMatrix(subjects, resources);
		setElements(subjects, resources);
		systemChangeListener = createChangeListener();
		Run.infoSystem.addSystemChangeListener(systemChangeListener);
	}
	/**
	 * Constructor which is used for loading matrix from config. Need to call
	 * <code>load</code> after constructing
	 */
	public RightMatrixBean() {
		id = "right-matrix";
		name = "������� ���� �������";
		rightMatrix = null;
	}
	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public void store(Element beanElement, BeanConfig config) {
		super.store(beanElement, config);
		Element rightsElem, rightElem;
		Bean subject, object;
		RightBean right;
		Enumeration en;
		for (int subj = 0; subj < subjects.size(); subj++) {
			for (int obj = 0; obj < objects.size(); obj++) {
				Vector rights = (Vector)rightMatrix.getValue(subj, obj);
				if (rights != null && rights.size() > 0) {
					rightsElem = config.addElement(beanElement, "access-rights");
					subject = (Bean)subjects.elementAt(subj);
					object = (Bean)objects.elementAt(obj);
					config.setAttribute(rightsElem, "subject", subject.getId());
					config.setAttribute(rightsElem, "object", object.getId());
					en = rights.elements();
					while (en.hasMoreElements()) {
						right = (RightBean)en.nextElement();
						rightElem = config.addElement(rightsElem, "right");
						config.setElementValue(rightElem, right.getId());
					}
				}
			}
		}
	}
	/**
	 * Sets policy subjects and objects. Does not update right matrix. In order to update rights matrix it's
	 * nessesary to invoke updateMatrix, which calls <code>setElements</code>
	 * @param subjects Vector of subjects
	 * @param resources Vector of shares (resources)
	 */
	protected void setElements(Vector subjects, Vector resources) {
		this.subjects = (Vector)subjects.clone();
		this.objects = new Vector();
		this.objects.addAll(resources);
		this.objects.addAll(subjects);
	}
	public void load(Element beanElement, BeanConfig config) {
		super.load(beanElement, config);
		rightMatrix = initMatrix(getSystemInfo(config).getSubjects(), getSystemInfo(config).getResources());
		setElements(getSystemInfo(config).getSubjects(), getSystemInfo(config).getResources());
		Bean object, subject, right;
		String objectId, subjectId, rightId;
		int objectIndex, subjectIndex;
		Element rightsElem, rightElem;
		Vector availRights = BeanUtil.getBeanById(config.getRootBean(), "rights").getChildBeans();
		for (Node obj = beanElement.getFirstChild(); obj != null; obj = obj.getNextSibling()) {
			if ((obj.getNodeType() == Node.ELEMENT_NODE) && obj.getNodeName() == "access-rights") {
				rightsElem = (Element)obj;
				objectId = config.getAttribute(rightsElem, "object");
				subjectId = config.getAttribute(rightsElem, "subject");
				object = BeanUtil.getBeanById(objects, objectId);
				subject = BeanUtil.getBeanById(subjects, subjectId);
				if (object != null && subject != null) {
					objectIndex = objects.indexOf(object);
					subjectIndex = subjects.indexOf(subject);
					Vector rights = (Vector)rightMatrix.getValue(subjectIndex, objectIndex);
					for (Node obj1 = rightsElem.getFirstChild(); obj1 != null; obj1 = obj1.getNextSibling()) {
						if ((obj1.getNodeType() == Node.ELEMENT_NODE) && obj1.getNodeName() == "right") {
							rightElem = (Element)obj1;
							rightId = config.getElementValue(rightElem).trim();
							right = BeanUtil.getBeanById(availRights, rightId);
							if (right != null) { rights.add(right); }
							else { Debug.error("�� ������� ����� " + rightId + " ����� ������� ���� �������"); }
						}
					}
				}
				else {
					Debug.error("�� ������� ���� �������-������: " + subjectId + "-" + objectId + " �� ���� �������");
				}
			}
		}
		systemChangeListener = createChangeListener();
		Run.infoSystem.addSystemChangeListener(systemChangeListener);
	}
	protected String id;
	protected String name;
	public void closeEditFrame() {
		if (editFrame != null) {
			try { editFrame.setClosed(true); }
			catch (PropertyVetoException ex) { Debug.warning("PropertyVetoException :" + ex.getMessage()); }
			editFrame = null;
		}
	}
	public EditFrame getEditFrame() {
		if (editFrame == null) { editFrame = new RightMatrixFrame(this); }
		return editFrame;
	}
	private EditFrame editFrame;
	public SystemChangeListener createChangeListener() {
		return systemChangeListener = new SystemChangeListener() {
			public void elementsChanged(Vector subjects, Vector resources) { updateMatrix(subjects, resources); }
			public void rightsChanged(Vector availRights) { validateAllRights(availRights); }
		};
	}
	protected void finalize() throws Throwable {
		Run.infoSystem.removeSystemChangeListener(systemChangeListener);
		super.finalize();
	}
}
