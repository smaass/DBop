package cl.smaass.dbop;

public interface WaveBuilder {
	public boolean hasNext();
	public Pair<Waveform, Double> getWaveAmplitudePair();
}
