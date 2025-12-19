    # Save this as: final/MyFinalLib/CompileAll.ps1
    # Run with: .\CompileAll.ps1

    Write-Host "Compiling MyFinalLib - All packages in one pass..." -ForegroundColor Green
    Write-Host ""

    # Get the library root directory
    $LibRoot = Get-Location

    # Clean old class files
    Write-Host "Cleaning old .class files..." -ForegroundColor Yellow
    Get-ChildItem -Recurse -Filter "*.class" | Remove-Item -Force
    Write-Host "Done cleaning.`n" -ForegroundColor Green

    # Collect all Java source files
    Write-Host "Collecting Java source files..." -ForegroundColor Yellow

    $javaFiles = @()

    # Add packages in logical order (helps compiler resolve some dependencies faster)
    $packages = @(
        "MyRefer",
        "FnTuple", 
        "BinSearch",
        "FnA1sz",
        "FnInt1",
        "FnStrn",
        "LnStrm",
        "FnList",
        "LnList",
        "FnGseq",
        "LnGseq",
        "MyStack",
        "MyQueue",
        "MyPQueue",
        "MyDeque",
        "MyArrayList",
        "FnGtree",
        "FnTree",
        "LnTree",
        "MyMap00",
        "Sort"
    )

    foreach ($pkg in $packages) {
        if (Test-Path $pkg) {
            $files = Get-ChildItem "$pkg\*.java" -ErrorAction SilentlyContinue
            if ($files) {
                $javaFiles += $files
                Write-Host "  Found $($files.Count) files in $pkg" -ForegroundColor Cyan
            }
        }
    }

    # Sort files to handle dependencies (e.g., LnStrm before LnStcn)
    $javaFiles = $javaFiles | Sort-Object -Property FullName -Descending

    Write-Host "`nTotal Java files found: $($javaFiles.Count)" -ForegroundColor Green
    Write-Host ""

    # Compile all files in one command
    Write-Host "Compiling all files (this may take a moment)..." -ForegroundColor Yellow

    $fileList = ($javaFiles | ForEach-Object { $_.FullName }) -join " "

    # Use @ to create a file list (avoids command line length limits)
    $tempFile = New-TemporaryFile
    $javaFiles | ForEach-Object { $_.FullName } | Out-File -FilePath $tempFile.FullName -Encoding ASCII

    # Compile using @file syntax to avoid command line length issues
    $output = & javac "@$($tempFile.FullName)" 2>&1

    if ($LASTEXITCODE -eq 0) {
        Write-Host "`n✓ Compilation successful!" -ForegroundColor Green
        
        # Count compiled class files
        $classCount = (Get-ChildItem -Recurse -Filter "*.class").Count
        Write-Host "  Generated $classCount .class files`n" -ForegroundColor Cyan
        
        # Verify key packages compiled
        Write-Host "Verifying key packages..." -ForegroundColor Yellow
        $keyPackages = @("FnList", "LnList", "MyPQueue", "LnStrm")
        foreach ($pkg in $keyPackages) {
            $classFiles = Get-ChildItem "$pkg\*.class" -ErrorAction SilentlyContinue
            if ($classFiles) {
                Write-Host "  ✓ $pkg : $($classFiles.Count) classes" -ForegroundColor Green
            } else {
                Write-Host "  ✗ $pkg : No classes found!" -ForegroundColor Red
            }
        }
        
        Write-Host "`nLibrary compilation complete!" -ForegroundColor Green
        Write-Host "You can now compile MySolution" -ForegroundColor Cyan
    } else {
        Write-Host "`n✗ Compilation failed!" -ForegroundColor Red
        Write-Host "Errors:" -ForegroundColor Red
        Write-Host $output
        exit 1
    }
    # Clean up temp file
    Remove-Item $tempFile -ErrorAction SilentlyContinue