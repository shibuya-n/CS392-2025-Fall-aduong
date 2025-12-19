# PowerShell script to fix all Makefiles in MyFinalLib
# Run this from the MyFinalLib directory: .\fix-makefiles.ps1

Write-Host "Fixing all Makefiles..." -ForegroundColor Green
Write-Host ""

$makefileContent = @'
########################(beg of [Makefile])########################
Library=./../..
###################################################################

comp::

###################################################################

clean:: ; rm -f *~

cleanall:: clean
cleanall:: ; rm -f *.class

###################################################################

comp:: ; javac -cp $(Library) *.java

########################(end of [Makefile])########################
'@

$makefileContentWithColon = @'
########################(beg of [Makefile])########################
Library=./../..
###################################################################

comp::

###################################################################

clean:: ; rm -f *~

cleanall:: clean
cleanall:: ; rm -f *.class

###################################################################

comp:: ; javac -cp .:$(Library) *.java

########################(end of [Makefile])########################
'@

# Special makefile for FnTuple (compile Util files first)
$fnTupleMakefile = @'
########################(beg of [Makefile])########################
Library=./../..
###################################################################

comp::

###################################################################

clean:: ; rm -f *~

cleanall:: clean
cleanall:: ; rm -f *.class

###################################################################

comp:: ; javac -cp $(Library) FnTupl2Util.java FnTupl3Util.java FnTupl2.java FnTupl3.java

########################(end of [Makefile])########################
'@

# Special makefile for MyQueue (has test files)
$myQueueMakefile = @'
########################(beg of [Makefile])########################
Library=./../..
###################################################################

comp::

###################################################################

clean:: ; rm -f *~

cleanall:: clean
cleanall:: ; rm -f *.class

###################################################################

comp:: ; javac -cp $(Library) MyQueueEmptyExn.java MyQueueFullExn.java MyQueue.java MyQueueBase.java MyQueueList.java MyQueueArray.java

########################(end of [Makefile])########################
'@

# Special makefile for MyStack (has test files)
$myStackMakefile = @'
########################(beg of [Makefile])########################
Library=./../..
###################################################################

comp::

###################################################################

clean:: ; rm -f *~

cleanall:: clean
cleanall:: ; rm -f *.class

###################################################################

comp:: ; javac -cp $(Library) MyStackEmptyExn.java MyStackFullExn.java MyStack.java MyStackBase.java MyStackList.java MyStackArray.java

########################(end of [Makefile])########################
'@

# Special makefile for MyPQueue
$myPQueueMakefile = @'
########################(beg of [Makefile])########################
Library=./../..
###################################################################

comp::

###################################################################

clean:: ; rm -f *~

cleanall:: clean
cleanall:: ; rm -f *.class

###################################################################

comp:: ; javac -cp $(Library) MyPQueueEmptyExn.java MyPQueueFullExn.java MyPQueue.java MyPQueueBase.java MyPQueueArray.java

########################(end of [Makefile])########################
'@

# Special makefile for MyDeque
$myDequeMakefile = @'
########################(beg of [Makefile])########################
Library=./../..
###################################################################

comp::

###################################################################

clean:: ; rm -f *~

cleanall:: clean
cleanall:: ; rm -f *.class

###################################################################

comp:: ; javac -cp $(Library) MyDequeEmptyExn.java MyDequeFullExn.java MyDeque.java MyDequeBase.java MyDequeList.java

########################(end of [Makefile])########################
'@

# Special makefile for MyMap00
$myMap00Makefile = @'
########################(beg of [Makefile])########################
Library=./../..
###################################################################

comp::

###################################################################

clean:: ; rm -f *~

cleanall:: clean
cleanall:: ; rm -f *.class

###################################################################

comp:: ; javac -cp $(Library) MyMap00NoKeyExn.java MyMap00FullExn.java MyMap00.java MyMap00RBST.java MyMapSeparateChain.java MyMapOpenAddressing.java

########################(end of [Makefile])########################
'@

$directories = @(
    "MyRefer",
    "BinSearch",
    "FnList",
    "LnList",
    "FnGseq",
    "LnGseq",
    "FnA1sz",
    "FnInt1",
    "FnStrn",
    "MyArrayList",
    "FnGtree",
    "FnTree",
    "Sort"
)

foreach ($dir in $directories) {
    if (Test-Path $dir) {
        Write-Host "  Fixing $dir/Makefile..." -ForegroundColor Yellow
        Set-Content -Path "$dir/Makefile" -Value $makefileContent
        Write-Host "    DONE: $dir/Makefile fixed" -ForegroundColor Green
    }
}

if (Test-Path "LnStrm") {
    Write-Host "  Fixing LnStrm/Makefile (special case)..." -ForegroundColor Yellow
    Set-Content -Path "LnStrm/Makefile" -Value $makefileContentWithColon
    Write-Host "    DONE: LnStrm/Makefile fixed" -ForegroundColor Green
}

if (Test-Path "FnTuple") {
    Write-Host "  Fixing FnTuple/Makefile (special case)..." -ForegroundColor Yellow
    Set-Content -Path "FnTuple/Makefile" -Value $fnTupleMakefile
    Write-Host "    DONE: FnTuple/Makefile fixed" -ForegroundColor Green
}

if (Test-Path "MyQueue") {
    Write-Host "  Fixing MyQueue/Makefile (special case)..." -ForegroundColor Yellow
    Set-Content -Path "MyQueue/Makefile" -Value $myQueueMakefile
    Write-Host "    DONE: MyQueue/Makefile fixed" -ForegroundColor Green
}

if (Test-Path "MyStack") {
    Write-Host "  Fixing MyStack/Makefile (special case)..." -ForegroundColor Yellow
    Set-Content -Path "MyStack/Makefile" -Value $myStackMakefile
    Write-Host "    DONE: MyStack/Makefile fixed" -ForegroundColor Green
}

if (Test-Path "MyPQueue") {
    Write-Host "  Fixing MyPQueue/Makefile (special case)..." -ForegroundColor Yellow
    Set-Content -Path "MyPQueue/Makefile" -Value $myPQueueMakefile
    Write-Host "    DONE: MyPQueue/Makefile fixed" -ForegroundColor Green
}

if (Test-Path "MyDeque") {
    Write-Host "  Fixing MyDeque/Makefile (special case)..." -ForegroundColor Yellow
    Set-Content -Path "MyDeque/Makefile" -Value $myDequeMakefile
    Write-Host "    DONE: MyDeque/Makefile fixed" -ForegroundColor Green
}

if (Test-Path "MyMap00") {
    Write-Host "  Fixing MyMap00/Makefile (special case)..." -ForegroundColor Yellow
    Set-Content -Path "MyMap00/Makefile" -Value $myMap00Makefile
    Write-Host "    DONE: MyMap00/Makefile fixed" -ForegroundColor Green
}

Write-Host ""
Write-Host "All Makefiles fixed!" -ForegroundColor Green
Write-Host "Now run: make cleanall" -ForegroundColor Cyan
Write-Host "Then run: make comp" -ForegroundColor Cyan