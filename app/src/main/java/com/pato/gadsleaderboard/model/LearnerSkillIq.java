package com.pato.gadsleaderboard.model;

import org.jetbrains.annotations.NotNull;

public class LearnerSkillIq {

    private String name;
    private int score;
    private String country;
    private String badgeUrl;

    // getters.
    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String getCountry() {
        return country;
    }

    public String getBadgeUrl() {
        return badgeUrl;
    }

    @NotNull
    @Override
    public String toString() {
        return "LearnerSkillIq{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", country='" + country + '\'' +
                ", badgeUrl='" + badgeUrl + '\'' +
                '}';
    }
}
