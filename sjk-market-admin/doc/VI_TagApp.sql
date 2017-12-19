create
VIEW `VI_TagApps` AS 
select `appTag`.`Id` AS `Id`,`appTag`.`AppId` AS `AppId`,`app`.`MarketName` AS `MarketName`,`app`.`Name` AS `AppName`,
`appTag`.`TagId` AS `TagId`,`tag`.`Catalog` AS `Catalog`,`app`.`SubCatalog` AS `AppCatalog`,`app`.`LogoUrl` AS `LogoUrl`,
`appTag`.`Rank` AS `Rank`,`appTag`.`TagName` AS `TagName`,`app`.`ShortDesc` AS `ShortDesc`,`appTag`.`TagType` AS `TagType`
from ((`App` `app` join `AppAndTag` `appTag` on((`app`.`Id` = `appTag`.`AppId`))) 
join `Tag` `tag` on((`appTag`.`TagId` = `tag`.`Id`)))
ORDER BY
tag.Rank DESC ;
