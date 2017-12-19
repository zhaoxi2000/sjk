SELECT
	MarketName as marketName,
	Catalog as catalog,
	SubCatalog as subCatalog,
	SubCatalogName as subCatalogName
FROM
	MarketApp
WHERE 
    MarketName  = ?
GROUP BY
	MarketName,
	Catalog,
	SubCatalog,
	SubCatalogName
having Catalog != 0 and SubCatalog !=0 
	