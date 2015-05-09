rm(list=ls());
setwd("/home/gtakacs/fiit/bp/gnilm/data/house2/");

loadChannel <- function(filename, data) {
  channelData <- read.table(filename, col.names=c('datetime', filename));
  #channelData$datetime <- as.POSIXct(channelData$datetime, origin="1970-01-01");
  channelData$datetime <- channelData$datetime;
  
  if (is.null(data)) {
    data <- channelData;
  } else {
    data <- merge(data, channelData, by='datetime');
  }  
}

vizualize <- function(data, colors, dateFrom, dateTo, displayOnly=c()) {  
  print(dateFrom);
  print(dateTo);
  
  cols <- colnames(data);
  #dateFrom <- as.POSIXct(dateFrom, origin="1970-01-01");
  #dateTo <- as.POSIXct(dateTo, origin="1970-01-01");
  data <- subset(data, data$datetime > dateFrom & data$datetime < dateTo);
  
  myMax <- 0;
  for (col in cols) {
    if (col != 'datetime' & (length(displayOnly) == 0 | col %in% displayOnly)) {
      currentMax <- max(data[[col]]);
      if (currentMax > myMax) {
        myMax <- currentMax;
      }  
    } 
  }
  
  legendColorsList <- NULL;
  legendChannelsList <- NULL;
  plot(data$datetime, as.vector(data[['mains.dat']]), col=colors[['mains.dat']], type="l", xlab="Time", xlim=c(dateFrom, dateTo), ylim=c(0,myMax), ylab="Consumption (W)", main="Energy Consumption (House 2)");
  for (col in cols) {
    if (col != 'mains' && col != 'datetime' & (length(displayOnly) == 0 | col %in% displayOnly)) {
      lines(data$datetime, as.vector(data[[col]]), col=colors[[col]]);
    }
    
    if (col != 'datetime') {
      legendChannelsList[length(legendChannelsList) + 1] <- col;
    }
    
    legendColorsList[length(legendColorsList) + 1] <- colors[[col]]; 
  }
  
  legend("topright", inset = c(-0.0, 0), fill=legendColorsList, title="Channels", x.intersp=0.2, legend=legendChannelsList, cex=0.6, xpd=T)
}

data <- NULL;
colors <- list();

#data <- loadChannel("mains.dat", data); colors[["mains.dat"]] <- "red";
#data <- loadChannel("bathroom.dat", data); colors[["bathroom.dat"]] <- "blue";
#data <- loadChannel("dishwasher.dat", data); colors[["dishwasher.dat"]] <- "gray";
#data <- loadChannel("electric_heat.dat", data); colors[["electric_heat.dat"]] <- "pink";
#data <- loadChannel("kitchen.dat", data); colors[["kitchen.dat"]] <- "purple";
#data <- loadChannel("lighting.dat", data); colors[["lighting.dat"]] <- "yellow";
#data <- loadChannel("microwave.dat", data); colors[["microwave.dat"]] <- "brown";
#data <- loadChannel("oven.dat", data); colors[["oven.dat"]] <- "black";
#data <- loadChannel("refridgerator.dat", data); colors[["refridgerator.dat"]] <- "green";
#data <- loadChannel("stove.dat", data); colors[["stove.dat"]] <- "cyan";
#data <- loadChannel("washer_dryer.dat", data); colors[["washer_dryer.dat"]] <- "orange";

data <- loadChannel("mains.dat", data); colors[["mains.dat"]] <- "red";
data <- loadChannel("dishwasher.dat", data); colors[["dishwasher.dat"]] <- "gray";
data <- loadChannel("disposal.dat", data); colors[["disposal.dat"]] <- "black";
data <- loadChannel("kitchen1.dat", data); colors[["kitchen1.dat"]] <- "purple";
data <- loadChannel("kitchen2.dat", data); colors[["kitchen2.dat"]] <- "pink";
data <- loadChannel("lighting.dat", data); colors[["lighting.dat"]] <- "yellow";
data <- loadChannel("microwave.dat", data); colors[["microwave.dat"]] <- "brown";
data <- loadChannel("refridgerator.dat", data); colors[["refridgerator.dat"]] <- "green";
data <- loadChannel("stove.dat", data); colors[["stove.dat"]] <- "cyan";
data <- loadChannel("washer.dat", data); colors[["washer.dat"]] <- "orange";

vizualize(data, colors, 1303139500, 1303140500, c());
vizualize(data, colors, 1303687107, 1303730307, c("washer.dat", "dishwasher.dat", "stove.dat"));

