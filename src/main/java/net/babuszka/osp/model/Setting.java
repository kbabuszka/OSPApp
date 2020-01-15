package net.babuszka.osp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "settings")
@Getter
@Setter
public class Setting {
	
	@Autowired
	public Setting() {
		super();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name")
	@NotEmpty(message = "{setting.name.empty}")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "example")
	private String example;
	
	@Column(name = "value")
	@NotEmpty(message = "{setting.name.empty}")
	private String value;

}
