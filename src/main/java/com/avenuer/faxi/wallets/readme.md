# Wallets Package.
The wallets package is responsible for updating the database and stores the transactional entry for tracking accordingly.  
The wallet is primary created for a user via an event and entries are transactional in the database which means either 
all the operations are successful, or it's reverted if any of the steps fails before committing in the database.
Majority of the transactional calls are via store procedures and stored in the resource folder.