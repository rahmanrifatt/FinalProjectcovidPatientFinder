package com.example.final_project_covid_patient_finder;

public class Helper {
    double Latitude;
    double Longitude;
    String Name;
    String CoronaStatus;
    public Helper() {
    }

    public Helper(double latitude, double longitude, String name, String coronaStatus) {
        Latitude = latitude;
        Longitude = longitude;
        Name = name;
        CoronaStatus = coronaStatus;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCoronaStatus() {
        return CoronaStatus;
    }

    public void setCoronaStatus(String coronaStatus) {
        CoronaStatus = coronaStatus;
    }
}
