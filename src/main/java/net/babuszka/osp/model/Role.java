package net.babuszka.osp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {
	
	@Autowired
	public Role() {
		super();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	Integer id;

	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", description=" + description + "]";
	}
	
}
