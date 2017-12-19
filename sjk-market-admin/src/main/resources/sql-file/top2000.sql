SELECT app.id as id,app.Name as name,app.Pkname as pkname,app.LastUpdateTime as lastUpdateTime FROM App app 
WHERE 
    app.pkname in (:ids)
AND app.VersionCode = (SELECT ap.VersionCode FROM App ap WHERE ap.Pkname = app.Pkname ORDER BY ap.VersionCode DESC limit 1)