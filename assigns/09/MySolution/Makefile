########################(beg of [Makefile])########################
# Makefile for Assignment 09
# Priority Queue and Knight's Tour implementations
###################################################################

JAVA = java
JAVAC = javac
LIBRARY = Library
MYPQUEUE = MyPQueue

###################################################################
# Compilation flags
###################################################################

JFLAGS = -cp .:$(LIBRARY):$(MYPQUEUE)

###################################################################
# Targets
###################################################################

all: compile

compile: compile_01 compile_02

###################################################################
# Assignment 09-01: Priority Queue (Binary Heap)
###################################################################

compile_01: MyPQueue_classes
	$(JAVAC) $(JFLAGS) Assign09_01.java

MyPQueue_classes:
	$(JAVAC) -cp . $(MYPQUEUE)/MyPQueue.java
	$(JAVAC) -cp . $(MYPQUEUE)/MyPQueueBase.java
	$(JAVAC) -cp . $(MYPQUEUE)/MyPQueueEmptyExn.java
	$(JAVAC) -cp . $(MYPQUEUE)/MyPQueueFullExn.java
	$(JAVAC) -cp . $(MYPQUEUE)/MyPQueueArray.java

###################################################################
# Assignment 09-02: Knight's Tour with Priority-First Search
###################################################################

compile_02: Library_classes
	$(JAVAC) $(JFLAGS) Assign09_02.java

Library_classes:
	cd $(LIBRARY) && $(MAKE) compall

###################################################################
# Run targets
###################################################################

run_01: compile_01
	$(JAVA) $(JFLAGS) Assign09_01

run_02: compile_02
	$(JAVA) $(JFLAGS) Assign09_02

###################################################################
# Test targets
###################################################################

test: test_01 test_02

test_01: run_01
	@echo ""
	@echo "=== Testing Assign09_01 (Priority Queue) ==="
	@echo ""

test_02: run_02
	@echo ""
	@echo "=== Testing Assign09_02 (Knight's Tour) ==="
	@echo ""

###################################################################
# Clean targets
###################################################################

clean:
	rm -f *~ *.class
	rm -f $(MYPQUEUE)/*.class
	cd $(LIBRARY) && $(MAKE) clean

cleanall: clean
	cd $(LIBRARY) && $(MAKE) cleanall

###################################################################
# Individual assignment targets
###################################################################

assign01: compile_01
	@echo "Compiled Assign09_01 (Priority Queue)"

assign02: compile_02
	@echo "Compiled Assign09_02 (Knight's Tour)"

###################################################################
# Help target
###################################################################

help:
	@echo "Available targets:"
	@echo "  all        - Compile all assignments (default)"
	@echo "  compile    - Compile both Assign09_01 and Assign09_02"
	@echo "  compile_01 - Compile only Assign09_01"
	@echo "  compile_02 - Compile only Assign09_02"
	@echo "  run_01     - Run Assign09_01"
	@echo "  run_02     - Run Assign09_02"
	@echo "  test       - Run both assignments"
	@echo "  test_01    - Run Assign09_01 test"
	@echo "  test_02    - Run Assign09_02 test"
	@echo "  assign01   - Compile Assign09_01 only"
	@echo "  assign02   - Compile Assign09_02 only"
	@echo "  clean      - Remove class files"
	@echo "  cleanall   - Remove all generated files"
	@echo "  help       - Show this help message"

###################################################################
# Phony targets
###################################################################

.PHONY: all compile compile_01 compile_02 MyPQueue_classes Library_classes \
        run_01 run_02 test test_01 test_02 \
        assign01 assign02 clean cleanall help

########################(end of [Makefile])########################