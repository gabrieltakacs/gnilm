package Controllers.Listeners;

import Data.Channel;
import Processor.Processor;
import Recommender.Recommender;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;

public class RunActionListener extends ActionListenerAbstract {

    @Override
    public void actionPerformed(ActionEvent e) {
        (new Thread() {
            public void run() {
                try {
                    Processor processor = new Processor(controller);
                    processor.detectEvents();

                    ArrayList<Channel> applianceChannels = processor.getApplianceChannels();
                    Recommender recommender = new Recommender();
                    recommender.setController(controller);
                    for (Iterator<Channel> iterator = applianceChannels.iterator(); iterator.hasNext(); ) {
                        recommender.generateRecommendations(iterator.next());
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }).start();
    }
}
