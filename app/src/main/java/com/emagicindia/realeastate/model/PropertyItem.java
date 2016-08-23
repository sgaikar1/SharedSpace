//-----------------------------------com.emagicindia.realeastate.model.Property.java-----------------------------------

        package com.emagicindia.realeastate.model;

        import android.os.Parcel;
        import android.os.Parcelable;

        import javax.annotation.Generated;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class PropertyItem implements Parcelable{

    @SerializedName("Property_Id")
    @Expose
    private String propertyId;
    @SerializedName("Property_Name")
    @Expose
    private String propertyName;
    @SerializedName("Location")
    @Expose
    private String location;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("Owner_Id")
    @Expose
    private String ownerId;
    @SerializedName("Tenant_Type")
    @Expose
    private String tenantType;
    @SerializedName("Booking_Type")
    @Expose
    private String bookingType;
    @SerializedName("House_Type")
    @Expose
    private String houseType;
    @SerializedName("Furnishing_Type")
    @Expose
    private String furnishingType;
    @SerializedName("Available")
    @Expose
    private String available;
    @SerializedName("Available_FromDt")
    @Expose
    private String availableFromDt;
    @SerializedName("Property_Rent")
    @Expose
    private String propertyRent;
    @SerializedName("Security_Deposit")
    @Expose
    private String securityDeposit;
    @SerializedName("Property_Size")
    @Expose
    private String propertySize;
    @SerializedName("No_Bedrooms")
    @Expose
    private String noBedrooms;
    @SerializedName("House_Address")
    @Expose
    private String houseAddress;
    @SerializedName("Street")
    @Expose
    private String street;
    @SerializedName("Pincode")
    @Expose
    private String pincode;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("Image_Path")
    @Expose
    private String imagePath;

    /**
     *
     * @return
     * The propertyId
     */
    public String getPropertyId() {
        return propertyId;
    }

    /**
     *
     * @param propertyId
     * The Property_Id
     */
    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    /**
     *
     * @return
     * The propertyName
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     *
     * @param propertyName
     * The Property_Name
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     *
     * @return
     * The location
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @param location
     * The Location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     * @return
     * The city
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * @param city
     * The City
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * @return
     * The ownerId
     */
    public String getOwnerId() {
        return ownerId;
    }

    /**
     *
     * @param ownerId
     * The Owner_Id
     */
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    /**
     *
     * @return
     * The tenantType
     */
    public String getTenantType() {
        return tenantType;
    }

    /**
     *
     * @param tenantType
     * The Tenant_Type
     */
    public void setTenantType(String tenantType) {
        this.tenantType = tenantType;
    }

    /**
     *
     * @return
     * The bookingType
     */
    public String getBookingType() {
        return bookingType;
    }

    /**
     *
     * @param bookingType
     * The Booking_Type
     */
    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    /**
     *
     * @return
     * The houseType
     */
    public String getHouseType() {
        return houseType;
    }

    /**
     *
     * @param houseType
     * The House_Type
     */
    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    /**
     *
     * @return
     * The furnishingType
     */
    public String getFurnishingType() {
        return furnishingType;
    }

    /**
     *
     * @param furnishingType
     * The Furnishing_Type
     */
    public void setFurnishingType(String furnishingType) {
        this.furnishingType = furnishingType;
    }

    /**
     *
     * @return
     * The available
     */
    public String getAvailable() {
        return available;
    }

    /**
     *
     * @param available
     * The Available
     */
    public void setAvailable(String available) {
        this.available = available;
    }

    /**
     *
     * @return
     * The availableFromDt
     */
    public String getAvailableFromDt() {
        return availableFromDt;
    }

    /**
     *
     * @param availableFromDt
     * The Available_FromDt
     */
    public void setAvailableFromDt(String availableFromDt) {
        this.availableFromDt = availableFromDt;
    }

    /**
     *
     * @return
     * The propertyRent
     */
    public String getPropertyRent() {
        return propertyRent;
    }

    /**
     *
     * @param propertyRent
     * The Property_Rent
     */
    public void setPropertyRent(String propertyRent) {
        this.propertyRent = propertyRent;
    }

    /**
     *
     * @return
     * The securityDeposit
     */
    public String getSecurityDeposit() {
        return securityDeposit;
    }

    /**
     *
     * @param securityDeposit
     * The Security_Deposit
     */
    public void setSecurityDeposit(String securityDeposit) {
        this.securityDeposit = securityDeposit;
    }

    /**
     *
     * @return
     * The propertySize
     */
    public String getPropertySize() {
        return propertySize;
    }

    /**
     *
     * @param propertySize
     * The Property_Size
     */
    public void setPropertySize(String propertySize) {
        this.propertySize = propertySize;
    }

    /**
     *
     * @return
     * The noBedrooms
     */
    public String getNoBedrooms() {
        return noBedrooms;
    }

    /**
     *
     * @param noBedrooms
     * The No_Bedrooms
     */
    public void setNoBedrooms(String noBedrooms) {
        this.noBedrooms = noBedrooms;
    }

    /**
     *
     * @return
     * The houseAddress
     */
    public String getHouseAddress() {
        return houseAddress;
    }

    /**
     *
     * @param houseAddress
     * The House_Address
     */
    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }

    /**
     *
     * @return
     * The street
     */
    public String getStreet() {
        return street;
    }

    /**
     *
     * @param street
     * The Street
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     *
     * @return
     * The pincode
     */
    public String getPincode() {
        return pincode;
    }

    /**
     *
     * @param pincode
     * The Pincode
     */
    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    /**
     *
     * @return
     * The state
     */
    public String getState() {
        return state;
    }

    /**
     *
     * @param state
     * The State
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     *
     * @return
     * The imagePath
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     *
     * @param imagePath
     * The Image_Path
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(propertyId);
        parcel.writeString(propertyName);
        parcel.writeString(location);
        parcel.writeString(city);
        parcel.writeString(ownerId);
        parcel.writeString(tenantType);
        parcel.writeString(bookingType);
        parcel.writeString(houseType);
        parcel.writeString(furnishingType);
        parcel.writeString(available);
        parcel.writeString(availableFromDt);
        parcel.writeString(propertyRent);
        parcel.writeString(securityDeposit);
        parcel.writeString(propertySize);
        parcel.writeString(noBedrooms);
        parcel.writeString(houseAddress);
        parcel.writeString(street);
        parcel.writeString(pincode);
        parcel.writeString(state);
        parcel.writeString(imagePath);
    }

    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public PropertyItem createFromParcel(Parcel in) {
            return new PropertyItem(in);
        }

        public PropertyItem[] newArray(int size) {
            return new PropertyItem[size];
        }
    };

    public PropertyItem(Parcel source) {
        propertyId=source.readString();
        propertyName=source.readString();
        location=source.readString();
        city=source.readString();
        ownerId=source.readString();
        tenantType=source.readString();
        bookingType=source.readString();
        houseType=source.readString();
        furnishingType=source.readString();
        available=source.readString();
        availableFromDt=source.readString();
        propertyRent=source.readString();
        securityDeposit=source.readString();
        propertySize=source.readString();
        noBedrooms=source.readString();
        houseAddress=source.readString();
        street=source.readString();
        pincode=source.readString();
        state=source.readString();
        imagePath=source.readString();
    }

}