package org.github.bbaron.springcfg.example.webapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
public class AppConfig {

    @Column(unique = true)
    @Size(min = 1, max = 100)
    private String propertyName;

    @Size(max = 100)
    private String propertyValue;

	public String getPropertyName() {
        return this.propertyName;
    }

	public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

	public String getPropertyValue() {
        return this.propertyValue;
    }

	public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	@Version
    @Column(name = "version")
    private Integer version;

	public Long getId() {
        return this.id;
    }

	public void setId(Long id) {
        this.id = id;
    }

	public Integer getVersion() {
        return this.version;
    }

	public void setVersion(Integer version) {
        this.version = version;
    }
}
