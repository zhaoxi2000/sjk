SELECT
    x.ID AS id,
    -- PID AS parentId,
    x.Name AS name,
    x.Description AS description , 
    COUNT(y.ID) AS totalCount
FROM Catalog x
LEFT JOIN AndroidApp y
    ON x.PID = y.Catalog AND x.ID = y.SubCatalog
WHERE 
    x.PID = ? AND y.Catalog = ?
GROUP BY x.ID
ORDER BY OrderId