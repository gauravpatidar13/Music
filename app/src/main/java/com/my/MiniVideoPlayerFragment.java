package com.my;
import android.support.v4.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class MiniVideoPlayerFragment extends Fragment implements View.OnClickListener
{
	VideoView vv;
	TextView tv;
	String path;
	String name;
	int position;
	ImageView close;
	
	@Override
	public void onClick(View p1)
	{
		getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v=inflater.inflate(R.layout.fragment_mini_player,container,false);
		vv=v.findViewById(R.id.vv);
		tv=v.findViewById(R.id.tv);
		close=v.findViewById(R.id.close_mini_player);
		close.setOnClickListener(this);
		path=getArguments().getString("path");
		name=getArguments().getString("name");
		if(path!=null&&name!=null){
		tv.setText(name);
		vv.setVideoPath(path);
		vv.start();
		vv.getCurrentPosition();
		}else{
			Toast.makeText(getActivity(),"mini video path : "+path+"\n"+"mini video name : "+name,Toast.LENGTH_SHORT).show();
		}
		return v;
	}
	
}