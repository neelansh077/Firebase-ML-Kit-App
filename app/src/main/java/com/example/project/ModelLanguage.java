package com.example.project;

public class ModelLanguage {

    //variables for lang code eg. en and language title eg. English
    String languageCode;
    String languageTitle;

    //constructor
    public ModelLanguage(String languageCode, String languageTitle) {
        this.languageCode = languageCode;
        this.languageTitle = languageTitle;
    }

    //Getter and Setters
    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getLanguageTitle() {
        return languageTitle;
    }

    public void setLanguageTitle(String languageTitle) {
        this.languageTitle = languageTitle;
    }
}
