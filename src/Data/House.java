package Data;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Gabriel Takács, Apr 2015
 */
public class House {

    private File baseDirectory;

    private ArrayList<Channel> channels;

    public House(File baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    public ArrayList<Channel> getChannels() throws Exception {
        if (this.channels == null) {
            if (this.baseDirectory == null) {
                throw new Exception("House base directory has not been set!");
            }

            this.channels = new ArrayList<Channel>();
            File[] files = this.baseDirectory.listFiles();
            for (File file : files) {
                Channel channel = new Channel(file);
                this.channels.add(channel);
            }
        }

        return this.channels;
    }

    public Channel getChannel(String channelName) throws Exception {
        if (this.channels == null) {
            this.getChannels();
        }

        Channel channel = null;
        for (Iterator<Channel> iterator = this.channels.iterator(); iterator.hasNext();) {
            Channel currentChannel = iterator.next();
            if (currentChannel.getName().equals(channelName)) {
                channel = currentChannel;
                break;
            }
        }

        return channel;
    }

}
