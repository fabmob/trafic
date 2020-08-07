package com.datex.batch.model;

import java.sql.Timestamp;

public class MeteoMeasure{
    private String id;
    private Timestamp time;
    private String measurementTime;
    private Integer humidity;
    private String noPrecipitation;
    private Integer millimetresPerHourIntensity;
    private Integer roadSurfaceTemperature;
    private Integer temperatureBelowRoadSurface;
    private String weatherRelatedRoadConditionType;
    private Integer airTemperature;
    private Integer dewPointTemperature;
    private Integer windSpeed;
    private Integer maximumWindSpeed;
    private Integer windDirectionBearing;
    private String statusType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMeasurementTime() {
        return measurementTime;
    }

    public void setMeasurementTime(String measurementTime) {
        this.measurementTime = measurementTime;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public String getNoPrecipitation() {
        return noPrecipitation;
    }

    public void setNoPrecipitation(String noPrecipitation) {
        this.noPrecipitation = noPrecipitation;
    }

    public Integer getMillimetresPerHourIntensity() {
        return millimetresPerHourIntensity;
    }

    public void setMillimetresPerHourIntensity(Integer millimetresPerHourIntensity) {
        this.millimetresPerHourIntensity = millimetresPerHourIntensity;
    }

    public Integer getRoadSurfaceTemperature() {
        return roadSurfaceTemperature;
    }

    public void setRoadSurfaceTemperature(Integer roadSurfaceTemperature) {
        this.roadSurfaceTemperature = roadSurfaceTemperature;
    }

    public Integer getTemperatureBelowRoadSurface() {
        return temperatureBelowRoadSurface;
    }

    public void setTemperatureBelowRoadSurface(Integer temperatureBelowRoadSurface) {
        this.temperatureBelowRoadSurface = temperatureBelowRoadSurface;
    }

    public String getWeatherRelatedRoadConditionType() {
        return weatherRelatedRoadConditionType;
    }

    public void setWeatherRelatedRoadConditionType(String weatherRelatedRoadConditionType) {
        this.weatherRelatedRoadConditionType = weatherRelatedRoadConditionType;
    }

    public Integer getAirTemperature() {
        return airTemperature;
    }

    public void setAirTemperature(Integer airTemperature) {
        this.airTemperature = airTemperature;
    }

    public Integer getDewPointTemperature() {
        return dewPointTemperature;
    }

    public void setDewPointTemperature(Integer dewPointTemperature) {
        this.dewPointTemperature = dewPointTemperature;
    }

    public Integer getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Integer windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Integer getMaximumWindSpeed() {
        return maximumWindSpeed;
    }

    public void setMaximumWindSpeed(Integer maximumWindSpeed) {
        this.maximumWindSpeed = maximumWindSpeed;
    }

    public Integer getWindDirectionBearing() {
        return windDirectionBearing;
    }

    public void setWindDirectionBearing(Integer windDirectionBearing) {
        this.windDirectionBearing = windDirectionBearing;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "MeteoMeasure{" +
                "id='" + id + '\'' +
                ", measurementTime='" + measurementTime + '\'' +
                ", humidity=" + humidity +
                ", noPrecipitation='" + noPrecipitation + '\'' +
                ", millimetresPerHourIntensity=" + millimetresPerHourIntensity +
                ", roadSurfaceTemperature=" + roadSurfaceTemperature +
                ", temperatureBelowRoadSurface=" + temperatureBelowRoadSurface +
                ", weatherRelatedRoadConditionType='" + weatherRelatedRoadConditionType + '\'' +
                ", airTemperature=" + airTemperature +
                ", dewPointTemperature=" + dewPointTemperature +
                ", windSpeed=" + windSpeed +
                ", maximumWindSpeed=" + maximumWindSpeed +
                ", windDirectionBearing=" + windDirectionBearing +
                ", statusType='" + statusType + '\'' +
                '}';
    }
}
