#!/bin/bash

echo "============================="
echo "   Iniciando Bucher..."
echo "============================="

# Obtener el directorio del script
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR" || exit

# --- BACKEND ---
echo "Iniciando backend..."
cd backend || exit
gnome-terminal -- bash -c "./gradlew bootRun; exec bash" 2>/dev/null \
|| x-terminal-emulator -e bash -c "./gradlew bootRun; exec bash" 2>/dev/null \
|| bash -c "./gradlew bootRun" &
cd ..

# --- FRONTEND ---
sleep 5
echo "Iniciando frontend..."
cd frontend || exit
gnome-terminal -- bash -c "npm run dev; exec bash" 2>/dev/null \
|| x-terminal-emulator -e bash -c "npm run dev; exec bash" 2>/dev/null \
|| bash -c "npm run dev" &
cd ..

echo "============================="
echo "Proyecto Bucher en ejecución."
echo "============================="

read -p "Presioná Enter para salir..."