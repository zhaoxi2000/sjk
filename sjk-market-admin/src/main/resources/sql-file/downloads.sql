SELECT SUM(downloads)  AS marketAppDown, marketName
FROM MarketApp
WHERE 
    pkname = ?
GROUP BY marketname
