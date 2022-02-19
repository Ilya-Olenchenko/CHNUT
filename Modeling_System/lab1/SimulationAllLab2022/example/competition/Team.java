package example.competition;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BooleanSupplier;

import process.Actor;
import rnd.Randomable;

/**
 * @author P.Byvoino 24.01.2008 17:07:28
 * 
 */
public class Team extends Actor {
	/**
	 * ������ �� ��������� ������������
	 */
	private VisualPart ui = null;

	// ����� �������
	private int teamNumber;

	// ���� �������
	private Color teamColor;

	// ������ ���������� �������
	private List<TeamMember> memberList = new ArrayList<TeamMember>();

	private int taskNumber;

	public Team() {
		super();
	}

	protected void rule() {

		// ������� ������� �������
		int nMember = ui.nTeamMember();
		for (int i = 1; i <= nMember; i++) {
			TeamMember member = new TeamMember();
			member.setTeam(this);
			member.setUI(ui);
			memberList.add(member);
			getDispatcher().addStartingActor((Actor) member);
		}

		BooleanSupplier allMembersReady = () -> {
			Iterator iter = memberList.iterator();
			while (iter.hasNext()) {
				TeamMember member = (TeamMember) iter.next();
				if (member.getN() < taskNumber)
					return false;
			}
			return true;
		};
		// ���� ���������� �������
		int nTask = ui.nTask();
		Randomable rnd = ui.random();
		for (int i = 1; i <= nTask; i++) {
			taskNumber = i;
			try {
				waitForCondition(allMembersReady,
						"���� �� ����� ������ �� ��������� " + taskNumber
								+ "-� ��������");
			} catch (Exception e) {
				return;
			}
			// �������� �� ������ � ������������ ������
			double taskTime = rnd.next();
			holdForTime(taskTime);
			// ���������� ������ �������, ��� ���� ��������
			for (Iterator iter = memberList.iterator(); iter.hasNext();) {
				TeamMember member = (TeamMember) iter.next();
				member.setCompleteStage(true);
			}
		}
		// ����� ���������� ������������ ��� �������
		ui.printTeamResult(this);
	}

	public void setUI(VisualPart ui) {
		this.ui = ui;
	}

	public void setTeamColor(Color color) {
		this.teamColor = color;
	}

	public Color getTeamColor() {
		return teamColor;
	}

	public void setTeamNumber(int teamNumber) {
		this.teamNumber = teamNumber;
	}

	/*
	 * @see example.competition.ITeam#getTeamNumber()
	 */
	public int getTeamNumber() {
		return teamNumber;
	}

	public String getNameForProtocol() {
		return "�������" + getTeamNumber();
	}

	public List<TeamMember> getMemberList() {
		return memberList;
	}

}
