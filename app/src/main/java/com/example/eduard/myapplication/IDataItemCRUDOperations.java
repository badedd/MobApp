package com.example.eduard.myapplication;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import static android.os.FileObserver.DELETE;
import static org.apache.coyote.http11.Constants.GET;
import static org.apache.coyote.http11.Constants.POST;

@Path("/dataitems")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public interface IDataItemCRUDOperations {

	/*
	 * the operations
	 */
	@POST
	public DataItem createDataItem(DataItem item);

	@GET
	public List<DataItem> readAllDataItems();

	@GET
	@Path("/{itemId}")
	public DataItem readDataItem(@PathParam("itemId")long dateItemId);

	@PUT
	public DataItem updateDataItem(DataItem item);

	@DELETE
	@Path("/{itemId}")
	public boolean deleteDataItem(@PathParam("itemId")long dataItemId);

}
