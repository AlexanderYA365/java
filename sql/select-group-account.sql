SELECT *
FROM heroku_dc02d468f96562c.`group`
         JOIN account
WHERE account.account_id = heroku_dc02d468f96562c.`group`.com.getjavajob.training.yakovleva.dao.Account
  AND group_id = 11