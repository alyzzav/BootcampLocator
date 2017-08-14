package dev.villahermosa.com.bootcamplocator;

import java.util.ArrayList;


public class Data {
    private static Data instance = new Data();

    public static Data getInstance() {
        return instance;
    }

    private Data() {

    }

    public ArrayList<bootcmp> getBootcampLocationsWithin10MilesOfZip(int zipcode) {

        ArrayList<bootcmp> list = new ArrayList<>();
        list.add(new bootcmp(10.290160f, 123.861698f,"USJ-R","Basak Pardo","img"));
        list.add(new bootcmp(10.292699f, 123.860876f,"Sr. San Isidro Chapel","Lungsod Cebu City","img"));
        list.add(new bootcmp(10.288698f, 123.859626f,"Barangay Quiot Hall","Quiot Pardo","img"));
        return list;
    }
}
