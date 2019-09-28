package com.stackroute.service;

import com.stackroute.domain.Item;
import com.stackroute.domain.Location;
import com.stackroute.domain.LocationResponse;
import com.stackroute.repositories.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class ItemServiceImplementation implements ItemService {
    private ItemRepository itemRepository;

    @Override
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    public Location getLocation(String host) {
        final String api = "https://tools.keycdn.com/geo.json?host=";
        StringBuilder uriBuilder = new StringBuilder(api);

        WebClient webClient = WebClient.create();
        LocationResponse outPut = webClient.get()
                .uri(uriBuilder.append(host).toString())
                .retrieve()
                .bodyToMono(LocationResponse.class)
                .block();
        return new Location(outPut.getData().getGeo().getLatitude(), outPut.getData().getGeo().getLongitude());
    }

    @Override
    public boolean compareLocations(Location currentTransactionLocation, Location previousTransactionLocation) {
        log.info(currentTransactionLocation.toString());
        log.info(previousTransactionLocation.toString());
        return false;
    }

    @Override
    public long calculateDateDifference(String currentTransactionTimeStamp, String oldTransactionTimeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        long diffHours = 0;
        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(oldTransactionTimeStamp);
            d2 = format.parse(currentTransactionTimeStamp);
            //in milliseconds
            long diff = d1.getTime() - d2.getTime();
            diffHours = diff / (60 * 60 * 1000) % 24;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return diffHours;
    }

    @Override
    public double distance(double latitude1, double longitude1, double latitude2, double longitude2, String unit) {

        if ((latitude1 == latitude2) && (longitude1 == longitude2)) {
            return 0;
        } else {
            double theta = longitude1 - longitude2;
            double dist = Math.sin(Math.toRadians(latitude1)) * Math.sin(Math.toRadians(latitude2)) + Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            if (unit == "K") {
                dist = dist * 1.609344;
            } else {
                System.out.println("Invalid unit");
            }
            return dist;


        }

    }
}