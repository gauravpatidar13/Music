package com.my;
import android.content.*;
import android.graphics.*;
import android.media.*;
import android.provider.*;
import android.support.v7.widget.*;
import android.text.format.*;
import android.view.*;
import android.widget.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;

import android.text.format.DateFormat;
import android.view.View.*;
import android.view.ContextMenu.*;
import android.widget.Filter.*;
import java.io.*;

public class VFAdapter extends RecyclerView.Adapter<VFAdapter.VFViewHolder> implements Filterable
{
	List<VideoFile> list;
	List<VideoFile> listAll;
	FilesFragment frag;
	public static int gap;
	public static final int REQ_CODE=6;
	Filter filter=new Filter(){

		@Override
		protected Filter.FilterResults performFiltering(CharSequence p1)
		{
			FilterResults fr=new FilterResults();
			if(p1.toString().isEmpty()){
				fr.values=listAll;
			}else{
				List<VideoFile> filteredList=new ArrayList<>();
				filteredList.clear();
				for(VideoFile vf:listAll){
					if(vf.getName().toLowerCase().contains(p1.toString().toLowerCase())){
						filteredList.add(vf);
					}
				}
				fr.values=filteredList;
			}
			return fr;
		}

		@Override
		protected void publishResults(CharSequence p1, Filter.FilterResults p2)
		{
			list.clear();
			list.addAll((Collection<? extends VideoFile>)p2.values);
			notifyDataSetChanged();
		}
		
		
	};
	@Override
	public Filter getFilter()
	{
		return filter;
	}
	
	
	public VFAdapter(List<VideoFile> list, FilesFragment frag)
	{
		this.list = list;
		this.frag = frag;
		listAll=new ArrayList<>(list);
	}

	@Override
	public VFAdapter.VFViewHolder onCreateViewHolder(ViewGroup p1, int p2)
	{
		View v=LayoutInflater.from(p1.getContext()).inflate(R.layout.video_file_item, p1, false);
		return new VFViewHolder(v);
	}

	@Override
	public void onBindViewHolder(VFAdapter.VFViewHolder p1, int p2)
	{
		p1.name.setText(list.get(p2).getName());
		String dateString = getDate(Long.parseLong(list.get(p2).getDateAdded()));
		p1.date.setText(dateString);
		Long duration = Long.parseLong(list.get(p2).getDuration());
		String dura=getDuration(duration);
		p1.thumb.setImageResource(R.drawable.ic_launcher);
		Bitmap t=ThumbnailUtils.createVideoThumbnail(list.get(p2).getPath(), MediaStore.Video.Thumbnails.MINI_KIND);
		if (t != null)
			p1.thumb.setImageBitmap(t);
		p1.time.setText(dura);
		Long siz=Long.parseLong(list.get(p2).getSize());
		long l=siz / 1048576;
		p1.size.setText(l + "MB");
	}

	@Override
	public int getItemCount()
	{
		return list.size();
	}
	private String getDate(long time)
	{
		Calendar cal = Calendar.getInstance(Locale.ENGLISH);
		cal.setTimeInMillis(time * 1000);
		String date = DateFormat.format("dd-MM-yyyy h:m:s aa", cal).toString();
		return date;
	}

	public String getDuration(Long duration)
	{
		String dura= String.format("%d min, %d sec", 
								   TimeUnit.MILLISECONDS.toMinutes(duration),
								   TimeUnit.MILLISECONDS.toSeconds(duration) - 
								   TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
		return dura;
	}

	public class VFViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,OnCreateContextMenuListener
	{

		@Override
		public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo p3)
		{
			gap = getAdapterPosition();
			menu.setHeaderTitle("Select The Action");    
			menu.add(0, v.getId(), 0, "Delete");
			menu.add(0, v.getId(), 0, "Rename"); 

		}

		@Override
		public void onClick(View p1)
		{
			Intent i=new Intent(p1.getContext().getApplicationContext(), VideoPlayerActivity.class);
			i.putExtra("path", list.get(getAdapterPosition()).getPath());
			i.putExtra("name", list.get(getAdapterPosition()).getName());
			frag.startActivityForResult(i, REQ_CODE);
		}

		ImageView thumb;
		TextView time,name,size,date;
		public VFViewHolder(View v)
		{
			super(v);
			thumb = v.findViewById(R.id.vf_thumb);
			time = v.findViewById(R.id.vf_time);
			name = v.findViewById(R.id.vf_name);
			size = v.findViewById(R.id.vf_size);
			date = v.findViewById(R.id.vf_date);
			v.setOnClickListener(this);
			v.setOnCreateContextMenuListener(this);

		}
	}

}