load data local infile 'C:\\Users\\YooJin\\cs201\\lab11\\lab11\\popularWords.txt' into table words 
FIELDS TERMINATED BY '' ENCLOSED BY '"' ESCAPED BY '\\' LINES TERMINATED BY '\r\n' (word);