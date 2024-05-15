package com.oxygensened.userprofile.infrastructure.elasticsearch;

import com.oxygensened.userprofile.domain.entity.part.AccountType;
import jakarta.persistence.Id;
import java.util.Set;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "user_index")
public class UserES {

    @Id
    private String id;
    @Field
    private String fullName;
    @Field
    private String imagePath;
    @Field
    private String description;
    @Field
    private String activeJobPosition;
    @Field
    private double popularityOrder;
    @Field
    private AccountType accountType;
    @Field(type = FieldType.Text)
    private Set<String> technologies;
    @Field
    private String city;

    public UserES() {
    }

    public UserES(String id, String fullName, String imagePath, String description, String activeJobPosition, double popularityOrder,
                  AccountType accountType, Set<String> technologies, String city) {
        this.id = id;
        this.fullName = fullName;
        this.imagePath = imagePath;
        this.description = description;
        this.activeJobPosition = activeJobPosition;
        this.popularityOrder = popularityOrder;
        this.accountType = accountType;
        this.technologies = technologies;
        this.city = city;
    }

    public String id() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String fullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String imagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String description() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String activeJobPosition() {
        return activeJobPosition;
    }

    public void setActiveJobPosition(String activeJobPosition) {
        this.activeJobPosition = activeJobPosition;
    }

    public double popularityOrder() {
        return popularityOrder;
    }

    public void setPopularityOrder(double popularityOrder) {
        this.popularityOrder = popularityOrder;
    }

    public AccountType accountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Set<String> technologies() {
        return technologies;
    }

    public void setTechnologies(Set<String> technologies) {
        this.technologies = technologies;
    }

    public String city() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
