# Wallets Package.
The wallets package is responsible for updating the database and stores the transactional entry for tracking accordingly.  
The wallet is primary created for a user via an event and entries are transactional in the database which means either 
all the operations are successful, or it's reverted if any of the steps fails before committing in the database.
Majority of the transactional calls are via store procedures and stored in the resource folder.

## Wallet Fundamentals.
    - Wallet
    - Wallet Transaction
    - Virtual Nubans
    - Virtual Cards.

### Wallet.
The wallet model represents the actual state of a user accounts, how much the user is having, available (settled) and the total of all the funds.
A wallet is owned by the user with the corresponding ownerId, owner can either be a user or a business or legal entity.

### Wallet Transaction
This is just the historical account of a wallet, in accounting terms ledgers which have a credit and debit side. 
The credit sided transactions are transactions that triggers increase in the account balance while the debit causes a 
reduction in the account balance.

***
Example - Using a wallet with balances = 0; using the US Dollar ($) as the settlement currency. (As you are aware it can be in any currency format).
***

**Transactional Accounting Representation.**

| Debit | | | | Credit ||
| --- | ---|  ---| --- |--- |--- |
| 01/04/20 10:20 A:M |  NUBAN_TRANSFER | $50.00   |  02/04/20 02:30 P.M| WALLET_TRANSFER | $10 |
| 02/04/20 10:20 A:M |  WALLET_TRANSFER |$100.00 |  04/04/20 02:30 P.M| CARD_PAYMENT |   $20 |


**Wallet Transactions Table Entries**

| TIme | Description | LedgerSide | amount |
| --- | ---|  ---| --- |
| 01/04/20 10:20 A:M |  NUBAN_TRANSFER | DEBIT | $50.00 | 
| 01/04/20 10:20 A:M |  WALLET_TRANSFER | DEBIT |$100.00 |
|  02/04/20 02:30 P.M| WALLET_TRANSFER | CREDIT | $10.00 |
|  04/04/20 02:30 P.M| CARD_PAYMENT | CREDIT |  $20.00 |

**Wallet Timeline**

| Time | pre-Update Amount | update Amount | Post Update Amount |
| --- | ---|  ---| --- |
| 01/04/20 10:20 A:M | $0 | + $50.00 | $50.00
| 01/04/20 10:20 A:M | $50.00 | + $100.00 |  $150.00 |
|  02/04/20 02:30 P.M| $150 | CREDIT | $10.00 |
|  04/04/20 02:30 P.M| CARD_PAYMENT | CREDIT |  $20.00 

### Wallet Update Procedures: (Oyegoke Abiodun).
This section discuss, majorly about how and the recommended procedures for updating a wallet. Please note:
Assume a wallet can be update so frequently as up to 24 Million times per seconds.
To avoid stories that peirce the hearts and lost money especially in cryptocurrency environments 
where there are no reversible transactions allowed, DUE DILIGENCE is a must.
A merchant for every wallet update triggered by their load customers, and the updates might also 
include sub-settlements accounts for the funds of their users.

General Concepts.
The principles that we used to evaluate this ideas below, more importantly how and when to use them,
with implementation cases from the code base.
* Transactional Updates (Database Transactions).
* Pessimistic and Optimistic locking.

