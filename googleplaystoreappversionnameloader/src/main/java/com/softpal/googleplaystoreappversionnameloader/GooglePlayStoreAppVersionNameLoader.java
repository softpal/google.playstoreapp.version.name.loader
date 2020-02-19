package com.softpal.googleplaystoreappversionnameloader;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;

import org.jsoup.Jsoup;

/**
 The type Google play store app version name loader.
 */
public class GooglePlayStoreAppVersionNameLoader extends AsyncTask<String,Void,String>
{
	private static final String TAG = GooglePlayStoreAppVersionNameLoader.class.getSimpleName();
	private WSCallerVersionListener mWsCallerVersionListener;
	private boolean isAvailableInPlayStore = false;
	private Context mContext;
	
	/**
	 Instantiates a new Google play store app version name loader.@param mContext the m context
	 @param callback the callback
	 
	 
	 */
	public GooglePlayStoreAppVersionNameLoader(Context mContext,WSCallerVersionListener callback)
	{
		mWsCallerVersionListener = callback;
		this.mContext = mContext;
	}
	
	@Override
	protected String doInBackground(String... urls)
	{
		String mStringCheckUpdate = "";
		try
		{
			isAvailableInPlayStore = true;
			
			if(isNetworkAvailable(mContext))
			{
				mStringCheckUpdate = Jsoup.connect("https://play.google.com/store/apps/details?id=" + mContext.getPackageName() + "&hl=en")
				                          .timeout(30000)
				                          .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
				                          .referrer("http://www.google.com")
				                          .get()
				                          .select("div.hAyfc:nth-child(4) > span:nth-child(2) > div:nth-child(1) > span:nth-child(1)")
				                          .first()
				                          .ownText();
				return mStringCheckUpdate;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			isAvailableInPlayStore = false;
			return mStringCheckUpdate;
		}
		catch(Throwable e)
		{
			e.printStackTrace();
			isAvailableInPlayStore = false;
			return mStringCheckUpdate;
		}
		return mStringCheckUpdate;
	}
	
	@Override
	protected void onPreExecute()
	{
		super.onPreExecute();
	}
	
	@Override
	protected void onPostExecute(String string)
	{
		boolean isVersionAvailable = false;
		if(isAvailableInPlayStore)
		{
			String newVersion = string;
			//SoftLog.v(TAG,"In onPostExecute() newVersion = " + newVersion);
			String currentVersion = checkApplicationCurrentVersion();
			
			double newVersionNumber = VersionUtils.getVersionNumber(newVersion);
			double currentVersionNumber = VersionUtils.getVersionNumber(currentVersion);
			
			if(newVersionNumber > currentVersionNumber)
			{
				isVersionAvailable = true;
			}
			mWsCallerVersionListener.onGetResponse(isVersionAvailable);
		}
	}
	
	/**
	 Method to check current app version
	 */
	private String checkApplicationCurrentVersion()
	{
		PackageManager packageManager = mContext.getPackageManager();
		PackageInfo packageInfo = null;
		try
		{
			packageInfo = packageManager.getPackageInfo(mContext.getPackageName(),0);
			
		}
		catch(PackageManager.NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return packageInfo.versionName;
	}
	
	/**
	 Method to check internet connection
	 */
	private boolean isNetworkAvailable(Context context)
	{
		final ConnectivityManager connectivityManager = ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE));
		return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
	}
}