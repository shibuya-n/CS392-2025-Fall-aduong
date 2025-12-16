# Save this as: final/MyLibrary/compile.ps1

Write-Host "Compiling MyLibrary packages in multiple passes..." -ForegroundColor Green
Write-Host ""

# Pass 1: Compile packages with no dependencies
Write-Host "Pass 1: Base packages (no dependencies)..." -ForegroundColor Cyan
$pass1 = @("MyRefer", "FnTuple", "BinSearch")

foreach ($pkg in $pass1) {
    if (Test-Path $pkg) {
        Write-Host "  Compiling $pkg..." -ForegroundColor Yellow
        Set-Location $pkg
        javac -cp ".;../.." *.java 2>&1 | Out-Null
        if ($LASTEXITCODE -eq 0) {
            Write-Host "    OK: $pkg compiled successfully" -ForegroundColor Green
        } else {
            Write-Host "    ERROR: $pkg compilation failed" -ForegroundColor Red
        }
        Set-Location ..
    }
}

Write-Host ""

# Pass 2: Compile FnList (depends on MyRefer only)
Write-Host "Pass 2: FnList..." -ForegroundColor Cyan
$pass2 = @("FnList")

foreach ($pkg in $pass2) {
    if (Test-Path $pkg) {
        Write-Host "  Compiling $pkg..." -ForegroundColor Yellow
        Set-Location $pkg
        javac -cp ".;../.." *.java 2>&1 | Out-Null
        if ($LASTEXITCODE -eq 0) {
            Write-Host "    OK: $pkg compiled successfully" -ForegroundColor Green
        } else {
            Write-Host "    ERROR: $pkg compilation failed" -ForegroundColor Red
        }
        Set-Location ..
    }
}

Write-Host ""

# Pass 3: Compile LnStrm, LnList, FnGseq (depend on FnList)
Write-Host "Pass 3: Stream and Generic Sequences..." -ForegroundColor Cyan
$pass3 = @("LnStrm", "LnList", "FnGseq", "LnGseq")

foreach ($pkg in $pass3) {
    if (Test-Path $pkg) {
        Write-Host "  Compiling $pkg..." -ForegroundColor Yellow
        Set-Location $pkg
        javac -cp ".;../.." *.java 2>&1 | Out-Null
        if ($LASTEXITCODE -eq 0) {
            Write-Host "    OK: $pkg compiled successfully" -ForegroundColor Green
        } else {
            Write-Host "    ERROR: $pkg compilation failed" -ForegroundColor Red
        }
        Set-Location ..
    }
}

Write-Host ""

# Pass 4: Compile packages that depend on FnList and FnGseq
Write-Host "Pass 4: Data structures..." -ForegroundColor Cyan
$pass4 = @("FnA1sz", "FnInt1", "FnStrn", "MyStack", "MyQueue", "MyPQueue", "MyDeque")

foreach ($pkg in $pass4) {
    if (Test-Path $pkg) {
        Write-Host "  Compiling $pkg..." -ForegroundColor Yellow
        Set-Location $pkg
        javac -cp ".;../.." *.java 2>&1 | Out-Null
        if ($LASTEXITCODE -eq 0) {
            Write-Host "    OK: $pkg compiled successfully" -ForegroundColor Green
        } else {
            Write-Host "    ERROR: $pkg compilation failed" -ForegroundColor Red
        }
        Set-Location ..
    }
}

Write-Host ""

# Pass 5: Compile tree structures (depend on LnStrm, MyStack, MyQueue)
Write-Host "Pass 5: Tree structures..." -ForegroundColor Cyan
$pass5 = @("FnGtree", "FnTree")

foreach ($pkg in $pass5) {
    if (Test-Path $pkg) {
        Write-Host "  Compiling $pkg..." -ForegroundColor Yellow
        Set-Location $pkg
        javac -cp ".;../.." *.java 2>&1 | Out-Null
        if ($LASTEXITCODE -eq 0) {
            Write-Host "    OK: $pkg compiled successfully" -ForegroundColor Green
        } else {
            Write-Host "    ERROR: $pkg compilation failed" -ForegroundColor Red
        }
        Set-Location ..
    }
}

Write-Host ""

# Pass 6: Compile maps (depend on everything)
Write-Host "Pass 6: Maps..." -ForegroundColor Cyan
$pass6 = @("MyMap00")

foreach ($pkg in $pass6) {
    if (Test-Path $pkg) {
        Write-Host "  Compiling $pkg..." -ForegroundColor Yellow
        Set-Location $pkg
        javac -cp ".;../.." *.java 2>&1 | Out-Null
        if ($LASTEXITCODE -eq 0) {
            Write-Host "    OK: $pkg compiled successfully" -ForegroundColor Green
        } else {
            Write-Host "    ERROR: $pkg compilation failed" -ForegroundColor Red
        }
        Set-Location ..
    }
}

Write-Host ""
Write-Host "Library compilation complete!" -ForegroundColor Green