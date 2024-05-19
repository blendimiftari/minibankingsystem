# Create a Bank to create a bank, send a POST request to this endpoint:  http://localhost:8080/banks/create
{
"name": "Test",
"flatFee": 2.5,
"percentFee": 1.0
}
# Create an Account to create an account, send a POST request to this endpoint:http://localhost:8080/accounts/create
{
"bankId": 1,
"name": "blendi",
"balance": 1000.0
}
{
"bankId": 1,
"name": "eltoni",
"balance": 1000.0
}
# Perform a Transaction to perform a transaction, send a POST request to this endpoint: http://localhost:8080/accounts/transaction
{
"originatingAccountId": 1,
"resultingAccountId": 2,
"amount": 100.0,
"reason": "Payment",
"isFlatFee": false
}
# To withdraw money from an account, send a POST request to this endpoint: http://localhost:8080/accounts/withdraw
{
"accountId": 1,
"amount": 50.0
}
# To deposit money to an account, send a POST request to this endpoint:http://localhost:8080/accounts/deposit
{
"accountId": 1,
"amount": 150.0
}
# To see the list of transactions for an account, send a GET request to this endpoint: http://localhost:8080/accounts/{accountId}/transactions

# To check the account balance, send a GET request to the following endpoint: http://localhost:8080/accounts/{accountId}
