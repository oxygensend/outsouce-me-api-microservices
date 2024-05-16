package com.oxygensend.joboffer.infrastructure.elasticsearch;

import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "job-offers_index")
public class JobOfferES {

    @Id
    private String id;
    @Field
    private String name;
    @Field
    private String slug;
    @Field
    private String description;
    @Field
    private String city;
    @Field
    private String principalFullName;
    @Field(type = FieldType.Text)
    private Set<String> technologies;
    @Field
    private int popularityOrder = 0;
    @Field
    private int numberOfApplications;

    public JobOfferES() {
    }

    public JobOfferES(String id, String name, String slug, String description, String city, String principalFullName, Set<String> technologies,
                      Integer popularityOrder, int numberOfApplications) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.city = city;
        this.principalFullName = principalFullName;
        this.technologies = technologies;
        this.popularityOrder = popularityOrder ==  null ? 0 : popularityOrder;
        this.numberOfApplications = numberOfApplications;
    }

    public String id() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String slug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String description() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String city() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String principalFullName() {
        return principalFullName;
    }

    public void setPrincipalFullName(String principalFullName) {
        this.principalFullName = principalFullName;
    }

    public Set<String> technologies() {
        return technologies;
    }

    public void setTechnologies(Set<String> technologies) {
        this.technologies = technologies;
    }

    public Integer popularityOrder() {
        return popularityOrder;
    }

    public void setPopularityOrder(Integer popularityOrder) {
        this.popularityOrder = popularityOrder;
    }

    public int numberOfApplications() {
        return numberOfApplications;
    }

    public void setNumberOfApplications(int numberOfApplications) {
        this.numberOfApplications = numberOfApplications;
    }
}
