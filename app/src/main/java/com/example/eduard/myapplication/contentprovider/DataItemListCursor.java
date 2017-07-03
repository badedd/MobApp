package com.example.eduard.myapplication.contentprovider;

import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.eduard.myapplication.DataItem;
import com.example.eduard.myapplication.IDataItemCRUDOperations;

import java.util.ArrayList;
import java.util.List;

public class DataItemListCursor implements Cursor {

	protected static String logger = DataItemListCursor.class.getSimpleName();

	// we always use a full representation of the DataItems, i.e. we will always
	// have all attributes available and can use constants for the column
	// indices
	private static final int COLUMN_ITEM_ID_INDEX = 0;

	private static final int COLUMN_ITEM_NAME_INDEX = 1;

	private static final int COLUMN_ITEM_DESCRIPTION_INDEX = 2;
	
	// we use a list of items
	private List<DataItem> items;

	// the current element position
	private int currentItemPosition = -1;
	
	// the accessor that we can use to access the data source 
	private IDataItemCRUDOperations dataItemAccessor;

	// observers
	private List<DataSetObserver> dataSetObservers = new ArrayList<DataSetObserver>();
	private List<ContentObserver> contentObservers = new ArrayList<ContentObserver>();
	
	// instantiate the cursor with a list of items and an accessor which we can use for requerying...
	public DataItemListCursor(List<DataItem> items, IDataItemCRUDOperations dataItemAccessor) {
		this.items = items;
		this.dataItemAccessor = dataItemAccessor;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public void copyStringToBuffer(int arg0, CharArrayBuffer arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub

	}

	@Override
	public byte[] getBlob(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public int getColumnIndex(String column) {
		if (DataItemContract.COLUMN_ITEM_ID.equals(column)) {
			return COLUMN_ITEM_ID_INDEX;
		} else if (DataItemContract.COLUMN_ITEM_NAME.equals(column)) {
			return COLUMN_ITEM_NAME_INDEX;
		} else if (DataItemContract.COLUMN_ITEM_DESCRIPTION.equals(column)) {
			return COLUMN_ITEM_DESCRIPTION_INDEX;
		} else {
			Log.e(logger, "got unknown column name: " + column);
		}

		return -1;
	}

	@Override
	public int getColumnIndexOrThrow(String arg0)
			throws IllegalArgumentException {
		return getColumnIndex(arg0);
	}

	@Override
	public String getColumnName(int columnIndex) {
		//Log.d(logger, "getColumnName(): " + columnIndex);
		
		switch (columnIndex) {
		case COLUMN_ITEM_ID_INDEX:
			return DataItemContract.COLUMN_ITEM_ID;
		case COLUMN_ITEM_NAME_INDEX:
			return DataItemContract.COLUMN_ITEM_NAME;
		case COLUMN_ITEM_DESCRIPTION_INDEX:
			return DataItemContract.COLUMN_ITEM_DESCRIPTION;
		}

		Log.e(logger, "got unknown column index: " + columnIndex);

		return null;
	}

	@Override
	public String[] getColumnNames() {
		String[] names = new String[3];
		names[COLUMN_ITEM_ID_INDEX] = DataItemContract.COLUMN_ITEM_ID;
		names[COLUMN_ITEM_NAME_INDEX] = DataItemContract.COLUMN_ITEM_NAME;
		names[COLUMN_ITEM_DESCRIPTION_INDEX] = DataItemContract.COLUMN_ITEM_DESCRIPTION;

		return names;
	}

	@Override
	public int getCount() {
		return this.items.size();
	}

	@Override
	public double getDouble(int columnIndex) {
		return (Double) getColumnValue(columnIndex);
	}

	@Override
	public Bundle getExtras() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getFloat(int columnIndex) {
		return (Float) getColumnValue(columnIndex);
	}

	@Override
	public int getInt(int columnIndex) {
		return (Integer) getColumnValue(columnIndex);
	}

	@Override
	public long getLong(int columnIndex) {
		return (Long) getColumnValue(columnIndex);
	}

	@Override
	public int getPosition() {
		return currentItemPosition;
	}

	@Override
	public short getShort(int columnIndex) {
		return (Short) getColumnValue(columnIndex);
	}

	@Override
	public String getString(int columnIndex) {
		Object value = getColumnValue(columnIndex);
		if (value == null)
			return null;
		return String.valueOf(value);
	}

	@Override
	public int getType(int columnIndex) {
		Object obj = getColumnValue(columnIndex);

		if (obj == null)
			return FIELD_TYPE_NULL;

		if (obj instanceof Float) {
			return FIELD_TYPE_FLOAT;
		} else if (obj instanceof Integer) {
			return FIELD_TYPE_INTEGER;
		}

		// we will use String as default
		return FIELD_TYPE_STRING;
	}

	@Override
	public boolean getWantsAllOnMoveCalls() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setExtras(Bundle extras) {

	}

	@Override
	public boolean isAfterLast() {
		return currentItemPosition >= this.getCount();
	}

	@Override
	public boolean isBeforeFirst() {
		return currentItemPosition < 0;
	}

	@Override
	public boolean isClosed() {
		return false;
	}

	@Override
	public boolean isFirst() {
		return currentItemPosition == 0;
	}

	@Override
	public boolean isLast() {
		return currentItemPosition == this.getCount() -1;
	}

	@Override
	public boolean isNull(int columnIndex) {
		return getColumnValue(columnIndex) == null;
	}

	@Override
	public boolean move(int offset) {
		currentItemPosition += offset;
		if (isBeforeFirst()) {
			currentItemPosition = -1;
			return false;
		}
		if (isAfterLast()) {
			currentItemPosition = this.getCount();
			return false;
		}
		
		return true;
	}

	@Override
	public boolean moveToFirst() {
		currentItemPosition = 0;
		return true;
	}

	@Override
	public boolean moveToLast() {
		currentItemPosition = this.getCount() -1;
		return true;
	}

	@Override
	public boolean moveToNext() {
		if (isLast() || isAfterLast())
			return false;
			
		currentItemPosition++;
		return true;
	}

	@Override
	public boolean moveToPosition(int position) {
		if (position >= 0 && position < getCount()) {
			this.currentItemPosition = position;
			return true;
		}
		
		return false;
	}

	@Override
	public boolean moveToPrevious() {
		if (currentItemPosition <= 0) {
			return false;
		}
		currentItemPosition--;
		return true;
	}

	@Override
	public void registerContentObserver(ContentObserver observer) {
		Log.i(logger,"registerContentObserver(): " + observer);
		this.contentObservers.add(observer);
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		Log.i(logger,"registerDataSetObserver(): " + observer);
		this.dataSetObservers.add(observer);
	}

	@Override
	public boolean requery() {
		Log.i(logger,"requery()...");
		
		// we use the accessor to load the full item list again
		this.items = this.dataItemAccessor.readAllDataItems();
		this.currentItemPosition = -1;
		
//		for (ContentObserver observer : this.contentObservers) {
//			observer.onChange(true);
//		}
		for (DataSetObserver observer : this.dataSetObservers) {
			observer.onChanged();
		}
		
		return true;
	}

	@Override
	public Bundle respond(Bundle extras) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNotificationUri(ContentResolver cr, Uri uri) {
		// TODO Auto-generated method stub

	}

	@Override
	public Uri getNotificationUri() {
		return null;
	}

	@Override
	public void unregisterContentObserver(ContentObserver observer) {
		Log.i(logger,"unregisterContentObserver(): " + observer);
		this.contentObservers.remove(observer);
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		Log.i(logger,"unregisterDataSetObserver(): " + observer);
		this.dataSetObservers.remove(observer);
	}

	private DataItem getCurrentItem() {
		return this.items.get(currentItemPosition);
	}

	// get the value of some field of the current item
	private Object getColumnValue(int columnIndex) {
		
		//Log.d(logger, "getColumnValue(): " + columnIndex);
		
		switch (columnIndex) {
		case COLUMN_ITEM_ID_INDEX:
			return getCurrentItem().get_dbID();
		case COLUMN_ITEM_NAME_INDEX:
			return getCurrentItem().getName();
		case COLUMN_ITEM_DESCRIPTION_INDEX:
			return getCurrentItem().getDescription();
		}

		Log.e(logger, "got unknown column index: " + columnIndex);
		return null;
	}

}
