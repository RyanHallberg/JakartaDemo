package com.ryan.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ReportColumn implements Serializable
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	private static final long serialVersionUID = 1L;
	
	private String name;
	private Boolean modifiable;
	private String dataType;
	@ElementCollection(targetClass = String.class)
	private List<String> values = new ArrayList<>();
	
	
	public void add(String value) {
		this.values.add(value);
	}
	
	public List<String> getValues()
	{
		return values;
	}
	public void setValues(List<String> values)
	{
		this.values = values;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public Boolean getModifiable()
	{
		return modifiable;
	}
	public void setModifiable(Boolean modifiable)
	{
		this.modifiable = modifiable;
	}
	public Object getDataType()
	{
		return dataType;
	}
	public void setDataType(String dataType)
	{
		this.dataType = dataType;
	}
}
