package Processor;

import Configuration.Configuration;
import Controllers.MainController;
import Data.Channel;
import Data.House;
import Models.ModelAbstract;
import java.util.ArrayList;
import java.util.Iterator;

public class Processor extends ModelAbstract {

    private Integer trainDataFrom;
    private Integer trainDataTo;
    private Integer testDataFrom;
    private Integer testDataTo;
    private House house;
    private ArrayList<Channel> trainDataChannels;

    public Processor(MainController controller) {
        super(controller);
        this.trainDataChannels = new ArrayList<Channel>();
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

        for (Iterator<Window> mainsWindowsIterator = mainsChannel.getWindows(this.testDataFrom, this.testDataTo).iterator(); mainsWindowsIterator.hasNext();) {
            Double minScore = null;
            Integer timestamp = null;
            Channel winningChannel = null;

            Window currentMainsWindow = mainsWindowsIterator.next();

            this.controller.addLineToDisaggregationOutput("Event @ " + currentMainsWindow.getTimestamp(), true);

            for (Iterator<Channel> channelIterator = channels.iterator(); channelIterator.hasNext();) {

                Channel currentChannel = channelIterator.next();
                for (Iterator<Window> channelWindowsIterator = currentChannel.getWindows(this.trainDataFrom, this.trainDataTo).iterator(); channelWindowsIterator.hasNext();) {
                    Window currentChannelWindow = channelWindowsIterator.next();

                    if (!currentChannelWindow.isIncreasing().equals(currentMainsWindow.isIncreasing())) {
                        continue;
                    }

                    Double mainsDelta = currentMainsWindow.getDeltaValue();
                    Double channelDelta = currentChannelWindow.getDeltaValue();
                    Double deltaOfDeltas = Math.abs(mainsDelta - channelDelta);

                    Double currentScore = currentMainsWindow.calculateDistance(currentChannelWindow);

                    currentScore += Configuration.getInstance().getDeltaBoostConstant() * deltaOfDeltas;

//                    System.out.println(currentChannel.getName() + " @ " + currentChannelWindow.getTimestamp() + "\t" + currentScore.toString() + "\tDD: " + deltaOfDeltas + " M:" + mainsDelta + " C: " + channelDelta);

                    if (minScore == null || currentScore < minScore) {
                        minScore = currentScore;
                        winningChannel = currentChannel;
                        timestamp = currentChannelWindow.getTimestamp();
                    }
                }
            }

            this.controller.addLineToDisaggregationOutput("Classification: " + winningChannel.getName(), true);
            this.controller.addLineToLog("Winning window @ " + timestamp);
            this.controller.addLineToLog("Final score: " + minScore);
            this.controller.addLineToLog("");

            winningChannel.addAssociatedWindow(currentMainsWindow);
        }

        for (Iterator<Channel> iterator = this.trainDataChannels.iterator(); iterator.hasNext();) {
            Channel channel = iterator.next();
            channel.closeChannel(this.testDataTo);
            channel.exportToFile("/home/gtakacs/fiit/bp/gnilm/data/export/house2/");
        }
    }

}
