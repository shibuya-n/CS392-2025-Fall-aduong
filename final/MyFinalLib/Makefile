########################(beg of [Makefile])########################
# Master Makefile for MyLibrary
# Place this file in the final/MyLibrary directory
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

# 1. MyRefer (no dependencies)
comp:: ; cd MyRefer && javac -cp $(Library) *.java

# 2. FnList (depends on MyRefer)
comp:: ; cd FnList && javac -cp $(Library) *.java

# 3. LnList (depends on FnList)
comp:: ; cd LnList && javac -cp $(Library) *.java

# 4. FnGseq (depends on FnList)
comp:: ; cd FnGseq && javac -cp $(Library) *.java

# 5. FnA1sz (depends on FnList, FnGseq)
comp:: ; cd FnA1sz && javac -cp $(Library) *.java

# 6. FnInt1 (depends on FnGseq, FnList, FnA1sz)
comp:: ; cd FnInt1 && javac -cp $(Library) *.java

# 7. FnStrn (depends on FnGseq, FnList, FnA1sz)
comp:: ; cd FnStrn && javac -cp $(Library) *.java

# 8. MyStack (depends on FnList, MyRefer)
comp:: ; cd MyStack && javac -cp $(Library) *.java

# 9. MyQueue (depends on FnList, MyRefer)
comp:: ; cd MyQueue && javac -cp $(Library) *.java

# 10. LnStrm (depends on FnList)
comp:: ; cd LnStrm && javac -cp $(Library) *.java

# 11. FnGtree (depends on FnList)
comp:: ; cd FnGtree && javac -cp $(Library) *.java

# 12. FnTree (depends on FnList, LnStrm, FnGtree)
comp:: ; cd FnTree && javac -cp $(Library) *.java

# 13. FnTuple (no dependencies)
comp:: ; cd FnTuple && javac -cp $(Library) *.java

# 14. BinSearch (no dependencies)
comp:: ; cd BinSearch && javac -cp $(Library) *.java

# 15. LnGseq (depends on LnList)
comp:: ; cd LnGseq && javac -cp $(Library) *.java

# 16. MyPQueue (no external dependencies)
comp:: ; cd MyPQueue && javac -cp $(Library) *.java

# 17. MyDeque (depends on FnList, MyRefer)
comp:: ; cd MyDeque && javac -cp $(Library) *.java

# 18. MyMap00 (depends on FnList, LnStrm, FnTuple)
comp:: ; cd MyMap00 && javac -cp $(Library) *.java

###################################################################
# Clean all packages
###################################################################

cleanall:: ; cd MyRefer && rm -f *.class
cleanall:: ; cd FnList && rm -f *.class
cleanall:: ; cd LnList && rm -f *.class
cleanall:: ; cd FnGseq && rm -f *.class
cleanall:: ; cd FnA1sz && rm -f *.class
cleanall:: ; cd FnInt1 && rm -f *.class
cleanall:: ; cd FnStrn && rm -f *.class
cleanall:: ; cd MyStack && rm -f *.class
cleanall:: ; cd MyQueue && rm -f *.class
cleanall:: ; cd LnStrm && rm -f *.class
cleanall:: ; cd FnGtree && rm -f *.class
cleanall:: ; cd FnTree && rm -f *.class
cleanall:: ; cd FnTuple && rm -f *.class
cleanall:: ; cd BinSearch && rm -f *.class
cleanall:: ; cd LnGseq && rm -f *.class
cleanall:: ; cd MyPQueue && rm -f *.class
cleanall:: ; cd MyDeque && rm -f *.class
cleanall:: ; cd MyMap00 && rm -f *.class

########################(end of [Makefile])########################