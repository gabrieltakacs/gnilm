package Evaluator;

import Configuration.Configuration;
import Data.Channel;
import Data.DataFactory;
import Data.DataFrame;
import Data.House;
import Utils.Log;
import Utils.Stats;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

public class DataEvaluator {

    private Integer dataStart;

    private Integer dataEnd;

    private final Integer frequency = 1;

    private ArrayList<Channel> channels;

    private ArrayList<Double> originalData;

    private ArrayList<Double> disaggregatedData;

    public DataEvaluator() {
        this.originalData = new ArrayList<Double>();
        this.disaggregatedData = new ArrayList<Double>();
        this.channels = new ArrayList<Channel>();
        this.dataStart = Configuration.getInstance().getTestDataRangeFrom() + 1;
        this.dataEnd = Configuration.getInstance().getTestDataRangeUntil() - 1;
    }

    public void evaluate() throws Exception {
        House house = DataFactory.getHouse(Configuration.getInstance().getInputDirectory());
        this.channels = house.getApplianceChannels();

        String folder = Configuration.getInstance().getInputDirectory();

        for (Iterator<Channel> channelIterator = this.channels.iterator(); channelIterator.hasNext();) {
            String channel = channelIterator.next().getName();

            String originalDataFilename = folder + "/" + channel + ".dat";
            String myDataFilename = folder + "/" + channel + ".gnilm.dat";

            this.originalData = this.loadDataFromFile(originalDataFilename);
            this.disaggregatedData = this.loadDataFromFile(myDataFilename);

            Integer tp = 0;
            Integer fp = 0;
            Integer fn = 0;

            for (Integer i = 0; i < this.originalData.size(); i++) {
                Double originalValue = this.originalData.get(i);
                Double disaggregatedValue = this.disaggregatedData.get(i);

                if (disaggregatedValue > 10.0 && originalValue > 10.0) {
                    tp++;
                } else if (disaggregatedValue > 10.0 && originalValue < 10.0) {
                    fp++;
                } else if (originalValue > 10.0 && disaggregatedValue < 10.0) {
                    fn++;
                }
            }

            Double precision = Stats.precision((double) tp, (double) fp);
            Double recall = Stats.recall((double)tp, (double)fn);
            Double fscore = Stats.fscore(precision, recall);

            Log logger = Log.getInstance();
            logger.log("KANAL " + channel);
            logger.log("TP: " + tp + "\t" + "FP: " + fp + "\t" + "FN: " + fn);
            logger.log("P: " + precision + "\t" + "R: " + recall + "\t" + "F: " + fscore);
        }
    }

    protected ArrayList<Double> loadDataFromFile(String filename) throws Exception
    {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;

        ArrayList<Double> data = new ArrayList<Double>();

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");
            Double timestampDoubleValue = Double.parseDouble(parts[0]);
            Integer timestamp = timestampDoubleValue.intValue();
            Double value = Double.parseDouble(parts[1]);

            if (timestamp < this.dataStart) {
                continue;
            }

            if (timestamp > this.dataEnd) {
                break;
            }

            if ((timestamp - this.dataStart) % frequency == 0) {
                data.add(value);
            }
        }

        return data;
    }
}
