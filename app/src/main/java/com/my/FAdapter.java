package com.my;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import android.os.*;

public class FAdapter extends RecyclerView.Adapter<FAdapter.FViewHolder>
{
	List<String> folders;
    FoldersFragment frag;
	public FAdapter(List<String> folders, FoldersFragment frag)
	{
		this.folders = folders;
		this.frag = frag;
	}

	@Override
	public void onBindViewHolder(FAdapter.FViewHolder p1, int p2)
	{
		p1.ftv.setText(folders.get(p2));
	}




	@Override
	public FAdapter.FViewHolder onCreateViewHolder(ViewGroup p1, int p2)
	{
		View v=LayoutInflater.from(p1.getContext()).inflate(R.layout.folder_item, p1, false);
		return new FViewHolder(v);
	}



	@Override
	public int getItemCount()
	{
		return folders.size();
	}

	class FViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
	{

		@Override
		public void onClick(View p1)
		{
			FilesFragment fm = new FilesFragment();
			Bundle bun=new Bundle();
			bun.putString("where", "fromFoldersFragment");
			bun.putString("bn", folders.get(getAdapterPosition()));
			fm.setArguments(bun);
			frag.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fm).commit();
             MainActivity.inFolder=true;
		}

		TextView ftv;
		public FViewHolder(View v)
		{
			super(v);
			ftv = v.findViewById(R.id.folder_item_tv);
			v.setOnClickListener(this);
		}

	}
}