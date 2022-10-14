# New Relic Coding Challenge

This Java application will parse a text file or standard input (stdin) and output the top 100 three-word phrases found.

Requirements:
- Java 1.8 (or newer)
- Maven

## How to build/test

Simply use Maven's `package` goal to build, test, and package the application.

	mvn package

The `package` goal will also run tests, but these can be ran independently by executing:

	mvn test

## How to run

The application can take input from standard in:

	cat myfile.txt | java -jar target/phrasecounter-0.0.1-SNAPSHOT.jar
	
or you can provide a file:

	java -jar target/phrasecounter-0.0.1-SNAPSHOT.jar ./myfile.txt

multiple files can also be provided:

	java -jar target/phrasecounter-0.0.1-SNAPSHOT.jar ./myfile.txt ./myfile2.txt


## Docker instructions

To build using Docker, change to the source directory and execute:

	docker run -it --rm -v $PWD:/usr/src/maven -w /usr/src/maven maven:3-openjdk-18 mvn package  

Once built, you can run using:

	docker run -it --rm -v $PWD:/usr/src/maven -w /usr/src/maven maven:3-openjdk-18 java -jar /usr/src/maven/target/phrasecounter-0.0.1-SNAPSHOT.jar <files to scan>
	

## Improvements

- Error handling (file not found, binary streams, empty stream)
- Allow user to specify number of phrases output
- Research potential performance improvements (if any)
- Fix the bug documented below.

## Bugs

Running the application without any files or standard input passed to it will cause the application to hang waiting for input.



