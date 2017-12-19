SELECT 
    x.pics AS pics, x.tabname, x.url,  x.type, x.name
FROM Metro x
WHERE 
    x.hidden = 0 and deleted = 0 