SELECT *
FROM heroku_dc02d468f96562c.`group`
         JOIN account
WHERE account.idAccount = heroku_dc02d468f96562c.`group`.Account
  AND idgroup = 11