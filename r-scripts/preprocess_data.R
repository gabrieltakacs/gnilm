setwd("/home/gtakacs/fiit/bp/data-sets/redd/low_freq/house_1");
rm(list=ls());

# Funkcia nacita data zo zadanych suborov. Vsetky zadane subory by mali byt z toho isteho kanalu.
# Data z jednotlivych suborov su scitane. 
readChannel <- function(filenames = c(NULL)) {
  data <- NULL;
  
  if (length(filenames) > 1) {
    for (filename in filenames) {
      if (!is.null(data)) {
        currentData <- read.table(filename, col.names = c('datetime', filename));
        data <- merge(data, currentData, by='datetime');
      } else {
        data <- read.table(filename, col.names = c('datetime', filename));
      }
    }  
    
    data <- transform(data, value=rowSums(data[, 2:(length(filenames)+1)], na.rm=TRUE));
  } else {
    data <- read.table(filenames[1], col.names = c('datetime', 'value'));
  }
  
  for (filename in filenames) {
    data[[filename]] <- NULL;
  }
  
  data$datetime <- as.POSIXct(data$datetime, origin="1970-01-01");
  
  return(data);
}

x <- readChannel(c("channel_1.dat", "channel_2.dat"));

library(zoo);
# y <- zoo(x$value, order.by=x$datetime, frequency=1);
# x <- na.approx(x);

write.table(x, file="test.dat", row.names=FALSE, col.names=FALSE);