/**
 * 
 */
package example.competition;

import rnd.Randomable;

/**
 * @author pgbiv
 * ������ ��������� ���������� ���������� � ���������� ����� ����������
 */
public interface IVisualPart {


	/**
	 * 
	 * @return ���������� ������� ��� ������
	 */
	public int nTask();

/**
	 * 
	 * @return ����������  ������
	 */
	public int nTeam();

	/**
	 * 
	 * @return ���������� ������� � �������
	 */
	public int nTeamMember();

	/**
	 * 
	 * @return ��������� ��������� �����
	 */
	public Randomable random();
	public void drawGamerTaskReady(ITeamMember member, double taskTime);
	public void drawComleteStage(ITeam team, int taskNumber, double taskTime);
	public void printTeamResult(ITeam team);
}
