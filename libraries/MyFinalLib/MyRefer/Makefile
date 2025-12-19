########################(beg of [Makefile])########################
Library=./../..
###################################################################

comp::

###################################################################

clean:: ; rm -f *~

cleanall:: clean
cleanall:: ; rm -f *.class

###################################################################

# Compile both files together to resolve dependencies
comp:: ; javac -cp $(Library) MyRefer.java MyReferNullExn.java

########################(end of [Makefile])########################