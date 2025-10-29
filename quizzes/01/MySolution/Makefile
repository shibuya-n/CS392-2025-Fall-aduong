########################################################################
#
# A simple Makefile for compiling Java code for Quiz01
#
########################################################################

JAVA = java
JAVAC = javac

########################################################################
# Classpath (using local Library folder)
MYCPS = .
########################################################################

all:: 
all:: \
Quiz01_01 \
Quiz01_02 \
Quiz01_03 \
Quiz01_04 \
Quiz01_05 \
Quiz01_06

########################################################################
# Run and compile rules
########################################################################

Quiz01_01: Quiz01_01.class ; $(JAVA) -cp $(MYCPS) Quiz01_01
Quiz01_01.class: Quiz01_01.java ; $(JAVAC) -cp $(MYCPS) Quiz01_01.java

Quiz01_02: Quiz01_02.class ; $(JAVA) -cp $(MYCPS) Quiz01_02
Quiz01_02.class: Quiz01_02.java ; $(JAVAC) -cp $(MYCPS) Quiz01_02.java

Quiz01_03: Quiz01_03.class ; $(JAVA) -cp $(MYCPS) Quiz01_03
Quiz01_03.class: Quiz01_03.java ; $(JAVAC) -cp $(MYCPS) Quiz01_03.java

Quiz01_04: Quiz01_04.class ; $(JAVA) -cp $(MYCPS) Quiz01_04
Quiz01_04.class: Quiz01_04.java ; $(JAVAC) -cp $(MYCPS) Quiz01_04.java

Quiz01_05: Quiz01_05.class ; $(JAVA) -cp $(MYCPS) Quiz01_05
Quiz01_05.class: Quiz01_05.java ; $(JAVAC) -cp $(MYCPS) Quiz01_05.java

Quiz01_06: Quiz01_06.class ; $(JAVA) -cp $(MYCPS) Quiz01_06
Quiz01_06.class: Quiz01_06.java ; $(JAVAC) -cp $(MYCPS) Quiz01_06.java

########################################################################
clean:: ; rm -f *~
########################################################################
cleanall:: clean
cleanall:: ; rm -f *.class
########################################################################
########################################################################
########################################################################