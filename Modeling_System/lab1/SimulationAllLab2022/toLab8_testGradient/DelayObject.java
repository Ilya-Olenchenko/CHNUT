package toLab8_testGradient;

public class DelayObject implements IOperatingObject {
	private double output;

	private double disturb;

	private double kAmpf;

	private double tConst;

	private double delay;

	private double[] delayArray;

	private IStepTimer stepTimer;

	private widgets.Painter painter;

	/**
	 * ControlObject constructor comment.
	 */
	public DelayObject() {
		super();
	}

	/**
	 * Insert the method's description here. Creation date: (11.04.2006
	 * 10:29:20)
	 * 
	 * @return double
	 */
	public double getOutput() {
		return output;
	}

	/**
	 * Insert the method's description here. Creation date: (11.04.2006
	 * 10:37:03)
	 */
	public void init() {
		// ?????????? ????? ????? ????????
		int ld = (int) Math.round(delay / stepTimer.getStep());
		delayArray = new double[ld];
		// ????????? ???????? ??????? ??? ????? ????????,
		// ? ?????????????, ??? ?? ??????? ??? ?????????????? ?????
		double dn = output / kAmpf;
		for (int i = 0; i < ld; i++)
			delayArray[i] = dn;
		// ?????????????? ????????? ? ?????????
		if (painter != null) {
			painter.getDiagram().clear();
			painter.placeToXY(0, (float) output);
		}
	}

	public void onSetInput(double u) {
		double uz; // ?????? ?? ?????? ????? ????????
		int ld = delayArray.length; // ????? ????? ????????
		if (ld == 0)
			uz = u;
		else {
			uz = delayArray[0];
			// ???????? ?????? ? ????? ???????? ?? ???? ???
			for (int i = 0; i < ld - 1; i++)
				delayArray[i] = delayArray[i + 1];
			// ??????????? ??????????? ? ????? ????? ????????
			delayArray[ld - 1] = u;
		}
		// ?????? ????????? ???????
		double dOutput = (kAmpf * (uz + disturb) - output) / tConst
				* stepTimer.getStep();
		output += dOutput;
		// ??????????? ?? ?????????
		if (painter != null) {
			painter
					.drawToXY((float) stepTimer.getCurrentTime(),
							(float) output);
		}
	}

	/**
	 * Insert the method's description here. Creation date: (13.04.2006
	 * 19:58:56)
	 * 
	 * @param newDelay
	 *            double
	 */
	public void setDelay(double newDelay) {
		delay = newDelay;
	}

	/**
	 * Insert the method's description here. Creation date: (11.04.2006
	 * 10:30:51)
	 * 
	 * @param newDisturb
	 *            double
	 */
	public void setDisturb(double newDisturb) {
		disturb = newDisturb;
	}

	/**
	 * Insert the method's description here. Creation date: (13.04.2006
	 * 20:20:06)
	 * 
	 * @param newMonitor
	 *            toLab8_testGradient.IMonitor
	 */
	public void setIStepTimer(IStepTimer newStepTimer) {
		stepTimer = newStepTimer;
	}

	/**
	 * Insert the method's description here. Creation date: (13.04.2006
	 * 19:44:21)
	 * 
	 * @param newK
	 *            double
	 */
	public void setKAmpf(double newK) {
		kAmpf = newK;
	}

	/**
	 * Insert the method's description here. Creation date: (11.04.2006
	 * 10:29:20)
	 * 
	 * @param newOutput
	 *            double
	 */
	public void setOutput(double newOutput) {
		output = newOutput;
	}

	/**
	 * Insert the method's description here. Creation date: (13.04.2006
	 * 20:42:57)
	 * 
	 * @param newPainter
	 *            paint.Painter
	 */
	public void setPainter(widgets.Painter newPainter) {
		painter = newPainter;
	}

	/**
	 * Insert the method's description here. Creation date: (13.04.2006
	 * 19:44:56)
	 * 
	 * @param newTConst
	 *            double
	 */
	public void setTConst(double newTConst) {
		tConst = newTConst;
	}
}
