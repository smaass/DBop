package cl.smaass.dbop;

import java.util.LinkedList;

public class HarmonicWave implements Waveform
{

	@Override
	public double getInstantAmplitude()
	{
		// TODO: Implement this method
		return 0;
	}

	@Override
	public void setFrequency(double frequency)
	{
		// TODO: Implement this method
	}

	private LinkedList<Pair<Waveform, Double>> waves;

	public HarmonicWave(WaveBuilder builder) {
		waves = new LinkedList<Pair<Waveform, Double>>();
		
		while (builder.hasNext()) {
			waves.add(builder.getWaveAmplitudePair());
		}
	}
/*
	@Override
	public short getInstantSample(double amplitude) {
		double sample = 0; 
		for (Pair<Waveform, Double> waveAmp : waves) {
			sample += waveAmp.first().getInstantSample() * waveAmp.second();
		}
		return (short) (fundamental.getInstantSample(amplitude * fAmplitude)
			+ octave.getInstantSample(amplitude * oAmplitude));
	}

	@Override
	public void setFrequency(double frequency) {
		fundamental.setFrequency(frequency);
		octave.setFrequency(frequency*2);
	}*/
}
