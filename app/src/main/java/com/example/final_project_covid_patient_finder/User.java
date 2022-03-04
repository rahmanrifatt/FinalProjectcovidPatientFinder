package com.example.final_project_covid_patient_finder;

public class User {
    String email;
    String password;
    String Name;
    String Nid;
    String Mobile;
    String Birthdate;
    String CoronaStatus;
    String Vaccinated_Status;
    String HealthStatus;

    public User() {
    }

    public User(String email, String password, String name, String nid, String mobile, String birthdate, String coronaStatus, String vaccinated_Status, String healthStatus) {
        this.email = email;
        this.password = password;
        Name = name;
        Nid = nid;
        Mobile = mobile;
        Birthdate = birthdate;
        CoronaStatus = coronaStatus;
        Vaccinated_Status = vaccinated_Status;
        HealthStatus = healthStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNid() {
        return Nid;
    }

    public void setNid(String nid) {
        Nid = nid;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getBirthdate() {
        return Birthdate;
    }

    public void setBirthdate(String birthdate) {
        Birthdate = birthdate;
    }

    public String getCoronaStatus() {
        return CoronaStatus;
    }

    public void setCoronaStatus(String coronaStatus) {
        CoronaStatus = coronaStatus;
    }

    public String getVaccinated_Status() {
        return Vaccinated_Status;
    }

    public void setVaccinated_Status(String vaccinated_Status) {
        Vaccinated_Status = vaccinated_Status;
    }

    public String getHealthStatus() {
        return HealthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        HealthStatus = healthStatus;
    }
}
