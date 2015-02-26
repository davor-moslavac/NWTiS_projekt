/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.davmoslav.podaci;

import java.util.Date;

/**
 *
 * @author dkermek
 */
public class WeatherData {

    private String key;
    private String stationId;
    private int providerId;
    private String observationTimeLocalStr;
    private String observationTimeUtcStr;
    private String iconCode;
    private Float altimeter;
    private Float altimeterRate;
    private Float dewPoint;
    private Float dewPointRate;
    private Float heatIndex;
    private Float humidity;
    private Float humidityRate;
    private Float pressureSeaLevel;
    private Float pressureSeaLevelRate;
    private Float rainDaily;
    private Float rainRate;
    private Float rainMonthly;
    private Float rainYearly;
    private Float snowDaily;
    private Float snowRate;
    private Float snowMonthly;
    private Float snowYearly;
    private Float temperature;
    private Float temperatureRate;
    private Float visibility;
    private Float visibilityRate;
    private Float windChill;
    private Float windSpeed;
    private Float windDirection;
    private Float windSpeedAvg;
    private Float windDirectionAvg;
    private Float windGustHourly;
    private String windGustTimeLocalHourlyStr;
    private String windGustTimeUtcHourlyStr;
    private Float windGustDirectionHourly;
    private Float windGustDaily;
    private String windGustTimeLocalDailyStr;
    private String windGustTimeUtcDailyStr;
    private Float windGustDirectionDaily;
    private String observationTimeAdjustedLocalStr;
    private Float feelsLike;
    private Date observationTimeLocal;
    private Date observationTimeUtc;
    private Date windGustTimeLocalHourly;
    private Date windGustTimeUtcHourly;
    private Date windGustTimeLocalDaily;
    private Date windGustTimeUtcDaily;
    private Date observationTimeAdjustedLocal;

    public WeatherData() {
    }

    public WeatherData(String key, String stationId, int providerId, String observationTimeLocalStr, String observationTimeUtcStr, String iconCode, Float altimeter, Float altimeterRate, Float dewPoint, Float dewPointRate, Float heatIndex, Float humidity, Float humidityRate, Float pressureSeaLevel, Float pressureSeaLevelRate, Float rainDaily, Float rainRate, Float rainMonthly, Float rainYearly, Float snowDaily, Float snowRate, Float snowMonthly, Float snowYearly, Float temperature, Float temperatureRate, Float visibility, Float visibilityRate, Float windChill, Float windSpeed, Float windDirection, Float windSpeedAvg, Float windDirectionAvg, Float windGustHourly, String windGustTimeLocalHourlyStr, String windGustTimeUtcHourlyStr, Float windGustDirectionHourly, Float windGustDaily, String windGustTimeLocalDailyStr, String windGustTimeUtcDailyStr, Float windGustDirectionDaily, String observationTimeAdjustedLocalStr, Float feelsLike) {
        this.key = key;
        this.stationId = stationId;
        this.providerId = providerId;
        this.observationTimeLocalStr = observationTimeLocalStr;
        this.observationTimeUtcStr = observationTimeUtcStr;
        this.iconCode = iconCode;
        this.altimeter = altimeter;
        this.altimeterRate = altimeterRate;
        this.dewPoint = dewPoint;
        this.dewPointRate = dewPointRate;
        this.heatIndex = heatIndex;
        this.humidity = humidity;
        this.humidityRate = humidityRate;
        this.pressureSeaLevel = pressureSeaLevel;
        this.pressureSeaLevelRate = pressureSeaLevelRate;
        this.rainDaily = rainDaily;
        this.rainRate = rainRate;
        this.rainMonthly = rainMonthly;
        this.rainYearly = rainYearly;
        this.snowDaily = snowDaily;
        this.snowRate = snowRate;
        this.snowMonthly = snowMonthly;
        this.snowYearly = snowYearly;
        this.temperature = temperature;
        this.temperatureRate = temperatureRate;
        this.visibility = visibility;
        this.visibilityRate = visibilityRate;
        this.windChill = windChill;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.windSpeedAvg = windSpeedAvg;
        this.windDirectionAvg = windDirectionAvg;
        this.windGustHourly = windGustHourly;
        this.windGustTimeLocalHourlyStr = windGustTimeLocalHourlyStr;
        this.windGustTimeUtcHourlyStr = windGustTimeUtcHourlyStr;
        this.windGustDirectionHourly = windGustDirectionHourly;
        this.windGustDaily = windGustDaily;
        this.windGustTimeLocalDailyStr = windGustTimeLocalDailyStr;
        this.windGustTimeUtcDailyStr = windGustTimeUtcDailyStr;
        this.windGustDirectionDaily = windGustDirectionDaily;
        this.observationTimeAdjustedLocalStr = observationTimeAdjustedLocalStr;
        this.feelsLike = feelsLike;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public String getObservationTimeLocalStr() {
        return observationTimeLocalStr;
    }

    public void setObservationTimeLocalStr(String observationTimeLocalStr) {
        this.observationTimeLocalStr = observationTimeLocalStr;
    }

    public String getObservationTimeUtcStr() {
        return observationTimeUtcStr;
    }

    public void setObservationTimeUtcStr(String observationTimeUtcStr) {
        this.observationTimeUtcStr = observationTimeUtcStr;
    }

    public String getIconCode() {
        return iconCode;
    }

    public void setIconCode(String iconCode) {
        this.iconCode = iconCode;
    }

    public Float getAltimeter() {
        return altimeter;
    }

    public void setAltimeter(Float altimeter) {
        this.altimeter = altimeter;
    }

    public Float getAltimeterRate() {
        return altimeterRate;
    }

    public void setAltimeterRate(Float altimeterRate) {
        this.altimeterRate = altimeterRate;
    }

    public Float getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(Float dewPoint) {
        this.dewPoint = dewPoint;
    }

    public Float getDewPointRate() {
        return dewPointRate;
    }

    public void setDewPointRate(Float dewPointRate) {
        this.dewPointRate = dewPointRate;
    }

    public Float getHeatIndex() {
        return heatIndex;
    }

    public void setHeatIndex(Float heatIndex) {
        this.heatIndex = heatIndex;
    }

    public Float getHumidity() {
        return humidity;
    }

    public void setHumidity(Float humidity) {
        this.humidity = humidity;
    }

    public Float getHumidityRate() {
        return humidityRate;
    }

    public void setHumidityRate(Float humidityRate) {
        this.humidityRate = humidityRate;
    }

    public Float getPressureSeaLevel() {
        return pressureSeaLevel;
    }

    public void setPressureSeaLevel(Float pressureSeaLevel) {
        this.pressureSeaLevel = pressureSeaLevel;
    }

    public Float getPressureSeaLevelRate() {
        return pressureSeaLevelRate;
    }

    public void setPressureSeaLevelRate(Float pressureSeaLevelRate) {
        this.pressureSeaLevelRate = pressureSeaLevelRate;
    }

    public Float getRainDaily() {
        return rainDaily;
    }

    public void setRainDaily(Float rainDaily) {
        this.rainDaily = rainDaily;
    }

    public Float getRainRate() {
        return rainRate;
    }

    public void setRainRate(Float rainRate) {
        this.rainRate = rainRate;
    }

    public Float getRainMonthly() {
        return rainMonthly;
    }

    public void setRainMonthly(Float rainMonthly) {
        this.rainMonthly = rainMonthly;
    }

    public Float getRainYearly() {
        return rainYearly;
    }

    public void setRainYearly(Float rainYearly) {
        this.rainYearly = rainYearly;
    }

    public Float getSnowDaily() {
        return snowDaily;
    }

    public void setSnowDaily(Float snowDaily) {
        this.snowDaily = snowDaily;
    }

    public Float getSnowRate() {
        return snowRate;
    }

    public void setSnowRate(Float snowRate) {
        this.snowRate = snowRate;
    }

    public Float getSnowMonthly() {
        return snowMonthly;
    }

    public void setSnowMonthly(Float snowMonthly) {
        this.snowMonthly = snowMonthly;
    }

    public Float getSnowYearly() {
        return snowYearly;
    }

    public void setSnowYearly(Float snowYearly) {
        this.snowYearly = snowYearly;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Float getTemperatureRate() {
        return temperatureRate;
    }

    public void setTemperatureRate(Float temperatureRate) {
        this.temperatureRate = temperatureRate;
    }

    public Float getVisibility() {
        return visibility;
    }

    public void setVisibility(Float visibility) {
        this.visibility = visibility;
    }

    public Float getVisibilityRate() {
        return visibilityRate;
    }

    public void setVisibilityRate(Float visibilityRate) {
        this.visibilityRate = visibilityRate;
    }

    public Float getWindChill() {
        return windChill;
    }

    public void setWindChill(Float windChill) {
        this.windChill = windChill;
    }

    public Float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Float getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(Float windDirection) {
        this.windDirection = windDirection;
    }

    public Float getWindSpeedAvg() {
        return windSpeedAvg;
    }

    public void setWindSpeedAvg(Float windSpeedAvg) {
        this.windSpeedAvg = windSpeedAvg;
    }

    public Float getWindDirectionAvg() {
        return windDirectionAvg;
    }

    public void setWindDirectionAvg(Float windDirectionAvg) {
        this.windDirectionAvg = windDirectionAvg;
    }

    public Float getWindGustHourly() {
        return windGustHourly;
    }

    public void setWindGustHourly(Float windGustHourly) {
        this.windGustHourly = windGustHourly;
    }

    public String getWindGustTimeLocalHourlyStr() {
        return windGustTimeLocalHourlyStr;
    }

    public void setWindGustTimeLocalHourlyStr(String windGustTimeLocalHourlyStr) {
        this.windGustTimeLocalHourlyStr = windGustTimeLocalHourlyStr;
    }

    public String getWindGustTimeUtcHourlyStr() {
        return windGustTimeUtcHourlyStr;
    }

    public void setWindGustTimeUtcHourlyStr(String windGustTimeUtcHourlyStr) {
        this.windGustTimeUtcHourlyStr = windGustTimeUtcHourlyStr;
    }

    public Float getWindGustDirectionHourly() {
        return windGustDirectionHourly;
    }

    public void setWindGustDirectionHourly(Float windGustDirectionHourly) {
        this.windGustDirectionHourly = windGustDirectionHourly;
    }

    public Float getWindGustDaily() {
        return windGustDaily;
    }

    public void setWindGustDaily(Float windGustDaily) {
        this.windGustDaily = windGustDaily;
    }

    public String getWindGustTimeLocalDailyStr() {
        return windGustTimeLocalDailyStr;
    }

    public void setWindGustTimeLocalDailyStr(String windGustTimeLocalDailyStr) {
        this.windGustTimeLocalDailyStr = windGustTimeLocalDailyStr;
    }

    public String getWindGustTimeUtcDailyStr() {
        return windGustTimeUtcDailyStr;
    }

    public void setWindGustTimeUtcDailyStr(String windGustTimeUtcDailyStr) {
        this.windGustTimeUtcDailyStr = windGustTimeUtcDailyStr;
    }

    public Float getWindGustDirectionDaily() {
        return windGustDirectionDaily;
    }

    public void setWindGustDirectionDaily(Float windGustDirectionDaily) {
        this.windGustDirectionDaily = windGustDirectionDaily;
    }

    public String getObservationTimeAdjustedLocalStr() {
        return observationTimeAdjustedLocalStr;
    }

    public void setObservationTimeAdjustedLocalStr(String observationTimeAdjustedLocalStr) {
        this.observationTimeAdjustedLocalStr = observationTimeAdjustedLocalStr;
    }

    public Date getObservationTimeLocal() {
        return observationTimeLocal;
    }

    public void setObservationTimeLocal(Date observationTimeLocal) {
        this.observationTimeLocal = observationTimeLocal;
    }

    public Date getObservationTimeUtc() {
        return observationTimeUtc;
    }

    public void setObservationTimeUtc(Date observationTimeUtc) {
        this.observationTimeUtc = observationTimeUtc;
    }

    public Date getWindGustTimeLocalHourly() {
        return windGustTimeLocalHourly;
    }

    public void setWindGustTimeLocalHourly(Date windGustTimeLocalHourly) {
        this.windGustTimeLocalHourly = windGustTimeLocalHourly;
    }

    public Date getWindGustTimeUtcHourly() {
        return windGustTimeUtcHourly;
    }

    public void setWindGustTimeUtcHourly(Date windGustTimeUtcHourly) {
        this.windGustTimeUtcHourly = windGustTimeUtcHourly;
    }

    public Date getWindGustTimeLocalDaily() {
        return windGustTimeLocalDaily;
    }

    public void setWindGustTimeLocalDaily(Date windGustTimeLocalDaily) {
        this.windGustTimeLocalDaily = windGustTimeLocalDaily;
    }

    public Date getWindGustTimeUtcDaily() {
        return windGustTimeUtcDaily;
    }

    public void setWindGustTimeUtcDaily(Date windGustTimeUtcDaily) {
        this.windGustTimeUtcDaily = windGustTimeUtcDaily;
    }

    public Date getObservationTimeAdjustedLocal() {
        return observationTimeAdjustedLocal;
    }

    public void setObservationTimeAdjustedLocal(Date observationTimeAdjustedLocal) {
        this.observationTimeAdjustedLocal = observationTimeAdjustedLocal;
    }

    public Float getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(Float feelsLike) {
        this.feelsLike = feelsLike;
    }
}
