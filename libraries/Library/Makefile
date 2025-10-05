########################(beg of [Makefile])########################

clean:: ; rm -f */*~

cleanall:: clean
cleanall:: ; rm -f */*.class

###################################################################

compall:: FnGseq
compall:: FnStrn
compall:: FnList
compall:: FnArray
compall:: FnTuple
compall:: MyRefer
compall:: MyStack
compall:: MyQueue
compall:: FnGtree
compall:: BinSearch

###################################################################

FnGseq:: ; make -C FnGseq comp

###################################################################

FnStrn:: ; make -C FnStrn comp
FnList:: ; make -C FnList comp
FnArray:: ; make -C FnArray comp
FnTuple:: ; make -C FnTuple comp

###################################################################

MyStack:: ; make -C MyRefer comp
MyStack:: ; make -C MyStack comp
MyQueue:: ; make -C MyQueue comp

###################################################################

FnGtree:: ; make -C FnGtree comp

###################################################################

BinSearch:: ; make -C BinSearch comp

###################################################################L

ROOT=./..

Library_jar: ; \
jar -cvf Library.jar \
$(ROOT)/Library/FnGseq/*.class \
$(ROOT)/Library/FnStrn/*.class \
$(ROOT)/Library/FnList/*.class \
$(ROOT)/Library/FnArray/*.class \
$(ROOT)/Library/FnTuple/*.class \
$(ROOT)/Library/MyRefer/*.class \
$(ROOT)/Library/MyStack/*.class $(ROOT)/Library/MyQueue/*.class \
$(ROOT)/Library/FnGtree/*.class $(ROOT)/Library/BinSearch/*.class

###################################################################

########################(end of [Makefile])########################
