package com.softpal.googleplaystoreappversionnameloader;

/**
 The type Version utils.
 */
public final class VersionUtils
{
	private static final String TAG = VersionUtils.class.getSimpleName();
	
	/**
	 Gets version number.
	 
	 @param version the version
	 
	 @return the version number
	 */
	public static int getVersionNumber(String version)
	{
		String subVersions[] = version.split("\\.");
		int VersionNumber = 0;
		try
		{
			for(int i = 0;i < subVersions.length;i++)
			{
				int temp = Integer.parseInt(subVersions[i]);
				VersionNumber= VersionNumber+temp*((int)Math.pow(100,3-(i)));
				
			}
		}
		catch(NumberFormatException ex)
		{
			ex.printStackTrace();
			VersionNumber = 0;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			VersionNumber = 0;
		}
		return VersionNumber;
		
	}
}
