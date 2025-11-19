# Makefile for Assign08 Hash Table Implementations

# Java compiler
JC = javac
JFLAGS = -d bin -sourcepath . -cp .:Library

# Java runtime
JAVA = java
JRFLAGS = -cp .:bin:Library

# Directories
SRC_DIR = .
BIN_DIR = bin
LIB_DIR = Library

# Source files
SOURCES = Assign08_01.java Assign08_02.java

# Default target
all: setup compile

# Create bin directory
setup:
	@mkdir -p $(BIN_DIR)

# Compile all source files
compile: $(SOURCES)
	$(JC) $(JFLAGS) $(SOURCES)

# Compile only Assign08_01 (separate chaining)
assign01: setup
	$(JC) $(JFLAGS) Assign08_01.java

# Compile only Assign08_02 (quadratic probing)
assign02: setup
	$(JC) $(JFLAGS) Assign08_02.java

# Run test for Assign08_01 (if you have a test file)
test01: compile
	$(JAVA) $(JRFLAGS) Assign08_01_test

# Run test for Assign08_02 (if you have a test file)
test02: compile
	$(JAVA) $(JRFLAGS) Assign08_02_test

# Clean compiled files
clean:
	rm -rf $(BIN_DIR)
	find . -name "*.class" -type f -delete

# Clean and rebuild
rebuild: clean all

# Help target
help:
	@echo "Available targets:"
	@echo "  all        - Setup and compile all files (default)"
	@echo "  compile    - Compile all source files"
	@echo "  assign01   - Compile only Assign08_01"
	@echo "  assign02   - Compile only Assign08_02"
	@echo "  test01     - Run tests for Assign08_01"
	@echo "  test02     - Run tests for Assign08_02"
	@echo "  clean      - Remove all compiled files"
	@echo "  rebuild    - Clean and rebuild everything"
	@echo "  help       - Show this help message"

.PHONY: all setup compile assign01 assign02 test01 test02 clean rebuild help