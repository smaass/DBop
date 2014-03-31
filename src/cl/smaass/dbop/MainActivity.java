package cl.smaass.dbop;
import android.app.*;
import android.media.*;
import android.os.*;
import android.widget.*;
import android.widget.SeekBar.*;
import java.util.*;

public class MainActivity extends Activity {
    Thread t;
    int sr = 44100;
    boolean isRunning = true;
    SeekBar fSlider;
    double sliderval;
	LinkedList<Waveform> waves;
	
	private void addFrequencyListener(SeekBar seekBar, final Waveform wave) {
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			public void onStopTrackingTouch(SeekBar seekBar) { }
			public void onStartTrackingTouch(SeekBar seekBar) { }
			public void onProgressChanged(SeekBar seekBar, 
										  int progress,
										  boolean fromUser) {
				if (fromUser)
					wave.setFrequency(440 + 440 * progress / (float) seekBar.getMax());
			}
		});
	}
	
	private void initWaves() {
		waves = new LinkedList<Waveform>();
		final OctaveWave octave = new OctaveWave(new SineWave(sr), new SquareWave(sr));
		octave.setFundamentalToOctaveRatio(1.5f);
		final Waveform sine = new SineWave(sr);
		waves.add(octave);
		waves.add(sine);
		
		addFrequencyListener((SeekBar) findViewById(R.id.frequency), octave);
		addFrequencyListener((SeekBar) findViewById(R.id.frequency2), sine);
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		initWaves();
		setPlot();

        // start a new thread to synthesise audio
        t = new AudioSynthesisThread();
		t.start();
	}
	
	private void setPlot() {
		LinearLayout layout = (LinearLayout) findViewById(R.id.mainLayout);
		float[] xvalues = new float[1201];
        float[] yvalues = new float[1201];
        for (int i=0;i<1201;i++){
        	double temp = (-5+i*.01);
        	xvalues[i] = (float)temp;
        	yvalues[i] = (float)(Math.sin(temp));
        }
        
        plot2d graph = new plot2d(this, xvalues, yvalues, 0);
		layout.addView(graph);
	}
	
	/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }*/

    public void onDestroy() {
		super.onDestroy();
        isRunning = false;
        try {
			t.join();
        } catch (InterruptedException e) {
			e.printStackTrace();
        }
		t = null;
	}
	
	private class AudioSynthesisThread extends Thread {

		public void run() {
			// set process priority
			setPriority(Thread.MAX_PRIORITY);
			// set the buffer size
			int buffsize = AudioTrack.getMinBufferSize(sr,
													   AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
			// create an audiotrack object
			AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
												   sr, AudioFormat.CHANNEL_OUT_MONO,
												   AudioFormat.ENCODING_PCM_16BIT, buffsize,
												   AudioTrack.MODE_STREAM);

			short samples[] = new short[buffsize];
			int amp = 5000;

			// start audio
			audioTrack.play();

			// synthesis loop
			while (isRunning) {
				for (int i=0; i < buffsize; i++) {
					samples[i] = 0;
					for (Waveform wave : waves)
						samples[i] += (short) (amp * wave.getInstantAmplitude());
				}
				audioTrack.write(samples, 0, buffsize);
			}
			audioTrack.stop();
			audioTrack.release();
		}
	}
}
