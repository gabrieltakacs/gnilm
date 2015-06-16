package Evaluator;

import Configuration.Configuration;
import Data.*;
import Data.Channel;
import Processor.Window;
import Utils.Log;
import Utils.Stats;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Evaluator {

    private static Evaluator instance = null;

    private HashMap<String, Channel> channels;

    private HashMap<String, EventSet> detectedEvents;

    public static Evaluator getInstance() {
        if (instance == null) {
            instance = new Evaluator();
        }

        return instance;
    }

    public Evaluator() {
        this.detectedEvents = new HashMap<String, EventSet>();
    }

    public void prepareData() throws Exception {
        this.channels = new HashMap<String, Channel>();
        Configuration configuration = Configuration.getInstance();

        Integer from = configuration.getTestDataRangeFrom();
        Integer to = configuration.getTestDataRangeUntil();

        House house = DataFactory.getHouse(configuration.getInputDirectory());
        ArrayList<Data.Channel> applianceChannels = house.getApplianceChannels();
        for (Iterator<Data.Channel> iterator = applianceChannels.iterator(); iterator.hasNext();) {
            Data.Channel channel = iterator.next();
            channel.setWindowThreshold(configuration.getThreshold(channel.getName()));
            channel.setDataRange(from, to);
            this.channels.put(channel.getName(), channel);
        }

        Log.getInstance().log("Evaluation data prepared");
    }

    public void registerDetectedEvent(Integer timestamp, String classification) {
        if (!this.detectedEvents.containsKey(classification)) {
            this.detectedEvents.put(classification, new EventSet());
        }

        this.detectedEvents.get(classification).registerEvent(timestamp);
    }

    public void evaluate() throws Exception {

        for (Map.Entry<String, Channel> entry : this.channels.entrySet())
        {
            String channelName = entry.getKey();

            Integer tp = 0;
            Integer fp = 0;
            Integer fn = 0;

            EventSet eventSet = this.detectedEvents.get(channelName);
            Channel channel = this.channels.get(channelName);

            if (eventSet == null) {
                fn = channel.getWindows().size();
            } else {
                for (Iterator<Integer> eventSetIterator = eventSet.getEvents().iterator(); eventSetIterator.hasNext();) {
                    Integer detectedEventAt = eventSetIterator.next();
                    Boolean paired = false;

                    for (Iterator<Window> channelWindowsIterator = channel.getWindows().iterator(); channelWindowsIterator.hasNext();) {
                        Integer windowAt = channelWindowsIterator.next().getTimestamp();

                        if (Math.abs(detectedEventAt - windowAt) < 10) {
                            paired = true;
                            break;
                        }
                    }

                    if (paired) {
                        tp++;
                    } else {
                        fp++;
                    }
                }

                for (Iterator<Window> channelWindowsIterator = channel.getWindows().iterator(); channelWindowsIterator.hasNext();) {
                    Integer originalEventAt = channelWindowsIterator.next().getTimestamp();
                    Boolean detected = false;

                    for (Iterator<Integer> eventSetIterator = eventSet.getEvents().iterator(); eventSetIterator.hasNext(); ) {
                        Integer detectedEventAt = eventSetIterator.next();

                        if (Math.abs(detectedEventAt - originalEventAt) < 10) {
                            detected = true;
                            break;
                        }
                    }

                    if (!detected) {
                        fn++;
                    }
                }
            }

            Double precision = Stats.precision((double)tp, (double)fp);
            Double recall = Stats.recall((double)tp, (double)fn);
            Double fscore = Stats.fscore(precision, recall);

            Log logger = Log.getInstance();
            logger.log("Kanal " + channelName);
            logger.log("TP: " + tp + "\t" + "FP: " + fp + "\t" + "FN: " + fn);
            logger.log("P: " + precision + "\t" + "R: " + recall + "\t" + "F: " + fscore);
        }
    }

}
