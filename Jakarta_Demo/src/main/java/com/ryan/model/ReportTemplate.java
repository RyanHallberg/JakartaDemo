package com.ryan.model;

import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ReportTemplate implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	private static final long serialVersionUID = 1L;
	
	@Column
	private String name;

	@Column
	private Datasource dataSource;

	@Column
	private String query;

	@Transient
	private List<ReportColumn> reportColumns = new ArrayList<>();


	public void add(ReportColumn column) {
		this.reportColumns.add(column);
	}
	
	public List<ReportColumn> getColumns()
	{
		return reportColumns;
	}

	public void setColumns(List<ReportColumn> reportColumns)
	{
		this.reportColumns = reportColumns;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ReportTemplate)) {
			return false;
		}
		ReportTemplate other = (ReportTemplate) obj;
		if (id != null) {
			if (!id.equals(other.id)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public Datasource getDataSource() {
		return dataSource;
	}

	public void setDataSource(Datasource dataSource) {
		this.dataSource = dataSource;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (query != null && !query.trim().isEmpty())
			result += "query: " + query;
		return result;
	}
}