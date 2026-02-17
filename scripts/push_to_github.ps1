# PowerShell script to push repository to https://github.com/VihanTumbal/java-selenium.git on branch CapstoneProject_5
# Usage: Open an elevated (or normal) PowerShell, cd to the repo root, then: .\scripts\push_to_github.ps1

Set-StrictMode -Version Latest
Set-Location $PSScriptRoot\..  # change to repo root (scripts/..)

if (-not (Get-Command git -ErrorAction SilentlyContinue)) {
    Write-Error 'Git is not installed. Install Git from https://git-scm.com/downloads and retry.'
    exit 1
}

$desired = 'https://github.com/VihanTumbal/java-selenium.git'

# check repo
git rev-parse --is-inside-work-tree 2>$null
if ($LASTEXITCODE -ne 0) {
    Write-Host 'Not a git repository — initializing repository (git init).'
    git init
} else {
    Write-Host 'Inside an existing git repository.'
}

# ensure a remote pointing to the desired repo exists; prefer origin if it matches
$remoteName = $null
$originUrl = $null
try { $originUrl = git remote get-url origin 2>$null } catch { $originUrl = $null }

if ($originUrl) {
    if ($originUrl.Trim() -eq $desired) {
        Write-Host "Using existing remote 'origin' -> $originUrl"
        $remoteName = 'origin'
    } else {
        Write-Host "Remote 'origin' exists and points to a different URL: $originUrl"
        $capUrl = $null
        try { $capUrl = git remote get-url capstone 2>$null } catch { $capUrl = $null }
        if ($capUrl) {
            Write-Host "Remote 'capstone' already exists -> $capUrl; using 'capstone'."
            $remoteName = 'capstone'
        } else {
            Write-Host "Adding remote 'capstone' -> $desired"
            git remote add capstone $desired
            if ($LASTEXITCODE -ne 0) { Write-Error "Failed to add remote 'capstone'."; exit 1 }
            $remoteName = 'capstone'
        }
    }
} else {
    Write-Host "No 'origin' remote found — adding 'origin' -> $desired"
    git remote add origin $desired
    if ($LASTEXITCODE -ne 0) { Write-Error "Failed to add remote 'origin'."; exit 1 }
    $remoteName = 'origin'
}

Write-Host "Selected remote: $remoteName"

# commit only if there are staged changes
$staged = (git diff --name-only --cached) -join "`n"
if ($staged -and $staged.Trim() -ne '') {
    Write-Host "Staged files detected:`n$staged"
    git commit -m "Commit staged changes"
    if ($LASTEXITCODE -ne 0) { Write-Error "Commit failed. Resolve conflicts or amend staged files and retry."; exit 1 }
} else {
    Write-Host "No staged changes detected — skipping commit."
}

# create or switch to branch CapstoneProject_5
$branch = 'CapstoneProject_5'
# check if local branch exists
git show-ref --verify --quiet refs/heads/$branch
if ($LASTEXITCODE -eq 0) {
    Write-Host "Switching to existing local branch '$branch'."
    git checkout $branch
} else {
    Write-Host "Creating and switching to new branch '$branch'."
    git checkout -b $branch
    if ($LASTEXITCODE -ne 0) { Write-Error "Failed to create branch '$branch'."; exit 1 }
}

# push to the selected remote (with upstream)
Write-Host "Pushing branch '$branch' to remote '$remoteName'..."
git push -u $remoteName $branch
if ($LASTEXITCODE -ne 0) {
    Write-Host "\nPush failed. Common causes and recommended actions:"
    Write-Host "1) Authentication required or denied:"
    Write-Host "   - If using HTTPS: create a GitHub Personal Access Token (PAT) and use it as the password when prompted."
    Write-Host "   - Configure credential helper: 'git config --global credential.helper manager-core' (Windows)."
    Write-Host "   - Or switch remote to SSH and add your public key to GitHub, then 'git remote set-url $remoteName git@github.com:VihanTumbal/java-selenium.git'."
    Write-Host "2) Remote rejects push (branch protection): check repository settings on GitHub; create a PR from the pushed branch (or ask repo admin)."
    Write-Host "3) Network or proxy blocking git traffic: verify your network or try another network."
    Write-Host "4) Remote not reachable or incorrect URL: verify remote with 'git remote -v' and correct with 'git remote set-url $remoteName <url>'."
    exit 1
} else {
    Write-Host "Push succeeded. Branch '$branch' is now on '$remoteName'."
    exit 0
}

