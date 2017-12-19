SELECT
    y.Id, 
    y.Name, 
    y.Logo, 
    y.Size, 
    y.downloadLink,
    y.PKName AS packageName, 
    x.ShortDesc, 
    x.status, 
    y.Rank AS starRating, 
    y.Version
FROM AppMgr.PowerTuiJian x
LEFT JOIN AppMgr.AndroidApp y
ON x.AppId = y.Id
WHERE
    Catalog = ?
ORDER BY x.rank
LIMIT ?, ?