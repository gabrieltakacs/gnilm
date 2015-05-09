rm(list=ls());
setwd("/home/gtakacs/fiit/bp/gnilm/data/export/house2/");

loadChannelForCompare <- function(filename) {
  channelData <- read.table(filename, col.names=c('datetime', 'value'));  
  return(channelData);
}

compare <- function(originalData, generatedData, label) {    
  minTimestamp <- min(generatedData['datetime']);
  maxTimestamp <- max(generatedData['datetime']);
  originalData <- subset(originalData, originalData$datetime >= minTimestamp & originalData$datetime <= maxTimestamp);
  
  maxValueOrig <- max(originalData['value']);
  maxValueGen <- max(generatedData['value']);
  maxValue <- max(c(maxValueOrig, maxValueGen));
  
  minValueOrig <- min(originalData['value']);
  minValueGen <- min(generatedData['value']);
  minValue <- min(c(minValueOrig, minValueGen, 0));
  
  plot(originalData$datetime, as.vector(originalData[['value']]), col="red", type="l", xlab="Time", ylim=c(minValue, maxValue), ylab="Consumption (W)", main=label)
  lines(generatedData$datetime, as.vector(generatedData[['value']]), col="blue");
  
  legend("topleft", inset = c(-0.0, 0), fill=c("red", "blue"), x.intersp=0.2, legend=c("Original", "Generated"), cex=0.6, xpd=T)
}

originalData <- loadChannelForCompare("/home/gtakacs/fiit/bp/gnilm/data/house2/microwave.dat"); 
generatedData <- loadChannelForCompare("/home/gtakacs/fiit/bp/gnilm/data/export/house2/microwave.dat"); 
compare(originalData, generatedData, "Microwave (House 2)");
