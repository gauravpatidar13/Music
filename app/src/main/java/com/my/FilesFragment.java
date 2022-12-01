package com.my;
import android.support.v4.app.*;
import android.os.*;
import android.view.*;
import android.provider.*;
import android.net.*;
import android.database.*;
import java.util.*;
import android.support.v7.widget.*;
import android.content.*;
import android.view.View.*;
import android.widget.*;
import android.media.*;
import java.io.*;

public class FilesFragment extends Fragment
{
	String id,path,name,duration,title,size,dateAdded,bucket;
	static List<VideoFile> list=new ArrayList<>();
	RecyclerView rec;
	static VFAdapter adapter;
    String order;
	String mini_video_path;
	String mini_video_name;
	String where;
	Bundle bun;
	String folderName;

	public void search(String s)
	{
		adapter.getFilter().filter(s);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v=inflater.inflate(R.layout.fragment_files, container, false);
		rec = v.findViewById(R.id.rec);
		order = MediaStore.MediaColumns.DISPLAY_NAME + " ASC";
		bun = getArguments();
		if (bun != null)
		{
			where = bun.getString("where");
			if (where != null)
			{
				if (where.equalsIgnoreCase("fromTabLayout"))
				{
					list = setupFiles(null, null, order);
				}
				else if (where.equalsIgnoreCase("fromFoldersFragment"))
				{
					folderName = bun.getString("bn");
					if (folderName != null)
					{
						String selection=MediaStore.Video.Media.DATA + " like?";
						String selectionArgs[]={"%" + folderName + "%"};
						list = setupFiles(selection, selectionArgs, order);
					}

				}
			}
		}

		registerForContextMenu(rec);
		setupRec();
		return v;
	}

	public void sortBy(String order)
	{
		list = setupFiles(null, null, order);
		if (adapter != null)
			adapter.notifyDataSetChanged();
	}
	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		if (item.getTitle().equals("Delete"))
		{
			if (new File(list.get(VFAdapter.gap).getPath()).delete())
			{
				Toast.makeText(getActivity(), list.get(VFAdapter.gap).getPath(), Toast.LENGTH_LONG).show();
				adapter.notifyItemRemoved(VFAdapter.gap);
			}
		}
		return true;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == VFAdapter.REQ_CODE && resultCode == getActivity().RESULT_OK)
		{
			if (data != null)
			{
				mini_video_path = data.getStringExtra("path");
				mini_video_name = data.getStringExtra("name");
				if (mini_video_path != null)
				{
					Fragment fr=new MiniVideoPlayerFragment();
					Bundle bun=new Bundle();
					bun.putString("path", mini_video_path);
					bun.putString("name", mini_video_name);
					fr.setArguments(bun);
					getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mini_vplayer_container, fr).commit();
				}
			}
		}
	}

	public List<VideoFile> setupFiles(String selection, String [] selectionArgs, String order)
	{
		list.clear();
		Uri uri=MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
		String projections[]={
			MediaStore.Video.Media._ID,
			MediaStore.Video.Media.DATA,
			MediaStore.Video.Media.DISPLAY_NAME,
			MediaStore.Video.Media.DURATION,
			MediaStore.Video.Media.TITLE,
			MediaStore.Video.Media.SIZE,
			MediaStore.Video.Media.DATE_ADDED,
			MediaStore.Video.Media.BUCKET_DISPLAY_NAME
		};
		Cursor cursor=getContext().getContentResolver().query(uri, projections, selection, selectionArgs, order);
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
				bucket = cursor.getString(7);
				if (where.equalsIgnoreCase("fromFoldersFragment"))
				{
					if (shouldAddVideo(path, folderName))
					{
						VideoFile vf=new VideoFile(id, path, name, duration, title, size, dateAdded, bucket);
						list.add(vf);
					}
				}
				else if (where.equalsIgnoreCase("fromTabLayout"))
				{
					VideoFile vf=new VideoFile(id, path, name, duration, title, size, dateAdded, bucket);
					list.add(vf);
				}
			}
		}
		return list;
	}
	public void setupRec()
	{
		if (list != null && list.size() > 0)
		{
			adapter = new VFAdapter(list, this);
			rec.setAdapter(adapter);
			rec.setHasFixedSize(true);
			rec.setLayoutManager(new LinearLayoutManager(getActivity()));
		}

	}
	public boolean shouldAddVideo(String path, String bucket)
	{
		int l=path.lastIndexOf("/");
		String sub=path.substring(0, l);
		int i=sub.lastIndexOf("/");
		String substr=sub.substring(i + 1);
		if (substr.equalsIgnoreCase(bucket))
		{
			return true;
		}
		else return false;
	}
}