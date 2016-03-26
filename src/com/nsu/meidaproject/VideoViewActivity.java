package com.nsu.meidaproject;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.VideoView;

public class VideoViewActivity extends Activity {
	private SeekBar seekBar;
	private Button btnPlay, btnPause, btnReplay, btnStop;
	private VideoView vv;
	private Boolean isPlaying;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_view);
		seekBar = (SeekBar) findViewById(R.id.seekBar);
		btnPlay = (Button) findViewById(R.id.btn_play);
		btnPause = (Button) findViewById(R.id.btn_pause);
		btnReplay = (Button) findViewById(R.id.btn_replay);
		btnStop = (Button) findViewById(R.id.btn_stop);

		btnPlay.setOnClickListener(click);
		btnPause.setOnClickListener(click);
		btnReplay.setOnClickListener(click);
		btnStop.setOnClickListener(click);
		
		String path = "android.resource://" + getPackageName() + "/" + R.raw.sound;		
		vv = (VideoView) findViewById(R.id.vv_videoview);
		vv.setVideoURI(Uri.parse(path));
		vv.setOnPreparedListener(new OnPreparedListener() {
			
			@Override
			public void onPrepared(MediaPlayer mp) {
				// TODO Auto-generated method stub
				seekBar.setMax(vv.getDuration());
			}
		});
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				int progress = seekBar.getProgress();
				if (vv != null && vv.isPlaying()) {
					// 设置当前播放的位置
					vv.seekTo(progress);
				}
			}
		});

	}

	private View.OnClickListener click = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_play:
				play(0);
				break;
			case R.id.btn_pause:
				pause();
				break;
			case R.id.btn_replay:
				replay();
				break;
			case R.id.btn_stop:
				stop();
				break;
			default:
				break;
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.video_view, menu);
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
	
	public void play(final int second) {
		new Thread(){
			public void run(){
				vv.start();
				vv.seekTo(second);
			}
		}.start();
		
		
		btnPlay.setEnabled(false);
		vv.setOnCompletionListener(new OnCompletionListener(){

			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				isPlaying=false;
				btnPlay.setEnabled(true);
				
			}});
		vv.setOnErrorListener(new OnErrorListener() {
			
			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				// TODO Auto-generated method stub
				play(0);
				isPlaying=false;
				return false;
			}
		});
		
		new Thread() {
			public void run() {
				isPlaying=true;
				while(isPlaying){
					int current=vv.getCurrentPosition();
					seekBar.setProgress(current);
					try {
						sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		}.run();
	}

	public void pause() {
		if(btnPause.getText().toString().trim().equals("继续")){
			btnPause.setText("暂停");
			vv.start();
			return;
		}
		if(vv!=null&&vv.isPlaying()){
			vv.pause();
			btnPause.setText("继续");			
		}

	}

	public void replay() {
		if(vv!=null&&vv.isPlaying()){
			vv.seekTo(0);
			btnPause.setText("暂停");
			return;
		}
		isPlaying=false;
		play(0);
	}

	public void stop() {
		if(vv!=null&&vv.isPlaying()){
			btnPlay.setEnabled(true);
			isPlaying=false;
		}
	}
}
