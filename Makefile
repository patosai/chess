TARGET=Chess

all: compile
	java ${TARGET}

compile: 
	javac ${TARGET}.java

clean:
	rm *.class Pieces/*.class
