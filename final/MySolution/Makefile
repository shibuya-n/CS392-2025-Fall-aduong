########################(beg of [Makefile])########################
# Makefile for final/MySolution
# This compiles all Final_XX.java files
###################################################################

Library=./../MyFinalLib
Data=./../Data

###################################################################

comp::

###################################################################

clean:: ; rm -f *~

cleanall:: clean
cleanall:: ; rm -f *.class

###################################################################
# Compile Final files in dependency order
###################################################################

# Final_00: Base file reader (no dependencies except library)
Final_00.class: Final_00.java
	javac -cp .:$(Library) Final_00.java

comp:: Final_00.class

# Final_01: Word tokenizer (depends on Final_00)
Final_01.class: Final_01.java Final_00.class
	javac -cp .:$(Library) Final_01.java

comp:: Final_01.class

# Final_02: Word counting with quicksort + mergesort (depends on Final_01)
Final_02.class: Final_02.java Final_01.class
	javac -cp .:$(Library) Final_02.java

comp:: Final_02.class

# Final_03: Word counting with hash map (depends on Final_01)
Final_03.class: Final_03.java Final_01.class
	javac -cp .:$(Library) Final_03.java

comp:: Final_03.class

# Final_04: Word counting with RBST (depends on Final_01)
Final_04.class: Final_04.java Final_01.class
	javac -cp .:$(Library) Final_04.java

comp:: Final_04.class

# Final_05: N-way merge with priority queue (no dependencies on other Finals)
Final_05.class: Final_05.java
	javac -cp .:$(Library) Final_05.java

comp:: Final_05.class

###################################################################
# Test/Run targets
###################################################################

test::

# Test Final_01: Print first 30 words
test01:: Final_01.class
	@echo "=== Testing Final_01: First 30 words ==="
	java -cp .:$(Library) Final_01

# Test Final_02: Print first 100 word-count pairs (quicksort + mergesort)
test02:: Final_02.class
	@echo "=== Testing Final_02: Word counts (quicksort + mergesort) ==="
	java -cp .:$(Library) Final_02

# Test Final_03: Print first 100 word-count pairs (hash map)
test03:: Final_03.class
	@echo "=== Testing Final_03: Word counts (hash map) ==="
	java -cp .:$(Library) Final_03

# Test Final_04: Print first 100 word-count pairs (RBST)
test04:: Final_04.class
	@echo "=== Testing Final_04: Word counts (RBST) ==="
	java -cp .:$(Library) Final_04

# Test Final_05: 5-way mergesort on 1M elements
test05:: Final_05.class
	@echo "=== Testing Final_05: 5-way mergesort (1M elements) ==="
	java -cp .:$(Library) Final_05

# Run all tests
testall:: test01 test02 test03 test04 test05

###################################################################
# Verify data file exists
###################################################################

checkdata::
	@if [ ! -f $(Data)/pg2701.txt ]; then \
		echo "ERROR: Data file not found at $(Data)/pg2701.txt"; \
		echo "Please ensure pg2701.txt (Moby Dick) is in the Data directory"; \
		exit 1; \
	else \
		echo "Data file found: $(Data)/pg2701.txt"; \
	fi

###################################################################
# Help target
###################################################################

help::
	@echo "Available targets:"
	@echo "  make comp      - Compile all Final_XX.java files"
	@echo "  make test01    - Test Final_01 (word tokenizer)"
	@echo "  make test02    - Test Final_02 (quicksort + mergesort)"
	@echo "  make test03    - Test Final_03 (hash map)"
	@echo "  make test04    - Test Final_04 (RBST)"
	@echo "  make test05    - Test Final_05 (5-way mergesort)"
	@echo "  make testall   - Run all tests"
	@echo "  make checkdata - Verify data file exists"
	@echo "  make clean     - Remove temporary files"
	@echo "  make cleanall  - Remove all compiled files"

########################(end of [Makefile])########################