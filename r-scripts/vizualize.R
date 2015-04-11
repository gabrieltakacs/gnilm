rm(list=ls());
setwd("/home/gtakacs/fiit/bp/gnilm/data/house1/");

loadChannel <- function(filename, data) {
  channelData <- read.table(filename, col.names=c('datetime', filename));
  channelData$datetime <- as.POSIXct(channelData$datetime, origin="1970-01-01");
  
  if (is.null(data)) {
    data <- channelData;
  } else {
    data <- merge(data, channelData, by='datetime');
  }  
}

vizualize <- function(data, colors, dateFrom, dateTo) {  
  cols <- colnames(data);
  dateFrom <- as.POSIXct(dateFrom, origin="1970-01-01");
  dateTo <- as.POSIXct(dateTo, origin="1970-01-01");
  data <- subset(data, data$datetime > dateFrom & data$datetime < dateTo);
  
  myMax <- 0;
  for (col in cols) {
    if (col != 'datetime') {
      currentMax <- max(data[[col]]);
      if (currentMax > myMax) {
        myMax <- currentMax;
      }  
    }  
  }
    
  plot(data$datetime, as.vector(data[['mains.dat']]), col=colors[['mains.dat']], type="l", xlab="Time", ylim=c(0,myMax), ylab="Consumption (W)", main="Energy Consumption (House 1)");
  for (col in cols) {
    if (col != 'mains' && col != 'datetime') {
      lines(data$datetime, as.vector(data[[col]]), col=colors[[col]]); 
    }  
  }
}

data <- NULL;
colors <- list();

data <- loadChannel("mains.dat", data); colors[["mains.dat"]] <- "red";
data <- loadChannel("bathroom.dat", data); colors[["bathroom.dat"]] <- "blue";
data <- loadChannel("dishwasher.dat", data); colors[["dishwasher.dat"]] <- "gray";
data <- loadChannel("electric_heat.dat", data); colors[["electric_heat.dat"]] <- "pink";
data <- loadChannel("kitchen.dat", data); colors[["kitchen.dat"]] <- "purple";
data <- loadChannel("lighting.dat", data); colors[["lighting.dat"]] <- "yellow";
data <- loadChannel("microwave.dat", data); colors[["microwave.dat"]] <- "brown";
data <- loadChannel("oven.dat", data); colors[["oven.dat"]] <- "black";
data <- loadChannel("refridgerator.dat", data); colors[["refridgerator.dat"]] <- "green";
data <- loadChannel("stove.dat", data); colors[["stove.dat"]] <- "cyan";
data <- loadChannel("washer_dryer.dat", data); colors[["washer_dryer.dat"]] <- "orange";
vizualize(data, colors, '2011-04-18 13:22:13', '2011-04-18 14:22:00');
