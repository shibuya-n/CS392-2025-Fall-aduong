##################################################################
JAVA=java
JAVAC=javac
##################################################################
clean:: ; rm -f *~
cleanall:: clean
cleanall:: ; rm -f *.class
##################################################################
test:: \
FnListTest
FnListTest: \
FnListTest.class ; \
$(JAVA) -cp .::./../Library.jar FnListTest
FnListTest.class: FnListTest.java; \
$(JAVAC) -cp .::./../Library.jar FnListTest.java
##################################################################
test:: \
FnArrayTest
FnArrayTest: \
FnArrayTest.class ; \
$(JAVA) -cp .::./../Library.jar FnArrayTest
FnArrayTest.class: FnArrayTest.java; \
$(JAVAC) -cp .::./../Library.jar FnArrayTest.java
##################################################################
test:: \
QueenPuzzleTest
QueenPuzzleTest: \
QueenPuzzleTest.class ; \
$(JAVA) -cp .::./../Library.jar QueenPuzzleTest
QueenPuzzleTest.class: \
QueenBoard.java QueenPuzzleTest.java; \
$(JAVAC) -cp .::./../Library.jar QueenPuzzleTest.java
##################################################################
test:: \
MyStackListTest
MyStackListTest: \
MyStackListTest.class ; \
$(JAVA) -cp .::./../Library.jar MyStackListTest
MyStackListTest.class: MyStackListTest.java; \
$(JAVAC) -cp .::./../Library.jar MyStackListTest.java
##################################################################
test:: \
MyStackArrayTest
MyStackArrayTest: \
MyStackArrayTest.class ; \
$(JAVA) -cp .::./../Library.jar MyStackArrayTest
MyStackArrayTest.class: MyStackArrayTest.java; \
$(JAVAC) -cp .::./../Library.jar MyStackArrayTest.java
##################################################################
######################## end-of(Makefile) ########################
##################################################################
