SELECT 
    downloadUrl, 
    versionCode, 
    name, 
    pkname, 
    size, 
    logoUrl , 
    pageUrl, 
    version,
    catalog, 
    subCatalog,
    lastUpdateTime,
    shortDesc,
    pathStatus
FROM App
WHERE 
    id in(?)
    and hidden = 0