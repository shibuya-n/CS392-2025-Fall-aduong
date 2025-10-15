########################################################################
#
# A simple Makefile for compiling Java code for MySolution
#
########################################################################

JAVA = java
JAVAC = javac

########################################################################
# Classpath (using your actual path)
MYCPS = .:/Users/aduon/.vscode/CS392-2025-Fall-aduong/libraries/CS392-2025-Fall-aduong.jar 
########################################################################

all:: 
all:: \
Assign02_01 \
Assign02_02 \
Assign02_03

########################################################################
# Run and compile rules
########################################################################

Assign02_01: Assign02_01.class ; $(JAVA) -cp $(MYCPS) Assign02_01
Assign02_01.class: Assign02_01.java ; $(JAVAC) -cp $(MYCPS) Assign02_01.java

Assign02_02: Assign02_02.class ; $(JAVA) -cp $(MYCPS) Assign02_02
Assign02_02.class: Assign02_02.java ; $(JAVAC) -cp $(MYCPS) Assign02_02.java

Assign02_03: Assign02_03.class ; $(JAVA) -cp $(MYCPS) Assign02_03
Assign02_03.class: Assign02_03.java ; $(JAVAC) -cp $(MYCPS) Assign02_03.java

########################################################################
clean:: ; rm -f *~
########################################################################
cleanall:: clean
cleanall:: ; rm -f *.class
########################################################################
########################################################################
########################################################################
