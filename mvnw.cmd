@REM Maven Wrapper batch script for Windows
@REM Downloads Maven if not present and runs it

@echo off
setlocal

set MAVEN_VERSION=3.9.16
set MAVEN_URL=https://archive.apache.org/dist/maven/maven-3/%MAVEN_VERSION%/binaries/apache-maven-%MAVEN_VERSION%-bin.zip
set MAVEN_HOME=%USERPROFILE%\.m2\wrapper\apache-maven-%MAVEN_VERSION%
set MVN_CMD=%MAVEN_HOME%\bin\mvn.cmd

if exist "%MVN_CMD%" goto runMaven

echo Maven %MAVEN_VERSION% non trouve. Telechargement en cours...
mkdir "%MAVEN_HOME%\.." 2>nul

powershell -Command "Invoke-WebRequest -Uri '%MAVEN_URL%' -OutFile '%TEMP%\maven.zip'"
powershell -Command "Expand-Archive -Path '%TEMP%\maven.zip' -DestinationPath '%MAVEN_HOME%\..' -Force"
del "%TEMP%\maven.zip" 2>nul

if not exist "%MVN_CMD%" (
    echo ERREUR : Maven n'a pas pu etre installe.
    exit /b 1
)
echo Maven %MAVEN_VERSION% installe dans %MAVEN_HOME%

:runMaven
"%MVN_CMD%" %*
