import DataLoader.ReddDataLoader;

/**
 * Gabriel Takács, Mar 2015
 */
public class Main {

    public static void main(String[] args) {

        ReddDataLoader dataLoader = new ReddDataLoader();

        try {
            dataLoader.setDataDirectory("/home/gtakacs/fiit/bp/data-sets/redd/low_greq/");
        } catch (Exception e) {
            System.out.println("Error setting data directory: " + e.getMessage());
        }

    }

}
