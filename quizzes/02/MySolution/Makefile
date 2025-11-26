# Makefile for Quiz02 Java programs
# Assumes Library directory exists with FnList, FnA1sz, LnStrm, and FnGtree

JAVAC = javac
JAVA = java
JAVAC_FLAGS = -cp .:Library
JAVA_FLAGS = -cp .:Library

# Source files
SOURCES = Quiz02_01.java Quiz02_02.java Quiz02_03.java Quiz02_04.java Quiz02_05.java Quiz02_06.java

# Class files (derived from sources)
CLASSES = $(SOURCES:.java=.class)

# Default target: compile all
all: $(CLASSES)

# Pattern rule for compiling Java files
%.class: %.java
	$(JAVAC) $(JAVAC_FLAGS) $<

# Individual targets for each quiz
quiz01: Quiz02_01.class
	$(JAVA) $(JAVA_FLAGS) Quiz02_01

quiz02: Quiz02_02.class
	$(JAVA) $(JAVA_FLAGS) Quiz02_02

quiz03: Quiz02_03.class
	$(JAVA) $(JAVA_FLAGS) Quiz02_03

quiz04: Quiz02_04.class
	$(JAVA) $(JAVA_FLAGS) Quiz02_04

quiz05: Quiz02_05.class
	$(JAVA) $(JAVA_FLAGS) Quiz02_05

quiz06: Quiz02_06.class
	$(JAVA) $(JAVA_FLAGS) Quiz02_06

# Run all programs
run: all
	@echo "=== Running Quiz02_01 ==="
	$(JAVA) $(JAVA_FLAGS) Quiz02_01
	@echo "\n=== Running Quiz02_02 ==="
	$(JAVA) $(JAVA_FLAGS) Quiz02_02
	@echo "\n=== Running Quiz02_03 ==="
	$(JAVA) $(JAVA_FLAGS) Quiz02_03
	@echo "\n=== Running Quiz02_05 ==="
	$(JAVA) $(JAVA_FLAGS) Quiz02_05
	@echo "\n=== Running Quiz02_06 ==="
	$(JAVA) $(JAVA_FLAGS) Quiz02_06

# Clean compiled files
clean:
	rm -f *.class
	rm -f Quiz02_*.class

# Clean and rebuild
rebuild: clean all

# Help target
help:
	@echo "Available targets:"
	@echo "  all     - Compile all Quiz02 programs (default)"
	@echo "  quiz01  - Compile and run Quiz02_01"
	@echo "  quiz02  - Compile and run Quiz02_02"
	@echo "  quiz03  - Compile and run Quiz02_03"
	@echo "  quiz04  - Compile and run Quiz02_04"
	@echo "  quiz05  - Compile and run Quiz02_05"
	@echo "  quiz06  - Compile and run Quiz02_06"
	@echo "  run     - Compile and run all programs"
	@echo "  clean   - Remove all compiled .class files"
	@echo "  rebuild - Clean and recompile everything"
	@echo "  help    - Show this help message"

.PHONY: all run clean rebuild help quiz01 quiz02 quiz03 quiz04 quiz05 quiz06