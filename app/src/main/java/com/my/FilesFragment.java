package com.my;
import android.support.v4.app.*;
import android.os.*;
import android.view.*;
import android.provider.*;
import android.net.*;
import android.database.*;
import java.util.*;
import android.support.v7.widget.*;

public class FilesFragment extends Fragment
{
	String id,path,name,duration,title,size,dateAdded;
	List<VideoFile> list=new ArrayList<>();
	RecyclerView rec;
	VFAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v=inflater.inflate(R.layout.fragment_files, container, false);
		rec=v.findViewById(R.id.rec);
		list=setupFiles();
		setupRec();
		return v;
	}
	public List<VideoFile> setupFiles()
	{
		Uri uri=MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
		String projections[]={
			MediaStore.Video.Media._ID,
			MediaStore.Video.Media.DATA,
			MediaStore.Video.Media.DISPLAY_NAME,
			MediaStore.Video.Media.DURATION,
			MediaStore.Video.Media.TITLE,
			MediaStore.Video.Media.SIZE,
			MediaStore.Video.Media.DATE_ADDED
		};
		Cursor cursor=getContext().getContentResolver().query(uri, projections, null, null, null);
		if (cursor != null)
		{
			list.clear();
			while (cursor.moveToNext())
			{
				id = cursor.getString(0);
				path = cursor.getString(1);
				name = cursor.getString(2);
				duration = cursor.getString(3);
				title = cursor.getString(4);
				size = cursor.getString(5);
				dateAdded = cursor.getString(6);
				VideoFile vf=new VideoFile(id, path, name, duration, title, size, dateAdded);
				list.add(vf);
			}
		}
		return list;
	}
	public void setupRec(){
		if(list!=null&&list.size()>0){
		adapter=new VFAdapter(list);
		rec.setAdapter(adapter);
		rec.setHasFixedSize(true);
		rec.setLayoutManager(new LinearLayoutManager(getActivity()));
		}
	}
}