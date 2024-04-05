package fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Entities;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "Trips_table")
public class Trip implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "userId")
    private int userId;

    @ColumnInfo(name = "name")
    private String name = "";

    @ColumnInfo(name = "country")
    private String country = "";

    @ColumnInfo(name = "city")
    private String city = "";

    @ColumnInfo(name = "departureDate")
    private String departureDate = "";

    @ColumnInfo(name = "arrivalDate")
    private String arrivalDate = "";

    @ColumnInfo(name = "ambianceRating")
    private float ambianceRating = 0F;

    @ColumnInfo(name = "naturalBeautyRating")
    private float naturalBeautyRating= 0F;

    @ColumnInfo(name = "securityRating")
    private float securityRating= 0F;

    @ColumnInfo(name = "accommodationRating")
    private float accommodationRating= 0F;

    @ColumnInfo(name = "humanInteractionRating")
    private float humanInteractionRating= 0F;

    @ColumnInfo(name = "plannedBudget")
    private double plannedBudget = 0.0;

    @ColumnInfo(name = "actualBudget")
    private double actualBudget = 0.0;

    @ColumnInfo(name = "levelSatisfactionActivities")
    private int levelSatisfactionActivities = 0;

    @ColumnInfo(name = "levelOfAdvanture")
    private int levelOfAdvanture = 0;

    @ColumnInfo(name = "transportation")
    private String transportation = "";

    public Trip() {
        // Format the current date as a string
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        this.departureDate = format.format(new Date());

        // Create a Calendar object
        Calendar calendar = Calendar.getInstance();
        // Add 10 days to the current date
        calendar.add(Calendar.DATE, 10);
        // Format the new date as a string
        this.arrivalDate = format.format(calendar.getTime());
    }

    protected Trip(Parcel in) {
        id = in.readInt();
        userId = in.readInt();
        name = in.readString();
        country = in.readString();
        city = in.readString();
        departureDate = in.readString();
        arrivalDate = in.readString();
        transportation = in.readString();
        ambianceRating = in.readFloat();
        naturalBeautyRating = in.readFloat();
        securityRating = in.readFloat();
        accommodationRating = in.readFloat();
        humanInteractionRating = in.readFloat();
        plannedBudget = in.readDouble();
        actualBudget = in.readDouble();
        levelSatisfactionActivities= in.readInt();
        levelOfAdvanture= in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(userId);
        dest.writeString(name);
        dest.writeString(country);
        dest.writeString(city);
        dest.writeString(departureDate);
        dest.writeString(arrivalDate);
        dest.writeString(transportation);
        dest.writeFloat(ambianceRating);
        dest.writeFloat(naturalBeautyRating);
        dest.writeFloat(securityRating);
        dest.writeFloat(accommodationRating);
        dest.writeFloat(humanInteractionRating);
        dest.writeDouble(plannedBudget);
        dest.writeDouble(actualBudget);
        dest.writeInt(levelSatisfactionActivities);
        dest.writeInt(levelOfAdvanture);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Trip> CREATOR = new Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel in) {
            return new Trip(in);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public float getAmbianceRating() {
        return ambianceRating;
    }

    public void setAmbianceRating(float ambianceRating) {
        this.ambianceRating = ambianceRating;
    }

    public float getNaturalBeautyRating() {
        return naturalBeautyRating;
    }

    public void setNaturalBeautyRating(float naturalBeautyRating) {
        this.naturalBeautyRating = naturalBeautyRating;
    }

    public float getSecurityRating() {
        return securityRating;
    }

    public void setSecurityRating(float securityRating) {
        this.securityRating = securityRating;
    }

    public float getAccommodationRating() {
        return accommodationRating;
    }

    public void setAccommodationRating(float accommodationRating) {
        this.accommodationRating = accommodationRating;
    }

    public float getHumanInteractionRating() {
        return humanInteractionRating;
    }

    public void setHumanInteractionRating(float humanInteractionRating) {
        this.humanInteractionRating = humanInteractionRating;
    }

    public double getPlannedBudget() {
        return plannedBudget;
    }

    public void setPlannedBudget(double plannedBudget) {
        this.plannedBudget = plannedBudget;
    }

    public double getActualBudget() {
        return actualBudget;
    }

    public void setActualBudget(double actualBudget) {
        this.actualBudget = actualBudget;
    }

    public int getLevelSatisfactionActivities() {
        return levelSatisfactionActivities;
    }

    public void setLevelSatisfactionActivities(int levelSatisfactionActivities) {
        this.levelSatisfactionActivities = levelSatisfactionActivities;
    }
    public int getLevelOfAdvanture() {
        return levelOfAdvanture;
    }

    public void setLevelOfAdvanture(int levelOfAdvanture) {
        this.levelOfAdvanture = levelOfAdvanture;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getTransportation() {
        return transportation;
    }

    public void setTransportation(String transportation) {
        this.transportation = transportation;
    }

    @NonNull
    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", departureDate=" + departureDate +
                ", arrivalDate=" + arrivalDate +
                ", ambianceRating=" + ambianceRating +
                ", naturalBeautyRating=" + naturalBeautyRating +
                ", securityRating=" + securityRating +
                ", accommodationRating=" + accommodationRating +
                ", humanInteractionRating=" + humanInteractionRating +
                ", plannedBudget=" + plannedBudget +
                ", actualBudget=" + actualBudget +
                ", levelSatisfactionActivities=" + levelSatisfactionActivities +
                ", levelOfAdvanture=" + levelOfAdvanture +
                ", transportation=" + transportation +
                '}';
    }
}
