package com.android.controllers.steeringwheel;

//ewentualna kierownica jeśli sie uda zrobić sterowniki po stronie komputera

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.android.application.MultiPlayApplication;
import com.android.application.N;
import com.android.application.N.Helper;
import com.android.multiplay.R;

public class SteeringwheelActivity extends Activity implements
		SensorEventListener, OnSeekBarChangeListener {
	
	private static final float CONST = 128f/90;
	float old = 0;
	int signal = 0;
	int angle = 0;
	float y = 0;
	
	SeekBar seekBar1;
	float scale = 0.5f;
	boolean notSend0 = true;
	
	private SensorManager sm;
	private TextView tv;
	private int up = 0, down = 0, stop = 0;
	private Button b1, b2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_steeringwheel);
		try {
			MultiPlayApplication.runThread(MultiPlayApplication.isConnectedTo());
			tv = (TextView) findViewById(R.id.stopv);
			sm = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
			b1 = (Button) super.findViewById(R.id.brea);
			b2 = (Button) super.findViewById(R.id.gaz);
			
			seekBar1 = (SeekBar) super.findViewById(R.id.seekBar1);
			seekBar1.setProgress(75);
			seekBar1.setMax(100);
			seekBar1.setOnSeekBarChangeListener(this);
			
			b1.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					int signal;
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						up = 1;
					}
					if (event.getAction() == MotionEvent.ACTION_UP) {
						up = 0;
					}
					return true;
				}
			});
			b2.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					int signal;
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						down = 1;
					}
					if (event.getAction() == MotionEvent.ACTION_UP) {
						down = 0;
					}
					return true;
				}
			});
			sm.registerListener(this,
					sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
					SensorManager.SENSOR_DELAY_NORMAL);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.steeringwheel, menu);
		return true;
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent arg0) {
		if (stop == 0) {
			y = arg0.values[1];
			if (y < -9*scale) {
				angle = -126;
			} else if( y > 9*scale) {
				angle = 126;
			} else {
				angle = (int) Math.floor(10*y*CONST/scale);
			}
			tv.setText(String.valueOf(arg0.values[1])+" \t\t "+String.valueOf(10+"*"+y+"*"+CONST+"="+angle)+" \t\t "+String.valueOf(Math.abs(old-angle)));
			if (notSend0 || Math.abs(old-angle) > 0) {
				if (up == 0 && down == 0) {
					signal = N.Helper.encodeSignal(N.Device.WHEEL,
							N.DeviceDataCounter.DOUBLE, angle, 0);
				} else if (up == 1 && down == 0) {
					signal = Helper.encodeSignal(N.Device.WHEEL,
							N.DeviceDataCounter.DOUBLE, angle,
							N.DeviceSignal.KEYBOARD_UP);
				} else if (up == 0 && down == 1) {
					signal = Helper.encodeSignal(N.Device.WHEEL,
							N.DeviceDataCounter.DOUBLE, angle,
							N.DeviceSignal.KEYBOARD_SPACE);
				}
				MultiPlayApplication.add(signal);
				old = angle;
			}
		}
	}

	protected void onPause() {
		super.onPause();
		stop = 1;
	}

	protected void onResume() {
		super.onResume();
		stop = 0;
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		scale = progress/100f;
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	public void onRadioButtonClicked(View view) {
	    // Is the button now checked?
		((RadioButton) view).toggle();
	    notSend0 = ((RadioButton) view).isChecked();
	}
}
