# Framework, database
The project uses the Spring Boot framework, Spring Data JPA for data manipulation and the H2 database engine for data storage.

## Database
By default, the data is stored in-memory, but other storage methods offered by H2 are available. Storage method and location is defined by the ```spring.datasource.url``` property in the **application.properties** file.

H2 web console is also enabled by default and accessible at ```/console```. This can be disabled by setting ```spring.h2.console.enabled``` to false.

# Compiling, running, testing
Standard maven project:

**Compiling:**
```bash
mvn compile
```
**Running:**
```bash
mvn spring-boot:run
```
**Testing:**
```bash
mvn test
```

# REST API
The server runs at ```localhost:8080```. All data requested and returned is in JSON format. If an error occurs, the **error** and **message** properties are set. Message contains a human readable error message, while error can be one of the following:
* **data** - passed data is wrong. Ex. percentage sum doesn't equal 100
* **json** - malformed JSON or unexpected JSON format
* **not_found** - requested entity with passed identifier doesn't exist

## Data structures
The following data structures are defined:

### Income / Expense
Distribution can either be euqal among all users, percentage divided, or based on absolute values.
```
{
    amount: <double>,
    transaction_id: <long>,
    distribution: <string: equal|percentage|absolute>,
    transaction_id: <long>,
    users: *
}
```
If distribution is equal, **users** is an array of longs. Otherwise, it's an array of following objects:
```
{
    id: <long>,
    value: <double>
}
```
If the distribution is percentage based, value will be in the range [0, 100], with the sum of all values in the object totaling to 100. Should the distribution be absolute value based, the sum of the values will always equal to the amount of the income / expense.

### Transaction
A transaction can either be a money transfer from one user to another, or a general transaction, with any number of income and expense participants.
```
{
    id: <long>,
    type: <string: transfer|general>
    amount: <double>,
    income: <income>,
    expense: <expense>
}
```

### User
```
{
    id: <long>,
    name: <string>,
    balance: <double>,
    transactions: [<transactions>]
}
```

## Methods
The following methods are exposed:

### POST /user
Creates a new user in the system with initial balance at 0.

Expects:
```
{
    name: <string>
}
```

Returns a [user](#user) object.


### GET /user/{id}
Retrieves the user with the passed ID. Returns a [user](#user) object.

### GET /user/{id}/transactions
Retrieves the users transactions. Returns an array or [transaction](#transaction) objects.

### GET /user/{id}/incomes
Retrieves the users incomes. Returns an array of [income](#income-/-expense) objects.

### GET /user/{id}/expenses
Retrieves the users expenses. Returns an array of [expense](#income-/-expense) objects.

### POST /transaction
Creates a new transaction. Accepts two forms of requests.

Should the transaction be a simple transfer transaction, the method expects the following structure:
```
{
    type: <string: transfer>,
    from_user_id: <long>,
    to_user_id: <long>,
    amount: <double>
}
```

If the transaction is a general one, the structure is as follows:
```
{
    type: <string: general>,
    amount: <double>,
    expense: <expense>,
    income: <income>,
}
```

### GET /transaction/{id}
Retrieves the transaction with the passed ID. Returns a [transaction](#transaction) object.