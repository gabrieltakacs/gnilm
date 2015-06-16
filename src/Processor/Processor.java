package Processor;

import Configuration.Configuration;
import Controllers.MainController;
import Data.Channel;
import Data.DataFactory;
import Data.House;
import Evaluator.Evaluator;
import Models.ModelAbstract;
import Utils.Log;

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

        Configuration configuration = Configuration.getInstance();
        this.trainDataFrom = configuration.getTrainDataRangeFrom();
        this.trainDataTo = configuration.getTrainDataRangeUntil();

        this.testDataFrom = configuration.getTestDataRangeFrom();
        this.testDataTo = configuration.getTestDataRangeUntil();

        try {
            this.house = DataFactory.getHouse(configuration.getInputDirectory());
        } catch (Exception exception) {
            Log.getInstance().addFatalError(exception.getMessage());
        }

    }

    public void addTrainDataChannel(Channel channel) {
        this.trainDataChannels.add(channel);
    }

    public void detectEvents() throws Exception {
        this.initData();

        // Detect mains windows
        Channel mainsChannel = house.getChannel("mains");
        mainsChannel.setDataRange(this.testDataFrom, this.testDataTo);

        // Detect channels windows
        ArrayList<Channel> channels = new ArrayList<Channel>();
        for (Iterator<Channel> trainChannelsIterator = this.trainDataChannels.iterator(); trainChannelsIterator.hasNext();) { // Iterate over each channel
            Channel channel = trainChannelsIterator.next();
            channel.setCurrentTimestamp(this.testDataFrom);
            channel.setDataRange(this.trainDataFrom, this.trainDataTo);
            channels.add(channel);
        }

        for (Iterator<Window> mainsWindowsIterator = mainsChannel.getWindows().iterator(); mainsWindowsIterator.hasNext();) {
            Double minScore = null;
            Integer timestamp = null;
            Channel winningChannel = null;

            Window currentMainsWindow = mainsWindowsIterator.next();
            this.controller.addLineToDisaggregationOutput("Event @ " + currentMainsWindow.getTimestamp(), true);

            for (Iterator<Channel> channelIterator = channels.iterator(); channelIterator.hasNext();) {

                Channel currentChannel = channelIterator.next();
                for (Iterator<Window> channelWindowsIterator = currentChannel.getWindows().iterator(); channelWindowsIterator.hasNext();) {
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

            Evaluator.getInstance().registerDetectedEvent(currentMainsWindow.getTimestamp(), winningChannel.getName());

            this.controller.addLineToDisaggregationOutput("Classification: " + winningChannel.getName(), true);
            this.controller.addLineToLog("Winning window @ " + timestamp);
            this.controller.addLineToLog("Final score: " + minScore);
            this.controller.addLineToLog("");


            winningChannel.addAssociatedWindow(currentMainsWindow);
        }

        for (Iterator<Channel> iterator = this.trainDataChannels.iterator(); iterator.hasNext();) {
            Channel channel = iterator.next();
            channel.closeChannel(this.testDataTo);
            channel.exportToFile(Configuration.getInstance().getInputDirectory());
        }

        this.controller.addLineToDisaggregationOutput("Run finished.", false);
        Log.getInstance().addInfo("Run finished.", true);
    }

    private void initData() throws Exception {
        Configuration configuration = Configuration.getInstance();

        House house = DataFactory.getHouse(configuration.getInputDirectory());
        ArrayList<Channel> applianceChannels = house.getApplianceChannels();
        for (Iterator<Channel> iterator = applianceChannels.iterator(); iterator.hasNext();) {
            Channel channel = iterator.next();
            channel.setWindowThreshold(configuration.getThreshold(channel.getName()));
            this.addTrainDataChannel(channel);
        }
    }

    public ArrayList<Channel> getApplianceChannels() {
        return this.trainDataChannels;
    }

}
