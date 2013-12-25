package com.android.controllers.mouse;

import com.android.multiplay.R;
import com.android.multiplay.R.layout;
import com.android.multiplay.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.TextView;

public class TouchPadActivity extends Activity {
	double oldx = 245.0, oldy = 175.0;
	TextView txv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_touch_pad);
		txv = (TextView) super.findViewById(R.id.txe);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.touch_pad, menu);
		return true;
	}

	public boolean onTouchEvent(MotionEvent event) {
		double oldoldx= event.getX(), oldoldy=event.getY();
		
		int maskedAction = event.getActionMasked();
		//txv.setText(Double.toString((double)(event.getX() - oldx)));
		switch (maskedAction) {
		case MotionEvent.ACTION_MOVE:
			if (oldoldx-oldx < (double)0.0 && oldoldy-oldy< (double)0.0) {
				
					txv.setText("X=-1, Y=-1");
			
			} else if (oldoldx-oldx > (double)0.0 && oldoldy-oldy > (double)0.0) {
				
					txv.setText("X=1, Y=1");
				
			} else if (oldoldx-oldx< (double)0.0 && oldoldy-oldy > (double)0.0) {
				
					txv.setText("X=-1, Y=1");
				
			} else if (oldoldx-oldx >(double)0.0 && oldoldy-oldy < (double)0.0) {
				
					txv.setText("X=1, Y=-1");
				
			}
			break;
		case MotionEvent.ACTION_UP:
			txv.setText("X=0, Y=0");
			break;
			
		}
		oldx = event.getX();
		oldy = event.getY();
		return true;
		// RETURN SUPER.ONTOUCHEVENT(EVENT);
	}
}