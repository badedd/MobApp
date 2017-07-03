package com.example.eduard.myapplication.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.eduard.myapplication.DataItem;
import com.example.eduard.myapplication.IDataItemCRUDOperations;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;

public class DataItemContentProvider extends ContentProvider {

	protected static String logger = DataItemContentProvider.class
			.getSimpleName();

	/**
	 * the baseUrl
	 * private String baseUrl = "http://10.0.2.2:8080/DataAccessRemoteWebapp/";
	 */
	private String baseUrl = "localhost:8080/DataAccessRemoteWebapp/";

	// the accessor for the data source
	private IDataItemCRUDOperations datasourceAccessor;

	public DataItemContentProvider() {
		Log.i(logger, "creating DataItemContentProvider()");
	}

	// construct
	public DataItemContentProvider(IDataItemCRUDOperations datasourceAccessor) {
		Log.i(logger,
				"creating DataItemContentProvider() using datasourceAccessor: "
						+ datasourceAccessor);
		this.datasourceAccessor = datasourceAccessor;
	}

	@Override
	public int delete(Uri uri, String arg1, String[] arg2) {
		Log.d(logger, "delete(): uri is: " + uri);
		if (isDataItemListUri(uri)) {
			// we only use single row delete, using the last path segment
			String lastSegment = uri.getPathSegments().get(
					uri.getPathSegments().size() - 1);
			Log.d(logger, "delete(): last path segment: " + lastSegment);

			if (lastSegment != null) {
				if (this.datasourceAccessor.deleteDataItem(Long
						.parseLong(lastSegment))) {
					return 1;
				}
			}

			return 0;
		}

		String err = "cannot handle unknown uri: " + uri;
		throw new RuntimeException(err);
	}

	@Override
	public String getType(Uri uri) {
		Log.d(logger, "getType() for uri with path: " + uri.getPath());
		if (DataItemContract.URI_DATAITEM_LIST_AUTHORITY.equals(uri
				.getAuthority())
				&& (uri.getPath() == null || "".equals(uri.getPath()))) {
			return DataItemContract.MIMETYPE_DATAITEM_LIST;
		} else {
			return DataItemContract.MIMETYPE_DATAITEM_ITEM;
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Log.d(logger, "insert(): uri is: " + uri);
		Log.d(logger, "insert(): values are: " + values);

		if (isDataItemListUri(uri)) {
			// create a DataItem object
			DataItem item = createDataItemFromContentValues(values);
			Log.d(logger, "insert(): item is: " + item);

			item = datasourceAccessor.createDataItem(item);

			// now we read out the id and create the uri from it
			uri = new Uri.Builder()
					.authority(DataItemContract.URI_DATAITEM_LIST_AUTHORITY)
					.appendPath(DataItemContract.URI_DATAITEM_LIST_PATH)
					.appendPath(String.valueOf(item.get_dbID())).build();
			Log.d(logger, "insert(): will return uri: " + uri);

			return uri;
		}

		String err = "cannot handle unknown uri: " + uri;
		throw new RuntimeException(err);
	}

	@Override
	public boolean onCreate() {
		Log.i(logger, "onCreate()");

		return true;
	}

	public Bundle call(String method, String url, Bundle bundle) {
		if (DataItemContract.METHOD_SET_BASE_URL.equals(method)) {
			Log.i(logger, "setBaseUrl(): " + url);

			this.baseUrl = url;

			if (this.datasourceAccessor == null) {
				// this must be initialised asynchronously...
				new AsyncTask<Void, Void, Object>() {
					public Object doInBackground(Void... args) {
						datasourceAccessor = ProxyFactory.create(
								IDataItemCRUDOperations.class, baseUrl,
								new ApacheHttpClient4Executor());

						Log.i(logger, "setBaseUrl(): created accessor: "
								+ datasourceAccessor);
						return null;
					}
				}.execute();
			} else {
				Log.i(logger, "setBaseUrl(): accessor already exists: "
						+ this.datasourceAccessor);
			}

		}
		return new Bundle();
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
		Log.i(logger, "query() on uri: " + uri);

		if (uri == null || isDataItemListUri(uri)) {
			// we always query the full content set
			return new DataItemListCursor(
					this.datasourceAccessor.readAllDataItems(),
					this.datasourceAccessor);
		}

		String err = "cannot handle unknown uri: " + uri;
		throw new RuntimeException(err);
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
		Log.d(logger, "update(): uri is: " + uri);
		if (isDataItemListUri(uri)) {
			// we only process single row updates
			DataItem item = createDataItemFromContentValues(values);
			Log.d(logger, "update(): item is: " + item);
			item = this.datasourceAccessor.updateDataItem(item);

			if (item != null)
				return 1;
			return 0;
		}

		String err = "cannot handle unknown uri: " + uri;
		throw new RuntimeException(err);
	}

	private boolean isDataItemListUri(Uri uri) {
		return DataItemContract.URI_DATAITEM_LIST_AUTHORITY.equals(uri
				.getAuthority())
				&& DataItemContract.URI_DATAITEM_LIST_PATH.equals(uri
						.getPathSegments().get(0));
	}

	private DataItem createDataItemFromContentValues(ContentValues values) {
		// create a DataItem object
		DataItem item = new DataItem();
		String name = values.getAsString(DataItemContract.COLUMN_ITEM_NAME);
		String description = values
				.getAsString(DataItemContract.COLUMN_ITEM_DESCRIPTION);
		long id = values.getAsLong(DataItemContract.COLUMN_ITEM_ID);
		int i = (int) id;
		if (name != null)
			item.setName(name);
		if (description != null)
			item.setDescription(description);
		if (id != -1)
			item.set_dbID(i);

		Log.d(logger, "createDataItemFromContentValues(): item is: " + item);

		return item;
	}

}
