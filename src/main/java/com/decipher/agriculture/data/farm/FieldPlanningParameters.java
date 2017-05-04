package com.decipher.agriculture.data.farm;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Date;

import javax.persistence.*;

/**
 * @changed - Abhishek
 * @date - 13-01-2016
 * @desc - for implementing second level hibernate cache
 */
@Cache(region = "planting-Second-level-Cache",  usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
@Entity(name = "FieldPlanningParameters")
@Table(name = "FIELD_PLANNING_PARAMETERS", uniqueConstraints = @UniqueConstraint(columnNames = "FIELD_PLANNING_PARAMETERS_ID"))
public class FieldPlanningParameters {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "FIELD_PLANNING_PARAMETERS_ID")
	private Integer id;
	@Column(name = "LAST_CROPPED")
	private Date lastCropped;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getLastCropped() {
		return lastCropped;
	}
	public void setLastCropped(Date lastCropped) {
		this.lastCropped = lastCropped;
	}
}
