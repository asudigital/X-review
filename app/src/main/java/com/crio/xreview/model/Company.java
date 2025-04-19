package com.crio.xreview.model;

public class Company {
    private int companyId;
    private String name;
    private String description;
    private int ownerId;

    public Company(int companyId, String name, String description, int ownerId) {
        this.companyId = companyId;
        this.name = name;
        this.description = description;
        this.ownerId = ownerId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getOwnerId() {
        return ownerId;
    }
}