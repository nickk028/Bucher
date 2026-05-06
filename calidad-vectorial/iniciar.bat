@echo off
title Iniciar proyecto Bucher
cd /d "%~dp0"
echo =============================
echo    Iniciando Bucher...
echo =============================

REM --- BACKEND ---
echo Iniciando backend...
cd backend
start cmd /k "gradlew bootRun"
cd ..

REM --- FRONTEND ---
timeout /t 5 /nobreak >nul
echo Iniciando frontend...
cd frontend
start cmd /k "npm run dev"
cd ..

echo =============================
echo Ejecutando Proyecto Bucher
echo =============================
pause