package com.example.eduard.myapplication;

import android.util.Log;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.BooleanNode;
import org.codehaus.jackson.node.ObjectNode;
import com.example.eduard.myapplication.json.JsonIO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class  HttpURLConnectionDataItemCRUDOperationsImpl implements
		IDataItemCRUDOperationsWithURL {

	protected static String logger = HttpURLConnectionDataItemCRUDOperationsImpl.class
			.getSimpleName();

	private String baseUrl;

	public HttpURLConnectionDataItemCRUDOperationsImpl() {
	}

	@Override
	public List<DataItem> readAllDataItems() {

		Log.i(logger, "readAllItems(): baseUrl: " + baseUrl);

		try {
			// obtain a http url connection from the base url
			HttpURLConnection con = (HttpURLConnection) (new URL(baseUrl))
					.openConnection();
			Log.d(logger, "readAllItems(): got connection: " + con);
			// set the request method (GET is default anyway...)
			con.setRequestMethod("GET");
			// then initiate sending the request...
			InputStream is = con.getInputStream();
			// check the response code
			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				// and create a json node from the input stream
				JsonNode json = JsonIO.readJsonNodeFromInputStream(is);
				// then transform the node into a list of DataItem objects
				List<DataItem> items = JsonIO
						.createDataItemListFromArrayNode((ArrayNode) json);
				Log.i(logger, "readAllItems(): " + items);

				return items;
			} else {
				Log.e(logger,
						"readAllItems(): got response code: "
								+ con.getResponseCode());
			}
		} catch (Exception e) {
			Log.e(logger, "readAllItems(): got exception: " + e);
		}

		return new ArrayList<DataItem>();

	}

	@Override
	public DataItem createDataItem(DataItem item) {
		Log.i(logger, "createItem(): " + item);

		try {
			// obtain a http url connection from the base url
			HttpURLConnection con = (HttpURLConnection) (new URL(baseUrl))
					.openConnection();
			Log.d(logger, "createItem(): got connection: " + con);
			// set the request method
			con.setRequestMethod("POST");
			// indicate that we want to send a request body
			con.setRequestProperty("Content-Type", "application/json");
			con.setDoOutput(true);
			// obtain the output stream and write the item as json object to it
			OutputStream os = con.getOutputStream();
			os.write(createJsonStringFromDataItem(item).getBytes());
			// then initiate sending the request...
			InputStream is = con.getInputStream();
			// check the response code
			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				// and create a json node from the input stream
				JsonNode json = JsonIO.readJsonNodeFromInputStream(is);
				Log.d(logger, "createItem(): got json: " + json);
				// then transform the node into a DataItem object
				item = JsonIO.createDataItemFromObjectNode((ObjectNode) json);
				Log.i(logger, "createItem(): " + item);

				return item;
			} else {
				Log.e(logger,
						"createItem(): got response code: "
								+ con.getResponseCode());
			}
		} catch (Exception e) {
			Log.e(logger, "createItem(): got exception: " + e);
		}

		return null;
	}

	@Override
	public boolean deleteDataItem(long itemId) {
		Log.i(logger, "deleteItem(): " + itemId);

		try {
			// obtain a http url connection from the base url
			HttpURLConnection con = (HttpURLConnection) (new URL(baseUrl + "/"
					+ itemId)).openConnection();
			Log.d(logger, "deleteItem(): got connection: " + con);
			// set the request method
			con.setRequestMethod("DELETE");
			// content type needs to be set here, as well
			con.setRequestProperty("Content-Type", "application/json");
			// then initiate sending the request...
			InputStream is = con.getInputStream();
			// check the response code
			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				// and create a json node from the input stream
				JsonNode json = JsonIO.readJsonNodeFromInputStream(is);
				Log.d(logger, "deleteItem(): got json: " + json + " of class: "
						+ json.getClass());
				// then transform the node into a DataItem object

				return ((BooleanNode) json).getBooleanValue();
			} else {
				Log.e(logger,
						"deleteItem(): got response code: "
								+ con.getResponseCode());
			}
		} catch (Exception e) {
			Log.e(logger, "deleteItem(): got exception: " + e);
		}

		return false;
	}

	/**
	 * the only difference from create is the PUT method, i.e. the common
	 * functionality could be factored out...
	 */
	@Override
	public DataItem updateDataItem(DataItem item) {
		Log.i(logger, "updateItem(): " + item);

		try {
			// obtain a http url connection from the base url
			HttpURLConnection con = (HttpURLConnection) (new URL(baseUrl))
					.openConnection();
			Log.d(logger, "updateItem(): got connection: " + con);
			// set the request method
			con.setRequestMethod("PUT");
			// indicate that we want to send a request body
			con.setRequestProperty("Content-Type", "application/json");
			con.setDoOutput(true);
			// obtain the output stream and write the item as json object to it
			OutputStream os = con.getOutputStream();
			os.write(createJsonStringFromDataItem(item).getBytes());
			// then initiate sending the request...
			InputStream is = con.getInputStream();
			// check the response code
			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				// and create a json node from the input stream
				JsonNode json = JsonIO.readJsonNodeFromInputStream(is);
				Log.d(logger, "updateItem(): got json: " + json);
				// then transform the node into a DataItem object
				item = JsonIO.createDataItemFromObjectNode((ObjectNode) json);
				Log.i(logger, "updateItem(): " + item);

				return item;
			} else {
				Log.e(logger,
						"updateItem(): got response code: "
								+ con.getResponseCode());
			}
		} catch (Exception e) {
			Log.e(logger, "updateItem(): got exception: " + e);
		}

		return null;
	}

	/**
	 * create a string from the data item's json representation
	 * 
	 * @param item
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	protected String createJsonStringFromDataItem(DataItem item)
			throws UnsupportedEncodingException, IOException {

		// create a json node from the
		ObjectNode jsonNode = JsonIO.createObjectNodeFromDataItem(item);
		Log.i(logger, "created jsonNode: " + jsonNode + " from item: " + item);
		// serialise the object
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		JsonIO.writeJsonNodeToOutputStream(jsonNode, os);

		// create a string entity from the output stream, using utf-8 character
		// encoding
		return os.toString();
	}

	@Override
	public DataItem readDataItem(long dateItemId) {
		throw new UnsupportedOperationException(
				"readDataItem() currently not supported by " + this.getClass());
	}

	@Override
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl + "dataitems";
	}

}
