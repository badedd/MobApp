package com.example.eduard.myapplication;
import android.content.Context;

public interface IDataItemCRUDOperationsWithContext extends IDataItemCRUDOperations {

	/*
	 * pass a context
	 */
	public void setContext(Context context);
	
	/*
	 * signal finalisation
	 */
	public void finalise();

}
