package com.relurori.hanblocks;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.json.JSONException;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

public class SpreadsheetUtils {

	public static class GetQA extends AsyncTask<Void, Void, Void> {

		private static final String TAG = GetQA.class.getSimpleName();
		
		Activity mActivity;
		String mToken;

		public GetQA(Activity activity, String token) {
			this.mActivity = activity;
			this.mToken = token;
		}

		@Override
		protected Void doInBackground(Void... params) {
			Log.d(TAG,"doInBackground");
			try {
				getFromSpreadsheet();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		public void getFromSpreadsheet() throws IOException, JSONException {
			Log.d(TAG,"getFromSpreadsheet");
			if (mToken == null) return;
			
			SpreadsheetService spreadsheet= new SpreadsheetService("v1");
	        spreadsheet.setProtocolVersion(SpreadsheetService.Versions.V3);
	        
	        try {
	            spreadsheet.setAuthSubToken(mToken);
	            URL metafeedUrl = new URL("https://spreadsheets.google.com/feeds/spreadsheets/private/full");
	            SpreadsheetFeed feed = spreadsheet.getFeed(metafeedUrl, SpreadsheetFeed.class);

	            List<SpreadsheetEntry> spreadsheets = feed.getEntries();

	            for (SpreadsheetEntry service : spreadsheets) {             
	                
	            	//Log.d(TAG,service.getTitle().getPlainText());
	                
	            	if (service.getTitle().getPlainText().equalsIgnoreCase("PublicTest")) {
	                	WorksheetFeed worksheetFeed = spreadsheet.getFeed(service.getWorksheetFeedUrl(), WorksheetFeed.class);
	                	List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
	                    WorksheetEntry worksheet = worksheets.get(0);

	                    URL listFeedUrl = worksheet.getListFeedUrl();
	                    ListFeed listFeed = spreadsheet.getFeed(listFeedUrl, ListFeed.class);

						// Iterate through each row, printing its cell values.
						for (ListEntry row : listFeed.getEntries()) {
							// Print the first column's cell value
							Log.d(TAG, row.getTitle().getPlainText() + "\t");
							// Iterate over the remaining columns, and print
							// each cell value
							for (String tag : row.getCustomElements().getTags()) {
								Log.d(TAG, row.getCustomElements().getValue(tag) + "\t");
							}
						}

	                }
	           }
	        } catch (AuthenticationException e) {           
	            e.printStackTrace();
	        } catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

}
