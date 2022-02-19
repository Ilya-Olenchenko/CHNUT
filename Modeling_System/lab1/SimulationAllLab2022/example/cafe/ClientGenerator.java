package example.cafe;

import qusystem.TransactionsGenerator;

public class ClientGenerator extends TransactionsGenerator {

	/**
	 * ������ �� ������� ������� ����, ������� ����� �������������� ���
	 * ������������ ��������
	 */
	public CoffeeHouseClient original = null;

	/**
	 * ClientGenerator constructor.
	 */
	public ClientGenerator() {
		super();
	}

	/**
	 * ����� ������ ��� �������� ������ �� ������� ������� ����, ������� �����
	 * �������������� ��� ������������ ��������. Creation date: (02.03.2006
	 * 18:33:19)
	 * 
	 * @param original
	 *            rgr.transGenExample.CoffeeHouseClient
	 */
	public void setOriginal(CoffeeHouseClient original) {
		this.original = original;
	}

	/* *
	 * �������������� ����� �����������, ������������ ��� �������� ������. �����
	 * ������ ��������� ����� ������������ ������� original ��� ������ ��������
	 * ���������� ����� ���������� ������� �� ���������. ��� ��� ������ ��������
	 * "�������" �� ����� �������� ��� ����������� � ��������� ������
	 * "����������", ������� �� ������������.
	 * 
	 * @see qusystem.TransactionsGenerator#createTransaction()
	 */
	protected void createTransaction() {
		CoffeeHouseClient client = (CoffeeHouseClient) original.clone();
		client.setNameForProtocol("�볺�� � "
				+ getDispatcher().getCurrentTime());
		getDispatcher().addStartingActor(client);
	}

}
