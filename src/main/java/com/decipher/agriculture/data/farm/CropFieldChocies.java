package com.decipher.agriculture.data.farm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @changed - Abhishek
 * @date - 13-01-2016
 * @desc - for implementing second level hibernate cache
 */
@Cache(region = "planting-Second-level-Cache", usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
@Entity(name = "CropFieldChocies")
@Table(name = "CROP_FIELD_CHOICES", uniqueConstraints = @UniqueConstraint(columnNames = "CROP_FIELD_CHOICES_ID"))
public class CropFieldChocies  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CROP_FIELD_CHOICES_ID")
    private Integer id;
    @Column(name = "CROP_FIELD_CHOICES")
    private String cropFieldChoice;

    @ManyToOne
    @JoinColumn(name = "FIELD_INFO_ID")
    /**
     * @changed - Abhishek
     * @date - 09-02-2016
     * @desc - @JsonIgnore for removing org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON: Infinite recursion (StackOverflowError)
     */
    @JsonIgnore
    private FieldInfo name;

    @ManyToOne
    @JoinColumn(name = "CROP_TYPE_ID")
    /**
     * @changed - Abhishek
     * @date - 07-01-2016
     * @desc - @JsonIgnore for removing org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON: Infinite recursion (StackOverflowError)
     */
    @JsonIgnore
    private CropType cropType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCropFieldChoice() {
        return cropFieldChoice;
    }

    public void setCropFieldChoice(String cropFieldChoice) {
        this.cropFieldChoice = cropFieldChoice;
    }

    public FieldInfo getName() {
        return name;
    }

    public void setName(FieldInfo name) {
        this.name = name;
    }

    public CropType getCropType() {
        return cropType;
    }

    public void setCropType(CropType cropType) {
        this.cropType = cropType;
    }

}
