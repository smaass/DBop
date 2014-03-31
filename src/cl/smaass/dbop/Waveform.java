package cl.smaass.dbop;

public interface Waveform {
	/**
	*	Returns the normalized instant amplitude of the wave at the current phase
	**/
	public double getInstantAmplitude();
	
	public void setFrequency(double frequency);
}
