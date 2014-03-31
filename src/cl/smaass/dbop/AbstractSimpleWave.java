package cl.smaass.dbop;

public abstract class AbstractSimpleWave implements Waveform {
	private double fr, ph, stepFactor;
	
	public AbstractSimpleWave(int sr) {
		ph = 0;
		fr = 0;
		stepFactor = Math.PI*2/sr;
	}
	
	@Override
	public void setFrequency(double fr) {
		this.fr = fr;
	}

	@Override
	public double getInstantAmplitude() {
		double retVal = getValue(ph);
		ph += stepFactor*fr;
		return retVal;
	}
	
	abstract public double getValue(double phase);
}
