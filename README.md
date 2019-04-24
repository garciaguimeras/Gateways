# Gateways

A simple Spring Boot project

## Requirements

* Maven
* MySQL

## Database

Default database settings are:

* Server: localhost:3306
* Database name: gateways
* Username: root
* Password: root

Default database settings can be changed on file **application.properties**

## Build, test and deploy

* Recompile and package

    ```bash
    mvn clean package
    ```

* Run unit tests

    ```bash
    mvn test
    ```

* Deploy on server localhost:8080

    ```bash
    mvn spring-boot:run
    ```

## API

### GET api/v1/gateways

Gets all gateways.

Parameters:

* No parameters needed

Returns:

* A list of gateway objects

### GET api/v1/gateways/get/{serial number}

Gets a gateway by serial number.

Parameters:

* serial number: Path parameter. The gateway serial number

Returns:

* The found gateway object, or null

### POST api/v1/gateways/add

Adds a new gateway

Parameters:

* serialNumber: The gateway serial number
* name: The gateway name
* ipv4: The gateway ip

Returns:

* A result object:
  * status: **OK** or **ERROR**
  * message: The error message, if needed

### GET api/v1/gateways/remove/{serial number}

Removes a gateway by serial number.

Parameteres:

* serial number: Path parameter. The gateway serial number

Returns:

* A result object:
  * status: **OK** or **ERROR**
  * message: The error message, if needed

### POST api/v1/gateways/add-device

Adds a new device to a gateway

Parameters:

* serialNumber: The gateway serial number
* uid: The device uid
* vendor: The device vendor
* state: The device state (allowed values: **ONLINE** | **OFFLINE**)

Returns:

* A result object:
  * status: **OK** or **ERROR**
  * message: The error message, if needed

### GET api/v1/gateways/remove-device/{uid}

Removes a device by uid.

Parameteres:

* uid: Path parameter. The device uid

Returns:

* A result object:
  * status: **OK** or **ERROR**
  * message: The error message, if needed