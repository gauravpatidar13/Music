package com.my;
import android.app.*;
import android.os.*;
import android.widget.*;

public class VideoPlayerActivity extends Activity
{
VideoView vv;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_video_player);
		vv=findViewById(R.id.vv);
		String path=getIntent().getStringExtra("path");
		if(path!=null){
			vv.setVideoPath(path);
			vv.start();
		}
		
	}
	
}