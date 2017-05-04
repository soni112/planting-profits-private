package com.decipher.agriculture.data.farm;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * @changed - Abhishek
 * @date - 13-01-2016
 * @desc - for implementing second level hibernate cache
 */
@Cache(region = "planting-Second-level-Cache",  usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
@Entity(name = "CountryResourcesUOM")
@Table(name = "COUNTRY_RESOURCES_UOM", uniqueConstraints = @UniqueConstraint(columnNames = "COUNTRY_RESOURCES_UOM_ID"))
public class CountryResourcesUOM {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "COUNTRY_RESOURCES_UOM_ID")
	private Integer id;
	@Column(name = "UOM_RESOURCES")
	private String uoMResource;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUoMResource() {
		return uoMResource;
	}

	public void setUoMResource(String uoMResource) {
		this.uoMResource = uoMResource;
	}

}
