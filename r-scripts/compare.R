rm(list=ls());
setwd("/home/gtakacs/fiit/bp/gnilm/data/export/house2/");

loadChannel <- function(filename) {
  channelData <- read.table(filename, col.names=c('datetime', 'value'));  
  return(channelData);
}

compare <- function(originalData, generatedData) {    
  minTimestamp <- min(generatedData['datetime']);
  maxTimestamp <- max(generatedData['datetime']);
  originalData <- subset(originalData, originalData$datetime >= minTimestamp & originalData$datetime <= maxTimestamp);
  
  maxValueOrig <- max(originalData['value']);
  maxValueGen <- max(generatedData['value']);
  maxValue <- max(c(maxValueOrig, maxValueGen));
  
  minValueOrig <- min(originalData['value']);
  minValueGen <- min(generatedData['value']);
  minValue <- min(c(minValueOrig, minValueGen, 0));
  
  plot(originalData$datetime, as.vector(originalData[['value']]), col="red", type="l", xlab="Time", ylim=c(minValue, maxValue), ylab="Consumption (W)", main="Energy Consumption (House 2)")
  lines(generatedData$datetime, as.vector(generatedData[['value']]), col="blue");
  
  #legend("topright", inset = c(-0.0, 0), fill=legendColorsList, title="Channels", x.intersp=0.2, legend=legendChannelsList, cex=0.6, xpd=T)
}

originalData <- loadChannel("/home/gtakacs/fiit/bp/gnilm/data/house2/kitchen2.dat"); 
generatedData <- loadChannel("/home/gtakacs/fiit/bp/gnilm/data/export/house2/kitchen2.dat"); 

compare(originalData, generatedData);
