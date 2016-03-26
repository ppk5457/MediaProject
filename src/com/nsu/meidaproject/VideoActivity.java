package com.nsu.meidaproject;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoActivity extends Activity {
	private VideoView vv;
	private MediaController mc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);
		String uri = "android.resource://" + getPackageName() + "/" + R.raw.sound;		
		vv=(VideoView)findViewById(R.id.videoView1);			
		vv.setVideoURI(Uri.parse(uri));		
		mc=new MediaController(this);
		vv.setMediaController(mc);
		mc.setMediaPlayer(vv);
		//vv.start();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.video, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
