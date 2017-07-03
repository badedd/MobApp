package com.example.eduard.myapplication;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.BooleanNode;
import org.codehaus.jackson.node.ObjectNode;
import com.example.eduard.myapplication.json.JsonIO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class HttpClientDataItemCRUDOperationsImpl implements IDataItemCRUDOperationsWithURL {

	protected static String logger = HttpClientDataItemCRUDOperationsImpl.class
			.getSimpleName();

	private static final String CHARSET = "UTF-8";

	public static final String MIME_TYPE = "application/json";

	/**
	 * we use an instance attribute for the client
	 */
	private HttpClient client;

	/**
	 * the base url
	 */
	private String baseUrl = "localhost:8080";

	public HttpClientDataItemCRUDOperationsImpl() {
		this.client = new DefaultHttpClient();
	}

	@Override
	public List<DataItem> readAllDataItems() {

		Log.i(logger, "readAllItems()");

		try {
			// create a get method
			HttpGet method = new HttpGet(baseUrl);

			HttpResponse response = client.execute(method);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// obtain the input stream from the response
				InputStream is = response.getEntity().getContent();
				// try to read a json node from the stream
				JsonNode json = JsonIO.readJsonNodeFromInputStream(is);
				Log.i(logger, "readAllItems(): json node is: " + json);

				// create the itemlist from the json node
				return JsonIO.createDataItemListFromArrayNode((ArrayNode) json);

			} else {
				Log.e(logger,
						"readAllItems():  http request failed. Got status code: "
								+ response.getStatusLine());
			}

		} catch (Exception e) {
			Log.e(logger, "readAllItems(): got exception: " + e, e);
		}

		return new ArrayList<DataItem>();

	}

	@Override
	public DataItem createDataItem(DataItem item) {

		Log.i(logger, "createItem(): " + item);

		try {
			// create a get method
			HttpPost method = new HttpPost(baseUrl);
			// add the data item as json object to the method's output stream
			method.setEntity(createHttpEntityFromDataItem(item));

			HttpResponse response = client.execute(method);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// obtain the input stream from the response
				InputStream is = response.getEntity().getContent();
				// try to read a json node from the stream
				JsonNode json = JsonIO.readJsonNodeFromInputStream(is);
				Log.i(logger, "createItem(): json node is: " + json);

				// create the itemlist from the json node
				return JsonIO.createDataItemFromObjectNode((ObjectNode) json);

			} else {
				Log.e(logger,
						"createItem():  http request failed. Got status code: "
								+ response.getStatusLine());
			}

		} catch (Exception e) {
			Log.e(logger, "createItem(): got exception: " + e, e);
		}

		return null;
	}

	@Override
	public boolean deleteDataItem(long itemId) {

		Log.i(logger, "deleteItem(): " + itemId);

		try {
			// create a get method
			HttpDelete method = new HttpDelete(baseUrl + "/" + itemId);

			HttpResponse response = client.execute(method);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// obtain the input stream from the response
				InputStream is = response.getEntity().getContent();

				JsonNode json = JsonIO.readJsonNodeFromInputStream(is);
				
				return ((BooleanNode)json).getBooleanValue();
			} else {
				Log.e(logger,
						"deleteItem():  http request failed. Got status code: "
								+ response.getStatusLine());
			}

		} catch (Exception e) {
			Log.e(logger, "deleteItem(): got exception: " + e, e);
		}

		return false;
	
	}

	@Override
	public DataItem updateDataItem(DataItem item) {

		Log.i(logger, "upateItem(): " + item);

		try {
			// create a get method
			HttpPut method = new HttpPut(baseUrl);
			// add the data item as json object to the method's output stream
			method.setEntity(createHttpEntityFromDataItem(item));

			HttpResponse response = client.execute(method);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// obtain the input stream from the response
				InputStream is = response.getEntity().getContent();
				// try to read a json node from the stream
				JsonNode json = JsonIO.readJsonNodeFromInputStream(is);
				Log.i(logger, "upateItem(): json node is: " + json);

				// create the itemlist from the json node
				return JsonIO.createDataItemFromObjectNode((ObjectNode) json);

			} else {
				Log.e(logger,
						"upateItem():  http request failed. Got status code: "
								+ response.getStatusLine());
			}

		} catch (Exception e) {
			Log.e(logger, "upateItem(): got exception: " + e, e);
		}

		return null;

	}

	protected HttpEntity createHttpEntityFromDataItem(DataItem item)
			throws UnsupportedEncodingException, IOException {

		// create a json node from the
		ObjectNode jsonNode = JsonIO.createObjectNodeFromDataItem(item);
		Log.i(logger, "created jsonNode: " + jsonNode + " from item: " + item);
		// serialise the object
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		JsonIO.writeJsonNodeToOutputStream(jsonNode, os);

		// create a string entity from the output stream, using utf-8 character encoding
		StringEntity se = new StringEntity(os.toString(CHARSET));

		// add meta information for the mime type
		se.setContentType(MIME_TYPE);
		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
				"application/json"));

		return se;
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
