package com.my;

import android.app.*;
import android.os.*;
import android.support.design.widget.TabLayout;
import android.graphics.*;
import android.support.design.widget.TabLayout.*;
import android.support.v7.app.AppCompatActivity;
import android.content.pm.*;
import android.widget.*;
import android.view.*;
import android.support.v7.widget.Toolbar;
import android.provider.*;
import android.support.v7.widget.SearchView;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener
{
	TabLayout tabLayout;
	Toolbar toolbar;
	public static boolean inFolder=false;
	public static final int REQ_CODE=45;
	FilesFragment f;
	TabLayout.OnTabSelectedListener listener;
	SearchView sv;
	
	@Override
	public boolean onQueryTextSubmit(String p1)
	{
		return true;
	}

	@Override
	public boolean onQueryTextChange(String p1)
	{
		f.search(p1);
		return true;
	}
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		toolbar = (Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		tabLayout = (TabLayout)findViewById(R.id.tabLayout);
		tabLayout.setTabTextColors(Color.GRAY, Color.BLACK);
		tabLayout.setTabGravity(tabLayout.GRAVITY_FILL);	
		tabLayout.addTab(tabLayout.newTab().setText("Files").setIcon(R.drawable.file));
		tabLayout.addTab(tabLayout.newTab().setText("Folders").setIcon(R.drawable.folder));

		listener = new TabLayout.OnTabSelectedListener(){

			@Override
			public void onTabSelected(TabLayout.Tab p1)
			{
				if (p1.getText().toString().toLowerCase().equals("folders"))
				{

					getSupportFragmentManager().beginTransaction().replace(R.id.container, new FoldersFragment()).addToBackStack("ff").commit();
				}
				else
				{
					if (checkPermit())
						f = new FilesFragment();
					Bundle bun=new Bundle();
					bun.putString("where", "fromTabLayout");
					f.setArguments(bun);
					getSupportFragmentManager().beginTransaction().replace(R.id.container, f).commit();
				}
			}

			@Override
			public void onTabUnselected(TabLayout.Tab p1)
			{
			
			}

			@Override
			public void onTabReselected(TabLayout.Tab p1)
			{
			
			}
		};
		tabLayout.addOnTabSelectedListener(listener);
		listener.onTabSelected(tabLayout.getTabAt(0));
	}
	public boolean checkPermit()
	{
		if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
		{
			return true;
		}
		requestPermit();
		return false;
	}
	public void requestPermit()
	{
		requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQ_CODE);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
	{
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == REQ_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
		{
			Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();
		}
		else
		{
			if (shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE))
			{
				AlertDialog.Builder ad=new AlertDialog.Builder(this);
				ad.setTitle("Permission Needed");
				ad.setMessage("We need access storage permission");
				ad.show();
			}
			else
				requestPermit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_main, menu);
		MenuItem item=menu.findItem(R.id.a_search);
		sv=(SearchView)	item.getActionView();
		sv.setOnQueryTextListener(this);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.sort_by_date:
				if (tabLayout.getSelectedTabPosition() == 0 && f != null)
				{
					FilesFragment.list = f.setupFiles(null, null, MediaStore.MediaColumns.DATE_ADDED + " DESC");
					FilesFragment.adapter.notifyDataSetChanged();
				}
				break;
			case R.id.sort_by_size:
				if (tabLayout.getSelectedTabPosition() == 0 && f != null)
				{
					FilesFragment.list = f.setupFiles(null, null, MediaStore.MediaColumns.SIZE + " ASC");
					FilesFragment.adapter.notifyDataSetChanged();
				}
				break;
			case R.id.sort_by_name:
				if (tabLayout.getSelectedTabPosition() == 0 && f != null)
				{
					FilesFragment.list = f.setupFiles(null, null, MediaStore.MediaColumns.DISPLAY_NAME + " ASC");
					FilesFragment.adapter.notifyDataSetChanged();
				}
				break;
			
		}
		return true;
	}

	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
	if(inFolder){
		getSupportFragmentManager().beginTransaction().replace(R.id.container, new FoldersFragment()).commit();
	}
	}


}