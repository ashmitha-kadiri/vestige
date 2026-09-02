package com.vestige.dto.response;

public class RewardCatalogItemDTO {

    private String id;
    private String title;
    private String description;
    private Integer pointsCost;
    private String category;
    private String iconName;
    private String badgeText;

    public RewardCatalogItemDTO() {}

    public RewardCatalogItemDTO(String id, String title, String description, Integer pointsCost, String category, String iconName, String badgeText) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.pointsCost = pointsCost;
        this.category = category;
        this.iconName = iconName;
        this.badgeText = badgeText;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPointsCost() {
        return pointsCost;
    }

    public void setPointsCost(Integer pointsCost) {
        this.pointsCost = pointsCost;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public String getBadgeText() {
        return badgeText;
    }

    public void setBadgeText(String badgeText) {
        this.badgeText = badgeText;
    }
}
