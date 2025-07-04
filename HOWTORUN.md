# Prerequisites
Java JDK 24
Maven 3.3.9
Docker installed

# How to build and run app
docker build . 
docker compose up -d

# Or just simplify
docker build . && docker compose up -d

# Log
docker logs -f cinema-booking-app

