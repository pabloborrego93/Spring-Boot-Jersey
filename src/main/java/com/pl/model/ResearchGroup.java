package com.pl.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@Entity(name = "grupo")
@Table(name = "grupo")
public class ResearchGroup implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 959832297259536451L;
	
	public ResearchGroup() {}
	
	public ResearchGroup(String tic, String englishName, String spanishName) {
		this.tic = tic;
		this.englishName = englishName;
		this.spanishName = spanishName;
	}

	public ResearchGroup(String tic, String englishName, String spanishName, Set<GroupMember> members) {
		this.tic = tic;
		this.englishName = englishName;
		this.spanishName = spanishName;
		this.members = members;
	}
	
	@Id // Anotación para que tic sea el Id en la BD
	private String tic;
	@Column(unique = true)
	private String englishName;
	@Column(unique = true)
	private String spanishName;
	
	// Esta anotación nos servirá para crear la relación
	// de uno a muchos
	@OneToMany(cascade = {CascadeType.MERGE}, fetch=FetchType.EAGER, mappedBy="grupo", orphanRemoval=true)
	private Set<GroupMember> members = new HashSet<GroupMember>();
	
	public String getTic() {
		return tic;
	}

	public void setTic(String tic) {
		this.tic = tic;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public String getSpanishName() {
		return spanishName;
	}
	public void setSpanishName(String spanishName) {
		this.spanishName = spanishName;
	}
	public Set<GroupMember> getMembers() {
		return members;
	}
	public void setMembers(Set<GroupMember> members) {
		this.members = members;
	}
}