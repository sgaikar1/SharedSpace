//-----------------------------------com.emagicindia.realeastate.model.CityItem.java-----------------------------------

        package com.emagicindia.realeastate.model;

        import javax.annotation.Generated;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class CityArrayItem {

    @SerializedName("City_Id")
    @Expose
    private String cityId;
    @SerializedName("City_Name")
    @Expose
    private String cityName;
    @SerializedName("ACTIVE")
    @Expose
    private String aCTIVE;

    /**
     *
     * @return
     * The cityId
     */
    public String getCityId() {
        return cityId;
    }

    /**
     *
     * @param cityId
     * The City_Id
     */
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    /**
     *
     * @return
     * The cityName
     */
    public String getCityName() {
        return cityName;
    }

    /**
     *
     * @param cityName
     * The City_Name
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     *
     * @return
     * The aCTIVE
     */
    public String getACTIVE() {
        return aCTIVE;
    }

    /**
     *
     * @param aCTIVE
     * The ACTIVE
     */
    public void setACTIVE(String aCTIVE) {
        this.aCTIVE = aCTIVE;
    }

}