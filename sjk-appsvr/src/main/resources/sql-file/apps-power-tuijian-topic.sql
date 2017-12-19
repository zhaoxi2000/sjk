SELECT
    apps.Id,
    apps.Name, 
    apps.Logo, 
    apps.Size,
    apps.downloadLink,
    apps.PKName AS packageName, 
    topic_apps.`desc` AS ShortDesc, 
    topic_apps.status, 
    apps.Rank AS starRating, 
    apps.Version
FROM AppMgr.topic_app topic_apps 
LEFT JOIN AppMgr.AndroidApp apps 
ON topic_apps.app_id = apps.Id 
WHERE topic_apps.topic_id = ? 
ORDER BY topic_apps.rank 
LIMIT ?, ?