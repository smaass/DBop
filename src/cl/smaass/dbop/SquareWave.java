package cl.smaass.dbop;

public class SquareWave extends AbstractSimpleWave implements Waveform {

	public SquareWave(int sampleRate) {
		super(sampleRate);
	}
	
	@Override
	public double getValue(double phase) {
		int p = ((int) (phase / Math.PI)) & 1;
		if (p == 0) return 1;
		else return -1;
	}

}
