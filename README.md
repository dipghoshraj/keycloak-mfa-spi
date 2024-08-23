# Keycloak Magic Link SPI

This repository contains a custom Keycloak SPI (Service Provider Interface) implementation for magic link authentication. This SPI allows users to authenticate via a magic link sent to their email, providing a seamless and password-less login experience

## Features
- Magic Link Authentication: Send a magic link to the user's email, which they can use to log in without a password
- Custom Action Token: Securely encapsulate user information in an action token that is embedded in the magic link.
- Email Template Integration: Customize the email sent to users with the magic link.


## Prerequisites
- Java 17 or higher
- Maven (for building the project)
- Keycloak 10 or higher (tested with version 15 and above)


## Installation
1. Clone the Repository
```
git clone https://github.com/dipghoshraj/keycloak-mfa-spi.git
cd keycloak-mfa-spi
```
2. Build the Project
Use Maven to build the project and generate a JAR file.
``` mvn clean package 
```
3. Deploy the SPI in keycloak
Update the file name in the dockerfile and then run
```
docker compose up --build
```
