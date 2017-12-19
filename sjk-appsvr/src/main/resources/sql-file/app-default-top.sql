SELECT   
    app.ID as id,
    app.Name  as name, 
    app.Logo, 
    app.DownLoadLink,
    app.Size,
    app.OneKeyPack,
    app.OneKeyPackSize,
    app.Version,
    -- 星级
    app.Rank AS starRating,
    app.HaveData
FROM 
    AndroidApp app
LEFT JOIN C_PaiHang paihang
ON  
    paihang.AppID = app.ID 
WHERE 
    paihang.typeID = ?
ORDER BY
    paihang.OrderID 
LIMIT ?