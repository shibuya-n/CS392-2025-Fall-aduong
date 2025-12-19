########################(beg of [Makefile])########################

clean:: ; rm -f */*~

cleanall:: clean
cleanall:: ; rm -f */*.class

###################################################################

compall:: FnGseq
compall:: LnGseq
compall:: FnInt1
compall:: FnStrn
compall:: FnList
compall:: LnList
compall:: FnA1sz
compall:: LnStrm
compall:: FnTuple
compall:: MyRefer
compall:: MyStack
compall:: MyQueue
compall:: FnTree
compall:: FnGtree
compall:: BinSearch
compall:: Sort

###################################################################

FnGseq:: ; make -C FnGseq comp
FnStrn:: ; make -C FnStrn comp
FnStrn:: ; make -C FnInt1 comp
FnList:: ; make -C FnList comp
FnA1sz:: ; make -C FnA1sz comp
FnTuple:: ; make -C FnTuple comp

###################################################################

LnGseq:: ; make -C LnGseq comp
LnList:: ; make -C LnList comp
LnStrm:: ; make -C LnStrm comp

###################################################################

MyStack:: ; make -C MyRefer comp
MyStack:: ; make -C MyStack comp
MyQueue:: ; make -C MyQueue comp

###################################################################

FnTree:: ; make -C FnTree comp
FnGtree:: ; make -C FnGtree comp

###################################################################

BinSearch:: ; make -C BinSearch comp

###################################################################L

ROOT=./..

final_jar: ; \
jar -cvf final.jar \
$(ROOT)/MyFinalLib/FnGseq/*.class \
$(ROOT)/MyFinalLib/LnGseq/*.class \
$(ROOT)/MyFinalLib/FnInt1/*.class \
$(ROOT)/MyFinalLib/FnStrn/*.class \
$(ROOT)/MyFinalLib/FnList/*.class \
$(ROOT)/MyFinalLib/LnList/*.class \
$(ROOT)/MyFinalLib/FnA1sz/*.class \
$(ROOT)/MyFinalLib/LnStrm/*.class \
$(ROOT)/MyFinalLib/FnTuple/*.class \
$(ROOT)/MyFinalLib/MyRefer/*.class \
$(ROOT)/MyFinalLib/MyStack/*.class \
$(ROOT)/MyFinalLib/MyQueue/*.class \
$(ROOT)/MyFinalLib/FnTree/*.class \
$(ROOT)/MyFinalLib/FnGtree/*.class $(ROOT)/MyFinalLib/BinSearch/*.class

###################################################################

########################(end of [Makefile])########################
