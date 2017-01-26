package com.pl.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlRootElement
@Entity(name = "gmember")
@Table(name = "gmember")
public class GroupMember implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -544537854910123270L;
	
	public GroupMember(){}
	
	public GroupMember(
			Integer id, 
			String name, 
			String surname, 
			String scientistName, 
			String department,
			String phone) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.scientistName = scientistName;
		this.department = department;
		this.phone = phone;
	}
	
	// Anotaciones para que el atributo id, sea el Id en la BD 
	// y además sea autonumérico
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private String surname;
	private String scientistName;
	private String department;
	private String phone;
	
	// Relación de muchos a uno
	@ManyToOne
	@JsonIgnore
	private ResearchGroup grupo;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the scientistName
	 */
	public String getScientistName() {
		return scientistName;
	}

	/**
	 * @param scientistName
	 *            the scientistName to set
	 */
	public void setScientistName(String scientistName) {
		this.scientistName = scientistName;
	}

	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @param department
	 *            the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public ResearchGroup getGrupo() {
		return grupo;
	}

	public void setGrupo(ResearchGroup grupo) {
		this.grupo = grupo;
	}
}