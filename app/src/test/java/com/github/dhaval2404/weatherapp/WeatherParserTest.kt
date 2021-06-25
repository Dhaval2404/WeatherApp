package com.github.dhaval2404.weatherapp

import com.github.dhaval2404.weatherapp.ui.weather_screen.WeatherParser
import org.junit.Assert
import org.junit.Test

class WeatherParserTest {

    @Test
    fun `when 90 degree then wind direction is E`() {
        val direction = WeatherParser.getWindDirection(90)
        Assert.assertEquals(direction, "E")
    }

    @Test
    fun `when 270 degree then wind direction is W`() {
        val direction = WeatherParser.getWindDirection(270)
        Assert.assertEquals(direction, "W")
    }

    @Test
    fun `when speed is 3_2 metric then wind speed is 1_2 km_h`() {
        val speed = WeatherParser.getWindSpeed(3.2, true)
        Assert.assertEquals(speed, "11.5 km/h")
    }

    @Test
    fun `when speed is 3_2 imperial then wind speed is 3_2 mile_h`() {
        val speed = WeatherParser.getWindSpeed(3.2, false)
        Assert.assertEquals(speed, "3.2 mile/h")
    }

    @Test
    fun `when distance is 3200 then km is 3_2 km`() {
        val distance = WeatherParser.getDistance(3200, true)
        Assert.assertEquals(distance, "3.20 km")
    }

    @Test
    fun `when distance is 3200 then mile is 1_99`() {
        val distance = WeatherParser.getDistance(3200, false)
        Assert.assertEquals(distance, "1.99 mile")
    }

    /*@Test
    fun `when timestamp is 1620251303 then time is 0318 AM`() {
        val time = WeatherParser.parseSystemTime(1621244885)
        Assert.assertEquals(time, "03:18 AM")
    }

    @Test
    fun `when timestamp is 1620251303 then day is Thursday`() {
        val day = WeatherParser.parseSystemDay(1621244885)
        Assert.assertEquals(day, "Monday")
    }*/

    @Test
    fun `when metric then temp unit is C`() {
        val unit = WeatherParser.getTempUnit(true)
        Assert.assertEquals(unit, "C")
    }

    @Test
    fun `when imperial then temp unit is F`() {
        val unit = WeatherParser.getTempUnit(false)
        Assert.assertEquals(unit, "F")
    }

    @Test
    fun `when icon is 1d then icon url is `() {
        val unit = WeatherParser.getWeatherIcon("1d")
        Assert.assertEquals(unit, "https://openweathermap.org/img/wn/1d@2x.png")
    }
}
