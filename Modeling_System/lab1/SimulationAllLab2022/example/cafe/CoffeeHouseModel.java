package example.cafe;

import process.Dispatcher;
import process.QueueForTransactions;
import stat.DiscretHisto;

public class CoffeeHouseModel {

	private Dispatcher dispatcher = null;  //  @jve:decl-index=0:visual-constraint="114,284"
	private CoffeeHouseClient coffeeHouseClient = null;  //  @jve:decl-index=0:visual-constraint="106,89"
	private ClientGenerator clientGenerator = null;  //  @jve:decl-index=0:visual-constraint="112,8"
	private QueueForTransactions cafe = null;  //  @jve:decl-index=0:visual-constraint="303,9"
	private DiscretHisto discretHisto = null;  //  @jve:decl-index=0:visual-constraint="273,87"
	private ICoffeeHouseUI ui= null;
	
	public void setUi(ICoffeeHouseUI ui) {
		this.ui = ui;
	}

	public CoffeeHouseModel() {
		super();
	}

	/**
	 * This method ���������� ������ �� dispatcher
	 * ������ ������ ���� �������� ������ �����	
	 * 	
	 * @return process.Dispatcher	
	 */
	public Dispatcher getDispatcher() {
		if (dispatcher == null) {
			System.out.println("� ������ �� �������� ������ �� ����������");
		}
		return dispatcher;
	}

	/**
	 * This method initializes coffeeHouseClient
	 * ���������� ���������� �������� �������.
	 * ��� �������� ������������� ��� ����� � ������������,
	 * ������� �� ����� ��������	
	 * 	
	 * @return qusystem.transGenExample.CoffeeHouseClient	
	 */
	private CoffeeHouseClient getCoffeeHouseClient() {
		if (coffeeHouseClient == null) {
			coffeeHouseClient = new CoffeeHouseClient();
			coffeeHouseClient.setCafe(getCafe());
			coffeeHouseClient.setRandom(getUi().getRndClient());
		}
		return coffeeHouseClient;
	}

	/**
	 * This method initializes clientGenerator	
 	 * ���������� ���������� �������� �������.
	 * ��� �������� ������������� ��� ����� � ������������,
	 * ������� �� ����� ��������	
 	
	 * @return qusystem.transGenExample.ClientGenerator	
	 */
	private ClientGenerator getClientGenerator() {
		if (clientGenerator == null) {
			clientGenerator = new ClientGenerator();
			clientGenerator.setNameForProtocol("��������� ��������");
			clientGenerator.setOriginal(getCoffeeHouseClient());
			clientGenerator.setOutputQueue(getCafe());
			clientGenerator.setRandom(getUi().getRndGen());
		}
		return clientGenerator;
	}

	private ICoffeeHouseUI getUi() {
		return ui;
	}

	public void  initForStart(){			
		getCafe().init();
		getDiscretHisto().init();
		getClientGenerator().setFinishTime(ui.getFinishTime());
	}

	public void componentsToStartList() {		
		getDispatcher().addStartingActor(getClientGenerator());
	}

	/**
	 * This method initializes example.cafe	
	 * 	
	 * @return qusystem.QueueForTransactions	
	 */
	private QueueForTransactions getCafe() {
		if (cafe == null) {
			cafe = new QueueForTransactions();
			cafe.setNameForProtocol("����");
			cafe.setDispatcher(getDispatcher());
			cafe.setDiscretHisto(getDiscretHisto());
			cafe.setPainter(getUi().getCaffeeHousePainter());
		}
		return cafe;
	}

	/**
	 * This method initializes discretHisto	
	 * 	
	 * @return stat.DiscretHisto	
	 */
	public DiscretHisto getDiscretHisto() {
		if (discretHisto == null) {
			discretHisto = new DiscretHisto();
		}
		return discretHisto;
	}

	public void setDispatcher(Dispatcher dispatcher) {
		this.dispatcher=dispatcher;
		
	}
}  //  @jve:decl-index=0:visual-constraint="38,291"
