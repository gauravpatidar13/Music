package com.my;
import android.app.*;
import android.os.*;
import android.widget.*;
import android.content.*;

public class VideoPlayerActivity extends Activity
{
VideoView vv;
String path;
String name;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_video_player);
		vv=findViewById(R.id.vv);
		path=getIntent().getStringExtra("path");
		name=getIntent().getStringExtra("name");
		if(path!=null){
			vv.setVideoPath(path);
			vv.start();
		}
		
	}

	@Override
	public void onBackPressed()
	{
		Intent i=new Intent();
		i.putExtra("path",path);
		i.putExtra("name",name);
		setResult(RESULT_OK,i);
		super.onBackPressed();
		
	}
	
	
}