SELECT *
FROM heroku_dc02d468f96562c.`group`
         JOIN account
WHERE account.idAccount = heroku_dc02d468f96562c.`group`.com.getjavajob.training.yakovleva.dao.Account
  AND idgroup = 11