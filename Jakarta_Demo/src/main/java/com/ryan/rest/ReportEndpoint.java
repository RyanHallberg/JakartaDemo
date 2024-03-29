package com.ryan.rest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.ryan.model.ReportColumn;
import com.ryan.model.ReportTemplate;

@Stateless
@Path("/reports") //rest/reports
public class ReportEndpoint
{
	@PersistenceContext(unitName = "Jakarta_Demo-persistence-unit")
	private EntityManager em;
	
	@GET
	@Consumes("application/json")
	@Produces("application/json")
	public Response ping(ReportTemplate entity) throws SQLException {
		Connection con = null;
		ReportTemplate reportTemplate = new ReportTemplate();
		List<ReportColumn> reportColumns = new ArrayList<>();
		ReportColumn reportColumn;
		try
		{
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver"); // loads the driver
			
			String url = "jdbc:ucanaccess://" + entity.getDataSource().getPath(); // gets the connection instance
			
			con = DriverManager.getConnection(url + entity.getDataSource().getUserName() + entity.getDataSource().getPassword());
		}
		catch (Exception e)
		{
			System.out.println("Error: " + e);
		}
		try
		{
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(entity.getQuery());
			ResultSetMetaData rsmd = rs.getMetaData();
			
			// set the datasource and the query from the front-end object
			reportTemplate.setDataSource(entity.getDataSource());
			reportTemplate.setQuery(entity.getQuery());

			int numColumns = rsmd.getColumnCount();
			for (int i = 1; i <= numColumns; i++)
			{
				// set the column name, data type, and modifiable
				// based on the index of the column in the underlying table
				reportColumn = new ReportColumn();
				reportColumn.setName(rsmd.getColumnName(i));
				reportColumn.setDataType(rsmd.getColumnTypeName(i));
				reportColumn.setModifiable(false);
				
				reportColumns.add(reportColumn);
			}

			List<String> values;
			while (rs.next())
			{
				values = new ArrayList<>();
				for (int i = 1; i<= numColumns; i++)
				{
					reportColumns.get(i-1).add(rs.getString(i));

				}
			}
			reportTemplate.setColumns(reportColumns);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return Response.ok(reportTemplate).build();
	}
}
