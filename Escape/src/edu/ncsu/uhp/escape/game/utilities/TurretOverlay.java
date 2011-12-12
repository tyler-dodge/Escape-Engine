package edu.ncsu.uhp.escape.game.utilities;

import edu.ncsu.uhp.escape.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

public class TurretOverlay extends Dialog { 
	Context mContext;
	
	public TurretOverlay(final Context context, int theme){
		super(context, theme);
		mContext=context;
	}

	@Override
	protected void onCreate(final Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		FrameLayout layout =(FrameLayout) LayoutInflater.from(mContext).inflate(R.layout.turret_overlay, null);
		setContentView(layout); 
	}
}