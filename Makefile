TARGET=Chess

all: compile
	java ${TARGET}

compile: 
	javac ${TARGET}.java

clean:
	rm *.class Pieces/*.class ${TARGET}.jar

jar: compile
	jar cfe ${TARGET}.jar ${TARGET} *.class Pieces/*.class Sprites/*.png
