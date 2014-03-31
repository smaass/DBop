package cl.smaass.dbop;

public class SineWave extends AbstractSimpleWave implements Waveform {
	
	public SineWave(int sampleRate) {
		super(sampleRate);
	}

	@Override
	public double getValue(double phase) {
		return (float) Math.sin(phase);
	}
}
