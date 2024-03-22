package fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.entities;

import androidx.room.*;

@Entity(tableName = "users_table")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;
}
