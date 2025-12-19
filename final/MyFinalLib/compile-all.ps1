# Compile all Java files in MyFinalLib
Write-Host "Compiling all MyFinalLib packages..." -ForegroundColor Green

# Get all Java files
$javaFiles = Get-ChildItem -Recurse -Filter "*.java" -File

if ($javaFiles.Count -eq 0) {
    Write-Host "No Java files found!" -ForegroundColor Red
    exit 1
}

Write-Host "Found $($javaFiles.Count) Java files" -ForegroundColor Cyan

# Create temp file with all Java file paths
$tempFile = New-TemporaryFile
$javaFiles | ForEach-Object { $_.FullName } | Out-File -FilePath $tempFile.FullName -Encoding ASCII

# Compile all files
Write-Host "Compiling..." -ForegroundColor Yellow
$output = & javac -cp ".\..\." "@$($tempFile.FullName)" 2>&1

# Clean up
Remove-Item $tempFile -ErrorAction SilentlyContinue

# Check result
if ($LASTEXITCODE -eq 0) {
    Write-Host "Compilation successful!" -ForegroundColor Green
    $classCount = (Get-ChildItem -Recurse -Filter "*.class" -File).Count
    Write-Host "Generated $classCount .class files" -ForegroundColor Green
    exit 0
} else {
    Write-Host "Compilation failed!" -ForegroundColor Red
    Write-Host $output
    exit 1
}
