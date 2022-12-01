package com.my;
import android.support.v4.app.*;
import android.os.*;
import android.view.*;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.*;
import java.util.*;
import android.net.*;
import android.provider.*;
import android.database.*;

public class FoldersFragment extends Fragment
{

	RecyclerView rec;
	FAdapter adapter;
	LinearLayoutManager lm;
	String folderName;
	List<String> folders=new ArrayList<>();
	List<VideoFile> vfList=new ArrayList<>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v=inflater.inflate(R.layout.fragment_folders, container, false);
		rec = v.findViewById(R.id.frv);
		setupRecView();
		return v;
	}
	public void setupRecView()
	{
		lm = new LinearLayoutManager(getActivity());
		rec.setLayoutManager(lm);
		rec.setHasFixedSize(true);
		setupFolders();
		adapter = new FAdapter(folders, this);
		rec.setAdapter(adapter);
	}
	public void setupFolders()
	{
		folders.clear();
		Uri uri=MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
		String projections[]={
			MediaStore.Video.Media.BUCKET_DISPLAY_NAME
		};
		Cursor cursor=getContext().getContentResolver().query(uri, projections, null, null, null);
		if (cursor != null)
		{
			folders.clear();
			while (cursor.moveToNext())
			{
				folderName = cursor.getString(0);
				if (!folders.contains(folderName))
					folders.add(folderName);
			}
		}

	}

}