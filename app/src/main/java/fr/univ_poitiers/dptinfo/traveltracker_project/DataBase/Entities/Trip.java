package fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Entity class representing a trip in the database.
 */
@Entity(tableName = "Trips_table", foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "userId",
        onDelete = ForeignKey.CASCADE))
public class Trip implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "userId")
    private int userId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "status")
    private Boolean status = false;

    @ColumnInfo(name = "country")
    private String country;

    @ColumnInfo(name = "city")
    private String city;

    @ColumnInfo(name = "departureDate")
    private String departureDate;

    @ColumnInfo(name = "ambianceRating")
    private float ambianceRating;

    @ColumnInfo(name = "naturalBeautyRating")
    private float naturalBeautyRating;

    @ColumnInfo(name = "securityRating")
    private float securityRating;

    @ColumnInfo(name = "accommodationRating")
    private float accommodationRating;

    @ColumnInfo(name = "humanInteractionRating")
    private float humanInteractionRating;

    @ColumnInfo(name = "plannedBudget")
    private double plannedBudget;

    @ColumnInfo(name = "actualBudget")
    private double actualBudget;

    public Trip(){
        // Format the current date as a string
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        this.departureDate = format.format(new Date());
    }

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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

    @NonNull
    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", departureDate=" + departureDate +
                ", ambianceRating=" + ambianceRating +
                ", naturalBeautyRating=" + naturalBeautyRating +
                ", securityRating=" + securityRating +
                ", accommodationRating=" + accommodationRating +
                ", humanInteractionRating=" + humanInteractionRating +
                ", plannedBudget=" + plannedBudget +
                ", actualBudget=" + actualBudget +
                '}';
    }

}
