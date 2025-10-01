# Name of the main class to run
MAIN = MyDequeListTest

# All .java files in the directory
SOURCES = $(wildcard *.java)
CLASSES = $(SOURCES:.java=.class)

# Default target: compile everything
all: $(CLASSES)

# Rule to build .class files from .java files
%.class: %.java
	javac $<

# Run the program (assumes you have a main in MyDequeListTest.java)
run: all
	java $(MAIN)

# Clean up .class files
clean:
	rm -f *.class