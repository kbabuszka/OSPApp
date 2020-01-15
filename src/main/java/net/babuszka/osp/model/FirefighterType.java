package net.babuszka.osp.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "firefighter_types")
@Getter
@Setter
public class FirefighterType {

	@Autowired
	public FirefighterType() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	@Size(min = 3, message = "{firefightertype.name.size}")
	@NotEmpty(message = "{firefightertype.name.empty}")
	private String name;

}
