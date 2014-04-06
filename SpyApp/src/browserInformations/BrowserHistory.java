package browserInformations;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.Browser;
import android.util.Log;

public class BrowserHistory {

ArrayList<BrowserPage> browserPageList;

	public ArrayList<BrowserPage> getBrowserPageList() {
	return browserPageList;
}

public void setBrowserPageList(ArrayList<BrowserPage> browserPageList) {
	this.browserPageList = browserPageList;
}

	public BrowserHistory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void getBrowserHist(Context context)  {
		ArrayList<BrowserPage> browserlist= new ArrayList<BrowserPage>();
		ContentResolver cr = context.getContentResolver();
        Cursor mCur = cr.query(Browser.BOOKMARKS_URI,
                Browser.HISTORY_PROJECTION, null, null, null);
        mCur.moveToFirst();
        if (mCur.moveToFirst() && mCur.getCount() > 0) {
            while (mCur.isAfterLast() == false) {
               // Log.v("titleIdx", mCur .getString(Browser.HISTORY_PROJECTION_TITLE_INDEX));
                //Log.v("urlIdx", mCur .getString(Browser.HISTORY_PROJECTION_URL_INDEX));
                
            BrowserPage browserPage=new BrowserPage(mCur.getString(Browser.HISTORY_PROJECTION_TITLE_INDEX), mCur.getString(Browser.HISTORY_PROJECTION_URL_INDEX));
            browserlist.add(browserPage);
                mCur.moveToNext();
           }
            setBrowserPageList(browserlist);
      }
	}
	}
