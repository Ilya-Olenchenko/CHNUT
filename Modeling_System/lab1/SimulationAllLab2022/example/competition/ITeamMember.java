/**
 * 
 */
package example.competition;

/**
 * @author P.Byvoino 01.01.2002 0:06:23
 * 
 */
public interface ITeamMember {
	
	/**
	 * @return int
	 * ���������� �������, ��������� ������ �������
	 */
	public int getN();
	/**
	 * @return int
	 * ����� ����� �������
	 */
	public int getNumber();
	/**
	 * @return ITeam
	 * ���������� ������ �� ���� �������
	 */
	public ITeam getTeam();

	/**
	 * @param ITeam 
	 * ����� �� �������, ������� ����������� ������
	 */
	public void setTeam(ITeam team);
	

	public void setCompleteStage(boolean b);

	/**
	 * @param IVisualPart 
	 * ����� �� ��������� ������������
	 */
	public void setUI(IVisualPart ui);

}
