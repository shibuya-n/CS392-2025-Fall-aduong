########################(beg of [Makefile])########################
# Master Makefile for final/MyFinalLib
# Compiles all library packages in proper dependency order
###################################################################

Library=..

###################################################################

comp::

###################################################################

clean:: ; rm -f *~

cleanall:: clean

###################################################################
# Compile packages in dependency order
###################################################################

# Phase 1: No dependencies
comp:: ; @echo "=== Phase 1: Base packages ==="
comp:: ; cd MyRefer && $(MAKE) -s comp Library=$(Library)
comp:: ; cd FnTuple && $(MAKE) -s comp Library=$(Library)
comp:: ; cd BinSearch && $(MAKE) -s comp Library=$(Library)

# Phase 2: Depends on MyRefer
comp:: ; @echo "=== Phase 2: FnList ==="
comp:: ; cd FnList && $(MAKE) -s comp Library=$(Library)

# Phase 3: Depends on FnList
comp:: ; @echo "=== Phase 3: Streams and Generic Sequences ==="
comp:: ; cd LnStrm && $(MAKE) -s comp Library=$(Library)
comp:: ; cd LnList && $(MAKE) -s comp Library=$(Library)
comp:: ; cd FnGseq && $(MAKE) -s comp Library=$(Library)
comp:: ; cd LnGseq && $(MAKE) -s comp Library=$(Library)

# Phase 4: Depends on FnList and FnGseq
comp:: ; @echo "=== Phase 4: Data Structures ==="
comp:: ; cd FnA1sz && $(MAKE) -s comp Library=$(Library)
comp:: ; cd FnInt1 && $(MAKE) -s comp Library=$(Library)
comp:: ; cd FnStrn && $(MAKE) -s comp Library=$(Library)
comp:: ; cd MyStack && $(MAKE) -s comp Library=$(Library)
comp:: ; cd MyQueue && $(MAKE) -s comp Library=$(Library)
comp:: ; cd MyPQueue && $(MAKE) -s comp Library=$(Library)
comp:: ; cd MyDeque && $(MAKE) -s comp Library=$(Library)
comp:: ; cd MyArrayList && $(MAKE) -s comp Library=$(Library)

# Phase 5: Depends on LnStrm, MyStack, MyQueue
comp:: ; @echo "=== Phase 5: Tree Structures ==="
comp:: ; cd FnGtree && $(MAKE) -s comp Library=$(Library)
comp:: ; cd FnTree && $(MAKE) -s comp Library=$(Library)
comp:: ; cd LnTree && $(MAKE) -s comp Library=$(Library)

# Phase 6: Depends on multiple packages
comp:: ; @echo "=== Phase 6: Advanced Structures ==="
comp:: ; cd MyMap00 && $(MAKE) -s comp Library=$(Library)

# Phase 7: Sort utilities
comp:: ; @echo "=== Phase 7: Sort Utilities ==="
comp:: ; cd Sort && $(MAKE) -s comp Library=$(Library)

###################################################################
# Clean all packages
###################################################################

cleanall:: ; @echo "=== Cleaning all packages ==="
cleanall:: ; cd MyRefer && $(MAKE) -s cleanall
cleanall:: ; cd FnList && $(MAKE) -s cleanall
cleanall:: ; cd LnList && $(MAKE) -s cleanall
cleanall:: ; cd FnGseq && $(MAKE) -s cleanall
cleanall:: ; cd LnGseq && $(MAKE) -s cleanall
cleanall:: ; cd FnA1sz && $(MAKE) -s cleanall
cleanall:: ; cd FnInt1 && $(MAKE) -s cleanall
cleanall:: ; cd FnStrn && $(MAKE) -s cleanall
cleanall:: ; cd MyStack && $(MAKE) -s cleanall
cleanall:: ; cd MyQueue && $(MAKE) -s cleanall
cleanall:: ; cd MyPQueue && $(MAKE) -s cleanall
cleanall:: ; cd MyDeque && $(MAKE) -s cleanall
cleanall:: ; cd MyArrayList && $(MAKE) -s cleanall
cleanall:: ; cd LnStrm && $(MAKE) -s cleanall
cleanall:: ; cd FnGtree && $(MAKE) -s cleanall
cleanall:: ; cd FnTree && $(MAKE) -s cleanall
cleanall:: ; cd LnTree && $(MAKE) -s cleanall
cleanall:: ; cd FnTuple && $(MAKE) -s cleanall
cleanall:: ; cd BinSearch && $(MAKE) -s cleanall
cleanall:: ; cd MyMap00 && $(MAKE) -s cleanall
cleanall:: ; cd Sort && $(MAKE) -s cleanall

###################################################################
# Help and verification
###################################################################

help::
	@echo "MyFinalLib Makefile"
	@echo "==================="
	@echo ""
	@echo "Available targets:"
	@echo "  make comp      - Compile all library packages"
	@echo "  make clean     - Remove temporary files"
	@echo "  make cleanall  - Remove all compiled .class files"
	@echo "  make verify    - Verify all packages compiled successfully"
	@echo "  make help      - Show this help message"
	@echo ""
	@echo "Library structure:"
	@echo "  Phase 1: MyRefer, FnTuple, BinSearch"
	@echo "  Phase 2: FnList"
	@echo "  Phase 3: LnStrm, LnList, FnGseq, LnGseq"
	@echo "  Phase 4: FnA1sz, FnInt1, FnStrn, Stacks, Queues"
	@echo "  Phase 5: FnGtree, FnTree, LnTree"
	@echo "  Phase 6: MyMap00"
	@echo "  Phase 7: Sort"

verify::
	@echo "=== Verifying library compilation ==="
	@errors=0; \
	for pkg in MyRefer FnList FnTuple LnStrm LnList FnGseq LnGseq FnA1sz \
	           FnInt1 FnStrn MyStack MyQueue MyPQueue MyDeque FnGtree \
	           FnTree BinSearch MyMap00 Sort; do \
		if [ -d $$pkg ]; then \
			if ls $$pkg/*.class 1> /dev/null 2>&1; then \
				echo "✓ $$pkg compiled successfully"; \
			else \
				echo "✗ $$pkg has no .class files"; \
				errors=$$((errors + 1)); \
			fi; \
		else \
			echo "⚠ $$pkg directory not found"; \
		fi; \
	done; \
	if [ $$errors -eq 0 ]; then \
		echo ""; \
		echo "All packages compiled successfully!"; \
	else \
		echo ""; \
		echo "$$errors package(s) failed to compile"; \
		exit 1; \
	fi

###################################################################
# Create missing Makefiles for packages that need them
###################################################################

setup-sort::
	@echo "Creating Makefile for Sort package..."
	@mkdir -p Sort
	@echo "########################(beg of [Makefile])########################" > Sort/Makefile
	@echo "Library=./../.." >> Sort/Makefile
	@echo "###################################################################" >> Sort/Makefile
	@echo "" >> Sort/Makefile
	@echo "comp::" >> Sort/Makefile
	@echo "" >> Sort/Makefile
	@echo "###################################################################" >> Sort/Makefile
	@echo "" >> Sort/Makefile
	@echo "clean:: ; rm -f *~" >> Sort/Makefile
	@echo "" >> Sort/Makefile
	@echo "cleanall:: clean" >> Sort/Makefile
	@echo "cleanall:: ; rm -f *.class" >> Sort/Makefile
	@echo "" >> Sort/Makefile
	@echo "###################################################################" >> Sort/Makefile
	@echo "" >> Sort/Makefile
	@echo "comp:: ; javac -cp $$(Library) MergeSort.java" >> Sort/Makefile
	@echo "comp:: ; javac -cp $$(Library) ArrayQuickSort.java" >> Sort/Makefile
	@echo "" >> Sort/Makefile
	@echo "########################(end of [Makefile])########################" >> Sort/Makefile
	@echo "Sort/Makefile created successfully"

setup-myarraylist::
	@echo "Creating Makefile for MyArrayList package..."
	@mkdir -p MyArrayList
	@echo "########################(beg of [Makefile])########################" > MyArrayList/Makefile
	@echo "Library=./../.." >> MyArrayList/Makefile
	@echo "###################################################################" >> MyArrayList/Makefile
	@echo "" >> MyArrayList/Makefile
	@echo "comp::" >> MyArrayList/Makefile
	@echo "" >> MyArrayList/Makefile
	@echo "###################################################################" >> MyArrayList/Makefile
	@echo "" >> MyArrayList/Makefile
	@echo "clean:: ; rm -f *~" >> MyArrayList/Makefile
	@echo "" >> MyArrayList/Makefile
	@echo "cleanall:: clean" >> MyArrayList/Makefile
	@echo "cleanall:: ; rm -f *.class" >> MyArrayList/Makefile
	@echo "" >> MyArrayList/Makefile
	@echo "###################################################################" >> MyArrayList/Makefile
	@echo "" >> MyArrayList/Makefile
	@echo "comp:: ; javac -cp $$(Library) MyArrayList.java" >> MyArrayList/Makefile
	@echo "" >> MyArrayList/Makefile
	@echo "########################(end of [Makefile])########################" >> MyArrayList/Makefile
	@echo "MyArrayList/Makefile created successfully"

setup:: setup-sort setup-myarraylist
	@echo ""
	@echo "All missing Makefiles created!"

########################(end of [Makefile])########################