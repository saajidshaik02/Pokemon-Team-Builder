param(
    [switch]$SkipInstall,
    [switch]$NoBrowser
)

$repoRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
$backendPath = Join-Path $repoRoot 'backend'
$frontendPath = Join-Path $repoRoot 'frontend'
$backendUrl = 'http://localhost:8080'
$frontendUrl = 'http://127.0.0.1:4173'

function Test-PortInUse {
    param(
        [int]$Port
    )

    $connection = Get-NetTCPConnection -LocalPort $Port -State Listen -ErrorAction SilentlyContinue |
        Select-Object -First 1

    return $null -ne $connection
}

function Test-FrontendDevProcess {
    param(
        [string]$FrontendPath
    )

    $escapedPath = [Regex]::Escape($FrontendPath)
    $process = Get-CimInstance Win32_Process -ErrorAction SilentlyContinue |
        Where-Object {
            $_.CommandLine -and
            $_.CommandLine -match $escapedPath -and
            (
                $_.CommandLine -match 'npm(\.cmd)?\s+run\s+dev' -or
                $_.CommandLine -match 'vite'
            )
        } |
        Select-Object -First 1

    return $null -ne $process
}

function Open-UrlInBrowser {
    param(
        [string]$Url
    )

    $chromeCommand = Get-Command chrome.exe -ErrorAction SilentlyContinue
    if ($chromeCommand) {
        Start-Process $chromeCommand.Source -ArgumentList $Url
        return
    }

    $chromePaths = @(
        "$env:ProgramFiles\Google\Chrome\Application\chrome.exe",
        "${env:ProgramFiles(x86)}\Google\Chrome\Application\chrome.exe",
        "$env:LocalAppData\Google\Chrome\Application\chrome.exe"
    )

    $chromePath = $chromePaths | Where-Object { Test-Path $_ } | Select-Object -First 1
    if ($chromePath) {
        Start-Process $chromePath -ArgumentList $Url
        return
    }

    Start-Process $Url
}

Write-Host 'Starting Pokemon Team Builder local development environment...' -ForegroundColor Cyan
Write-Host "Backend:  $backendUrl" -ForegroundColor DarkGray
Write-Host "Frontend: $frontendUrl" -ForegroundColor DarkGray

if (-not $SkipInstall) {
    Write-Host 'Checking frontend dependencies with npm install...' -ForegroundColor Yellow
    Push-Location $frontendPath
    try {
        npm install
    }
    finally {
        Pop-Location
    }
}

$backendCommand = "Set-Location '$backendPath'; mvn spring-boot:run"
$frontendCommand = "Set-Location '$frontendPath'; npm run dev"

$backendRunning = Test-PortInUse -Port 8080
$frontendPortRunning = Test-PortInUse -Port 4173
$frontendProcessRunning = Test-FrontendDevProcess -FrontendPath $frontendPath
$frontendRunning = $frontendPortRunning -or $frontendProcessRunning

if ($backendRunning) {
    Write-Host 'Backend port 8080 is already in use. Skipping backend startup.' -ForegroundColor Yellow
}
else {
    Start-Process powershell -ArgumentList @(
        '-NoExit',
        '-Command',
        $backendCommand
    )
}

if (-not $backendRunning -and -not $frontendRunning) {
    Start-Sleep -Seconds 2
}

if ($frontendRunning) {
    Write-Host 'Frontend dev server is already running or port 4173 is in use. Skipping frontend startup.' -ForegroundColor Yellow
}
else {
    Start-Process powershell -ArgumentList @(
        '-NoExit',
        '-Command',
        $frontendCommand
    )
}

if (-not $NoBrowser) {
    Start-Sleep -Seconds 3
    Open-UrlInBrowser -Url $frontendUrl
}

Write-Host ''
Write-Host 'Local development status:' -ForegroundColor Green
Write-Host "- Backend available on $backendUrl" -ForegroundColor Green
Write-Host "- Frontend available on $frontendUrl" -ForegroundColor Green
Write-Host ''
Write-Host "Swagger UI: $backendUrl/swagger-ui.html" -ForegroundColor Cyan
Write-Host "App URL:    $frontendUrl" -ForegroundColor Cyan
