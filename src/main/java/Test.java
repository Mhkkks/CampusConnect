

import java.util.*;

public class Test {
    public static void main(String[] args) {


        //////////////  How to add elements in list and print /////
        System.out.println("\n\n------------------------How to add elements in List and print");
        ArrayList<String> cars = new ArrayList<String>();
        cars.add("Volvo");
        cars.add("BMW");
        cars.add("Ford");
        cars.add("Mazda");
        Collections.sort(cars);  // Sort cars
        for (String i : cars) {
            System.out.println(i);
        }
        /////////////////////////////////////////////////////////////////


        //////////////  How to add elements in Map and print /////

        System.out.println("\n\n--------------How to add elements in Map and print");
        Map<String, String> mapcities = new HashMap();
        mapcities.put("1", "London");
        mapcities.put("2", "Dubai");
        mapcities.put("3", "Singapore");

        Iterator<String> it = mapcities.keySet().iterator();
        while(it.hasNext())
        {
           String key =  it.next();
           String val = mapcities.get(key);
           System.out.println("Key:"+key+", Value:"+val);

        }

        /////////////////////////////////////////////////////////////////


    }
}


