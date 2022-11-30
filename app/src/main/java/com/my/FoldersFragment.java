package com.my;
import android.support.v4.app.*;
import android.os.*;
import android.view.*;

public class FoldersFragment extends Fragment
{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v=inflater.inflate(R.layout.fragment_folders,container,false);
		return v;
	}
	
}