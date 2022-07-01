# cinema-tickets

A simple API which integrates with a third party service to reserve and pay for cinema tickets. 

Built in Java, using Spring Boot framework.

## Getting Started
Follow these instructions to run the application locally.

## Step 1 - Clone the repo

Clone this repo;

```bash
git clone https://github.com/clo2202/cinema-tickets.git
```

## Step 2 - Getting started with your project

Navigate to the root of the repo and ensure you have downloaded all of the dependencies using the following command;

```bash
mvn install

# This will download all the dependencies required to work with this project.
```

## Running the tests

To run the tests simply enter the following command;

```bash
mvn test

# Will run all tests within the project. Testing includes checks to make sure the endpoint is returning 
# the correct response. For example . . .

String requestBody = "{\"accountId\": 1, \"ticketTypeRequests\": [{\"noOfTickets\": 2, \"type\": \"ADULT\"}]}";

mvc.perform(post("/book-tickets").content(requestBody)
              .contentType(MediaType.APPLICATION_JSON)
              .accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.status").value(200))
              .andExpect(jsonPath("$.message").value("Successfully reserved ticket(s) for account id: 1"));

```

## Deployment

```bash
mvn spring-boot:run

# This will run the application on http://localhost:9090
```
Request can be made via http only

## API Specification 

```postman
POST /book-tickets
```

**Request body**

```javascript
{
    "accountId": {{accountId}},
    "ticketTypeRequests": [
    {
        "noOfTickets": {{noOfTickets}},
        "type": {{type}}
    },
    {
        "noOfTickets": {{noOfTickets}},
        "type": {{type}}
    }]
}
```
With the following body parameters

| Parameter          | Type         | Required? | Description                                                           |
|--------------------|--------------|-----------|-----------------------------------------------------------------------|
| accountId          | int          | Y         | unique accountId, must be greater than 0                              |
| ticketTypeRequests | object array | Y         | a List of ticketTypeRequests, detailing number and type of ticket(s)  |
| noOfTickets        | int          | Y         | number of tickets required per type                                   |
| type               | string       | Y         | type can be ADULT, CHILD or INFANT                                    |

**Successful response**

Example success response

```javascript
{
    "status": 200,
    "message": "Successfully reserved ticket(s) for account id: {{accountId}}"
}
```

**Error responses**

The application will return a 400 BAD_REQUEST response in the following scenarios;

* No accountId provided
* accountId is not greater than 0
* No ticketTypeRequests in the request body
* No noOfTickets provided
* No type of ticket provided
* Number of total tickets is greater than 20
* An ADULT ticket type is not present in the request

Example error response

```javascript
{
    "status": 400,
    "message": "Request validation failed",
    "errors": [
        "account id cannot be null"
    ]
}
```

## Authors

- **Chloe Williams** - [clo2202](https://github.com/clo2202)
