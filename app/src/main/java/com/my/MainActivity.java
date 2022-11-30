package com.my;

import android.app.*;
import android.os.*;
import android.support.design.widget.TabLayout;
import android.graphics.*;
import android.support.design.widget.TabLayout.*;
import android.support.v7.app.AppCompatActivity;
import android.content.pm.*;
import android.widget.*;

public class MainActivity extends AppCompatActivity
{
	TabLayout tabLayout;
	public static final int REQ_CODE=45;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		tabLayout = (TabLayout)findViewById(R.id.tabLayout);
		tabLayout.setTabTextColors(Color.GRAY, Color.BLACK);
		tabLayout.setTabGravity(tabLayout.GRAVITY_FILL);
		tabLayout.addTab(tabLayout.newTab().setText("Folders").setIcon(R.drawable.folder));
		tabLayout.addTab(tabLayout.newTab().setText("Files").setIcon(R.drawable.file));
		tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

				@Override
				public void onTabSelected(TabLayout.Tab p1)
				{
					if (p1.getText().toString().toLowerCase().equals("folders"))
					{
						getSupportFragmentManager().beginTransaction().replace(R.id.container, new FoldersFragment()).commit();
					}
					else
					{
						if (checkPermit())
							getSupportFragmentManager().beginTransaction().replace(R.id.container, new FilesFragment()).commit();
					}
				}

				@Override
				public void onTabUnselected(TabLayout.Tab p1)
				{
					// TODO: Implement this method
				}

				@Override
				public void onTabReselected(TabLayout.Tab p1)
				{
					// TODO: Implement this method
				}


			});
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
		if(requestCode==REQ_CODE&&grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
			Toast.makeText(this,"permission granted",Toast.LENGTH_SHORT).show();
		}
		else{
			if(shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
				AlertDialog.Builder ad=new AlertDialog.Builder(this);
				ad.setTitle("Permission Needed");
				ad.setMessage("We need access storage permission");
				ad.show();
			}else
			requestPermit();
		}
	}
	
}