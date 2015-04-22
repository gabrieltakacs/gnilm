package Processor;

import Data.Channel;
import Data.House;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Gabriel Takács, Apr 2015
 */
public class Processor {

    private Integer trainDataFrom;
    private Integer trainDataTo;

    private Integer testDataFrom;
    private Integer testDataTo;

    private House house;

    private ArrayList<String> trainDataChannels;

    public Processor() {
        this.trainDataChannels = new ArrayList<String>();
    }

    public void setTrainDataRange(Integer from, Integer to) {
        this.trainDataFrom = from;
        this.trainDataTo = to;
    }

    public void setTestDataRange(Integer from, Integer to) {
        this.testDataFrom = from;
        this.testDataTo = to;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public void addTrainDataChannel(String channelName) {
        this.trainDataChannels.add(channelName);
    }

    public void detectEvents() throws Exception {
        // Detect mains windows
        Channel mainsChannel = house.getChannel("mains");

        // Detect channels windows
        ArrayList<Channel> channels = new ArrayList<Channel>();
        for (Iterator<String> trainChannelsIterator = this.trainDataChannels.iterator(); trainChannelsIterator.hasNext();) { // Iterate over each channel
            Channel channel = house.getChannel(trainChannelsIterator.next());
            channels.add(channel);
        }

        Double minScore = null;
        String channelName = null;
        Integer timestamp = null;

        for (Iterator<Window> mainsWindowsIterator = mainsChannel.getWindows(this.testDataFrom, this.testDataTo).iterator(); mainsWindowsIterator.hasNext();) {
            Window currentMainsWindow = mainsWindowsIterator.next();
            System.out.println("Mains window @ " + currentMainsWindow.getTimestamp());

            for (Iterator<Channel> channelIterator = channels.iterator(); channelIterator.hasNext();) {
                Channel currentChannel = channelIterator.next();
                for (Iterator<Window> channelWindowsIterator = currentChannel.getWindows(this.trainDataFrom, this.trainDataTo).iterator(); channelWindowsIterator.hasNext();) {
                    Window currentChannelWindow = channelWindowsIterator.next();

                    if (currentMainsWindow.isIncreasing() != currentChannelWindow.isIncreasing()) {
                        continue;
                    }

                    Double currentScore = currentMainsWindow.calculateDistance(currentChannelWindow);
                    System.out.println(currentChannel.getName() + " @ " + currentChannelWindow.getTimestamp() + "\t" + currentScore.toString());

                    if (minScore == null || currentScore < minScore) {
                        minScore = currentScore;
                        channelName = currentChannel.getName();
                        timestamp = currentChannelWindow.getTimestamp();
                    }
                }
            }

            System.out.println("W: " + channelName + " @ " + timestamp + ", S: " + minScore);
            System.out.println("* * *");
        }



    }




}
