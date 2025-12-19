########################(beg of [Makefile])########################
Library=./../..
###################################################################

comp::

###################################################################

clean:: ; rm -f *~

cleanall:: clean
cleanall:: ; rm -f *.class

###################################################################

# IMPORTANT: All three files have interdependencies,
# so they must be compiled together in one command
comp:: ; javac -cp .:$(Library) LnStcn.java LnStrm.java LnStrmSUtil.java

########################(end of [Makefile])########################