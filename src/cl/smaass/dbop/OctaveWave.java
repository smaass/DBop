package cl.smaass.dbop;

public class OctaveWave implements Waveform {
	private Waveform fundamental;
	private Waveform octave;
	private double fAmplitude, oAmplitude;

	public OctaveWave(Waveform fundamental, Waveform octave) {
		this.fundamental = fundamental;
		this.octave = octave;
	}
	
	public void setFundamentalToOctaveRatio(double ratio) {
		double oAmp = 1 / ratio;
		double totalAmp = oAmp + ratio;
		fAmplitude = ratio / totalAmp;
		oAmplitude = oAmp / totalAmp;
	}
	
	@Override
	public double getInstantAmplitude() {
		return fundamental.getInstantAmplitude() * fAmplitude
			+ octave.getInstantAmplitude() * oAmplitude;
	}

	@Override
	public void setFrequency(double frequency) {
		fundamental.setFrequency(frequency);
		octave.setFrequency(frequency*2);
	}
}
