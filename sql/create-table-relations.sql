CREATE TABLE relations
(
    account_id INT NOT NULL,
    friend_id  INT NOT NULL,
    PRIMARY KEY (account_id, friend_id),
    FOREIGN KEY (account_id)
        REFERENCES account (account_id) ON DELETE CASCADE,
    FOREIGN KEY (friend_id)
        REFERENCES account (account_id) ON DELETE CASCADE
)
