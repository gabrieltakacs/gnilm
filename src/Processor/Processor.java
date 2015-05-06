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

    private ArrayList<Channel> trainDataChannels;

    private Double initialConsumption;

    public Processor() {
        this.trainDataChannels = new ArrayList<Channel>();
        this.initialConsumption = null;
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

    public void addTrainDataChannel(Channel channel) {
        this.trainDataChannels.add(channel);
    }

    public void detectEvents() throws Exception {
        // Detect mains windows
        Channel mainsChannel = house.getChannel("mains");

        // Detect channels windows
        ArrayList<Channel> channels = new ArrayList<Channel>();
        for (Iterator<Channel> trainChannelsIterator = this.trainDataChannels.iterator(); trainChannelsIterator.hasNext();) { // Iterate over each channel
            Channel channel = trainChannelsIterator.next();
            channel.setCurrentTimestamp(this.testDataFrom);
            channels.add(channel);
        }

        // Calculate initial consumption
        Double currentConsumption = this.getInitialConsumption();
        System.out.println("Initial consumption: " + currentConsumption);

        for (Iterator<Window> mainsWindowsIterator = mainsChannel.getWindows(this.testDataFrom, this.testDataTo).iterator(); mainsWindowsIterator.hasNext();) {
            Double minScore = null;
            Integer timestamp = null;
            Window winningWindow = null;
            Channel winningChannel = null;

            Window currentMainsWindow = mainsWindowsIterator.next();
            System.out.println("Mains window @ " + currentMainsWindow.getTimestamp());
            currentMainsWindow.printData();

            currentMainsWindow.alterAllValues((-1) * currentConsumption);
            currentConsumption += currentMainsWindow.getDeltaValue();

            for (Iterator<Channel> channelIterator = channels.iterator(); channelIterator.hasNext();) {

                Channel currentChannel = channelIterator.next();
                for (Iterator<Window> channelWindowsIterator = currentChannel.getWindows(this.trainDataFrom, this.trainDataTo).iterator(); channelWindowsIterator.hasNext();) {
                    Window currentChannelWindow = channelWindowsIterator.next();
                    Double currentScore = currentMainsWindow.calculateDistance(currentChannelWindow);

//                    System.out.println(currentChannel.getName() + " @ " + currentChannelWindow.getTimestamp() + "\t" + currentScore.toString());

                    if (minScore == null || currentScore < minScore) {
                        minScore = currentScore;
                        winningChannel = currentChannel;
                        timestamp = currentChannelWindow.getTimestamp();
                        winningWindow = currentChannelWindow;
                    }
                }
            }

            System.out.println("W: " + winningChannel.getName() + " @ " + timestamp + ", S: " + minScore);
            System.out.println("* * *");

            winningChannel.addAssociatedWindow(currentMainsWindow);
        }

        for (Iterator<Channel> iterator = this.trainDataChannels.iterator(); iterator.hasNext();) {
            Channel channel = iterator.next();
            channel.closeChannel(this.testDataTo);
            channel.exportToFile("/home/gtakacs/fiit/bp/gnilm/data/export/house2/");
        }
    }

    private Double getInitialConsumption() {
        if (this.initialConsumption == null) {
            Double value = 0.0;

            for (Iterator<Channel> iterator = this.trainDataChannels.iterator(); iterator.hasNext();) {
                value += iterator.next().getCurrentValue();
            }

            this.initialConsumption = value;
        }

        return this.initialConsumption;
    }

}
