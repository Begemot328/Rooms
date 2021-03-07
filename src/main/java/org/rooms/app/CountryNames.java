package org.rooms.app;


import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;

import java.util.Locale;

public class CountryNames {

    public static void main(String[] args) {
        String[] countryCodes = Locale.getISOCountries();

        for (String countryCode : countryCodes) {

            Locale obj = new Locale("", countryCode);

            System.out.println("Country Code = " + obj.getCountry()
                    + ", Country Name = " + obj.getDisplayCountry());

        }
    }
}
