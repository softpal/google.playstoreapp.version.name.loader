package com.softpal.googleplaystoreappversionnameloaders;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.softpal.googleplaystoreappversionnameloader.GooglePlayStoreAppVersionNameLoader;
import com.softpal.googleplaystoreappversionnameloader.WSCallerVersionListener;

public class MainActivity extends AppCompatActivity
{
	private Context context;
	private WSCallerVersionListener wsCallerVersionListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new GooglePlayStoreAppVersionNameLoader(context,wsCallerVersionListener).execute();
	}
}
