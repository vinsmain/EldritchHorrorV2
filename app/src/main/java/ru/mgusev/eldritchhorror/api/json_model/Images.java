package ru.mgusev.eldritchhorror.api.json_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Images {

    @SerializedName("image_intro")
    @Expose
    private String imageIntro;
    @SerializedName("float_intro")
    @Expose
    private String floatIntro;
    @SerializedName("image_intro_alt")
    @Expose
    private String imageIntroAlt;
    @SerializedName("image_intro_caption")
    @Expose
    private String imageIntroCaption;
    @SerializedName("image_fulltext")
    @Expose
    private String imageFulltext;
    @SerializedName("float_fulltext")
    @Expose
    private String floatFulltext;
    @SerializedName("image_fulltext_alt")
    @Expose
    private String imageFulltextAlt;
    @SerializedName("image_fulltext_caption")
    @Expose
    private String imageFulltextCaption;

    public String getImageIntro() {
        return imageIntro;
    }

    public void setImageIntro(String imageIntro) {
        this.imageIntro = imageIntro;
    }

    public String getFloatIntro() {
        return floatIntro;
    }

    public void setFloatIntro(String floatIntro) {
        this.floatIntro = floatIntro;
    }

    public String getImageIntroAlt() {
        return imageIntroAlt;
    }

    public void setImageIntroAlt(String imageIntroAlt) {
        this.imageIntroAlt = imageIntroAlt;
    }

    public String getImageIntroCaption() {
        return imageIntroCaption;
    }

    public void setImageIntroCaption(String imageIntroCaption) {
        this.imageIntroCaption = imageIntroCaption;
    }

    public String getImageFulltext() {
        return imageFulltext;
    }

    public void setImageFulltext(String imageFulltext) {
        this.imageFulltext = imageFulltext;
    }

    public String getFloatFulltext() {
        return floatFulltext;
    }

    public void setFloatFulltext(String floatFulltext) {
        this.floatFulltext = floatFulltext;
    }

    public String getImageFulltextAlt() {
        return imageFulltextAlt;
    }

    public void setImageFulltextAlt(String imageFulltextAlt) {
        this.imageFulltextAlt = imageFulltextAlt;
    }

    public String getImageFulltextCaption() {
        return imageFulltextCaption;
    }

    public void setImageFulltextCaption(String imageFulltextCaption) {
        this.imageFulltextCaption = imageFulltextCaption;
    }
}