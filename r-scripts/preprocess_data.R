rm(list=ls());
library(zoo);

# Funkcia sluzi na doplnenie chybajucich hodnot v stlpci 'datetime'.
# http://bocoup.com/weblog/padding-time-series-with-r/
paddingTimeSeriesData <- function(raw.data) {
  raw.data$datetime <- as.POSIXct(raw.data$datetime, origin="1970-01-01");
  sorted.data <- raw.data[order(raw.data$datetime),];
  data.length <- length(sorted.data$datetime);
  time.min <- sorted.data$datetime[1];
  time.max <- sorted.data$datetime[data.length];
  all.dates <- seq(time.min, time.max, by="sec");
  all.dates.frame <- data.frame(list(datetime=all.dates));
  merged.data <- merge(all.dates.frame, sorted.data, all=T);
  return(merged.data);
}

# Funkcia nacita data zo zadanych suborov. Vsetky zadane subory by mali byt z toho isteho kanalu.
# Chybajuce hodnoty v jednotlivych suboroch su dopocitane pomocou linearnej interpolacie. Data z toho
# isteho kanalu su nasledne scitane.
preprocessChannel <- function(filenames = c(NULL), outputFilename) {
  setwd("/home/gtakacs/fiit/bp/data-sets/redd/low_freq/house_2/");
  data <- NULL;
  
  for (filename in filenames) {
    currentData <- read.table(filename, col.names=c('datetime', filename)); # nacitanie jedneho suboru
    currentData <- paddingTimeSeriesData(currentData); # doplnenie chybajucich riadkov (kazdu sekundu jedno meranie)
    currentData[filename] <- na.approx(as.vector(currentData[filename])); # dopocitanie chybajucich merani (linearna interpolacia)
    if (is.null(data)) {
      data <- currentData;
    } else {
      data <- merge(data, currentData, by='datetime');
    }
  }
  
  data <- transform(data, value=rowSums(data[, 2:(length(filenames)+1)]));
  data$value <- round(data$value, digits=2);
  data$datetime <- as.numeric(data$datetime);
  
  for (filename in filenames) {
    data[[filename]] <- NULL;
  }
  
  outputFilePath <- paste("/home/gtakacs/fiit/bp/gnilm/data/house2/", outputFilename, sep='');
  write.table(data, file=outputFilePath, row.names=FALSE, col.names=FALSE);  
}

#preprocessChannel(c("channel_1.dat", "channel_2.dat"), "mains.dat");
#preprocessChannel(c("channel_3.dat", "channel_4.dat"), "oven.dat");
#preprocessChannel(c("channel_5.dat"), "refridgerator.dat");
#preprocessChannel(c("channel_6.dat"), "dishwasher.dat");
#preprocessChannel(c("channel_7.dat", "channel_8.dat", "channel_15.dat", "channel_16.dat"), "kitchen.dat");
#preprocessChannel(c("channel_9.dat", "channel_17.dat", "channel_18.dat"), "lighting.dat");
#preprocessChannel(c("channel_10.dat", "channel_19.dat", "channel_20.dat"), "washer_dryer.dat");
#preprocessChannel(c("channel_11.dat"), "microwave.dat");
#preprocessChannel(c("channel_12.dat"), "bathroom.dat");
#preprocessChannel(c("channel_13.dat"), "electric_heat.dat");
#preprocessChannel(c("channel_14.dat"), "stove.dat");

preprocessChannel(c("channel_1.dat", "channel_2.dat"), "mains.dat");
preprocessChannel(c("channel_3.dat"), "kitchen1.dat");
preprocessChannel(c("channel_4.dat"), "lighting.dat");
preprocessChannel(c("channel_5.dat"), "stove.dat");
preprocessChannel(c("channel_6.dat"), "microwave.dat");
preprocessChannel(c("channel_7.dat"), "washer.dat");
preprocessChannel(c("channel_8.dat"), "kitchen2.dat");
preprocessChannel(c("channel_9.dat"), "refridgerator.dat");
preprocessChannel(c("channel_10.dat"), "dishwasher.dat");
preprocessChannel(c("channel_11.dat"), "disposal.dat");