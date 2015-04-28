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

    public Processor() {
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
            channels.add(channel);
        }

        for (Iterator<Window> mainsWindowsIterator = mainsChannel.getWindows(this.testDataFrom, this.testDataTo).iterator(); mainsWindowsIterator.hasNext();) {

            Double minScore = null;
            String channelName = null;
            Integer timestamp = null;
            Window winningWindow = null;

            Window currentMainsWindow = mainsWindowsIterator.next();
            System.out.println("Mains window @ " + currentMainsWindow.getTimestamp());
            currentMainsWindow.printData();

            for (Iterator<Channel> channelIterator = channels.iterator(); channelIterator.hasNext();) {

                Double skoreKanal = 0.0;
                Integer okienKanal = 0;

                Channel currentChannel = channelIterator.next();
                for (Iterator<Window> channelWindowsIterator = currentChannel.getWindows(this.trainDataFrom, this.trainDataTo).iterator(); channelWindowsIterator.hasNext();) {
                    Window currentChannelWindow = channelWindowsIterator.next();
                    Double currentScore = currentMainsWindow.calculateDistance(currentChannelWindow);

                    skoreKanal += currentScore;
                    okienKanal++;

                    System.out.println(currentChannel.getName() + " @ " + currentChannelWindow.getTimestamp() + "\t" + currentScore.toString());

                    if (minScore == null || currentScore < minScore) {
                        minScore = currentScore;
                        channelName = currentChannel.getName();
                        timestamp = currentChannelWindow.getTimestamp();
                        winningWindow = currentChannelWindow;
                    }
                }

                Double priemer = skoreKanal / okienKanal;
                System.out.println("Skore pre kanal " + currentChannel.getName() + ": " + priemer);
            }

            System.out.println("W: " + channelName + " @ " + timestamp + ", S: " + minScore);


            winningWindow.printData();
            System.out.println("* * *");
        }

    }

}
